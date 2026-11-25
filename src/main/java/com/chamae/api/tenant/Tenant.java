package com.chamae.api.tenant;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tenants")
@Getter
@Setter
@NoArgsConstructor
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal averageTicket;


}
