package controllers;

import play.templates.TemplateLoader;
import play.templates.Template;
import play.mvc.*;

public class Label extends Controller{
	
	public static void test(){
		Template t = TemplateLoader.load("label/drive.html")
	}
	
}