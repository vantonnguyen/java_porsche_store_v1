package vn.tonnguyen.porsche_store_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.tonnguyen.porsche_store_v1.model.Cart;
import vn.tonnguyen.porsche_store_v1.model.CartDetail;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("""
           SELECT DISTINCT c
           FROM Cart c
           LEFT JOIN FETCH c.cartDetails d
           LEFT JOIN FETCH d.car
           WHERE c.user.id = :userId
           """)
    Optional<Cart> findByUserId(@Param("userId") Integer userId);
    @Query("select cd from CartDetail cd where cd.cart.id = :cartId")
    List<CartDetail> findDetails(@Param("cartId") Integer cartId);
    @Modifying
    @Query("delete from CartDetail d where d.cart.id = :cartId")
    void deleteAllCartDetailsByCartId(@Param("cartId") Integer cartId);
}
