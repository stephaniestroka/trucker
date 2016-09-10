package controllers;

import com.google.inject.Inject;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;
import services.GmapService;
import utilities.Location;

import java.util.ArrayList;
import views.html.*;


public class GmapsController extends Controller{

    private GmapService gmapService;

    @Inject
    public GmapsController(GmapService gmapService) {
        this.gmapService = gmapService;
    }

    public Result map() {
         return ok(gmaps.render());
    }

    public Result javascriptRoutes() {
        return ok(
                JavaScriptReverseRouter.create("gmapsJsRoutes",
                        (routes.javascript.GmapsController.newItinerary()),
                        (routes.javascript.GmapsController.nextLocation())
                )
        ).as("text/javascript");
    }

    public Result newItinerary() {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location(48.262551, 11.658352));
        locations.add(new Location(48.261859, 11.662859));
        locations.add(new Location(48.261295, 11.669886));
        locations.add(new Location(48.266204, 11.671545));
        locations.add(new Location(48.266997, 11.669898));
        locations.add(new Location(48.267408, 11.666502));
        locations.add(new Location(48.267451, 11.662232));
        locations.add(new Location(48.263819, 11.658699));

        gmapService.makeNewItinerary(locations);

        return ok(Json.newObject().put("code", 200).put("message", "A new Itinerary has been made"));
    }

    public Result nextLocation() {
        if(!gmapService.hasItinerary()) {
            return ok(Json.newObject().put("code", 400).put("message", "There's no itinerary"));
        } else if (gmapService.getItinerary().isCompleted()) {
            return ok(Json.newObject().put("code", 200).put("message", "Itinerary is completed"));
        }

        Location location = gmapService.getItinerary().nextLocation();
        return  ok(Json.toJson(location));
    }
}
