package uaic.info.account_management_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import twitter4j.Twitter;
import twitter4j.auth.RequestToken;
import uaic.info.account_management_service.services.TwitterService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class GetTwitterTokenController {
    private final TwitterService twitterService;

    @RequestMapping("/getToken")
    public RedirectView getToken(HttpServletRequest request, Model model) {
        //this will be the URL that we take the user to
        String twitterUrl = "";

        try {
            //get the Twitter object
            Twitter twitter = twitterService.getTwitter();

            //get the callback url so they get back here
            String callbackUrl = "https://aset-testing.herokuapp.com/twitterCallback";

            //go get the request token from Twitter
            RequestToken requestToken = twitter.getOAuthRequestToken(callbackUrl);

            //put the token in the session because we'll need it later
            request.getSession().setAttribute("requestToken", requestToken);

            //let's put Twitter in the session as well
            request.getSession().setAttribute("twitter", twitter);

            //now get the authorization URL from the token
            twitterUrl = requestToken.getAuthorizationURL();

            System.out.println("Authorization URL is " + twitterUrl);
        } catch (Exception e) {
            System.out.println("Problem logging in with Twitter! " + e.getMessage());
            e.printStackTrace();
        }

        //redirect to the Twitter URL
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(twitterUrl);
        return redirectView;
    }
}
