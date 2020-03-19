package tusdigital.community.community.vo;


import tusdigital.community.community.exception.CustomizeErrorCode;
import tusdigital.community.community.exception.CustomizeException;

/**
 * 依旧是一层套一层  该层作为Json实例传输  需要代码和信息来传递 要传输的数据data
 * 例子 传输commentVo 而commentVo 又封装了 comment表，总数和对应user的信息
 * 该类表示 传输信息有两种情况  带数据的成功和不带的 怎么样报错的失败
 *
 */

public class ResultVo<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultVo errorOf(Integer code, String message) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMessage(message);
        return resultVo;
    }

    public static ResultVo errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultVo errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());
    }

    public static ResultVo okOf() {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setMessage("请求成功");
        return resultVo;
    }

    public static <T> ResultVo okOf(T t) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setMessage("请求成功");
        resultVo.setData(t);
        return resultVo;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
