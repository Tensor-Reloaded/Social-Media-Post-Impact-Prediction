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
@Table(name = "accounts")
@NamedQuery(name = "Account.findByTwitterId", query = "SELECT account FROM Account account WHERE account.twitterId = ?1")
public class Account {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "twitter_id")
    private Long twitterId;

    @NotBlank
    @Column(name = "twitter_username")
    private String twitterUsername;
}
