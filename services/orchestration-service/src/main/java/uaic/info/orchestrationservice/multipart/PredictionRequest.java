package uaic.info.orchestrationservice.multipart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter @Setter
public class PredictionRequest {
    private String tweetText;
    private MultipartFile image;
}
