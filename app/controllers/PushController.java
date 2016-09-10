package controllers;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Call;
import play.mvc.Controller;
import play.mvc.Result;
import services.firebase.push.PushService;
import services.firebase.push.request.PushRequest;
import services.firebase.push.request.action.AuthenticationRequestAction;
import services.firebase.push.request.action.RequestAction;
import services.firebase.push.request.action.SpeedingWarningRequestAction;
import services.firebase.push.request.action.TakeBreakRequestAction;
import services.firebase.push.response.PushResponse;

/**
 *
 */
public class PushController extends Controller {
    
    @Inject
    private PushService pushService;
    
    @Transactional
    public CompletionStage<Result> push(String userId, String method) {
        final PushRequest pushRequest = new PushRequest();
        final RequestAction requestAction;
        if (method.equals("login")) {
            requestAction = new AuthenticationRequestAction();
        } else if (method.equals("speed")) {
            requestAction = new SpeedingWarningRequestAction();
        } else if (method.equals("sleep")) {
            requestAction = new TakeBreakRequestAction();
        } else {
            // TODO: we should return a bad request now, but how do we do that if we're returning a completion stage?
            throw new RuntimeException("This action is unknown");
        }
        pushRequest.setAction(requestAction);
        pushRequest.setUserId(userId);
        CompletionStage<UUID> stage  = pushService.sendPush(pushRequest);
        return stage.thenApply(idRequest -> {
            if(idRequest != null){
                String pollingLocation = routes.PushController.status(idRequest.toString()).url();
                ObjectNode response = Json.newObject();
                response.put("idRequest", idRequest.toString());
                return status(202, response).withHeader("Location", pollingLocation);
            } else {
                // TODO: handle failures. For now we just fake that everything went well.
//                return internalServerError("FAILURE");
                return ok();
            }
        });
    }
    
    public Result status(String jobIdString){
        UUID jobId = (UUID.fromString(jobIdString));
        if(!pushService.isJobInQueue(jobId)){
            return badRequest("Request " + jobId + " doesn't exist");
        }
        else if(pushService.hasJobAResponse(jobId)){
            Call getResponse = routes.PushController.getResponse(jobId.toString());
            return redirect(getResponse);
        } else {
            ObjectNode responseBody = Json.newObject();
            responseBody.put("status", "WAITING");
            return ok(responseBody);
        }
    }
    
    /**
     * Called by the registered client.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result respondToRequest(String jobIdString){
        UUID jobId = UUID.fromString(jobIdString);
        if(!pushService.isJobInQueue(jobId)){
            return badRequest("Request " + jobId + " doesn't exist");
        }
        JsonNode jsonResponse = request().body().asJson();
        PushResponse response = Json.fromJson(jsonResponse, PushResponse.class);

        pushService.respondToRequest(jobId, response);

        ObjectNode responseBody = Json.newObject();
        responseBody.put("message", "RESPONDED");
        return ok(responseBody);
    }
    
    public Result getResponse(String jobIdString){
        UUID jobId = (UUID.fromString(jobIdString));
        if(!pushService.isJobInQueue(jobId) ||
                !pushService.hasJobAResponse(jobId)){
            return badRequest();
        }
        PushResponse response = pushService.fetchResponse(jobId);
        return ok(Json.toJson(response));
    }
    
}
