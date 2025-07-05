package vn.tonnguyen.porsche_store_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.tonnguyen.porsche_store_v1.service.interf.CarModelService;

@Controller
@RequestMapping("/models")
public class CarModelController {
    private final CarModelService carModelService;

    @Autowired
    public CarModelController(final CarModelService carModelService) {
        this.carModelService = carModelService;
    }

    @GetMapping()
    public String getModelByCategory(@RequestParam String category, Model model) {
        model.addAttribute("carModels", carModelService.findByCategoryName(category));
        return "car_model/list";
    }
}
