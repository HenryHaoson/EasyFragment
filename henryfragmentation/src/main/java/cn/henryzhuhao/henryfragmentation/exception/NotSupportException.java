package cn.henryzhuhao.henryfragmentation.exception;

/**
 * Created by HenryZhuhao on 2017/3/24.
 */

public class NotSupportException extends SupportException {
    public NotSupportException(String fargmentTag) {
        super(NotSupportException.class.getName(),String.format("%s is not extended from SupportFragment",fargmentTag));
    }
}
