package com.suye.iblog.component;
/**
 * 返回的内容
 * success:判断是否响应
 * message:返回的信息
 * body:返回的数据
 */
public class Response {
    private Boolean success;
    private String message;
    private Object body;

    public  Response(){}
    public Response(Boolean success, String message){
        this.success = success;
        this.message=message;
    }

    public Response(Boolean success, String message, Object body){
        this.success = success;
        this.message=message;
        this.body=body;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString(){
        return success+message;
    }
}
