package vn.tonnguyen.porsche_store_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tonnguyen.porsche_store_v1.model.Category;

import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    //List<Category> findAll();
}
