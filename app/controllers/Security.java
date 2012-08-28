/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author daviddale
 */
public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        //User user = User.find("byEmail", username).first();
        //return user != null && user.password.equals(password);
        return true;
    }    
    static boolean check(String profile) {
        /*User user = User.find("byEmail", connected()).first();
        if ("administrator".equals(profile)) {
            return user.admin;
        }
        else {
            return false;
        }*/
        return true;
    }       
    
}
