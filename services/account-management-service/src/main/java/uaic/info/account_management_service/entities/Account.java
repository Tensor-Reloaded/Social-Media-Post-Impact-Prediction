package uaic.info.account_management_service.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @NotNull
    private Long id;

    @NotNull
    private String key;

    @NotNull
    private String secret;
}
