package vn.tonnguyen.porsche_store_v1.service.interf;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentService {
    String createVNPayURL(long amount, String orderInfo, String orderId, String language, String ipAddr);
    Map<String, String> handelVNPayIPN(HttpServletRequest request);
    String getResponseCode(HttpServletRequest request);
}
