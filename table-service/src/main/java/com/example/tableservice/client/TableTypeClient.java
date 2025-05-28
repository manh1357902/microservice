package com.example.tableservice.client;

import com.example.tableservice.dto.response.ApiResponse;
import com.example.tableservice.dto.response.TableTypeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "table-type-service")
public interface TableTypeClient {
    @GetMapping("/api/v1/table-types/{id}")
    ApiResponse<TableTypeResponse> getTableTypeById(@PathVariable("id") Long id);
}
