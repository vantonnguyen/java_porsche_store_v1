package vn.tonnguyen.porsche_store_v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.tonnguyen.porsche_store_v1.model.CarModel;
import vn.tonnguyen.porsche_store_v1.repository.CarModelRepository;
import vn.tonnguyen.porsche_store_v1.service.interf.CarModelService;

import java.util.List;

@Service
public class CarModelServiceImpl implements CarModelService {
    private CarModelRepository carModelRepository;
    @Autowired
    public CarModelServiceImpl(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }
    @Override
    public List<CarModel> findAll(){
        return carModelRepository.findAll();
    }
    @Override
    public List<CarModel> findByCategoryName(String name){
        return carModelRepository.findByCategoryName(name);
    }


}
