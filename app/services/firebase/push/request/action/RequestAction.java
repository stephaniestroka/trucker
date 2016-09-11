package services.firebase.push.request.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * Created by gmoretti on 04.08.2016.
 */
@JsonTypeInfo(
        use = Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
    @Type(value = AuthenticationRequestAction.class),
    @Type(value = TakeBreakRequestAction.class),
    @Type(value = SpeedRequestAction.class)
})
public abstract class RequestAction {}
