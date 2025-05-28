package com.example.tabletypeservice.dto.request;

import com.example.tabletypeservice.constant.Constant;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for creating or updating a table type.
 * Contains fields and validation rules for table type details.
 */
@Data
public class TableTypeRequest {

    @NotBlank(message = Constant.NAME_REQUIRED)

    private String name;

    @Size(max = Constant.N_ONE_THOUSAND, message = Constant.DESCRIPTION_SIZE)
    private String description;

    @NotNull(message = Constant.CAPACITY_REQUIRED)
    @Min(value = Constant.N_ONE, message = Constant.TABLE_TYPE_CAPACITY_MIN)
    private Integer capacity;
}
