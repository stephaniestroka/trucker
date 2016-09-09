package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


public class GmapsController extends Controller{

    public Result map() {
         return ok(gmaps.render());
    }

}
