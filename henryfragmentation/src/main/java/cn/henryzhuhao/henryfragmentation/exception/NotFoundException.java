package cn.henryzhuhao.henryfragmentation.exception;

/**
 * Created by HenryZhuhao on 2017/3/24.
 */

public class NotFoundException extends SupportException {

    public NotFoundException(String error) {
        super(NotFoundException.class.getName(), String.format("%s is not found", error));
    }
}
