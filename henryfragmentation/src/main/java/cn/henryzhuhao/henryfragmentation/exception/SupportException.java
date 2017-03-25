package cn.henryzhuhao.henryfragmentation.exception;

/**
 * Created by HenryZhuhao on 2017/3/24.
 */

public class SupportException extends RuntimeException {
    public SupportException(String action,String error){
        super(String.format("error:action is %s , error is %s \n",action,error));
    }
}
