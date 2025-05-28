package com.example.tabletypeservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing a type of restaurant table.
 * Used for categorizing tables by capacity, description, and other attributes.
 * Supports stored procedure for creation.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@NamedStoredProcedureQuery(
    name = "sp_create_table_type",
    procedureName = "sp_create_table_type",
    parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_name", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_description", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_capacity", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_new_id", type = Long.class)
    })
public class TableType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Table type name is required")
    @Size(max = 150, message = "Table type name must be at most 150 characters")
    @Column(name = "name", unique = true, nullable = false, length = 150)
    private String name;

    @Size(max = 150, message = "Description must be at most 150 characters")
    @Column(name = "description", columnDefinition = "TEXT", length = 150)
    private String description;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 20, message = "Capacity must be at most 20")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

