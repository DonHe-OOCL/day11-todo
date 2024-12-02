package com.todo.common;

public class  ResponseEntity<T> {
    private T result;
    private String message;

    private Status status;

    public enum Status {
        SUCCESS,
        ERROR
    }

    public ResponseEntity() {
    }

    public ResponseEntity(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public ResponseEntity(T data, Status status) {
        this.result = data;
        this.status = status;
    }

    public ResponseEntity(T data, String message, Status status) {
        this.result = data;
        this.message = message;
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static <T> ResponseEntity<T> success(T data) {
        return new ResponseEntity<>(data, Status.SUCCESS);
    }

    public static <T> ResponseEntity<T> error(String message) {
        return new ResponseEntity<>(message, Status.ERROR);
    }
}
