package controllers;

import com.google.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;
import services.GmapService;
import utilities.Coordinate;
import views.html.*;

import java.util.ArrayList;


public class GmapsController extends Controller{

    private GmapService gmapService;

    @Inject
    public GmapsController(GmapService gmapService) {
        this.gmapService = gmapService;
    }

    public Result map() {
         return ok(gmaps.render());
    }

    public Result testGet() {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(-35.27801,149.12958));
        coordinates.add(new Coordinate(-35.28032,149.12907));
        coordinates.add(new Coordinate(-35.28099,149.12929));
        coordinates.add(new Coordinate(-35.28144,149.12984));

        return ok(gmapService.runSnapToRoad(coordinates));
    }

}
