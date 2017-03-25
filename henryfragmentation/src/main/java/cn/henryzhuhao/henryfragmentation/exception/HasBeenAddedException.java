package cn.henryzhuhao.henryfragmentation.exception;

/**
 * Created by HenryZhuhao on 2017/3/24.
 */

public class HasBeenAddedException extends SupportException {
    public HasBeenAddedException(String fragmentTag) {
        super(HasBeenAddedException.class.getName(),String.format("%s has already been added",fragmentTag));
    }
}
