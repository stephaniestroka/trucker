package services.firebase.push.response.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import services.firebase.push.response.PushResponse;

/**
 * Created by gmoretti on 29.08.2016.
 */
public class AuthenticationResponseAction extends ResponseAction {

    boolean success;

    public AuthenticationResponseAction() {
    }

    @JsonCreator
    public AuthenticationResponseAction(boolean success) {
        this.success = success;
    }

    /**
     * Returns whether the user has been successfully authenticated.
     * (Please note that a rejection is also possible, which is indicated
     * with false and null in the {@link PushResponse}.)
     */
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
}
