package vn.tonnguyen.porsche_store_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;
import vn.tonnguyen.porsche_store_v1.service.interf.ModelService;

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
            return "error/404"; // Trang 404 nếu không tìm thấy xe
        }
        model.addAttribute("car", car);
        return "car/detail"; // Trả về trang chi tiết xe
    }

}
