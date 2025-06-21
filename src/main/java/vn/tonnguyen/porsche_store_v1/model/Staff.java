package vn.tonnguyen.porsche_store_v1.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "staffs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Staff.Role role;
    public enum Role {
        admin,
        warehouse,
        shipper
    }

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name="sex")
    private Staff.Sex sex;
    public enum Sex {
        male,
        female
    }

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "CCCD", length = 20)
    private String cccd;

    @Column(name = "address", length = 100)
    private String address;

    @ColumnDefault("current_timestamp()")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("current_timestamp()")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private User.Status status;
    public enum Status {
        active,
        locked,
        disabled
    }
}