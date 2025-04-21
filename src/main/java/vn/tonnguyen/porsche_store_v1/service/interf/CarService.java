package vn.tonnguyen.porsche_store_v1.service.interf;

import vn.tonnguyen.porsche_store_v1.model.Car;

import java.util.List;

public interface CarService {
    public List<Car> findAll();
    public Car findBySlug(String slug);
    public Car save(Car car);
}
