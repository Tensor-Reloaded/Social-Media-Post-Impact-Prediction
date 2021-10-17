package uaic.info.predictions_management_service.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import uaic.info.predictions_management_service.entities.User;

public interface UsersDao extends JpaRepository<User, Long> {
}
