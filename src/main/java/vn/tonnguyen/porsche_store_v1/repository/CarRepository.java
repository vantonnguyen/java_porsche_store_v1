package vn.tonnguyen.porsche_store_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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

    Optional<Car> findBySlug(String slug);

    @Modifying
    @Transactional
    @Query("update Car c set c.stock = c.stock - :quantity where c.id = :carId")
    void decreaseStockAfterOrder(@Param("carId") Integer carId, @Param("quantity") int quantity);
}
