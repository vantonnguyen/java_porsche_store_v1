package vn.tonnguyen.porsche_store_v1.service.impl;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tonnguyen.porsche_store_v1.model.*;
import vn.tonnguyen.porsche_store_v1.repository.OrderRepository;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;
import vn.tonnguyen.porsche_store_v1.service.interf.CartService;
import vn.tonnguyen.porsche_store_v1.service.interf.OrderService;
import vn.tonnguyen.porsche_store_v1.service.interf.UserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final CarService carService;

    public OrderServiceImpl(UserService userService, CartService cartService, OrderRepository orderRepository, CarService carService) {
        this.userService = userService;
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.carService = carService;
    }

    @Override
    @Transactional
    public Order createOrder(String username, User order_user, String paymentMethod) {
        User user = userService.findByUsername(username);
        Cart cart = cartService.findByUserId(user.getId());
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        if (cart.getCartDetails().isEmpty()) {
            throw new RuntimeException("Cart details not found");
        }
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(Instant.now());
        order.setFullName(order_user.getFullName());
        order.setPhone(order_user.getPhone());
        order.setShippingAddress(order_user.getAddress());
        order.setPaymentMethod(paymentMethod);
        order.setStatus(Order.Status.PENDING);
        BigDecimal shippingFee = BigDecimal.ZERO;
        order.setShippingFee(shippingFee);
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartDetail cartDetail : cart.getCartDetails()) {
            BigDecimal price = cartDetail.getPrice().setScale(2, RoundingMode.HALF_UP);
            int quantity = cartDetail.getQuantity();
            BigDecimal subTotal = price.multiply(BigDecimal.valueOf(quantity))
                    .setScale(2, RoundingMode.HALF_UP);

            OrderDetail od = new OrderDetail();
            od.setOrder(order);
            od.setCar(cartDetail.getCar());
            od.setCarName(cartDetail.getCar().getName());
            od.setQuantity(quantity);
            od.setPrice(price);
            od.setSubTotal(subTotal);

            order.getOrderDetails().add(od);
            totalAmount = totalAmount.add(subTotal);
            carService.decreaseStockAfterOrder(cartDetail.getCar().getId(), quantity);
        }

        totalAmount = totalAmount.setScale(2, RoundingMode.HALF_UP);
        order.setTotalAmount(totalAmount);

        BigDecimal finalAmount = totalAmount.add(shippingFee)
                .setScale(2, RoundingMode.HALF_UP);
        order.setFinalAmount(finalAmount);

        orderRepository.save(order);
        cartService.deleteAllCartDetailsByCartId(cart.getId());
        return order;
    }

    @Override
    public  List<Order> findByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public  List<OrderDetail> findOrderDetailsWithCarByOrderId(Integer orderId){
        return orderRepository.findOrderDetailsWithCarByOrderId(orderId);
    }

    @Override
    public Order findById(Integer orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
