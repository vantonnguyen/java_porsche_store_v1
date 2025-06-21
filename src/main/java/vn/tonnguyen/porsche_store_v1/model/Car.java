package vn.tonnguyen.porsche_store_v1.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status = Status.active;
    public enum Status {
        active,
        discontinued
    }

    @Column(name="name",nullable = false,length = 100)
    private String name;

    @Column(name="slug",nullable = false, length = 150, unique = true)
    private String slug;

    @Column(name="year")
    private int year;

    @Column(name="price",nullable = false, precision = 20, scale = 2)
    private BigDecimal price;

    @Column(name="color",length = 100)
    private String color;

    @Column(name = "engine",length = 100)
    private String engine;

    @Column(name="horsepower")
    private int horsepower;

    @Column(name = "max_speed")
    private int maxSpeed;

    @Enumerated(EnumType.STRING)
    @Column(name="transmission")
    private Transmission transmission;
    public enum Transmission {
        Automatic, Manual
    }

    @Enumerated(EnumType.STRING)
    @Column(name="fuel_type")
    private FuelType fuelType;
    public enum FuelType {
        Gasoline, Electric, Hybrid
    }

    @Column(name="description",length = 255)
    private String description;

    @Column(name="stock")
    private int stock = 0;

    @Column(name = "image_url", length = 100)
    private String imageUrl;

    @ColumnDefault("current_timestamp()")
    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private CarModel carModel;


}
