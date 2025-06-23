package vn.tonnguyen.porsche_store_v1.service.interf;

import vn.tonnguyen.porsche_store_v1.model.Cart;
import vn.tonnguyen.porsche_store_v1.model.CartDetail;
import vn.tonnguyen.porsche_store_v1.model.User;

import java.util.List;

public interface CartService {
    Cart findByUser(User user);
    Cart save(Cart cart);
    void addToCart(String username, Integer carId, int quantity);
}
