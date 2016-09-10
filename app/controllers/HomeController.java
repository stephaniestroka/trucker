package controllers;

import play.mvc.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render("Truck Simulator.", "images/man_truck1.jpg"));
    }
    
    public Result simulation(int id) {
        return ok(dashboard.render("Truck Simulator.", "images/man_truck3.jpg"));
    }
    
    public Result showDataDetails(String type) {
        final String image;
        final String title;
        final String description;
        if (type.equals("public")) {
            image = "images/cloud_1_labeled_partners.png";
            title = "Traffic Data";
            description = "Data provided by MAN to improve road and traffic conditions.";
        } else if (type.equals("man")) {
            image = "images/cloud_2_labeled_partners.png";
            title = "MAN Data Provider";
            description = "The new business of MAN: Data Provisioning.";
        } else if (type.equals("driver")) {
            image = "images/cloud_4_labeled_partners.png";
            title = "Personal Driver Profile";
            description = "The personal data of the driver.";
        } else if (type.equals("employer")) {
            image = "images/cloud_3_labeled.png";
            title = "Truck Owner";
            description = "Delivery history tracked by the logistics company.";
        } else {
            image = title = description = null;
            return badRequest();
        }
        return ok(data.render("Truck Simulator.", image, title, description));
    }
}
