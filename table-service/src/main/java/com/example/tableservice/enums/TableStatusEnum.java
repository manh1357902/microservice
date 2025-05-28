package com.example.tableservice.enums;

/**
 * Enum representing the status of a restaurant table.
 * Used to indicate whether a table is available, occupied, reserved, or being cleaned.
 */
public enum TableStatusEnum {

    /**
     * The table is available, no customers are seated, ready to receive new customers.
     * Typically used when the table has been cleaned and is not occupied.
     */
    AVAILABLE,

    /**
     * The table is currently occupied by customers or in use.
     * Typically used when customers have ordered food or are eating.
     */
    OCCUPIED,

    /**
     * The table has been reserved through the booking system (online, phone, etc.).
     * Typically used when customers have booked a table but haven't arrived yet.
     */
    RESERVED,

    /**
     * The table is in the cleaning process and cannot be used by new customers.
     * Typically used when customers have left and the staff is preparing the table for new guests.
     */
    CLEANING
}