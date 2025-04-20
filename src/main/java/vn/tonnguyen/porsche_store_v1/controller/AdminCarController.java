package vn.tonnguyen.porsche_store_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;
import vn.tonnguyen.porsche_store_v1.service.interf.ModelService;

@Controller
@RequestMapping("/admin/cars")
public class AdminCarController {
    private final CarService carService;
    private final ModelService modelService;

    @Autowired
    public AdminCarController(CarService carService, ModelService modelService) {
        this.carService = carService;
        this.modelService = modelService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "admin/car/cars";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("models",modelService.findAll() );
        return "admin/car/create";
    }

}
