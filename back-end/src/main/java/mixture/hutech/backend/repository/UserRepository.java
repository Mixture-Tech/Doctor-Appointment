package mixture.hutech.backend.repository;

import mixture.hutech.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    /*@Query("SELECT new mixture u from User WHERE u.id = :userId")
    Optional<User> findById(@Param("userId") String userId);*/
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.appointments a WHERE u.id = :userId")
    Optional<User> findByUserId(@Param("userId") String userId);

//    @Query("SELECT u FROM User u WHERE u.role.name = 'DOCTOR'")
//    Optional<User> findDoctorById(String id);
}
