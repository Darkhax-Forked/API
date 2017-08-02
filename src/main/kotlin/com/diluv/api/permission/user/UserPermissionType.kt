package com.diluv.api.permission.user

enum class UserPermissionType(val id: Int) {

    BAN_USER(1),
    REMOVE_COMMENT(2),

    CREATE_PROJECT(10),
    CREATE_BETA_KEYS(11),
}
