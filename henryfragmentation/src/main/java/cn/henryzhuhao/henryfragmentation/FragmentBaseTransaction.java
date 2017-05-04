package cn.henryzhuhao.henryfragmentation;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by HenryZhuhao on 2017/3/24.
 */

public class FragmentBaseTransaction {
    private FragmentBaseTransaction() {

    }

    public static Builder start(FragmentManager fm) {
        return new Builder(fm);
    }

    public static class Builder {
        private FragmentManager fm;
        private FragmentTransaction ft;

        private Builder(FragmentManager fm) {
            this.fm = fm;
            ft = fm.beginTransaction();
        }

        public void commit() {
            ft.commitAllowingStateLoss();
            fm.executePendingTransactions();
            ft = null;
            fm = null;
        }

        public Builder add(@IdRes int containerId, @NonNull Fragment fragment) {
            ft.add(containerId, fragment, getFragmentTag(fragment));
            return this;
        }

        public Builder remove(Fragment fragment) {
            if (isFragmentAdded(fm, fragment)) {
                ft.detach(fragment);
                ft.remove(fragment);
            }
            return this;
        }

        public Builder removeAll() {
            List<Fragment> fragments = fm.getFragments();
            if (fragments == null || fragments.size() == 0) {
                return this;
            }
            for (Fragment fragment : fragments) {
                if (isFragmentAdded(fm, fragment)) {
                    ft.detach(fragment);
                    ft.remove(fragment);
                }
            }
            return this;
        }

        public Builder show(Fragment fragment) {
            ft.show(fragment);
            return this;
        }

        public Builder hide(Fragment fragment) {
            ft.hide(fragment);
            return this;
        }

        public Builder hideAll() {
            List<Fragment> fragments = fm.getFragments();
            if (fragments == null || fragments.size() == 0) {
                return this;
            }
            for (Fragment fragment : fragments) {
                if (fragment == null) {
                    continue;
                }
                hide(fragment);
            }
            return this;
        }

    }

    public static String getFragmentTag(@NonNull Fragment fragment) {
        if (fragment instanceof SupportFragment) {
            return ((SupportFragment) fragment).getFragmentTAG();
        }
        return fragment.getClass().getSimpleName();
    }

    public static Fragment getFragment(FragmentManager fm, String fragmentTag) {
        return fm.findFragmentByTag(fragmentTag);
    }

    public static Boolean isFragmentAdded(FragmentManager fm, Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        Fragment fragmentByTag = fm.findFragmentByTag(getFragmentTag(fragment));
        return fragmentByTag != null && fragmentByTag.isAdded();
    }
}
