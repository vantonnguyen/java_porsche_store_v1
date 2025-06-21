package vn.tonnguyen.porsche_store_v1.repository;

import org.springframework.data.repository.CrudRepository;
import vn.tonnguyen.porsche_store_v1.model.Staff;

import java.util.Optional;

public interface StaffRepository extends CrudRepository<Staff, Integer> {
    Optional<Staff> findByUsername(String username);
}
