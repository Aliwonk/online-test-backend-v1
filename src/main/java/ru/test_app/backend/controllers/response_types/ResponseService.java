package ru.test_app.backend.controllers.response_types;

public class ResponseService {
    private int statusCode;
    private String err;
    private Object data;

    /* CONSTRUCTORS */

    public ResponseService() {}

    /* GETTERS */

    public int getStatusCode() {
        return statusCode;
    }

    public String getErr() {
        return err;
    }

    public Object getData() {
        return data;
    }

    /* SETTERS */

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
