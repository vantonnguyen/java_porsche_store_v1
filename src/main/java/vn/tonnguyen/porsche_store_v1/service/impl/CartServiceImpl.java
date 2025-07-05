package vn.tonnguyen.porsche_store_v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.model.Cart;
import vn.tonnguyen.porsche_store_v1.model.CartDetail;
import vn.tonnguyen.porsche_store_v1.model.User;
import vn.tonnguyen.porsche_store_v1.repository.CartRepository;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;
import vn.tonnguyen.porsche_store_v1.service.interf.CartService;
import vn.tonnguyen.porsche_store_v1.service.interf.UserService;

import java.time.Instant;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final CarService carService;


    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserService userService, CarService carService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.carService = carService;
    }

    @Override
    public Cart findByUserId(Integer userId) {
        return cartRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public List<CartDetail> findDetails(Integer cartId) {
        return cartRepository.findDetails(cartId);
    }

    @Override
    @Transactional
    public void addToCart(String username, Integer carId, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must > 0");
        User user = userService.findByUsername(username);
        Car car = carService.findById(carId);
        Cart cart = this.findByUserId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setStatus(Cart.Status.ACTIVE);
            cart.setCreatedAt(Instant.now());
            this.save(cart);
        }
        CartDetail line = cart.getCartDetails().stream()
                .filter(l -> l.getCar().getId().equals(carId))
                .findFirst().orElse(null);
        if (line != null) {
            Integer newQty = line.getQuantity() + quantity;
            if (newQty > line.getCar().getStock()) {
                throw new RuntimeException("The quantity in your cart exceeds our car stock");
            }
            line.setQuantity(newQty);
        } else {
            CartDetail newLine = new CartDetail();
            newLine.setCart(cart);
            newLine.setQuantity(quantity);
            newLine.setCar(car);
            newLine.setPrice(car.getPrice());
            cart.getCartDetails().add(newLine);
        }
    }

    @Override
    @Transactional
    public void deleteFromCart(String username, Integer carId) {
        User user = userService.findByUsername(username);
        Cart cart = this.findByUserId(user.getId());
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        CartDetail line = cart.getCartDetails().stream()
                .filter(l -> l.getCar().getId().equals(carId))
                .findFirst().orElse(null);
        if (line == null) {
            throw new RuntimeException("Cart is empty");
        }
        cart.getCartDetails().remove(line);
    }

    @Override
    public long countItemsByUserId(Integer userId) {
        Cart cart = this.findByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        return cart.getCartDetails().size();
    }

}
