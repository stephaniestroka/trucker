package controllers;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.User;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.RegistrationService;

/**
 *
 */
public class RegistrationController extends Controller {

    private final RegistrationService registrationService;
    private FormFactory formFactory;

    @Inject
    public RegistrationController(RegistrationService registrationService,
                                  FormFactory formFactory) {
        this.registrationService = registrationService;
        this.formFactory = formFactory;
    }

    @BodyParser.Of(BodyParser.Json.class)
    @Transactional
    public Result register() {
        JsonNode jsonRequestBody = request().body().asJson();
        ObjectNode result = Json.newObject();

        Form<User> userValidationForm = formFactory.form(User.class).bind(jsonRequestBody);
        if(userValidationForm.hasErrors()) {
            result.put("message", "Invalid registration request.");
            result.put("error", userValidationForm.errorsAsJson());
            return badRequest(result);
        }

        User user = Json.fromJson(jsonRequestBody, User.class);

        registrationService.registerUser(user);
        result.put("status", 200);
        result.put("message", "Device is registered.");

        return ok(result);
    }

    @Transactional(readOnly = true)
    public Result getToken(String id) {
        String token = registrationService.findTokenForUserId(id);
        ObjectNode result = Json.newObject();
        result.put("fcmToken", token);
        return ok(result);
    }
    
}
