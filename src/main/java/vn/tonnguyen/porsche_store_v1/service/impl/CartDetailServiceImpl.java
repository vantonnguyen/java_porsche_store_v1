package vn.tonnguyen.porsche_store_v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tonnguyen.porsche_store_v1.model.Cart;
import vn.tonnguyen.porsche_store_v1.model.CartDetail;
import vn.tonnguyen.porsche_store_v1.repository.CartDetailRepository;
import vn.tonnguyen.porsche_store_v1.service.interf.CartDetailService;

import java.util.List;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    private final CartDetailRepository cartDetailRepository;

    @Autowired
    public CartDetailServiceImpl(CartDetailRepository cartDetailRepository) {
        this.cartDetailRepository = cartDetailRepository;
    }

    @Override
    public CartDetail save(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public CartDetail findByCartAndCarId(Cart cart, Integer carId) {
        return cartDetailRepository.findByCartAndCarId(cart, carId)
                .orElse(null);
    }

    @Override
    public List<CartDetail> findByCart(Cart cart) {
        return cartDetailRepository.findByCart(cart);
    }

    @Override
    public long countByCart(Cart cart) {
        return cartDetailRepository.countByCart(cart);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        System.out.println("THỰC SỰ GỌI deleteById với ID = " + id);
        cartDetailRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIdCustom(Integer id) {
        cartDetailRepository.deleteByIdCustom(id);
    }

}
