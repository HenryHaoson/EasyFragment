package cn.henryzhuhao.henryfragmentation;

import java.util.ArrayList;

/**
 * Created by HenryZhuhao on 2017/3/20.
 */

public class SupportFragmentStack {
    public static String SAVED_KEY_FRAGMENT_STACK = "saved.key.fragmentstack";
    private ArrayList<String> fragmentStack;

    public SupportFragmentStack() {
        fragmentStack = new ArrayList<>();
    }

    public ArrayList<String> getFragmentStack() {
        return fragmentStack;
    }

    public void restoreStack(ArrayList<String> fragmentStack) {
        this.fragmentStack = fragmentStack;
    }

    public Boolean addTagToStack(String fragmentTag) {
        if (!fragmentStack.contains(fragmentTag)) {
            fragmentStack.add(fragmentTag);
            return true;
        }
        return false;
    }

    public Boolean removeTagfromStack(String fragmentTag) {
        if (fragmentStack.contains(fragmentTag)) {
            fragmentStack.remove(fragmentTag);
            return true;
        }
        return false;
    }

    public String getPopStackFragment() {
        if (fragmentStack.size() > 0) {
            String fragmentTag = fragmentStack.get(fragmentStack.size() - 1);
            if (fragmentTag == null || fragmentTag.trim().isEmpty()) {
                fragmentStack.remove(fragmentStack.size() - 1);
                return getPopStackFragment();
            } else {
                return fragmentTag;
            }
        }
        return null;
    }

    public void clearStack() {
        fragmentStack.clear();
    }
}
