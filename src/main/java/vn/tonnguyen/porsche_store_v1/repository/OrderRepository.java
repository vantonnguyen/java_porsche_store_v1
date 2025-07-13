package vn.tonnguyen.porsche_store_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.tonnguyen.porsche_store_v1.model.Order;
import vn.tonnguyen.porsche_store_v1.model.OrderDetail;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
    @Query("select distinct od from OrderDetail od join fetch od.car where od.order.id = :orderId")
    List<OrderDetail> findOrderDetailsWithCarByOrderId(@Param("orderId") Integer orderId);
}
