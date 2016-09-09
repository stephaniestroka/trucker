package services.firebase.push;

import java.util.UUID;

import services.firebase.push.request.PushRequest;
import services.firebase.push.response.PushResponse;

/**
 * Created by gmoretti on 29.07.2016.
 */
public class PushJob {

    private final UUID id = UUID.randomUUID();
    private PushRequest request;
    private PushResponse response;
    
    public UUID getId() {
        return id;
    }
    
    public PushRequest getRequest() {
        return request;
    }
    
    public void setRequest(PushRequest request) {
        this.request = request;
    }
    
    public boolean hasAResponse(){
        return response != null;
    }
    
    public PushResponse getResponse() {
        return response;
    }
    
    public void setResponse(PushResponse response) {
        this.response = response;
    }

}
