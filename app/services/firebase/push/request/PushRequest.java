package services.firebase.push.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.libs.Json;
import services.firebase.push.request.action.RequestAction;


/**
 * Created by gmoretti on 04.08.2016.
 */
public class PushRequest {

    /* -------------------------------------------------- User ID -------------------------------------------------- */

    @JsonIgnore
    // The user ID is ignored in JSON, which is passed in the HTTP body, whereas the user ID is appended to the path.
    private String userId;

    /**
     * Returns the identifier of the user receiving the push request.
     */
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /* -------------------------------------------------- Request Action -------------------------------------------------- */

    private RequestAction action;

    /**
     * Returns the action that is sent to the user.
     */
    public RequestAction getAction() {
        return action;
    }
    
    public void setAction(RequestAction action) {
        this.action = action;
    }

    /* -------------------------------------------------- Response Resource -------------------------------------------------- */

    private String responseResource;

    /**
     * Returns the REST resource to which the mobile application puts the response.
     */
    public String getResponseResource() {
        return responseResource;
    }
    
    public void setResponseResource(String responseResource) {
        this.responseResource = responseResource;
    }

    /* -------------------------------------------------- Sender -------------------------------------------------- */

    private String sender;

    /**
     * Returns the identifier (?) of the sending service provider.
     */
    public String getSender() {
        return sender;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }

    /* -------------------------------------------------- Title -------------------------------------------------- */

    private String title;

    /**
     * Returns the title that is displayed to the user when opening the push request.
     */
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    /* -------------------------------------------------- Description -------------------------------------------------- */

    private String description;

    /**
     * Returns the description of the push request to pass further information to the user.
     */
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    /* -------------------------------------------------- Conversion -------------------------------------------------- */

    // TODO put this in a super abstract class
    public String toJson(){
        return Json.toJson(this).toString();
    }
    
    public static PushRequest fromJson(String json){
        return Json.fromJson(Json.parse(json), PushRequest.class);
    }
    
}
