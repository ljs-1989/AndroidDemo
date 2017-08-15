package android.cx.com.activity;

import android.cx.com.paopao.BubbleDrawer;
import android.cx.com.paopao.FloatCircle;
import android.cx.com.paopao.TranslateCircle;
import android.cx.com.paopao.BubbleView;
import android.cx.com.list_card.R;
import android.cx.com.utils.DimenUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

public class QiPaoctivity extends AppCompatActivity {
    BubbleView transBubbleView,floatBubbleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qi_paoctivity);
        //移动的气泡
        transBubbleView = (BubbleView) findViewById(R.id.trans_bubble);
        transBubbleView.setDrawer(initBubbleDrawabler());

        //浮动的气泡
        floatBubbleView = (BubbleView) findViewById(R.id.float_bubble);
        floatBubbleView.setDrawer(initFloatBubbleDrawabler(DimenUtils.getScreenWidth(this)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        transBubbleView.onDrawResume();
        floatBubbleView.onDrawResume();
    }
   private BubbleDrawer initFloatBubbleDrawabler(int width){
       BubbleDrawer mBubbleDrawer = new BubbleDrawer(this);
      int color =  this.getResources().getColor(R.color.colorAccent);
       FloatCircle mFloatBubble1 = new FloatCircle.Builder(0.20f * width, 0.30f * width, 0.06f * width, 0.022f * width, 0.26f * width,
               color, 0.0100f).build();
       FloatCircle mFloatBubble2 = new FloatCircle.Builder(0.58f * width, 0.15f * width, -0.15f * width, 0.032f * width, 0.15f * width,
               color, 0.00200f).build();
       FloatCircle mFloatBubble3 = new FloatCircle.Builder(0.9f * width, 0.19f * width, 0.08f * width, -0.015f * width, 0.14f * width,
               color, 0.0150f).build();
       FloatCircle mFloatBubble4 = new FloatCircle.Builder(0.3f * width, 0.25f * width, -0.08f * width, -0.015f * width, 0.12f * width,
               color, 0.00600f).build();
       FloatCircle mFloatBubble5 = new FloatCircle.Builder(0.20f * width, 0.50f * width, -0.06f * width, 0.022f * width, 0.12f * width,
               color,  0.00300f).build();
       FloatCircle mFloatBubble6 = new FloatCircle.Builder(0.70f * width, 0.60f * width, 0.10f * width, 0.050f * width, 0.30f * width,
               color, 0.00200f).build();

       mBubbleDrawer.addCircleItem(mFloatBubble1);
       mBubbleDrawer.addCircleItem(mFloatBubble2);
       mBubbleDrawer.addCircleItem(mFloatBubble3);
       mBubbleDrawer.addCircleItem(mFloatBubble4);
      mBubbleDrawer.addCircleItem(mFloatBubble5);
       mBubbleDrawer.addCircleItem(mFloatBubble6);
       //设置渐变背景 如果不需要渐变 设置相同颜色即可
       mBubbleDrawer.setBackgroundGradient(new int[]{0xff782346, 0xffffff90});
       return mBubbleDrawer;
   };


    private BubbleDrawer initBubbleDrawabler(){
        BubbleDrawer mBubbleDrawer = new BubbleDrawer(this);
        TranslateCircle mCircleItem = new TranslateCircle.Builder()
                .mCircleColor(this.getResources().getColor(R.color.circle_one))
                .startCircleCenter(12,34)
                .endCircleCenter(145,256)
                .mCurRadius(180)
                .moveStub((int)DimenUtils.convertDpToPixel(1,this))
                .setBubbleView(transBubbleView)
                .build();
        TranslateCircle mCircleItem2 = new TranslateCircle.Builder()
                .mCircleColor(this.getResources().getColor(R.color.colorPrimary))
                .startCircleCenter(23,54)
                .endCircleCenter(723,334)
                .mCurRadius(80)
                .moveStub((int)DimenUtils.convertDpToPixel(1,this))
                .setBubbleView(transBubbleView)
                .build();
        TranslateCircle mCircleItem3 = new TranslateCircle.Builder()
                .mCircleColor(this.getResources().getColor(R.color.circle_one))
                .startCircleCenter(760,24)
                .endCircleCenter(12,354)
                .moveStub((int)DimenUtils.convertDpToPixel(1,this))
                .mCurRadius(100)
                .setBubbleView(transBubbleView)
                .build();
        mBubbleDrawer.addCircleItem(mCircleItem);
        mBubbleDrawer.addCircleItem(mCircleItem2);
        mBubbleDrawer.addCircleItem(mCircleItem3);
        //设置渐变背景 如果不需要渐变 设置相同颜色即可
        mBubbleDrawer.setBackgroundGradient(new int[]{0xff782346, 0xffff34ff});
        return mBubbleDrawer;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        transBubbleView.onDrawDestroy();
        floatBubbleView.onDrawDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        transBubbleView.onDrawPause();
        floatBubbleView.onDrawPause();
    }
}
