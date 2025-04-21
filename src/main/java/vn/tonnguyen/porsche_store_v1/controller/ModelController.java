package vn.tonnguyen.porsche_store_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.tonnguyen.porsche_store_v1.service.interf.ModelService;

@Controller
@RequestMapping("/models")
public class ModelController {
    private final ModelService modelService;

    @Autowired
    public ModelController(final ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping()  //   http://localhost:8080/models?category=911
    public String getModelByCategory(@RequestParam String category, Model model) {
        model.addAttribute("models", modelService.findByCategory_Name(category));
        return "model/models";
    }
}
