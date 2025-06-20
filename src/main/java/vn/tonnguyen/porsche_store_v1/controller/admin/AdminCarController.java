package vn.tonnguyen.porsche_store_v1.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;
import vn.tonnguyen.porsche_store_v1.service.interf.CarModelService;

@Controller
@RequestMapping("/admin/cars")
public class AdminCarController {
    private final CarService carService;
    private final CarModelService carModelService;

    @Autowired
    public AdminCarController(CarService carService, CarModelService carModelService) {
        this.carService = carService;
        this.carModelService = carModelService;
    }

    @GetMapping("/")
    public String showCarList(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "admin/cars/list";
    }

    @GetMapping("/create")
    public String createCar(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("carModels", carModelService.findAll());
        return "admin/cars/create";
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public String createCar(
            Model model, @ModelAttribute("car") Car car,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        try {
            carService.create(car, imageFile);
            redirectAttributes.addFlashAttribute("successMessage", "Car created successfully");
            return "redirect:/admin/cars/";
        } catch (Exception e) {
            model.addAttribute("carModels", carModelService.findAll());
            model.addAttribute("car", car);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "admin/cars/create";
        }
    }

    @GetMapping("/detail/{id}")
    public String showCarDetail(
            Model model,
            @PathVariable("id") Integer id) {

        model.addAttribute("car", carService.findById(id));
        return "admin/cars/detail";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(
            Model model,
            @PathVariable("id") Integer id) {
        Car car = carService.findById(id);
        model.addAttribute("car", car);
        return "admin/cars/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteCar(
            Model model,
            @PathVariable("id") Integer id,
            RedirectAttributes redirectAttributes) {

        carService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Car deleted successfully");
        return "redirect:/admin/cars/";
    }

    @GetMapping("/update/{id}")
    public String updateCar(
            Model model,
            @PathVariable("id") Integer id) {

        Car car = carService.findById(id);
        model.addAttribute("car", car);
        model.addAttribute("carModels", carModelService.findAll());
        return "admin/cars/update";
    }

    @PostMapping(value = "/update", consumes = "multipart/form-data")
    public String updateCar(
            Model model,
            @ModelAttribute("car") Car car,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        try {
            carService.update(car, imageFile);
            redirectAttributes.addFlashAttribute("successMessage", "Car updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Update failed: " + e.getMessage());
            return "redirect:/admin/cars/update/" + car.getId();
        }
        return "redirect:/admin/cars/";
    }


}
