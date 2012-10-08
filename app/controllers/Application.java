package controllers;

import java.util.*;
import models.*;
import play.mvc.*;

public class Application extends Controller {

    public static void index() {
        List<Item> items = Item.findAll();
        renderTemplate("application/index.html" , items );
    }    
}