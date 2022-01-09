package uaic.info.orchestrationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PredictionResponseDto {
    private Integer predictedNumberOfLikes;
}
