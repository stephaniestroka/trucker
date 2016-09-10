package services.firebase.push;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import controllers.routes;
import play.Logger;
import play.libs.Json;
import services.RegistrationService;
import services.firebase.FirebaseService;
import services.firebase.message.Message;
import services.firebase.push.request.PushRequest;
import services.firebase.push.response.PushResponse;

/**
 * Created by gmoretti on 29.07.2016.
 */
public class PushService {

    private static final Logger.ALogger logger = Logger.of(FirebaseService.class);

    private  FirebaseService firebase;
    private RegistrationService registrationService;
    private HashMap<UUID, PushJob> pushRequestsQueue = new HashMap<>();

    @Inject
    public PushService(FirebaseService firebase, RegistrationService registrationService) {
        this.firebase = firebase;
        this.registrationService = registrationService;
    }

    public CompletionStage<UUID> sendPush(PushRequest pushRequest){
        // TODO make the request expire after xx minutes.
        UUID idJob = createPushJob();
        // TODO: probably must be adjusted
        final String respondLocation = routes.PushController.respondToRequest(idJob.toString()).url();
        pushRequest.setResponseResource(respondLocation);
        CompletionStage<Boolean> resultOfSuccess = firebase.post(convertInFirebaseMessage(pushRequest));
        return resultOfSuccess.thenApply(isSuccess -> {
            if (isSuccess) {
                return idJob;
            } else {
                logger.error("post to firebase failed");
                pushRequestsQueue.remove(idJob);
                return null;
            }
        });
    }

    public boolean isJobInQueue(UUID jobId){
        return pushRequestsQueue.containsKey(jobId);
    }

    public boolean hasJobAResponse(UUID jobId){
        PushJob job = pushRequestsQueue.get(jobId);
        if(job == null){
            logger.error("Tried to access to a non existent Job: " + jobId);
            return false;
        } else {
            return job.hasAResponse();
        }
    }

    public PushResponse fetchResponse(UUID jobId){
        PushJob job = pushRequestsQueue.get(jobId);
        if(job == null){
            logger.error("Tried to access to a non existent Job: " + jobId);
            return null;
        } else if(!job.hasAResponse()){
            logger.error("Response for job " + jobId + "doesn't exist yet");
            return null;
        } else {
            pushRequestsQueue.remove(jobId);
            return job.getResponse();
        }
    }

    public void respondToRequest(UUID jobId, PushResponse response){
        if(isJobInQueue(jobId)){
            Logger.info("Received response for Job " + jobId);
            PushJob request =  pushRequestsQueue.get(jobId);
            request.setResponse(response);
        } else {
            logger.error("Tried to respond to a non existent Job: " + jobId);
        }
    }

    public boolean isRequestValid(PushRequest pushRequest){
        return registrationService.findUser(pushRequest.getUserId()) != null && pushRequest.getAction() != null;
    }

    public Message convertInFirebaseMessage(PushRequest pushRequest){
        logger.debug("converting a PushRequest in Firebase's Message...");
        Message message = new Message();
        String token = registrationService.findTokenForUserId(pushRequest.getUserId());
        if (token == null) {
            // TODO: do something to prevent calling that with null.
            throw new RuntimeException("Failed to find token for user '" + pushRequest.getUserId() + "'");
        }
        message.setTo(token);
        message.addData(PushRequest.class.getSimpleName(), Json.toJson(pushRequest).toString());
        return message;
    }

    private UUID createPushJob(){
        PushJob job = new PushJob();
        pushRequestsQueue.put(job.getId(), job);
        logger.debug("created pushJob with id: " + job.getId());
        return job.getId();
    }
    
}
