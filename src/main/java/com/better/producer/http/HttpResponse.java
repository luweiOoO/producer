package com.better.producer.http;

import java.io.Serializable;

public class HttpResponse<T> implements Serializable {

    private static final long serialVersionUID = -2321938196423302865L;

    /**
     * code码
     */
    private String code;

    /**
     * 提示
     */
    private String msg;

    /**
     * 结果
     */
    private T result;

    public HttpResponse(){

    }

    public HttpResponse(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public HttpResponse(String code, String msg, T result){
        this.code = code;
        this.result = result;
        this.msg = msg;
    }
    public static <T> HttpResponse<T> error(String ret, String msg){
        return new HttpResponse<>(ret,msg);
    }

    public static <T> HttpResponse<T> success(T result){
        return new HttpResponse<>("200","OK",result);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
