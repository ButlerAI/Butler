package com.nohowdezign.butler.core;

import com.nohowdezign.butler.database.UserProfile;
import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.annotations.Intent;
import com.nohowdezign.butler.responder.Responder;

/**
 * @author Noah Howard
 */
public class ProfileConfig {

    @Intent(keyword = "name")
    public void setUserName(AbstractIntent intent, Responder responder) {
        String name = "";
        if(intent.getOptionalArguments().containsKey("PERSON")) {
            UserProfile profile = new UserProfile();
            String newName = intent.getOptionalArguments().get("PERSON").split("_")[0];
            profile.setAttributeOnProfile("name", UserProfile.DEFAULT_USER, newName);
            UserProfile.DEFAULT_USER = newName;
            responder.respondWithMessage("I will now call you " + UserProfile.DEFAULT_USER);
        } else {
            responder.respondWithMessage("I could not hear your new name, so I will continue to call you " + UserProfile.DEFAULT_USER);
        }
    }

}
