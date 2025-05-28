package com.example.tableservice.dto.request;

import com.example.tableservice.constant.Constant;
import com.example.tableservice.enums.TableStatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for creating or updating a restaurant table.
 * Contains fields and validation rules for table details.
 */
@Data
public class RestaurantTableRequest {

    @NotNull(message = Constant.NUMBER_REQUIRED)
    @Min(value = Constant.N_ONE, message = Constant.NUMBER_MIN)
    private Integer number;

    @NotNull(message = Constant.TABLE_TYPE_REQUIRED)
    private Long tableTypeId;

    @Min(value = Constant.N_ZERO, message = Constant.PRICE_LARGER_THAN_ZERO)
    private BigDecimal price;

    @NotNull(message = Constant.TABLE_STATUS_REQUIRED)
    private TableStatusEnum status;
}
