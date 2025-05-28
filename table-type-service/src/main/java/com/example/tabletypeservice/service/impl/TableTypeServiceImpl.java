package com.example.tabletypeservice.service.impl;

import com.example.tabletypeservice.constant.Constant;
import com.example.tabletypeservice.dto.request.TableTypeRequest;
import com.example.tabletypeservice.dto.response.TableTypeResponse;
import com.example.tabletypeservice.entity.TableType;
import com.example.tabletypeservice.exception.BadRequestException;
import com.example.tabletypeservice.exception.ConflictException;
import com.example.tabletypeservice.exception.NotFoundException;
import com.example.tabletypeservice.mapper.TableTypeMapper;
import com.example.tabletypeservice.repository.TableTypeRepository;
import com.example.tabletypeservice.service.ITableTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link ITableTypeService}.
 * Provides business logic for managing table types.
 */
@Service
@RequiredArgsConstructor
public class TableTypeServiceImpl implements ITableTypeService {

    private final TableTypeRepository tableTypeRepository;
    private final TableTypeMapper tableTypeMapper;

    /**
     * Creates a new table type using a stored procedure.
     *
     * @param request the request containing table type details
     * @return the created table type
     * @throws ConflictException if a table type with the same name already exists
     * @throws BadRequestException if the created table type cannot be found
     */
    @Override
    @Transactional
    public TableTypeResponse createTableType(TableTypeRequest request) {

        if(tableTypeRepository.existsByNameAndIsDeletedIsFalse(request.getName())) {
            throw new ConflictException(Constant.TABLE_TYPE_ALREADY_EXISTS);
        }

        Long newId = tableTypeRepository.createTableType(request.getName(), request.getDescription(), request.getCapacity());
        // Return the created table type as a response
        TableType createdTableType = tableTypeRepository.findByIdAndIsDeletedIsFalse(newId)
                .orElseThrow(() -> new BadRequestException(Constant.TABLE_TYPE_CREATED_FAILED));
        return tableTypeMapper.toResponse(createdTableType);
    }

    /**
     * Updates an existing table type.
     *
     * @param id      the ID of the table type to update
     * @param request the request containing updated table type details
     * @return the updated table type
     * @throws NotFoundException if the table type is not found
     */
    @Override
    @Transactional
    public TableTypeResponse updateTableType(Long id, TableTypeRequest request) {
        TableType tableTypExist = tableTypeRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(Constant.TABLE_TYPE_NOT_FOUND));
        if (tableTypeRepository.existsByNameAndIsDeletedIsFalseAndIdNot(request.getName(), id)) {
            throw new ConflictException(Constant.TABLE_TYPE_ALREADY_EXISTS);
        }
        tableTypExist.setName(request.getName());
        tableTypExist.setDescription(request.getDescription());
        tableTypExist.setCapacity(request.getCapacity());
        return tableTypeMapper.toResponse(tableTypeRepository.save(tableTypExist));
    }

    /**
     * Deletes a table type by marking it as deleted and soft-deletes associated restaurant tables.
     *
     * @param id the ID of the table type to delete
     * @throws NotFoundException if the table type is not found
     */
    @Override
    @Transactional
    public void deleteTableType(Long id) {
        TableType tableType = tableTypeRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(Constant.TABLE_TYPE_NOT_FOUND));
        tableType.setIsDeleted(true);
        tableTypeRepository.save(tableType);
    }

    /**
     * Retrieves all table types without pagination, searching by keyword and table capacity.
     *
     * @param keyword The keyword to search for in the table type names.
     * @param capacity The capacity to filter the table types by number of seats. If null, no filtering by capacity will be applied.
     * @return A list of TableTypeResponse objects containing information about the table types.
     */
    @Override
    public List<TableTypeResponse> getAllTableTypesNotPagination(String keyword, Integer capacity) {
        return tableTypeRepository.getAllTableTypeNotPagination(keyword, capacity).stream()
                .map(tableTypeMapper::toResponse)
                .toList();
    }
    /**
     *
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
    public Page<TableTypeResponse> getAllTableTypesPagination(String keyword, Integer capacity, int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return tableTypeRepository.getALlTableTypePagination(keyword, capacity, pageable)
                .map(tableTypeMapper::toResponse);
    }

    /**
     * Retrieves the details of a specific table type.
     *
     * @param id the ID of the table type
     * @return the details of the table type
     * @throws NotFoundException if the table type is not found
     */
    @Override
    public TableTypeResponse getDetailTableType(Long id) {
        TableType tableType = tableTypeRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(Constant.TABLE_TYPE_NOT_FOUND));
        return tableTypeMapper.toResponse(tableType);
    }
}
