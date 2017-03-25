package cn.henryzhuhao.henryfragmentation;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.Date;

import cn.henryzhuhao.henryfragmentation.action.SupportFragmentAction;
import cn.henryzhuhao.henryfragmentation.exception.HasBeenAddedException;
import cn.henryzhuhao.henryfragmentation.exception.NotAddedException;
import cn.henryzhuhao.henryfragmentation.exception.NotFoundException;
import cn.henryzhuhao.henryfragmentation.exception.NotSupportException;
import cn.henryzhuhao.henryfragmentation.exception.SupportException;

/**
 * Created by HenryZhuhao on 2017/3/19.
 */

public class SupportFragment extends Fragment implements SupportFragmentAction {
    public String fragmentTag=null;
    public String TAG = this.getClass().getName().toString();
    public SupportFragment parentFragment;
    public SupportActivity activity;
    public FragmentManager fm;
    public FragmentManager parentFm;
    public SupportFragmentStack mFragmentStack = new SupportFragmentStack();

    private static final String SAVED_BUNDLE_IS_HIDDEN = "saved.bundle.isHidden";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getChildFragmentManager();
        activity=(SupportActivity)getActivity();
        if (savedInstanceState != null) {
            Boolean isSupportHidden = savedInstanceState.getBoolean(SAVED_BUNDLE_IS_HIDDEN);
            if (isSupportHidden) {
                parentFm.beginTransaction().hide(this).commit();
            } else {
                parentFm.beginTransaction().show(this).commit();
            }
        }
    }

    @Override
    public void loadfragment(@IdRes int containerId, @NonNull SupportFragment fragment) {
        startfragment(containerId, fragment);
    }

    @Override
    public void loadfragments(@IdRes int containerId, int postion, @NonNull SupportFragment... fragments) {
        for (SupportFragment fragment : fragments) {
            if (mFragmentStack.addTagToStack(fragment.getFragmentTAG())) {
                FragmentBaseTransaction.start(fm).add(containerId, fragment)
                        .hideAll().show(fragment).commit();
            } else {
                throwException(new HasBeenAddedException(fragment.getFragmentTAG()));
            }
        }
    }

    @Override
    public void startfragment(@IdRes int containerId, @NonNull SupportFragment fragment) {
        if (mFragmentStack.addTagToStack(fragment.getFragmentTAG())) {
            FragmentBaseTransaction.start(fm).add(containerId, fragment)
                    .hideAll().show(fragment).commit();
        } else {
            throwException(new HasBeenAddedException(fragment.getFragmentTAG()));
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_BUNDLE_IS_HIDDEN, isHidden());
    }


    @Override
    public void show(@NonNull SupportFragment fragment) {
        FragmentBaseTransaction.start(fm).hideAll().show(fragment).commit();
    }

    @Override
    public void close(@NonNull SupportFragment fragment) {
        if (mFragmentStack.removeTagfromStack(fragment.getFragmentTAG())) {
            FragmentBaseTransaction.start(fm).remove(fragment).commit();
        } else {
            throwException(new NotAddedException(fragment.getFragmentTAG()));
        }

        String popStackFragment = mFragmentStack.getPopStackFragment();
        if (popStackFragment == null) {
            close();
            return;
        }
        Fragment popFragment = FragmentBaseTransaction.getFragment(fm, popStackFragment);
        if (popFragment != null) {
            FragmentBaseTransaction.start(fm).hideAll().show(popFragment).commit();
        } else {
            throwException(new NotAddedException(popStackFragment));
        }
    }

    @Override
    public void close() {
        mFragmentStack.clearStack();
        SupportFragment parentFragment = (SupportFragment) getParentFragment();
        if (parentFragment == null) {
            activity.close();
        } else {
            parentFragment.close(this);
        }
    }

    @Override
    public void onBackPressed() {
        String popStackFragment = mFragmentStack.getPopStackFragment();//得到栈顶的Fragment
        if (popStackFragment == null) {//如果当前的视图没有嵌套的Fragment，则关闭当前页面
            close();
            return;
        }
        Fragment fragment = FragmentBaseTransaction.getFragment(fm, popStackFragment);
        if (fragment == null) {
            throwException(new NotFoundException(popStackFragment));
            return;
        }
        if (fragment instanceof SupportFragment) {
            ((SupportFragment) fragment).onBackPressed();//委托下层执行回退动作
        } else {
            throwException(new NotSupportException(popStackFragment));
        }
    }

    public SupportFragment() {
        super();
    }

    @Override
    public void destroyfragment(SupportFragment fragment) {

    }

    @Override
    public void destroyfragments(SupportFragment... fragments) {
        for (SupportFragment fragment : fragments) {

        }
    }

    @Override
    public void throwException(SupportException e) {
        throw e;
    }

    @Override
    public String getFragmentTAG() {
        if(fragmentTag==null){
            fragmentTag=TAG+new Date().getTime();
        }
        return fragmentTag;
    }

}
