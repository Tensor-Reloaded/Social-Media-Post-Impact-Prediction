package uaic.info.orchestrationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uaic.info.orchestrationservice.dto.GetAccessTokenRequestBody;
import uaic.info.orchestrationservice.dto.TwitterAccessToken;
import uaic.info.orchestrationservice.dto.TwitterRequestToken;
import uaic.info.orchestrationservice.entities.Tweet;
import uaic.info.orchestrationservice.entities.TweetMetaData;

@FeignClient("account-management")
public interface AccountManagementClient {
    @RequestMapping(value = "/api/v1/request_token", method = RequestMethod.POST)
    TwitterRequestToken getRequestToken();

    @RequestMapping(value = "/api/v1/access_token", method = RequestMethod.POST)
    TwitterAccessToken getAccessToken(GetAccessTokenRequestBody requestBody);
}
