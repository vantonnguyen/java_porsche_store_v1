package vn.tonnguyen.porsche_store_v1.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.tonnguyen.porsche_store_v1.extension.UpLoadImage;
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

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "admin/car/cars";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("models", modelService.findAll());
        return "admin/car/create";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute("car") Car car, @RequestParam("imageFile") MultipartFile imageFile, Model model) {
        try {
            if (imageFile.isEmpty()) {
                model.addAttribute("error", "Please select a file");
                model.addAttribute("models", modelService.findAll());
                return "admin/car/create";
            }
            String fileName = UpLoadImage.processUpload(imageFile);
            if (fileName == null) {
                model.addAttribute("error", "Upload file failed");
                model.addAttribute("models", modelService.findAll());
                return "admin/car/create";
            }
            car.setImageUrl(fileName);
            carService.save(car);
            return "redirect:/admin/cars";
        } catch (Exception ex) {
            model.addAttribute("error", "Đã xảy ra lỗi khi lưu xe: " + ex.getMessage());
            model.addAttribute("car", car);
            model.addAttribute("models", modelService.findAll());
            return "admin/car/create"; // quay lại form tạo xe
        }
    }


}
