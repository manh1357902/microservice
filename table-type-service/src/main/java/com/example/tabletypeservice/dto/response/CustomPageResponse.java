package com.example.tabletypeservice.dto.response;


import com.example.tabletypeservice.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Custom Page Response DTO for paginated data.
 * Wraps paginated content and metadata for client responses.
 *
 * @param <T> the type of content in the response
 */
@Data
@AllArgsConstructor
public class CustomPageResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    /**
     * Converts a Spring Data Page object to a CustomPageResponse.
     *
     * @param page the Page object
     * @param <T>  the type of content
     * @return a CustomPageResponse object
     */
    public static <T> CustomPageResponse<T> fromPage(Page<T> page) {
        return new CustomPageResponse<>(
                page.getContent(),
                page.getNumber() + Constant.N_ONE,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}

