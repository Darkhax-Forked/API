package com.diluv.api.error;

public enum ErrorMessages {
    NOT_FOUND(Errors.NOT_FOUND, ""),

    AUTH_TOKEN_NULL(Errors.UNAUTHORIZED, "Token not found"),
    AUTH_TOKEN_INVALID(Errors.BAD_REQUEST, "The token is invalid"),
    AUTH_TOKEN_EXPIRED(Errors.BAD_REQUEST, "The token is expired"),

    AUTH_MFA_NULL(Errors.UNAUTHORIZED, "MFA Token not found"),
    AUTH_MFA_INVALID(Errors.BAD_REQUEST, "MFA token is invalid"),
    AUTH_MFA_EXPIRED(Errors.BAD_REQUEST, "MFA token is expired"),

    AUTH_REFRESH_TOKEN_NULL(Errors.UNAUTHORIZED, "Refresh Token not found"),
    AUTH_REFRESH_TOKEN_INVALID(Errors.BAD_REQUEST, "The refresh token is invalid"),
    AUTH_REFRESH_TOKEN_EXPIRED(Errors.BAD_REQUEST, "The refresh token is expired"),

    AUTH_EMAIL_TOKEN_NULL(Errors.UNAUTHORIZED, "Email Token not found"),
    AUTH_EMAIL_TOKEN_INVALID(Errors.BAD_REQUEST, "The Email token is invalid"),

    AUTH_LOGIN_USER_EMAIL_NULL(Errors.BAD_REQUEST, "Username or email is needed to login"),
    AUTH_LOGIN_PASSWORD_NULL(Errors.BAD_REQUEST, "Password is needed to login"),

    AUTH_PASSWORD_INCORRECT(Errors.UNAUTHORIZED, "The password is incorrect"),

    AUTH_REGISTER_EMAIL_NULL(Errors.BAD_REQUEST, "Registering requires an email"),
    AUTH_REGISTER_EMAIL_INVALID(Errors.BAD_REQUEST, "Email is invalid"),
    AUTH_REGISTER_EMAIL_TAKEN(Errors.BAD_REQUEST, "Email is already used"),
    AUTH_REGISTER_USERNAME_NULL(Errors.BAD_REQUEST, "Registering requires a username"),
    AUTH_REGISTER_USERNAME_INVALID(Errors.BAD_REQUEST, "User is invalid"),
    AUTH_REGISTER_USERNAME_TAKEN(Errors.BAD_REQUEST, "Username is already used"),
    AUTH_REGISTER_PASSWORD_NULL(Errors.BAD_REQUEST, "Registering requires a password"),
    AUTH_REGISTER_PASSWORD_INVALID(Errors.BAD_REQUEST, "Password is invalid"),

    AUTH_RECAPTCHA_NULL(Errors.BAD_REQUEST, "Registering requires a recaptcha response"),
    AUTH_RECAPTCHA_INVALID(Errors.UNAUTHORIZED, "Recaptcha is invalid"),

    PROJECT_NAME_NULL(Errors.BAD_REQUEST, "Project creation requires a project name"),
    PROJECT_NAME_TAKEN(Errors.BAD_REQUEST, "Project name is already taken"),
    PROJECT_DESCRIPTION_NULL(Errors.BAD_REQUEST, "Project creation requires a description"),
    PROJECT_SHORT_DESCRIPTION_NULL(Errors.BAD_REQUEST, "Project creation requires a short description"),

    PROJECT_NOT_FOUND(Errors.NOT_FOUND, "Project not found"),
    PROJECT_SLUG_NULL(Errors.BAD_REQUEST, "An slug is needed"),

    USER_NOT_FOUND(Errors.NOT_FOUND, "The user doesn't exist"),
    USER_NOT_VERIFIED(Errors.FORBIDDEN, "The user is not verified"),

    UNAUTHORIZED_REQUEST(Errors.FORBIDDEN, "User not authorized for request"),

    PROJECT_CATEGORY_NOT_FOUND(Errors.NOT_FOUND, "Project category not found"),

    INTERNAL_SERVER_ERROR(Errors.INTERNAL_SERVER_ERROR, "Internal error, devs have been notified"),;

    public final Errors errors;
    public final String errorMessage;

    ErrorMessages(Errors errors, String errorMessage) {
        this.errors = errors;
        this.errorMessage = errorMessage;
    }

}
