package uaic.info.predictions_management_service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TweetPredictionDetails {
    private Long id;
    private String text;
    private Object image;
    private Long userId;
    private Long userFollowers;
    private Date postedDate;
    private Integer predictedNumberOfLikes;

    public static class TweetPredictionDetailsBuilder {
        private Long id;
        private String text;
        private Object image;
        private Long userId;
        private Long userFollowers;
        private Date postedDate;
        private Integer predictedNumberOfLikes;

        TweetPredictionDetailsBuilder() {
        }

        public TweetPredictionDetails.TweetPredictionDetailsBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public TweetPredictionDetails.TweetPredictionDetailsBuilder text(final String text) {
            this.text = text;
            return this;
        }

        public TweetPredictionDetails.TweetPredictionDetailsBuilder image(final Object image) {
            this.image = image;
            return this;
        }

        public TweetPredictionDetails.TweetPredictionDetailsBuilder userId(final Long userId) {
            this.userId = userId;
            return this;
        }

        public TweetPredictionDetails.TweetPredictionDetailsBuilder userFollowers(final Long userFollowers) {
            this.userFollowers = userFollowers;
            return this;
        }

        public TweetPredictionDetails.TweetPredictionDetailsBuilder postedDate(final Date postedDate) {
            this.postedDate = postedDate;
            return this;
        }

        public TweetPredictionDetails.TweetPredictionDetailsBuilder predictedNumberOfLikes(final Integer predictedNumberOfLikes) {
            this.predictedNumberOfLikes = predictedNumberOfLikes;
            return this;
        }

        public TweetPredictionDetails build() {
            return new TweetPredictionDetails(this.id, this.text, this.image, this.userId, this.userFollowers, this.postedDate, this.predictedNumberOfLikes);
        }
    }
}
