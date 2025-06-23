package vn.tonnguyen.porsche_store_v1.service.interf;

import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.model.Cart;
import vn.tonnguyen.porsche_store_v1.model.CartDetail;

import java.util.List;

public interface CartDetailService {
    CartDetail save(CartDetail cartDetail);
    CartDetail findByCartAndCarId(Cart cart,Integer carId);
    List<CartDetail> findByCart(Cart cart);
    long countByCart(Cart cart);
    void deleteById(Integer id);
    void deleteByIdCustom(Integer id);
}
