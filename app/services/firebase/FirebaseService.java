package services.firebase;

import java.util.concurrent.CompletionStage;

import com.google.inject.Inject;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import services.firebase.message.Message;

/**
 * Created by gmoretti on 29.07.2016.
 */
public class FirebaseService {

    private static final Logger.ALogger logger = Logger.of(FirebaseService.class);

    private final String urlString;
    private final String apiKey;

    //TODO close the connection of wsClient
    private final WSClient wsClient;

    @Inject
    public FirebaseService(WSClient wsClient, Configuration configuration) {
        this.wsClient = wsClient;
        this.apiKey = configuration.getString("firebase.apikey");
        this.urlString = configuration.getString("firebase.fcm.sendurl");
    }

    public CompletionStage<Boolean> post(Message pushRequest) {

        CompletionStage<WSResponse> response = postToFirebase(pushRequest);
        return response.thenApply(this::hasBeenSuccessful);
    }

    CompletionStage<WSResponse> postToFirebase(Message msg) {

        CompletionStage<WSResponse> response = wsClient.url(urlString)
                .setContentType("application/json")
                .setHeader("Authorization", "key=" + apiKey)
                .post(Json.toJson(msg));

        // Alternatively, one could wait for the response with: response.toCompletableFuture().get();

        logger.info("A Message for " + msg.getTo() + " has been sent via firebase.");

        return response;
    }

    private boolean hasBeenSuccessful(WSResponse response) {

        int status = response.getStatus();
        int failure = response.asJson().findPath("failure").asInt();

        logger.debug("Firebase Response - Status: " + status + " | " + "Failures: " + failure);

        return (status == 200) & (failure == 0);
    }
}
