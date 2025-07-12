package vn.tonnguyen.porsche_store_v1.service.interf;

import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.model.Order;
import vn.tonnguyen.porsche_store_v1.model.User;

public interface OrderService {
    Order createOrder(String username, User order_user, String paymentMethod);
}
