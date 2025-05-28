package com.example.tabletypeservice.service;

import com.example.tabletypeservice.dto.request.TableTypeRequest;
import com.example.tabletypeservice.dto.response.TableTypeResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface for managing table types.
 * Defines business operations for creating, updating, retrieving, and deleting table types.
 */
public interface ITableTypeService {

    /**
     * Creates a new table type.
     *
     * @param request the {@link TableTypeRequest} containing the details of the new table type
     * @return a {@link TableTypeResponse} representing the created table type
     */
    TableTypeResponse createTableType(TableTypeRequest request);

    /**
     * Updates an existing table type.
     *
     * @param id      the ID of the table type to update
     * @param request the {@link TableTypeRequest} containing the updated details
     * @return a {@link TableTypeResponse} representing the updated table type
     */
    TableTypeResponse updateTableType(Long id, TableTypeRequest request);

    /**
     * Deletes a table type by its ID.
     *
     * @param id the ID of the table type to delete
     */
    void deleteTableType(Long id);


    /**
     * Retrieves all table types without pagination, searching by keyword and table capacity.
     *
     * @param keyword The keyword to search for in the table type names.
     * @param capacity The capacity to filter the table types by number of seats. If null, no filtering by capacity will be applied.
     * @return A list of TableTypeResponse objects containing information about the table types.
     */
    List<TableTypeResponse> getAllTableTypesNotPagination(String keyword, Integer capacity);


    /**
     * Retrieves all table types with pagination, searching by keyword, table capacity, and sorting based on the given criteria.
     *
     * @param keyword The keyword to search for in the table type names.
     * @param capacity The capacity to filter the table types by number of seats. If null, no filtering by capacity will be applied.
     * @param page The page number (starting from 0) for pagination.
     * @param size The number of records per page.
     * @param sortBy The column by which to sort (e.g., "name", "capacity").
     * @param sortDir The direction of sorting ("asc" for ascending, "desc" for descending).
     * @return A Page object containing the table types with pagination based on the search and sorting criteria.
     */
    Page<TableTypeResponse> getAllTableTypesPagination(String keyword, Integer capacity, int page, int size, String sortBy, String sortDir);

    /**
     * Retrieves a specific table type by its ID.
     *
     * @param id the ID of the table type to retrieve
     * @return a {@link TableTypeResponse} representing the retrieved table type
     */
    TableTypeResponse getDetailTableType(Long id);
}
