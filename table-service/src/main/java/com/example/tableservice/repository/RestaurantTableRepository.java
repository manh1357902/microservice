package com.example.tableservice.repository;

import com.example.tableservice.entity.RestaurantTable;
import com.example.tableservice.enums.TableStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Repository interface for managing {@link RestaurantTable} entities.
 * Provides methods for CRUD operations and custom queries related to restaurant tables.
 */
@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {

    /**
     * Finds a RestaurantTable by its ID and ensures it is not marked as deleted.
     *
     * @param id the ID of the RestaurantTable
     * @return an Optional containing the RestaurantTable if found and not deleted, or empty if not found or deleted
     */
    Optional<RestaurantTable> findByIdAndIsDeletedFalse(Long id);

    /**
     * Retrieves a paginated list of RestaurantTable entities, optionally filtered by several parameters.
     *
     * @param number      optional filter for the table number. If null, this filter is not applied.
     * @param tableTypeId optional filter for the table type ID. If null, this filter is not applied.
     * @param minPrice    optional filter for the minimum price. If null, this filter is not applied.
     * @param maxPrice    optional filter for the maximum price. If null, this filter is not applied.
     * @param status      optional filter for the table status. If null, this filter is not applied.
     * @param pageable    pagination information (page, size, sorting)
     * @return a paginated list of RestaurantTable entities matching the specified filters
     */
    @Query("SELECT rt FROM RestaurantTable rt WHERE rt.isDeleted = false "+
            "AND (:number IS NULL OR rt.number = :number) " +
            "AND (:tableTypeId IS NULL OR rt.tableTypeId = :tableTypeId) " +
            "AND (:minPrice IS NULL OR rt.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR rt.price <= :maxPrice) " +
            "AND (:status IS NULL OR rt.status = :status)"
    )
    Page<RestaurantTable> getAllTable(Integer number, Long tableTypeId, BigDecimal minPrice, BigDecimal maxPrice, TableStatusEnum status, Pageable pageable);


    /**
     * Checks if a RestaurantTable with the given number exists and is not deleted.
     *
     * @param number the table number
     * @return true if a non-deleted RestaurantTable with the given number exists, otherwise false
     */
    boolean existsByNumberAndIsDeletedIsFalse(Integer number);
    /**
     * Checks if a RestaurantTable with the given number exists, is not deleted, and has a different ID.
     *
     * @param number the table number
     * @param id     the ID of the RestaurantTable to exclude from the check
     * @return true if a non-deleted RestaurantTable with the given number exists and has a different ID, otherwise false
     */
    boolean existsByNumberAndIsDeletedIsFalseAndIdNot(Integer number, Long id);
}
