package uaic.info.account_management_service.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PostDto {
    @NotNull String tweetText;
    @NotNull private String imageData;
}
