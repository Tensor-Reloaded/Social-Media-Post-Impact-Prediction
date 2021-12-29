package uaic.info.account_management_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;


@Service
@RequiredArgsConstructor
public class TwitterService {
    @Lookup
    public Twitter getTwitter() {
        return null;
    }
}
