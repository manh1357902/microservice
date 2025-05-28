package com.example.apigateway.enumn;

import lombok.Getter;

import java.util.List;

/**
 * Enum representing user roles and their associated permissions.
 */
@Getter
public enum UserRoleEnum {

    ANONYMOUS(List.of(
            Permission.LOGIN,
            Permission.REFRESH_TOKEN,
            Permission.VIEW_TABLE_TYPE_LIST,
            Permission.VIEW_RESTAURANT_TABLE_LIST_PAGINATION,
            Permission.VIEW_RESTAURANT_TABLE_DETAIL,

            // Swagger permissions
            Permission.SWAGGER_UI,
            Permission.SWAGGER_HTML,
            Permission.SWAGGER_API_DOCS,
            Permission.SWAGGER_RESOURCES,
            Permission.SWAGGER_WEBJARS
    )),

    USER(List.of(
            Permission.VIEW_TABLE_TYPE_LIST,
            Permission.VIEW_RESTAURANT_TABLE_LIST_PAGINATION,
            Permission.VIEW_RESTAURANT_TABLE_DETAIL
    )),
    ADMIN(List.of(
            Permission.VIEW_TABLE_TYPE_LIST, Permission.CREATE_TABLE_TYPE,
            Permission.UPDATE_TABLE_TYPE,
            Permission.DELETE_TABLE_TYPE,
            Permission.CREATE_RESTAURANT_TABLE,
            Permission.UPDATE_RESTAURANT_TABLE,
            Permission.DELETE_RESTAURANT_TABLE
    ));

    final List<Permission> permissions;

    UserRoleEnum(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
