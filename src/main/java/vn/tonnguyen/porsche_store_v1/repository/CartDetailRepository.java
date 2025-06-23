package vn.tonnguyen.porsche_store_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.model.Cart;
import vn.tonnguyen.porsche_store_v1.model.CartDetail;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    Optional<CartDetail> findByCartAndCarId(Cart cart,Integer carId);
    List<CartDetail> findByCart(Cart cart);
    long countByCart(Cart cart);
    //void deleteById(Integer id);
    //void delete(CartDetail cartDetail);
    @Modifying
    @Query("DELETE FROM CartDetail cd WHERE cd.id = :id")
    void deleteByIdCustom(@Param("id") Integer id);
}
