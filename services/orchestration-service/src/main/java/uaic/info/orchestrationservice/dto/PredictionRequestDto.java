package uaic.info.orchestrationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PredictionRequestDto {
    private String tweetText;
    private String b64ImageData;
}
