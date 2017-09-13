package com.diluv.api.permission.user;

public enum UserPermissionType {

    BAN_USER(1),
    REMOVE_COMMENT(2),

    CREATE_BETA_KEYS(90),;


    public final int id;

    UserPermissionType(int id) {
        this.id = id;
    }

    public boolean hasPermission(int permissionId) {
        return (1 << id & permissionId) > 0;
    }

    public static int addPermissions(UserPermissionType[] permissions) {
        int id = 0;

        for (UserPermissionType permission : permissions) {
            id = id | (1 << permission.id);
        }

        return id;
    }
}
