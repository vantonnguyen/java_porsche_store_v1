package vn.tonnguyen.porsche_store_v1.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cart_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false, precision = 20, scale = 2)
    private BigDecimal price;

    @ManyToOne()
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne()
    @JoinColumn(name = "car_id")
    private Car car;

    public BigDecimal getSubTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

}