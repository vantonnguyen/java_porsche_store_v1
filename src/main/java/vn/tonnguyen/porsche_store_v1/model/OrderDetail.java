package vn.tonnguyen.porsche_store_v1.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "car_name", nullable = false)
    private String carName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false, precision = 20, scale = 2)
    private BigDecimal price;

    @Column(name = "sub_total", nullable = false, precision = 20, scale = 2)
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public BigDecimal getSubTotal() {
        return this.price.multiply(BigDecimal.valueOf(this.quantity));
    }

}