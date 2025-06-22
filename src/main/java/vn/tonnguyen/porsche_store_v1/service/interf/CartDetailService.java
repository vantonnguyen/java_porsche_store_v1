package vn.tonnguyen.porsche_store_v1.service.interf;

import vn.tonnguyen.porsche_store_v1.model.Cart;
import vn.tonnguyen.porsche_store_v1.model.CartDetail;

public interface CartDetailService {
    CartDetail save(CartDetail cartDetail);
    CartDetail findByCartAndCarId(Cart cart,Integer carId);
}
