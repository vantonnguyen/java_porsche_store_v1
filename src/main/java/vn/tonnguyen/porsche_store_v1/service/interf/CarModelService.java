package vn.tonnguyen.porsche_store_v1.service.interf;

import vn.tonnguyen.porsche_store_v1.model.CarModel;

import java.util.List;

public interface CarModelService {
    public List<CarModel> findAll();
    public List<CarModel> findByCategoryName(String name);

}
