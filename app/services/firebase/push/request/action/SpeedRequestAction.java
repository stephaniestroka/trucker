package services.firebase.push.request.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class SpeedRequestAction extends RequestAction {
    
    @JsonCreator
    public SpeedRequestAction(@JsonProperty int speed) {
        this.speed = speed;
    }
    
    private int speed;
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public int getSpeed() {
        return speed;
    }
    
}
