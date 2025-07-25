package vn.tonnguyen.porsche_store_v1.service.interf;

import org.springframework.data.repository.query.Param;
import vn.tonnguyen.porsche_store_v1.model.Cart;
import vn.tonnguyen.porsche_store_v1.model.CartDetail;

import java.util.List;

public interface CartService {
    Cart findByUserId(Integer userId);
    Cart save(Cart cart);
    void addToCart(String username, Integer carId, int quantity);
    List<CartDetail> findDetails(Integer cartId);
    void deleteFromCart(String username, Integer carId);
    long countItemsByUserId(Integer userId);
    void updateQuantity(String username, Integer carId, int delta);
    void deleteAllCartDetailsByCartId(Integer cartId);

}
