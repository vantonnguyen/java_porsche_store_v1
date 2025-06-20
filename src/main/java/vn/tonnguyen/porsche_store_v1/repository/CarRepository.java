package vn.tonnguyen.porsche_store_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tonnguyen.porsche_store_v1.model.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    //List<Car> findAll();
    //Optional<Car> findById(Integer id);
    //Car save(Car car);
    //void deleteById(Integer id);
    //boolean existsById(Integer id);
    //long count();

    Car findBySlug(String slug);

}
