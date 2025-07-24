package vn.tonnguyen.porsche_store_v1.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.tonnguyen.porsche_store_v1.service.interf.PaymentService;
import vn.tonnguyen.porsche_store_v1.util.VNPayUtil;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping()
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payment/vnpay-create")
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
            String paymentUrl = paymentService.createVNPayURL(amount, orderInfo, orderId, language, ipAddr);
            return "redirect:" + paymentUrl;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/cart";
        }
    }

    @PostMapping("/IPN")
    @ResponseBody
    public Map<String, String> handleVNPayIPN(HttpServletRequest request) {
        return paymentService.handelVNPayIPN(request);
    }

    @GetMapping("/payment/vnpay-return")
    public String handleVnpayReturn(HttpServletRequest request, Model model) {
        try {
            String responseCode = paymentService.getResponseCode(request);
            switch (responseCode) {
                case "00":
                    model.addAttribute("message", "Payment successful!");
                    return "payment/success";

                case "02":
                    model.addAttribute("message", "Invalid signature!");
                    return "payment/error";

                case "99":
                    model.addAttribute("message", "System error occurred.");
                    return "payment/error";

                default:
                    model.addAttribute("message", "Payment failed!");
                    return "payment/failed";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
