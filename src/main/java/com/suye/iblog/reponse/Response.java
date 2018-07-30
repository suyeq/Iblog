package com.suye.iblog.reponse;
/**
 * 返回的内容
 * isSuccess:判断是否响应
 * message:返回的信息
 * body:返回的数据
 */
public class Response {
    private Boolean isSuccess;
    private String message;
    private Object body;


    public Response(Boolean isSuccess,String message,Object body){
        this.isSuccess=isSuccess;
        this.message=message;
        this.body=body;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
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
}
