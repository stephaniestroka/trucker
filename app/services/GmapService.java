package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import utilities.Itinerary;
import utilities.Location;
import utilities.SnappedPoint;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class GmapService {

    private final String apiURL = "https://roads.googleapis.com/v1/snapToRoads";
    private final String apikey = "AIzaSyBoOklxjoiao1YpYuvzJu5EmDvMv3zvxeI";

    private WSClient wsClient;

    private Itinerary itinerary;

    @Inject
    public GmapService(WSClient wsClient) {
        this.wsClient = wsClient;
    }

    public void makeNewItinerary(List<Location> locations) {
        Location[] itineraryLocations = runSnapToRoadRequest(locations);
        this.itinerary = new Itinerary(itineraryLocations);
    }

    public Itinerary getItinerary() {
        return this.itinerary;
    }

    public boolean hasItinerary() {
        return itinerary != null;
    }

    private Location[] runSnapToRoadRequest(List<Location> locations) {

        String requestURL = apiURL;
        String path = "";
        String interpolate = "true";

        for (Location c : locations) {
            path = path
                    + c.getLatitude().toString() + ","
                    + c.getLongitude().toString()
                    + "|";
        }
        path = path.substring(0, path.length()-1);

        CompletionStage<WSResponse> promise = wsClient.url(apiURL)
                .setQueryParameter("path", path)
                .setQueryParameter("interpolate", interpolate)
                .setQueryParameter("key", apikey).get();

        try {

            WSResponse response = promise.toCompletableFuture().get();
            return processSnapToRoadResponse(response);

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error sending a GET request to Google Maps API", e);
        }
    }

    private Location[] processSnapToRoadResponse(WSResponse response) {

        JsonNode snappedPointsJson = response.asJson().withArray("snappedPoints");
        SnappedPoint[] snappedPoints = new SnappedPoint[snappedPointsJson.size()];

        for (int i = 0; i < snappedPointsJson.size(); i++) {
            JsonNode singlePointJson = snappedPointsJson.get(i);
            snappedPoints[i] = Json.fromJson(singlePointJson, SnappedPoint.class);
        }

        return getSnappedPointsLocation(snappedPoints);
    }

    private Location[] getSnappedPointsLocation(SnappedPoint[] snappedPoints) {

        Location[] snappedLocations = new Location[snappedPoints.length];

        int i = 0;
        for (SnappedPoint p : snappedPoints) {
            snappedLocations[i] = p.getLocation();
            i++;
        }

        return snappedLocations;
    }

}
