package com.example.tabletypeservice.constant;

/**
 * Defines application-wide constant messages and parameters for validation, logging, and error handling.
 * This class centralizes all static message strings to avoid hardcoding throughout the codebase.
 */
public class Constant {


    // This class is not meant to be instantiated
    private Constant() {}
    public static final String ASTERISK = "*";
    public static final String SLASH = "/";
    public static final String SLASH_ASTERISK_ASTERISK = "/**";
    public static final String SUCCESS = "Success";
    public static final String ERROR = "ERROR";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String HTTP_METHOD_NOT_ALLOWED = "HTTP_METHOD_NOT_ALLOWED";
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String UNKNOWN = "unknown";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String FORBIDDEN = "FORBIDDEN";
    public static final String AES = "AES";
    public static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding"; //CBC mode with PKCS5 padding
    // Parameters
    public static final int N_ZERO = 0;
    public static final int N_ONE = 1;
    public static final int N_TWO = 2;

    public static final int N_TEN = 10;
    public static final int N_FIFTY = 50;
    public static final int N_ONE_HUNDRED_AND_FIFTY = 150;
    public static final int N_ONE_THOUSAND = 1000;
    public static final int SIX_TEEN = 16;
    public static final String ZERO = "0";
    public static final String S_ONE = "1";
    public static final String S_TEN = "10";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String CREATED_AT = "createdAt";
    public static final String UPDATED_AT = "updatedAt";
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static final String LONG = "long";
    public static final String INT64 = "int64";
    public static final String S_TRUE = "true";

    // Messages for Food
    public static final String NAME_REQUIRED = "Name is required";
    public static final String PRICE_LARGER_THAN_ZERO = "Price must be greater than 0";
    public static final String IMAGE_REQUIRED = "Image is required";
    public static final String FOOD_CATEGORY_REQUIRED = "Food Category is required";
    public static final String NAME_SIZE = "Name must be less than or equal to {max} characters";
    public static final String DESCRIPTION_SIZE = "Description must be less than or equal to {max} characters";
    public static final String FOOD_NOT_FOUND = "Food not found";
    public static final String FOOD_CATEGORY_NAME_EXIST = "Food category name already exists";

    // Messages for Food Category
    public static final String FOOD_CATEGORY_NOT_FOUND = "Food Category not found";

    //Messages for TableType
    public static final String TABLE_TYPE_REQUIRED = "Table type is required";
    public static final String CAPACITY_REQUIRED = "Capacity is required";
    public static final String TABLE_TYPE_CREATED_FAILED = "Table type creation failed";
    public static final String TABLE_TYPE_ALREADY_EXISTS = "Table type name already exists";
    public static final String TABLE_TYPE_NOT_FOUND = "Table type not found";
    public static final String TABLE_TYPE_CAPACITY_MIN = "Capacity must be greater than 0";
    // Messages for Restaurant Table
    public static final String TABLE_NOT_FOUND = "Table not found";
    public static final String NUMBER_MIN = "Number must be greater than 0";
    public static final String NUMBER_REQUIRED = "Number is required";
    public static final String TABLE_STATUS_REQUIRED = "Table status is required";
    public static final String TABLE_NUMBER_EXIST = "Table number already exists";

    
    // Image upload messages
    public static final String UPLOAD_START = "Uploading image to Cloudinary: {}";
    public static final String UPLOAD_SUCCESS = "Image uploaded successfully: {}";

    // Image delete messages
    public static final String DELETE_START = "Deleting image from Cloudinary: {}";
    public static final String DELETE_SUCCESS = "Image deleted successfully: {}";

    // Exception messages
    public static final String RUNTIME_EXCEPTION_OCCURRED = "RuntimeException occurred: {}";
    public static final String IO_EXCEPTION_OCCURRED = "IOException occurred: {}";
    public static final String NOT_FOUND_EXCEPTION_OCCURRED = "NotFoundException occurred: {}";
    public static final String BAD_REQUEST_EXCEPTION_OCCURRED = "BadRequestException occurred: {}";
    public static final String VALIDATION_EXCEPTION_OCCURRED = "Validation exception occurred: {}";
    public static final String UNEXPECTED_EXCEPTION_OCCURRED = "Unexpected exception occurred: {}";

    // IllegalArgumentException messages
    public static final String ILLEGAL_ARGUMENT_EXCEPTION_OCCURRED = "IllegalArgumentException occurred: {}";

    // MethodArgumentTypeMismatchException messages
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION_OCCURRED = "MethodArgumentTypeMismatchException occurred: {}";
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH = "Parameter '%s' is of invalid type. Expected type: %s.";

    // NoResourceFoundException messages
    public static final String NO_RESOURCE_FOUND_EXCEPTION_OCCURRED = "NoResourceFoundException occurred: {}";
    // Specific error messages
    public static final String VALIDATION_FAILED = "Validation failed for one or more fields.";

    // Database-related messages
    public static final String DATABASE_EXCEPTION_OCCURRED = "Database exception occurred: {}";
    public static final String DATABASE_ERROR_OCCURRED = "A database error occurred.";

    // Database constraint violation messages
    public static final String DATA_INTEGRITY_VIOLATION_OCCURRED = "DataIntegrityViolationException occurred: {}";

    // File size limit messages
    public static final String FILE_SIZE_LIMIT_EXCEEDED = "File size exceeds the maximum allowed limit: {}";
    public static final String FILE_TOO_LARGE = "The uploaded file is too large. Please upload a file smaller than the allowed size.";

    // Conflict exception messages
    public static final String CONFLICT_EXCEPTION_OCCURRED = "Conflict exception occurred.";
    public static final String CONFLICT = "CONFLICT_ERROR.";
    public static final String HttpRequestMethodNotSupportedException = "HttpRequestMethodNotSupportedException occurred: {}";
    public static final String HTTP_METHOD_NOT_SUPPORT = "HTTP method not supported: ";
    // HttpMessageNotReadableException messages
    public static final String INVALID_REQUEST_BODY = "INVALID_REQUEST_BODY";
    public static final String FAILED_TO_READ_REQUEST_BODY = "Failed to read HTTP request body";
    // Cloudinary-related messages
    public static final String UPLOAD_SEGMENT = "/upload/";
    public static final String VERSION_PREFIX_REGEX = "v[0-9]+/";
    public static final String FILE_EXTENSION_REGEX = "\\.[^.]+$";
    public static final String EMPTY_STRING = "";

}

