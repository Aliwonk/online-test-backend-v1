package ru.test_app.backend.controllers.response_types;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseController {
    private int statusCode;
    private String message;
    private Object data;
    private String errorMessage;

    public ResponseController() {
    }

    public ResponseController(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ResponseController(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    /* GETTERS */

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /* SETTERS */

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
