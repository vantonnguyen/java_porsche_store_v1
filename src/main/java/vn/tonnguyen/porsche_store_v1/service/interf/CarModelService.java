package vn.tonnguyen.porsche_store_v1.service.interf;

import vn.tonnguyen.porsche_store_v1.model.CarModel;

import java.util.List;

public interface CarModelService {
    List<CarModel> findAll();
    List<CarModel> findByCategoryName(String name);

}
