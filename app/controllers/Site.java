/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import play.mvc.Controller;

/**
 * Non secure site controller.
 * @author daviddale
 */
public class Site extends Controller {
    
    /**
     * This method is the for pulling up the home page of the non-secure
     * drive-cleaners site.
     */
	public static void index(){
        renderTemplate("site/index.html");
    }
    
}
