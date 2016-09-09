package services;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import model.User;
import play.Logger;
import play.db.jpa.JPAApi;

/**
 *
 */
@Singleton
public class RegistrationService {
    
    private final JPAApi jpaApi;
    private final Logger.ALogger logger = Logger.of(this.getClass());
    
    @Inject
    public RegistrationService(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public void registerUser(User user){
        final @Nullable User dbUser = findUser(user.getIdentifier());
        if(dbUser != null){
            dbUser.setToken(user.getToken());
            logger.info("User " + user.getIdentifier() + " already registered. Updating his token to " + user.getToken() + ".");
        } else {
            jpaApi.em().persist(user);
            logger.info("User " + user.getIdentifier() + " with token " + user.getToken() + " persisted in DB.");
        }
    }

    public User findUser(@Nonnull String identifier){
        return jpaApi.em().find(User.class, identifier);
    }

    public @Nullable String findTokenForUserId(@Nonnull String identifier) {
        User user = findUser(identifier);
        return user != null ? user.getToken() : null;
    }    
    
}
