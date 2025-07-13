package vn.tonnguyen.porsche_store_v1.service.interf;

import org.springframework.data.repository.query.Param;
import vn.tonnguyen.porsche_store_v1.model.Order;
import vn.tonnguyen.porsche_store_v1.model.OrderDetail;
import vn.tonnguyen.porsche_store_v1.model.User;

import java.util.List;

public interface OrderService {
    Order createOrder(String username, User order_user, String paymentMethod);
    List<Order> findByUserId(Integer userId);
    List<OrderDetail> findOrderDetailsWithCarByOrderId(Integer orderId);
}
