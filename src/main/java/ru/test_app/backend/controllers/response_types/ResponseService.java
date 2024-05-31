package ru.test_app.backend.controllers.response_types;

public class ResponseService {
    private Object data;

    /* CONSTRUCTORS */

    public ResponseService() {}

    public ResponseService(Object data) {
        this.data = data;
    }

    /* GETTERS */

    public Object getData() {
        return data;
    }

    /* SETTERS */

    public void setData(Object data) {
        this.data = data;
    }
}
