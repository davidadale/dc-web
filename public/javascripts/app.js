var orderApp = angular.module('order-form', []);
var FLOAT_REGEXP = /^\-?\d+((\.|\,)\d+)?$/;

//var VISA = /^4[0-9]{12}(?:[0-9]{3})?$/
var VISA = /^4[0-9]{3}/ // modified to make it open so it doesn't switch back and forth between 12 and 16
var MASTER = /^5[1-5][0-9]{14}$/ 
var AMERICAN = /^3[47][0-9]{13}$/
var DISCOVER = /^6(?:011|5[0-9]{2})[0-9]{12}$/


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
});