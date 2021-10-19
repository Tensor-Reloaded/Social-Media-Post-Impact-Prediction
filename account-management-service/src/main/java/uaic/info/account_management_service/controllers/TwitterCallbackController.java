package uaic.info.account_management_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import twitter4j.Twitter;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import uaic.info.account_management_service.entities.Account;
import uaic.info.account_management_service.services.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TwitterCallbackController {
    private final AccountService accountService;

    //This is where we land when we get back from Twitter
    @RequestMapping("/twitterCallback")
    public String twitterCallback(@RequestParam(value="oauth_verifier", required=false) String oauthVerifier,
                                  @RequestParam(value="denied", required=false) String denied,
                                  HttpServletRequest request, HttpServletResponse response, Model model) {

        if (denied != null) {
            //if we get here, the user didn't authorize the app
            return "redirect:login";
        }

        //get the objects from the session
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");

        try {
            //get the access token
            AccessToken token = twitter.getOAuthAccessToken(requestToken, oauthVerifier);

            //take the request token out of the session
            request.getSession().removeAttribute("requestToken");

            Optional<Account> queryResponse = accountService.getByTwitterId(twitter.getId());
            if(queryResponse.isEmpty()){
                Account account = new Account();
                account.setTwitterId(twitter.getId());
                account.setTwitterUsername(twitter.getScreenName());
                accountService.createNewAccount(account);

                //store the username so that we can display it on the web page
//                model.addAttribute("username", twitter.getScreenName());
                model.addAttribute("account", account);
            }
            else{
                model.addAttribute("account", queryResponse.get());
//                model.addAttribute("username", queryResponse.get().getTwitterUsername());
            }

            return "loggedIn";
        } catch (Exception e) {
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
            return "redirect:login";
        }
    }
}
