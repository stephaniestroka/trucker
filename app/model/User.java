package model;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;


/**
 *
 */
@Entity
public class User {
    
    @Id
    private @Required @Nonnull String identifier;
    private @Required @Nonnull String token;

    public User(){}

    public User(String identifier, String token) {
        this.identifier = identifier;
        this.token = token;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Returns the identifier of the user, which is a.k.a. user ID.
     */
    public String getIdentifier() {
        return identifier;
    }

    public String getToken() {
        return token;
    }

}
