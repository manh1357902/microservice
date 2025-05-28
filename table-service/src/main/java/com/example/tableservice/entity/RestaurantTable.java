package com.example.tableservice.entity;


import com.example.tableservice.constant.Constant;
import com.example.tableservice.enums.TableStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing a restaurant table.
 * Maps to the restaurant_table table in the database and contains details such as number, type, price, and status.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Table number is required")
    @Min(value = 1, message = "Table number must be at least 1")
    private Integer number;

    @NotNull(message = "Table type is required")
    private Long tableTypeId;

    @DecimalMin(value = Constant.ZERO, message = Constant.PRICE_LARGER_THAN_ZERO)
    @Column(precision = Constant.N_TEN, scale = Constant.N_TWO, name = "price")
    private BigDecimal price;

    @NotNull(message = "Table status is required")
    @Enumerated(EnumType.STRING)
    private TableStatusEnum status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean isDeleted;

    @PrePersist
    private void prePersist() {
        this.isDeleted = false;
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

