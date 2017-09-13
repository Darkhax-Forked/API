package com.diluv.api.permission.project;

public enum ProjectPermissionTypes {

    EDIT_DESCRIPTION(1),
    EDIT_SETTINGS(2),
    ADD_USER(3),

    UPLOAD_FILE(30),;

    public final int id;

    ProjectPermissionTypes(int id) {
        this.id = id;
    }
}