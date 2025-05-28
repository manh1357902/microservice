package com.example.tabletypeservice.repository;

import com.example.tabletypeservice.entity.TableType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link TableType} entities.
 * Provides methods for CRUD operations and custom queries related to table types.
 */
@Repository
public interface TableTypeRepository extends JpaRepository<TableType, Long> {


    /**
     * Finds a TableType by its ID and ensures it is not marked as deleted.
     *
     * @param id the ID of the TableType
     * @return an Optional containing the TableType if found and not deleted, or empty if not found or deleted
     */
    Optional<TableType> findByIdAndIsDeletedIsFalse(Long id);

    /**
     * Checks if a TableType with the given name exists and is not deleted, excluding a specific ID.
     *
     * @param name the name of the TableType
     * @param id   the ID to exclude from the check
     * @return true if a non-deleted TableType with the given name exists (excluding the given ID), otherwise false
     */
    boolean existsByNameAndIsDeletedIsFalseAndIdNot(String name, Long id);

    /**
     * Checks if a TableType with the given name exists and is not deleted.
     *
     * @param name the name of the TableType
     * @return true if a non-deleted TableType with the given name exists, otherwise false
     */
    boolean existsByNameAndIsDeletedIsFalse(String name);

    /**
     * Finds all non-deleted TableTypes with list, optionally filtered by keyword and capacity.
     *
     * @param keyword  the keyword to filter TableTypes by name
     * @param capacity the capacity to filter TableTypes by
     * @return a list of non-deleted TableTypes matching the criteria
     */
    @Query("SELECT tt FROM TableType tt WHERE tt.isDeleted = false " +
            "AND (:keyword IS NULL OR LOWER(tt.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:capacity IS NULL OR tt.capacity = :capacity)")
    List<TableType> getAllTableTypeNotPagination(String keyword, Integer capacity);

    /**
     * Finds all non-deleted TableTypes with pagination, filtered by name and capacity.
     *
     * @param keyword   the keyword to filter TableTypes by name
     * @param capacity  the capacity to filter TableTypes by
     * @param pageable  the pagination information
     * @return a page of non-deleted TableTypes matching the criteria
     */
    @Query("SELECT tt FROM TableType tt WHERE tt.isDeleted = false " +
            "AND (:keyword IS NULL OR LOWER(tt.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:capacity IS NULL OR tt.capacity = :capacity)")
    Page<TableType> getALlTableTypePagination(String keyword, Integer capacity, Pageable pageable);

    /**
     * Calls a stored procedure to create a new table type in the database.
     *
     * <p>This method invokes the stored procedure defined by the name sp_create_table_type },
     * passing the provided parameters to create a new table type in the database.</p>
     *
     * @param name        the name of the table type to be created
     * @param description a brief description of the table type
     * @param capacity    the seating capacity of the table type
     * @return the ID of the newly created table type, or null if the operation fails
     */
    @Procedure(name= "sp_create_table_type")
    Long createTableType(@Param("p_name") String name,@Param("p_description") String description, @Param("p_capacity") Integer capacity);

}
