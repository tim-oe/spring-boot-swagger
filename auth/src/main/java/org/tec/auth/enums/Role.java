package org.tec.auth.enums;

public enum Role {
    PUBLIC(0),             // Default invalid/unverified/everyone (allows everyone to execute)
    NEW_CUSTOMER(10),      // newly added custom that has not don personel setup
    CUSTOMER(20),          // User
    CUSTOMER_MANAGER(50),  // this is a customer that can manage other customers ie Agency
    SUPPORT(100),          // Administrator (Customer support)
    ADMIN(110),            // Administrator (Customer support with extra abilities)
    GROUP_ADMIN(130),       // Group Admin manages group accounts (enterprise)
    MANAGER(150),          // Manager
    PRODUCT_MANAGER(170),  // Super Manager permissions for product managers.
    SUPERUSER(200);        // Superuser (dev)

    /** the instance type */
    private final int value;

    /**
     * Ctor
     * @param value the role level
     */
    Role(final int value) {
        this.value = value;
    }

    /**
     * get the role level
     * @return the role level
     */
    public int getValue() {
        return value;
    }

    /**
     * Check if permission level is CUSTOMER
     * @param permissionLevel int
     * @return true if CUSTOMER
     */
    public static boolean isCustomer(final int permissionLevel) {
        return permissionLevel == CUSTOMER.value;
    }

    /**
     * Check if permission level is CUSTOMER MANAGER
     * @param permissionLevel int
     * @return true if CUSTOMER MANAGER
     */
    public static boolean isCustomerManager(final int permissionLevel) {
        return permissionLevel == CUSTOMER_MANAGER.value;
    }

    /**
     * Check if permission level is ADMIN or higher
     * @param permissionLevel int
     * @return true if ADMIN or higher
     */
    public static boolean isAdmin(final int permissionLevel) {
        return permissionLevel >= ADMIN.value;
    }

    /**
     * Check if permission level is GROUP_ADMIN or higher
     * @param permissionLevel int
     * @return true if GROUP_ADMIN or higher
     */
    public static boolean isGroupAdmin(final int permissionLevel) {
        return permissionLevel >= GROUP_ADMIN.value;
    }

    /**
     * Check if permission level is MANAGER
     * @param permissionLevel int
     * @return true if MANAGER or higher
     */
    public static boolean isManager(final int permissionLevel) {
        return permissionLevel >= MANAGER.value;
    }

    /**
     * Check if permission level is PRODUCT_MANAGER
     * @param permissionLevel int
     * @return true if PRODUCT_MANAGER or higher
     */
    public static boolean isProductManager(final int permissionLevel) {
        return permissionLevel >= PRODUCT_MANAGER.value;
    }

    /**
     * Check if permission level is SUPPORT
     * @param permissionLevel int
     * @return true if SUPPORT or higher
     */
    public static boolean isSupport(final int permissionLevel) {
        return permissionLevel >= SUPPORT.value;
    }

    /**
     * Check if permission level is SUPERUSER
     * @param permissionLevel int
     * @return true if SUPERUSER or higher
     */
    public static boolean isSuperuser(final int permissionLevel) {
        return permissionLevel >= SUPERUSER.value;
    }
}
