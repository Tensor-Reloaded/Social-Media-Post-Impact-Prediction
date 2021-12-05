package uaic.info.orchestrationservice.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tweet {
    private final String text;
    private final String imageData;
    private final TweetMetaData meta;
}
