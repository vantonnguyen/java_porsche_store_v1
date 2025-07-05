package vn.tonnguyen.porsche_store_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;

@Controller
@RequestMapping("/models")
public class CarController {
    private final CarService carService;
    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{slug}")
    public String getCarDetail(@PathVariable("slug") String slug, Model model) {
        Car car = carService.findBySlug(slug);
        if (car == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }
        model.addAttribute("car", car);
        return "car/detail";
    }

}
