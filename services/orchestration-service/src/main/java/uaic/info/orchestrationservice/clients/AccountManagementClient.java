package uaic.info.orchestrationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uaic.info.orchestrationservice.entities.Tweet;
import uaic.info.orchestrationservice.entities.TweetMetaData;

@FeignClient("account-management")
public interface AccountManagementClient {
    @RequestMapping(method = RequestMethod.GET, path = "api/v1/authorize")
    void authorize();

    @RequestMapping(method = RequestMethod.GET, path = "api/v1/userData", produces = "application/json")
    TweetMetaData getUserData();

    @RequestMapping(method = RequestMethod.POST, path = "api/v1/tweet", consumes = "application/json")
    void postTweet(Tweet tweet);

    @RequestMapping(method = RequestMethod.GET, path = "api/v1/tweet/{id}", produces = "application/json")
    Tweet getTweet(@PathVariable("id") String tweetId);
}
