package cn.henryzhuhao.henryfragmentation.action;

import cn.henryzhuhao.henryfragmentation.SupportFragment;

/**
 * Created by HenryZhuhao on 2017/3/19.
 */

public interface SupportFragmentAction extends SupportAction{
    /**
     *销毁fragment界面及其资源
     * @param fragment
     */
    void destroyfragment(SupportFragment fragment);

    void destroyfragments(SupportFragment... fragments);


    void onBackPressed();
    /**
     * 返回Fragment的TAG
     * 用于添加到Fragment事务中，可复写
     */
    String getFragmentTAG();

}
