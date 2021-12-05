package uaic.info.predictions_management_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uaic.info.predictions_management_service.entities.User;

public interface UsersRepository extends JpaRepository<User, Long> {
}
