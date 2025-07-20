package vn.tonnguyen.porsche_store_v1.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.tonnguyen.porsche_store_v1.service.interf.PaymentService;
import vn.tonnguyen.porsche_store_v1.util.VNPayUtil;

import java.math.BigDecimal;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/create")
    public String redirectToVNPay(HttpServletRequest request,
                                  RedirectAttributes redirectAttributes,
                                  @RequestParam("amount") String amountStr,
                                  @RequestParam("orderInfo") String orderInfo,
                                  @RequestParam("orderId") String orderId,
                                  @RequestParam(value = "language", required = false, defaultValue = "vn") String language) {
        try {
            BigDecimal decimalAmount = new BigDecimal(amountStr);
            if (decimalAmount.compareTo(BigDecimal.ZERO) <= 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "Amount must be greater than 0.");
                return "redirect:/cart";
            }
            long amount = decimalAmount.longValue();
            String ipAddr = VNPayUtil.getIpAddress(request);
            String paymentUrl = paymentService.createVNPayPayment(amount, orderInfo, orderId, language, ipAddr);
            System.out.println(paymentUrl);
            return "redirect:" + paymentUrl;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/cart";
        }
    }

}
