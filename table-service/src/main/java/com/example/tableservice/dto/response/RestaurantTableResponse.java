package com.example.tableservice.dto.response;

import com.example.tableservice.enums.TableStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for returning restaurant table details in API responses.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantTableResponse {

    private Long id;
    private Integer number;
    private Long tableTypeId;
    private BigDecimal price;
    private TableStatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

