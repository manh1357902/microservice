package com.example.tabletypeservice.mapper;

import com.example.tabletypeservice.dto.response.TableTypeResponse;
import com.example.tabletypeservice.entity.TableType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper bean for converting between {@link TableType} and {@link TableTypeResponse}.
 * Provides mapping methods for entity-DTO transformations in the table type domain.
 */
@Component
@RequiredArgsConstructor
public class TableTypeMapper {

     private final ModelMapper modelMapper;

     /**
      * Maps a TableType entity to a TableTypeResponse DTO.
      *
      * @param tableType the TableType entity
      * @return the mapped TableTypeResponse DTO
      */
     public TableTypeResponse toResponse(TableType tableType) {
         return modelMapper.map(tableType, TableTypeResponse.class);
     }
}
