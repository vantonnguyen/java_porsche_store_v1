package vn.tonnguyen.porsche_store_v1.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ColumnDefault("current_timestamp()")
    @Column(name = "order_date", nullable = false)
    private Instant orderDate;

    @Column(name = "total_amount", nullable = false, precision = 20, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "final_amount", nullable = false, precision = 20, scale = 2)
    private BigDecimal finalAmount;

    @ColumnDefault("'COD'")
    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @ColumnDefault("'PENDING'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public enum Status {
        PENDING, CONFIRMED, PROCESSING, SHIPPING, DELIVERED, COMPLETED, CANCELLED, FAILED, REFUNDED
    }

    @ColumnDefault("'UNPAID'")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    public enum PaymentStatus {
        UNPAID, SUCCESS, FAILED
    }

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @Column(name = "note")
    private String note;

    @ColumnDefault("0.00")
    @Column(name = "shipping_fee", precision = 10, scale = 2)
    private BigDecimal shippingFee;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();
}