package vn.tonnguyen.porsche_store_v1.service.interf;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentService {
    Map<String, String> processVNPayIPN(HttpServletRequest request);
    String createVNPayPayment(long amount, String orderInfo, String orderId, String language, String ipAddr);
}
