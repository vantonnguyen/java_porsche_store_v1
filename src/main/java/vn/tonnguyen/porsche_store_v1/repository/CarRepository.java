package vn.tonnguyen.porsche_store_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tonnguyen.porsche_store_v1.model.Car;

import java.util.List;
@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    public List<Car> findAll();
    public Car findBySlug(String slug);
}
