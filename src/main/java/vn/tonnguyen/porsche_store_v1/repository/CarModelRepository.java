package vn.tonnguyen.porsche_store_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.tonnguyen.porsche_store_v1.model.CarModel;

import java.util.List;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Integer> {
    @Query(""" 
            SELECT distinct cm 
            FROM CarModel cm 
            JOIN FETCH cm.cars 
            WHERE cm.category.name = :name 
            """)
    List<CarModel> findByCategoryName(@Param("name")String name);
}
