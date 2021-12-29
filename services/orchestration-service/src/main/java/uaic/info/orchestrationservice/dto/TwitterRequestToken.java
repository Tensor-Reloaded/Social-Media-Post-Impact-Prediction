package uaic.info.orchestrationservice.dto;

import lombok.Data;
import twitter4j.auth.RequestToken;

@Data
public class TwitterRequestToken {
    private final RequestToken requestToken;
}
