package com.diluv.api.permission.user

enum class UserPermissionType(val id: Int) {

    BAN_USER(1),
    REMOVE_COMMENT(2),

    CREATE_BETA_KEYS(90), ;

    fun hasPermission(permissionId: Int): Boolean {
        if (1 shl id and permissionId > 0) {
            return true
        }
        return false
    }
}

fun addPermissions(permissions: Array<UserPermissionType>): Int {
    var id = 0

    for (permission in permissions) {
        id = id or (1 shl permission.id)
    }

    return id
}
