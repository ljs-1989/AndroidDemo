package android.liujs.com.testndk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //使用静态代码块，表示我们要加载的资源文件为libsecret.so
    static{
        System.loadLibrary("secret");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.text_id)).setText(stringFromat());
    }

    //声明一个本地native方法
    public native String stringFromat();
}
