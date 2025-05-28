package com.example.tableservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for returning table type details in API responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTypeResponse {

    private Long id;

    private String name;

    private String description;

    private Integer capacity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

