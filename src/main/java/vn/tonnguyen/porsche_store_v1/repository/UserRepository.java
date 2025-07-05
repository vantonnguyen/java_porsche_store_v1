package vn.tonnguyen.porsche_store_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.tonnguyen.porsche_store_v1.model.User;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    @Query("select u.id from User u where u.username = :username")
    Integer findIdByUsername(@Param("username") String username);

}
