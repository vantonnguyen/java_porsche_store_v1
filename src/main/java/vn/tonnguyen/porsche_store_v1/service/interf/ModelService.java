package vn.tonnguyen.porsche_store_v1.service.interf;

import vn.tonnguyen.porsche_store_v1.model.CarModel;

import java.util.List;

public interface ModelService {
    public List<CarModel> findAll();
    List<CarModel> findByCategory_Name(String name);

}
