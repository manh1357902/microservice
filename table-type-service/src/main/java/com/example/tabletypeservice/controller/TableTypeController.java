package com.example.tabletypeservice.controller;

import com.example.tabletypeservice.constant.Constant;
import com.example.tabletypeservice.dto.request.TableTypeRequest;
import com.example.tabletypeservice.dto.response.ApiResponse;
import com.example.tabletypeservice.dto.response.CustomPageResponse;
import com.example.tabletypeservice.dto.response.TableTypeResponse;
import com.example.tabletypeservice.service.ITableTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/table-types")
@RequiredArgsConstructor
public class TableTypeController {

    private final ITableTypeService tableTypeService;

    @GetMapping("/list")
    public ResponseEntity<Object> getAll(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Integer capacity
    ) {
        List<TableTypeResponse> tableTypesResponse = tableTypeService.getAllTableTypesNotPagination(keyword, capacity);
        return ResponseEntity.ok(ApiResponse.success(Constant.SUCCESS, tableTypesResponse));
    }

    @GetMapping("/page")
    public ResponseEntity<Object> getAllPage(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<TableTypeResponse> tableTypesResponse = tableTypeService.getAllTableTypesPagination(keyword, capacity, page - 1, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(Constant.SUCCESS, CustomPageResponse.fromPage(tableTypesResponse)));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody TableTypeRequest request) {
        TableTypeResponse response = tableTypeService.createTableType(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(Constant.SUCCESS, response));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody TableTypeRequest request) {
        TableTypeResponse response = tableTypeService.updateTableType(id, request);
        return ResponseEntity.ok(ApiResponse.success(Constant.SUCCESS, response));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        tableTypeService.deleteTableType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDetail(@PathVariable Long id) {
        TableTypeResponse response = tableTypeService.getDetailTableType(id);
        return ResponseEntity.ok(ApiResponse.success(Constant.SUCCESS, response));
    }
}