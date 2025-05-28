package com.example.tableservice.mapper;

import com.example.tableservice.dto.request.RestaurantTableRequest;
import com.example.tableservice.dto.response.RestaurantTableResponse;
import com.example.tableservice.entity.RestaurantTable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper bean for converting between {@link RestaurantTableRequest}, {@link RestaurantTable}, and {@link RestaurantTableResponse}.
 * Provides mapping methods for entity-DTO transformations in the restaurant table domain.
 */
@Component
@RequiredArgsConstructor
public class RestaurantTableMapper {
    private final ModelMapper modelMapper;
    /**
     * Maps a RestaurantTableRequest DTO to a RestaurantTable entity.
     *
     * @param restaurantTableRequest the RestaurantTableRequest DTO
     * @return the mapped RestaurantTable entity
     */
    public RestaurantTable toEntity(RestaurantTableRequest restaurantTableRequest) {
        return modelMapper.map(restaurantTableRequest, RestaurantTable.class);
    }
    /**
     * Maps a RestaurantTable entity to a RestaurantTableResponse DTO.
     *
     * @param restaurantTable the RestaurantTable entity
     * @return the mapped RestaurantTableResponse DTO
     */
    public RestaurantTableResponse toRestaurantTableResponse(RestaurantTable restaurantTable) {
        return modelMapper.map(restaurantTable, RestaurantTableResponse.class);
    }
}
