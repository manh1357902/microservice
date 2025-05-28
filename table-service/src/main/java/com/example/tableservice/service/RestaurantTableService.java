package com.example.tableservice.service;

import com.example.tableservice.dto.request.RestaurantTableRequest;
import com.example.tableservice.dto.response.RestaurantTableResponse;
import com.example.tableservice.enums.TableStatusEnum;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

/**
 * Service interface for managing restaurant tables.
 * Defines business operations for creating, updating, retrieving, and deleting restaurant tables.
 */
public interface RestaurantTableService {

    /**
     * Creates a new restaurant table.
     *
     * @param request the {@link RestaurantTableRequest} containing the details of the new table
     * @return a {@link RestaurantTableResponse} representing the created table
     */
    RestaurantTableResponse create(RestaurantTableRequest request);

    /**
     * Retrieves the details of a specific restaurant table by its ID.
     *
     * @param id the ID of the table to retrieve
     * @return a {@link RestaurantTableResponse} representing the table
     */
    RestaurantTableResponse getDetail(Long id);

    /**
     * Retrieves a paginated and sorted list of RestaurantTable entities, optionally filtered by several parameters.
     *
     * @param number      optional filter for the table number. If null, this filter is not applied.
     * @param tableTypeId optional filter for the table type ID. If null, this filter is not applied.
     * @param minPrice    optional filter for the minimum price. If null, this filter is not applied.
     * @param maxPrice    optional filter for the maximum price. If null, this filter is not applied.
     * @param status      optional filter for the table status. If null, this filter is not applied.
     * @param page        the page number to retrieve (for pagination).
     * @param size        the number of records per page (for pagination).
     * @param sortBy      the field by which the results will be sorted.
     * @param sortDir     the direction of sorting (either "ASC" or "DESC").
     * @return a paginated list of RestaurantTableResponse DTOs that match the specified filters, sorted and paginated.
     */
    Page<RestaurantTableResponse> getAllTable(Integer number, Long tableTypeId, BigDecimal minPrice, BigDecimal maxPrice, TableStatusEnum status, int page, int size, String sortBy, String sortDir);

    /**
     * Updates an existing restaurant table.
     *
     * @param id      the ID of the table to update
     * @param request the {@link RestaurantTableRequest} containing the updated details
     * @return a {@link RestaurantTableResponse} representing the updated table
     */
    RestaurantTableResponse update(Long id, RestaurantTableRequest request);

    /**
     * Deletes a restaurant table by its ID.
     *
     * @param id the ID of the table to delete
     */
    void delete(Long id);
}
