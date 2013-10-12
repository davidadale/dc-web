var orderMgmt = angular.module('order-management',['ngTable','ngResource']);

orderMgmt.controller('OrderManagerController',function($scope, $filter, $resource, ngTableParams){
    $scope.data = []
    var API


    $scope.init = function( id ){
        API = $resource( "/admin/order/:id/items", {id: id} )
    }

    $scope.tableParams = new ngTableParams({
        page: 1,            // show first page
        total: 0, //$scope.data.length, // length of data
        count: 10,          // count per page
        sorting: {
            foo: 'asc'     // initial sorting
        }
    });

    // watch for changes of parameters
    $scope.$watch('tableParams', function(params) {

        $scope.loading = true;
        API.get(  params.url() , function(json){
                $scope.loading = false;
                $scope.items = json.data
                $scope.tableParams.total = json.resultSize
            })
    }, true);

})


orderMgmt.directive('dropzone', function() {
    return {
        restrict: 'EA',
        link: function(scope, el, attrs) {
            el.dropzone({
                url: attrs.url,
                maxFilesize: attrs.maxsize,
                init: function() {

                    this.on('success', function(file, json) {
                        //alert( json.data[0].CamelAwsS3Key )
                        scope.$apply(function(){
                            for(var i in json.data ){
                                scope.items.push({foo: json.data[i].filename });
                            }
                        });
                    });

                    /*this.on('addedfile', function(file) {
                        scope.$apply(function(){
                            scope.items.push({foo: 'added'});
                        });
                    });*/

                }
            })
        }
    }
});



var orderApp = angular.module('order-form', []);
var FLOAT_REGEXP = /^\-?\d+((\.|\,)\d+)?$/;

//var VISA = /^4[0-9]{12}(?:[0-9]{3})?$/
var VISA = /^4[0-9]{3}/ // modified to make it open so it doesn't switch back and forth between 12 and 16
var MASTER = /^5[1-5][0-9]{14}$/ 
var AMERICAN = /^3[47][0-9]{13}$/
var DISCOVER = /^6(?:011|5[0-9]{2})[0-9]{12}$/

orderApp.directive('ccExpiry',function(){
  return { 
    link: function(scope,elm,attrs,ctrl){      
      
      elm.mask("99/99")
      elm.bind('keyup', parse)

      function parse(){
        var vals = elm.val().split("/")
        scope.expireMonth = vals[0]
        scope.expireYear = vals[1]
        scope.$apply()
      }
    }
  }
})

orderApp.directive('ccNumber', function() {
  return {
    require: 'ngModel',
    link: function(scope, elm, attrs, ctrl) {

        elm.mask("9999 9999 9999 9999",{completed: function() {
              ctrl.$setViewValue( this.val().replace( /\s/gi,'') );              
              scope.$apply();
        }});           


      ctrl.$parsers.unshift(function(viewValue) {

        ctrl.$setValidity('cc', false); 
        ctrl.$setValidity('visa', false); 
        ctrl.$setValidity('master', false); 
        ctrl.$setValidity('amex', false); 
        ctrl.$setValidity('discover', false); 

        if( VISA.test(viewValue) ){
          ctrl.$setValidity('cc', true); 
          ctrl.$setValidity('visa', true);          
          return viewValue
        }else if( MASTER.test(viewValue) ){
          ctrl.$setValidity('cc', true); 
          ctrl.$setValidity('master', true);          
          return viewValue
        }else if( AMERICAN.test(viewValue) ){
          ctrl.$setValidity('cc', true); 
          ctrl.$setValidity('amex', true);          
          return viewValue
        }else if( DISCOVER.test(viewValue) ){
          ctrl.$setValidity('cc', true); 
          ctrl.$setValidity('discover', true);          
          return viewValue
        }else{
          ctrl.$setValidity('cc', false);
          return undefined
        }

        
        

      });
    }
  };
});

orderApp.controller('OrderController', function($scope) {
    $scope.ccnumber = ''
    $scope.expireMonth = '05'
    $scope.expireYear = '13'
});