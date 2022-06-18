package com.mjl.dfglxtv3.common;


public class ResponseEntity<T> {
    private String code;
    private String msg;
    private T result;

    public ResponseEntity() {
    }

    public ResponseEntity(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseEntity(T result) {
        this.result = result;
    }

    public ResponseEntity(String code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public static <T> ResponseEntity<T> success() {
        return new ResponseEntity<T>("200", "success");
    }

    public static <T> ResponseEntity<T> success(String msg) {
        return new ResponseEntity<T>("200", msg);
    }

    public static <T> ResponseEntity<T> success(T result) {
        return new ResponseEntity<T>("200", "success", result);
    }

    public static <T> ResponseEntity<T> error(String code, String msg) {
        return new ResponseEntity<T>(code, msg);
    }

    public static <T> ResponseEntity<T> error(String code, String msg, T result) {
        return new ResponseEntity<T>(code, msg, result);
    }

}
