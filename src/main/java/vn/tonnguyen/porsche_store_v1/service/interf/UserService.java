package vn.tonnguyen.porsche_store_v1.service.interf;

import org.springframework.stereotype.Service;
import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Integer id);
    User save(User user);
    void delete(Integer id);

    User update(User user);
    User findByUsername(String username);
    Integer findIdByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
