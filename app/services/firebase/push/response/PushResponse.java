package services.firebase.push.response;


import play.libs.Json;
import services.firebase.push.response.action.ResponseAction;

/**
 * Created by gmoretti on 29.07.2016.
 */
public class PushResponse {

    /* -------------------------------------------------- Positive Response -------------------------------------------------- */

    private Boolean isPositiveResponse;

    /**
     * Returns false if the user rejected the push request.
     */
    public Boolean getIsPositiveResponse() {
        return isPositiveResponse;
    }
    
    public void setIsPositiveResponse(Boolean positiveResponse) {
        isPositiveResponse = positiveResponse;
    }

    /* -------------------------------------------------- Response Action -------------------------------------------------- */

    private ResponseAction action;

    /**
     * Returns the response of the user or null if the {@link #getIsPositiveResponse() response was negative}.
     */
    public ResponseAction getAction() {
        return action;
    }
    
    public void setAction(ResponseAction action) {
        this.action = action;
    }

    /* -------------------------------------------------- Conversion -------------------------------------------------- */

    // TODO put this in a super abstract class
    public String toJson() {
        return Json.toJson(this).toString();
    }
    
    public static PushResponse fromJson(String json) {
        return Json.fromJson(Json.parse(json), PushResponse.class);
    }

}
