package controllers;

import com.google.inject.Inject;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;
import services.GmapService;
import utilities.Location;

import java.util.ArrayList;
import java.util.List;

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

        gmapService.makeNewItinerary(getDemoLocations());
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

    private List<Location> getDemoLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location(48.26712,11.6688));
        locations.add(new Location(48.26732,11.66718));
        locations.add(new Location(48.26754,11.66527));
        locations.add(new Location(48.26794,11.6627));
        locations.add(new Location(48.26747,11.66211));
        locations.add(new Location(48.26625,11.66107));
        locations.add(new Location(48.26364,11.65898));
        locations.add(new Location(48.26277,11.65826));
        locations.add(new Location(48.26228,11.65788));
        locations.add(new Location(48.26149,11.65732));
        locations.add(new Location(48.25963,11.6559));
        locations.add(new Location(48.25846,11.65497));
        locations.add(new Location(48.25843,11.6548));
        locations.add(new Location(48.25833,11.65472));
        locations.add(new Location(48.2582,11.65478));
        locations.add(new Location(48.25749,11.6545));
        locations.add(new Location(48.2568,11.6542));
        locations.add(new Location(48.25614,11.65403));
        locations.add(new Location(48.25471,11.65386));
        locations.add(new Location(48.25269,11.65368));
        locations.add(new Location(48.24998,11.65314));
        locations.add(new Location(48.24966,11.65302));
        locations.add(new Location(48.24952,11.65282));
        locations.add(new Location(48.24935,11.65226));
        locations.add(new Location(48.24893,11.65132));
        locations.add(new Location(48.24927,11.65079));
        locations.add(new Location(48.24992,11.64502));
        locations.add(new Location(48.25095,11.64013));
        locations.add(new Location(48.24988,11.63606));
        locations.add(new Location(48.24971,11.63548));
        locations.add(new Location(48.25023,11.63463));
        locations.add(new Location(48.25025,11.63328));
        locations.add(new Location(48.25119,11.63277));
        locations.add(new Location(48.25218,11.63238));
        locations.add(new Location(48.25252,11.63157));
        locations.add(new Location(48.25469,11.63095));
        locations.add(new Location(48.26053,11.63299));
        locations.add(new Location(48.26246,11.63616));
        locations.add(new Location(48.26373,11.64186));
        locations.add(new Location(48.26415,11.64526));
        locations.add(new Location(48.26402,11.65012));
        locations.add(new Location(48.26261,11.65765));
        locations.add(new Location(48.26218,11.66036));
        locations.add(new Location(48.26115,11.66908));
        locations.add(new Location(48.2637,11.67083));
        locations.add(new Location(48.26633,11.6716));
        locations.add(new Location(48.26707,11.66915));

        return locations;
    }
}
