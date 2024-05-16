package com.example.blog.exception;

public enum ExceptionMessage {

    USER_ALREADY_EXIST_EXCEPTION("User Already Exist"),

    USER_WITH_EMAIL_NOT_FOUND_EXCEPTION("user with email %s not found"),

    ACCOUNT_ACTIVATION_FAILED_EXCEPTION("Account activation was not successfully"),
    USER_NOT_FOUND_EXCEPTION("User not found"),

    INVALID_CREDENTIALS_EXCEPTION("Invalid Credentials"),

    INVALID_AUTHORIZATION_HEADER_EXCEPTION("Invalid authorization header"),
    VERIFICATION_FAILED_EXCEPTION("Verification Failed"),

    MESSAGE_SENT_SUCCESSFULLY("Message sent successfully."),

    SUCCESS("Success"),
    NOTIFICATION_SENT_SUCCESSFULLY("Notification sent successfully"),


    AUTHENTICATION_NOT_SUPPORTED("Authentication not supported on this system"),

    RESOURCE_NOT_FOUND("");




    ExceptionMessage(String message){
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
