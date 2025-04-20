package vn.tonnguyen.porsche_store_v1.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tonnguyen.porsche_store_v1.model.CarModel;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<CarModel, Integer> {
//    EntityGraph giúp chỉ định rõ: khi chạy truy vấn, load thêm thuộc tính cars đi kèm CarModel
    //@EntityGraph(attributePaths = "cars")
    List<CarModel> findByCategory_Name(String name);
}
