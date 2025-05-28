package com.example.tableservice.controller;

import com.example.tableservice.constant.Constant;
import com.example.tableservice.dto.request.RestaurantTableRequest;
import com.example.tableservice.dto.response.ApiResponse;
import com.example.tableservice.dto.response.CustomPageResponse;
import com.example.tableservice.dto.response.RestaurantTableResponse;
import com.example.tableservice.enums.TableStatusEnum;
import com.example.tableservice.service.RestaurantTableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * REST controller for managing restaurant tables.
 * Provides endpoints for CRUD operations, pagination, and filtering of restaurant tables.
 */
@RestController
@RequestMapping("/api/v1/restaurant-tables")
@RequiredArgsConstructor
public class RestaurantTableController {

    private final RestaurantTableService restaurantTableService;

    /**
     * Retrieves a paginated and sorted list of restaurant tables, optionally filtered by several parameters.
     *
     * @param number      Optional filter for the table number. If null, this filter is not applied.
     * @param tableTypeId Optional filter for the table type ID. If null, this filter is not applied.
     * @param minPrice    Optional filter for the minimum price. If null, this filter is not applied.
     * @param maxPrice    Optional filter for the maximum price. If null, this filter is not applied.
     * @param status      Optional filter for the table status. If null, this filter is not applied.
     * @param page        The page number to retrieve (starts from 1). Default is 1.
     * @param size        The number of records per page. Default is 10.
     * @param sortBy      The field by which the results will be sorted. Default is by ID.
     * @param sortDir     The direction of sorting ("ASC" or "DESC"). Default is ascending.
     * @return a ResponseEntity containing the paginated list of restaurant tables, wrapped in a custom API response.
     */
    @GetMapping("/page")
    public ResponseEntity<Object> getAll(
            @RequestParam(required = false) Integer number,
            @RequestParam(required = false) Long tableTypeId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) TableStatusEnum status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Page<RestaurantTableResponse> restaurantTablePages = restaurantTableService.getAllTable(number, tableTypeId, minPrice,maxPrice, status, page - 1, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(Constant.SUCCESS, CustomPageResponse.fromPage(restaurantTablePages)));
    }

    /**
     * Retrieves the details of a specific restaurant table by its ID.
     *
     * @param id the ID of the restaurant table
     * @return a {@link ResponseEntity} containing the restaurant table details {@link RestaurantTableResponse}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDetail(@PathVariable Long id) {
        RestaurantTableResponse restaurantTableResponse = restaurantTableService.getDetail(id);
        return ResponseEntity.ok(ApiResponse.success(Constant.SUCCESS, restaurantTableResponse));
    }

    /**
     * Creates a new restaurant table.
     *
     * @param request the {@link RestaurantTableRequest} containing the details of the new table
     * @return a {@link ResponseEntity} containing the created {@link RestaurantTableResponse}
     */
    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody RestaurantTableRequest request) {
        RestaurantTableResponse restaurantTableResponse = restaurantTableService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(Constant.SUCCESS, restaurantTableResponse));
    }

    /**
     * Updates an existing restaurant table.
     *
     * @param id      the ID of the table to update
     * @param request the {@link RestaurantTableRequest} containing the updated details
     * @return a {@link ResponseEntity} containing the updated {@link RestaurantTableResponse}
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody RestaurantTableRequest request) {
        RestaurantTableResponse restaurantTableResponse = restaurantTableService.update(id,request);
        return ResponseEntity.ok(ApiResponse.success(Constant.SUCCESS, restaurantTableResponse));
    }

    /**
     * Deletes a restaurant table by its ID.
     *
     * @param id the ID of the table to delete
     * @return a {@link ResponseEntity} with no content
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantTableService.delete(id);
        return ResponseEntity.noContent().build();
    }
}