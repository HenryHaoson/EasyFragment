package cn.henryzhuhao.changemanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.henryzhuhao.henryfragmentation.SupportFragment;

/**
 * Created by HenryZhuhao on 2017/3/22.
 */

public class FrgTest extends SupportFragment {

    public View view;
    private int num;
    private int count=0;
    public static String BUNDLE_TAG="bundle.tag";

    public static FrgTest newInstance(int num) {

        Bundle args = new Bundle();
        args.putInt(BUNDLE_TAG,num);
        FrgTest fragment = new FrgTest();
        fragment.setNum(num);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=LayoutInflater.from(getContext()).inflate(R.layout.frg_test,null);
        if(savedInstanceState==null){
            Bundle args=getArguments();
            num=args.getInt(BUNDLE_TAG);
        }else {
            num=savedInstanceState.getInt(BUNDLE_TAG);
        }
        TextView tv=(TextView)view.findViewById(R.id.fg_text);
        tv.setText(num+"");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startfragment(R.id.fgtest_container,FrgTest.newInstance(count));
                count++;
            }
        });
        return view;
    }

    public void setNum(int num){
        this.num=num;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_TAG,num);
    }
}
