package com.example.authservice.enumn;

import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
public enum Permission {

    SWAGGER_UI(HttpMethod.GET, "/swagger-ui/**"),
    SWAGGER_HTML(HttpMethod.GET, "/swagger-ui.html"),
    SWAGGER_API_DOCS(HttpMethod.GET, "/v3/api-docs/**"),
    SWAGGER_RESOURCES(HttpMethod.GET, "/swagger-resources/**"),
    SWAGGER_WEBJARS(HttpMethod.GET, "/webjars/**"),
    // Authentication APIs
    LOGIN(HttpMethod.POST, "/auth/login"),
    REFRESH_TOKEN(HttpMethod.POST, "/auth/refresh-token"),
    // Category Food Table APIs
    VIEW_TABLE_TYPE_LIST_PAGINATION(HttpMethod.GET, "/table-type/page"),
    VIEW_TABLE_TYPE_LIST(HttpMethod.GET, "/table-type/list"),
    CREATE_TABLE_TYPE(HttpMethod.POST, "/table-type/create"),
    UPDATE_TABLE_TYPE(HttpMethod.PUT, "/table-type/update/*"),
    DELETE_TABLE_TYPE(HttpMethod.DELETE, "/table-type/delete/*"),

    // Restaurant Table APIs
    VIEW_RESTAURANT_TABLE_LIST_PAGINATION(HttpMethod.GET, "/table/page"),
    VIEW_RESTAURANT_TABLE_DETAIL(HttpMethod.GET, "/table/*"),
    CREATE_RESTAURANT_TABLE(HttpMethod.POST, "/table/create"),
    UPDATE_RESTAURANT_TABLE(HttpMethod.PUT, "/table/update/*"),
    DELETE_RESTAURANT_TABLE(HttpMethod.DELETE, "/table/delete/*");

    private final HttpMethod method;
    private final String endpoint;

    Permission(HttpMethod method, String endpoint) {
        this.method = method;
        this.endpoint = endpoint;
    }
}