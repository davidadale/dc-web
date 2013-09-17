/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.*;
import play.libs.*;
import org.apache.commons.mail.*;
import notifiers.*;
/**
 * The security extension point to handle 
 * authenticating users.
 * @author daviddale
 */
public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        User user = User.find("byEmail", username).first();
        return ( user != null) && ( user.password.equals(password) );        
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
    
    public static void forgotPassword(){
        renderTemplate("Secure/forgot-pw.html");
    }

    public static void sendPasswordReminder(String email ) throws Throwable{
        User user = User.find("byEmail", email ).first();
        if( user== null ){
            Customer customer = Customer.find("byEmail", email).first();
            if( customer!=null ){
                Mails.verifyAccount( customer );                    
                flash.success("Account has not yet been verified. Please check your email and verify the account to create password.");
                Secure.login();
            }else{
                flash.error("Email was not found in the system. Please sign up.");
                Orders.cancel();                
            }


        }else{            
            Mails.forgotPassword( user );
            flash.success("Check your email for your password.");
            Secure.login();
        }

    }

    public static void changePassword( User user ){
        renderTemplate("Secure/changePassword.html", user );
    }

    public static void updatePassword( String token, String password, String confirm ){
        if( password!=null && password.equals( confirm ) ){
            User user = User.find("byVerificationToken", token ).first();
            System.out.println ("User is false 2: " + (user==null) );
            if( user!=null ){
                user.password = password;
                user.save();
                session.put( "username",user.email );
                onAuthenticated();
            }
        }

    }

    static void signInAs( User user  ){
        session.put( "username",user.email );
        onAuthenticated();
    }

    static void onAuthenticated(){
        User user = User.find( "byEmail",connected() ).first();
        if( user.isAdmin ){
            Admin.index();
        }else{
            Client.index();
        } 
    }

    static void onDisconnected(){
        Site.index();
    } 
    
}
