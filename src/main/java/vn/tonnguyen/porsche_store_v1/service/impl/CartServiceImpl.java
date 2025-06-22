package vn.tonnguyen.porsche_store_v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.model.Cart;
import vn.tonnguyen.porsche_store_v1.model.CartDetail;
import vn.tonnguyen.porsche_store_v1.model.User;
import vn.tonnguyen.porsche_store_v1.repository.CartDetailRepository;
import vn.tonnguyen.porsche_store_v1.repository.CartRepository;
import vn.tonnguyen.porsche_store_v1.repository.UserRepository;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;
import vn.tonnguyen.porsche_store_v1.service.interf.CartDetailService;
import vn.tonnguyen.porsche_store_v1.service.interf.CartService;
import vn.tonnguyen.porsche_store_v1.service.interf.UserService;

import java.time.Instant;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartDetailService cartDetailService;
    private final UserService userService;
    private final CarService carService;


    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartDetailService cartDetailService, UserService userService, CarService carService) {
        this.cartRepository = cartRepository;
        this.cartDetailService = cartDetailService;
        this.userService = userService;
        this.carService = carService;
    }

    @Override
    public Cart findByUser(User user) {
        return cartRepository.findByUser(user).orElse(null);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void addToCart(String username, Integer carId, int quantity) {
        User user = userService.findByUsername(username);
        Cart cart = this.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setStatus(Cart.Status.ACTIVE);
            cart.setCreatedAt(Instant.now());
            this.save(cart);
        }
        CartDetail existingDetail = cartDetailService.findByCartAndCarId(cart,carId);
        if (existingDetail != null) {
            existingDetail.setQuantity(existingDetail.getQuantity() + quantity);
            cartDetailService.save(existingDetail);
        } else {
            CartDetail newdetail = new CartDetail();
            Car car = carService.findById(carId);
            newdetail.setCart(cart);
            newdetail.setQuantity(quantity);
            newdetail.setCar(car);
            newdetail.setPrice(car.getPrice());
            cartDetailService.save(newdetail);
        }

    }
}
