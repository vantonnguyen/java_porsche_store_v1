package vn.tonnguyen.porsche_store_v1.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.tonnguyen.porsche_store_v1.config.VNPayConfig;
import vn.tonnguyen.porsche_store_v1.model.Order;
import vn.tonnguyen.porsche_store_v1.service.interf.OrderService;
import vn.tonnguyen.porsche_store_v1.service.interf.PaymentService;
import vn.tonnguyen.porsche_store_v1.util.VNPayUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    private String secretKey = VNPayConfig.vnp_HashSecret;

    private final OrderService orderService;

    @Autowired
    public PaymentServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String createVNPayURL(long amount, String orderInfo, String orderId, String language, String ipAddr) {
        try {
            String vnp_TxnRef = orderId;
            String vnp_IpAddr = ipAddr;
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(calendar.getTime());
            calendar.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(calendar.getTime());

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
            vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
            vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", orderInfo);
            vnp_Params.put("vnp_OrderType", "other");
            vnp_Params.put("vnp_Locale", language);
            vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            String paymentUrl = VNPayUtil.buildPaymentUrl(vnp_Params);
            return paymentUrl;
        } catch (Exception e) {
            return "Exception" + e.getMessage();
        }
    }

    @Override
    public String getResponseCode(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> {
            if (value.length > 0) fields.put(key, value[0]);
        });
        String vnpSecureHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        try {
            String signValue = VNPayUtil.hashAllFields(fields, secretKey);
            if (vnpSecureHash != null && vnpSecureHash.equals(signValue)) {
                return fields.getOrDefault("vnp_ResponseCode", "Unknown");
            } else {
                return "02";
            }
        } catch (Exception e) {
            return "99";
        }
    }

    @Override
    public Map<String, String> handelVNPayIPN(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> {
            if (value.length > 0) fields.put(key, value[0]);
        });
        String vnpSecureHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        Map<String, String> response = new HashMap<>();
        try {
            String signValue = VNPayUtil.hashAllFields(fields, secretKey);
            if (!signValue.equals(vnpSecureHash)) {
                response.put("RspCode", "97");
                response.put("Message", "Invalid Checksum");
                return response;
            }
            String vnpTxnRef = request.getParameter("vnp_TxnRef");
            String vnpAmount = request.getParameter("vnp_Amount");
            String vnpResponseCode = request.getParameter("vnp_ResponseCode");
            Order order = orderService.findById(Integer.parseInt(vnpTxnRef));
            if (order == null) {
                response.put("RspCode", "01");
                response.put("Message", "Order Not Found");
                return response;
            }
            if (order.getStatus() == Order.Status.CONFIRMED) {
                response.put("RspCode", "02");
                response.put("Message", "Order Already Confirmed");
                return response;
            }
            BigDecimal amount = BigDecimal.valueOf(Long.parseLong(vnpAmount)).divide(BigDecimal.valueOf(100));
            if (amount != order.getFinalAmount()) {
                response.put("RspCode", "04");
                response.put("Message", "Invalid Amount");
                return response;
            }
            if ("00".equals(vnpResponseCode)) {
                order.setStatus(Order.Status.CONFIRMED);
            } else {
                order.setStatus(Order.Status.FAILED);
            }
            orderService.save(order);
            response.put("RspCode", "00");
            response.put("Message", "Confirm Success");
        } catch (Exception e) {
            response.put("RspCode", "99");
            response.put("Message", "Unknown Error");
        }
        return response;
    }

}
