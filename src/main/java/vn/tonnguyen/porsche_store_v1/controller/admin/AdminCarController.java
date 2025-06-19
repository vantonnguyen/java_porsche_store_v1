package vn.tonnguyen.porsche_store_v1.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.tonnguyen.porsche_store_v1.extension.UpLoadImage;
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

    @GetMapping("/add")
    public String addCar(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("carModels", carModelService.findAll());
        return "admin/cars/add";
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute("car") Car car, @RequestParam("imageFile") MultipartFile imageFile, Model model) {
        try {
            if (imageFile.isEmpty()) {
                model.addAttribute("error", "Please select a file");
                model.addAttribute("carModels", carModelService.findAll());
                return "admin/cars/add";
            }
            String fileName = UpLoadImage.processUpload(imageFile);
            if (fileName == null) {
                model.addAttribute("error", "Upload file failed");
                model.addAttribute("carModels", carModelService.findAll());
                return "admin/cars/add";
            }
            car.setImageUrl(fileName);
            carService.save(car);
            return "redirect:/admin/cars/";
        } catch (Exception ex) {
            model.addAttribute("error", "Đã xảy ra lỗi khi lưu xe: " + ex.getMessage());
            model.addAttribute("car", car);
            model.addAttribute("carModels", carModelService.findAll());
            return "admin/cars/add";
        }
    }

    @RequestMapping("/detail")
    public String showCarDetail(Model model, @RequestParam int id) {
        model.addAttribute("car",carService.findById(id));
        return "admin/cars/detail";
    }

    @RequestMapping("/delete")
    public String deleteCar(Model model, @RequestParam int id) {
        model.addAttribute("car",carService.findById(id));
        carService.deleteById(id);
        return "admin/cars/delete";
    }


}
