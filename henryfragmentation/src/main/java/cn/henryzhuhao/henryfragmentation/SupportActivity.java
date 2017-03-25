package cn.henryzhuhao.henryfragmentation;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import cn.henryzhuhao.henryfragmentation.action.SupportActivityAction;
import cn.henryzhuhao.henryfragmentation.exception.HasBeenAddedException;
import cn.henryzhuhao.henryfragmentation.exception.NotAddedException;
import cn.henryzhuhao.henryfragmentation.exception.NotFoundException;
import cn.henryzhuhao.henryfragmentation.exception.NotSupportException;
import cn.henryzhuhao.henryfragmentation.exception.SupportException;

/**
 * Created by HenryZhuhao on 2017/3/19.
 */

public class SupportActivity extends AppCompatActivity implements SupportActivityAction {
    protected String Tag = this.getClass().getSimpleName();
    private SupportFragmentStack mFragmentStack;
    protected FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        mFragmentStack = new SupportFragmentStack();
        if (savedInstanceState != null) {
            restoreFragmentStack(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SupportFragmentStack.SAVED_KEY_FRAGMENT_STACK,mFragmentStack.getFragmentStack());
    }

    @Override
    public void loadfragment(@IdRes int containerId, @NonNull SupportFragment fragment) {
        startfragment(containerId, fragment);
    }

    @Override
    public void loadfragments(@IdRes int containerId, int postion, @NonNull SupportFragment... fragments) {
//        for (SupportFragment fragment:fragments){
//            if (mFragmentStack.addTagToStack(fragment.getFragmentTAG())) {
//                FragmentBaseTransaction.start(fm).add(containerId, fragment).commit();
//            } else {
//                throwException(new HasBeenAddedException(fragment.getFragmentTAG()));
//            }
//        }
//        FragmentBaseTransaction.start(fm).show(fragments[postion]);
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
    public void show(@NonNull SupportFragment fragment) {
        FragmentBaseTransaction.start(fm).hideAll().show(fragment).commit();
    }

    @Override
    public void close(@NonNull SupportFragment fragment) {
        if(mFragmentStack.removeTagfromStack(fragment.getFragmentTAG())){
            FragmentBaseTransaction.start(fm).remove(fragment).commit();
        }else {
            throwException(new NotAddedException(fragment.getFragmentTAG()));
        }

        String popStackFragment=mFragmentStack.getPopStackFragment();
        if(popStackFragment==null){
            close();
            return;
        }
        Fragment popFragment =FragmentBaseTransaction.getFragment(fm,popStackFragment);
        if(popFragment!=null){
            FragmentBaseTransaction.start(fm).hideAll().show(popFragment).commit();
        }else {
            throwException(new NotAddedException(popStackFragment));
        }

    }

    @Override
    public void close() {
        mFragmentStack.clearStack();
        finish();
    }

    public void restoreFragmentStack(Bundle savedInstanceState) {
        mFragmentStack.restoreStack((ArrayList<String>) savedInstanceState.getSerializable(SupportFragmentStack.SAVED_KEY_FRAGMENT_STACK));
    }

    @Override
    public void onBackPressed() {
        String popStackFragment = mFragmentStack.getPopStackFragment();//得到栈顶的Fragment
        if (popStackFragment == null) {//如果当前的视图没有嵌套的Fragment，则关闭当前页面
            close();
            return;
        }
        Log.e("back",popStackFragment);
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

    @Override
    public void throwException(SupportException e) {
        throw e;
    }
}
