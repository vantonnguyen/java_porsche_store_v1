package vn.tonnguyen.porsche_store_v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.tonnguyen.porsche_store_v1.model.CarModel;
import vn.tonnguyen.porsche_store_v1.repository.ModelRepository;
import vn.tonnguyen.porsche_store_v1.service.interf.ModelService;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
    private ModelRepository modelRepository;
    @Autowired
    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }
    @Override
    public List<CarModel> findAll(){
        return modelRepository.findAll();
    }

    @Override
    public List<CarModel> findByCategory_Name(String name){
        return modelRepository.findByCategory_Name(name);
    }


}
