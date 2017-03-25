package cn.henryzhuhao.henryfragmentation.exception;

/**
 * Created by HenryZhuhao on 2017/3/24.
 */

public class NotAddedException extends SupportException {
    public NotAddedException(String fragmentTag) {
        super(NotAddedException.class.getName(),String.format("%s has not been added",fragmentTag));
    }
}
