package cn.henryzhuhao.changemanager;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

public class MainActivity extends cn.henryzhuhao.henryfragmentation.SupportActivity {
    public FragmentManager fm;
    public FragmentTransaction ft;
    public int num=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        final SupportFragment[] fragments=new SupportFragment[4];
//        for(int i=0;i<4;i++){
//            fragments[i]=FrgTest.newInstance(i);
//            this.loadfragment(R.id.fg_container,fragments[i]);
//        }
        Button btn=(Button)findViewById(R.id.id_change);
        startfragment(R.id.fg_container,FrgTest.newInstance(num));
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                show(fragments[(num++)%4]);
//            }
//        });

    }
}
