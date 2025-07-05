package vn.tonnguyen.porsche_store_v1.service.interf;

import org.springframework.data.jpa.repository.Query;
import vn.tonnguyen.porsche_store_v1.model.CarModel;

import java.util.List;

public interface CarModelService {
    List<CarModel> findAll();
    List<CarModel> findByCategoryName(String name);

}
