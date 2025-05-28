package com.example.tableservice.service.impl;

import com.example.tableservice.client.TableTypeClient;
import com.example.tableservice.constant.Constant;
import com.example.tableservice.dto.request.RestaurantTableRequest;
import com.example.tableservice.dto.response.ApiResponse;
import com.example.tableservice.dto.response.RestaurantTableResponse;
import com.example.tableservice.dto.response.TableTypeResponse;
import com.example.tableservice.entity.RestaurantTable;
import com.example.tableservice.enums.TableStatusEnum;
import com.example.tableservice.exception.ConflictException;
import com.example.tableservice.exception.NotFoundException;
import com.example.tableservice.mapper.RestaurantTableMapper;
import com.example.tableservice.repository.RestaurantTableRepository;
import com.example.tableservice.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Implementation of {@link com.example.tableservice.service.RestaurantTableService}.
 * Provides business logic for managing restaurant tables.
 */
@Service
@RequiredArgsConstructor
public class RestaurantTableServiceImpl implements RestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;
    private final RestaurantTableMapper restaurantTableMapper;
    private final TableTypeClient tableTypeClient;

    /**
     * Creates a new restaurant table.
     *
     * @param request the request containing restaurant table details
     * @return the created restaurant table
     * @throws NotFoundException if the table type is not found
     * @throws ConflictException if the table number already exists
     */
    @Override
    @Transactional
    public RestaurantTableResponse create(RestaurantTableRequest request) {
        if(restaurantTableRepository.existsByNumberAndIsDeletedIsFalse(request.getNumber())) {
            throw new ConflictException(Constant.TABLE_NUMBER_EXIST);
        }
        ApiResponse<TableTypeResponse> tableTypeResponse = tableTypeClient.getTableTypeById(request.getTableTypeId());
        if(tableTypeResponse.getMessage().equals(Constant.SUCCESS) && tableTypeResponse.getData() == null){
            throw new NotFoundException(Constant.TABLE_TYPE_NOT_FOUND);
        }
        RestaurantTable table = restaurantTableMapper.toEntity(request);
        table.setTableTypeId(request.getTableTypeId());
        return restaurantTableMapper.toRestaurantTableResponse(restaurantTableRepository.save(table));
    }

    /**
     * Retrieves the details of a specific restaurant table.
     *
     * @param id the ID of the restaurant table
     * @return the details of the restaurant table
     * @throws NotFoundException if the restaurant table is not found
     */
    @Override
    public RestaurantTableResponse getDetail(Long id) {
        RestaurantTable table = restaurantTableRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(Constant.TABLE_NOT_FOUND));
        return restaurantTableMapper.toRestaurantTableResponse(table);
    }

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
    @Override
    public Page<RestaurantTableResponse> getAllTable(Integer number, Long tableTypeId, BigDecimal minPrice, BigDecimal maxPrice, TableStatusEnum status, int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return restaurantTableRepository.getAllTable(number, tableTypeId, minPrice, maxPrice, status, pageable)
                .map(restaurantTableMapper::toRestaurantTableResponse);
    }

    /**
     * Updates an existing restaurant table.
     *
     * @param id      the ID of the restaurant table to update
     * @param request the request containing updated restaurant table details
     * @return the updated restaurant table
     * @throws NotFoundException if the restaurant table or table type is not found
     * @throws ConflictException if the table number already exists
     */
    @Override
    @Transactional
    public RestaurantTableResponse update(Long id, RestaurantTableRequest request) {
        RestaurantTable table = restaurantTableRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(Constant.TABLE_NOT_FOUND));
        if(restaurantTableRepository.existsByNumberAndIsDeletedIsFalseAndIdNot(request.getNumber(), id)) {
            throw new ConflictException(Constant.TABLE_NUMBER_EXIST);
        }
        ApiResponse<TableTypeResponse> tableTypeResponse = tableTypeClient.getTableTypeById(request.getTableTypeId());
        if(tableTypeResponse.getMessage().equals(Constant.SUCCESS) && tableTypeResponse.getData() == null){
            throw new NotFoundException(Constant.TABLE_TYPE_NOT_FOUND);
        }
        table.setNumber(request.getNumber());
        table.setTableTypeId(request.getTableTypeId());
        table.setPrice(request.getPrice());
        table.setStatus(request.getStatus());
        return restaurantTableMapper.toRestaurantTableResponse(restaurantTableRepository.save(table));
    }

    /**
     * Deletes a restaurant table by marking it as deleted.
     *
     * @param id the ID of the restaurant table to delete
     * @throws NotFoundException if the restaurant table is not found
     */
    @Override
    @Transactional
    public void delete(Long id) {
        RestaurantTable table = restaurantTableRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(Constant.TABLE_NOT_FOUND));
        table.setIsDeleted(true);
        restaurantTableRepository.save(table);
    }

}
