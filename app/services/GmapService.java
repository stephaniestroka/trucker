package services;

import com.google.inject.Inject;
import play.Logger;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import utilities.Coordinate;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class GmapService {

    private final String apiURL = "https://roads.googleapis.com/v1/snapToRoads";
    private final String apikey = "AIzaSyBoOklxjoiao1YpYuvzJu5EmDvMv3zvxeI";

    private WSClient wsClient;

    @Inject
    public GmapService(WSClient wsClient) {
        this.wsClient = wsClient;
    }

    public String runSnapToRoad(List<Coordinate> coordinates) {
        String requestURL = apiURL;
        String path = "";
        String interpolate = "true";

        for (Coordinate c : coordinates) {
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
            return promise.toCompletableFuture().get().getBody().toString();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error sending a GET request to Google Maps API", e);
        }
    }

}
