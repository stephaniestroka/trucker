package services.firebase.message;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import play.data.validation.Constraints.Required;

/**
 * @see <a href="https://firebase.google.com/docs/cloud-messaging/http-server-ref">Firebase Cloud Messaging HTTP Protocol</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    
    public Message () {}
    
    @Required
    private String to;
    private String collapse_key;
    private String priority;
    private Boolean delay_while_idle;
    private Integer time_to_live;
    private Boolean dry_run;
    private NotificationAndroid notification;
    private Map<String,String> data;

    /**
     * Returns the token of the recipient.
     * For everything else, RTFM.
     */
    public String getTo() {
        return to;
    }

    public void setTo(String to){
        this.to = to;
    }

    public Map<String, String> getData() {
        return data;
    }

    public String getCollapse_key() {
        return collapse_key;
    }

    public void setCollapse_key(String collapse_key) {
        this.collapse_key = collapse_key;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Boolean isDelay_while_idle() {
        return delay_while_idle;
    }

    public void setDelay_while_idle(Boolean delay_while_idle) {
        this.delay_while_idle = delay_while_idle;
    }

    public Integer getTime_to_live() {
        return time_to_live;
    }

    public void setTime_to_live(Integer time_to_live) {
        this.time_to_live = time_to_live;
    }

    public Boolean isDry_run() {
        return dry_run;
    }

    public void setDry_run(Boolean dry_run) {
        this.dry_run = dry_run;
    }

    public NotificationAndroid getNotification() {
        return notification;
    }

    public void setNotification(NotificationAndroid notification) {
        this.notification = notification;
    }

    public void addData(String title, String data){
        if(this.data == null)
            this.data = new HashMap<String,String>();
        this.data.put(title, data);
    }

}
