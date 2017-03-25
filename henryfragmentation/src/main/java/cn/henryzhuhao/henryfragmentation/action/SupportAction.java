package cn.henryzhuhao.henryfragmentation.action;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import cn.henryzhuhao.henryfragmentation.SupportFragment;
import cn.henryzhuhao.henryfragmentation.exception.SupportException;

/**
 * Created by HenryZhuhao on 2017/3/19.
 */

public interface SupportAction {


    /**
     * 加载根单个fragment
     * @param containerId
     * @param fragment
     */
    void loadfragment(@IdRes int containerId,@NonNull SupportFragment fragment);

    /**
     * 加载多个fragment使用场景类似于底部导航栏，懒加载
     * @param containerId
     * @param postion
     * @param fragments
     */
    void loadfragments(@IdRes int containerId,int postion,@NonNull SupportFragment ... fragments);
    /**
     * 打开新的页面
     * 默认操作：隐藏其余所有的Fragment
     */
    void startfragment(@IdRes int containerId, @NonNull SupportFragment fragment);

    /**
     * 显示指定的fragment隐藏其他的fragment
     *
     * @param fragment
     */
    void show(@NonNull SupportFragment fragment);


    /**
     * 关闭页面
     * 只有没有子Fragment的时候才执行该方法
     * 默认操作：显示上个页面
     */
    void close(@NonNull SupportFragment fragment);

    /**
     * 关闭当前页面
     * 默认操作交给上级处理自己
     */
    void close();

    void throwException(SupportException e);

}
