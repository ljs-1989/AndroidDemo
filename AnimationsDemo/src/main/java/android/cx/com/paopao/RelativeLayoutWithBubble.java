package android.cx.com.paopao;

import android.content.Context;
import android.cx.com.list_card.R;
import android.cx.com.utils.DimenUtils;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujs on 2017/6/28.
 * 邮箱：725459481@qq.com
 */

public class RelativeLayoutWithBubble extends RelativeLayout {

    private List<View> listBubbleView;
    private Context mContext;
    private int screenWidth,viewHeight;

    public RelativeLayoutWithBubble(Context context) {
        this(context,null);
    }

    public RelativeLayoutWithBubble(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RelativeLayoutWithBubble(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        screenWidth = (int)(DimenUtils.getScreenWidth(mContext));
        viewHeight = (int)DimenUtils.convertDpToPixel(180,mContext);
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    private List<View> getListBubbleView(){
              listBubbleView = new ArrayList<View>();
                  int radius1 = (int)DimenUtils.convertDpToPixel(130,mContext);
                listBubbleView.add(initSranslateBubbleView(-DimenUtils.convertDpToPixel(400,mContext),-radius1,
                        0,viewHeight-radius1,radius1));
                  int radius2 = (int)DimenUtils.convertDpToPixel(50,mContext);
                listBubbleView.add(initSranslateBubbleView(-DimenUtils.convertDpToPixel(400,mContext),screenWidth*0.5f,
                        -2*radius2,-radius2,radius2));
               int radius3 = (int)DimenUtils.convertDpToPixel(35,mContext);
                listBubbleView.add(initSranslateBubbleView(screenWidth*0.5f,screenWidth*0.7f,-radius3*3,viewHeight-radius3*2,radius3));
                int radius4 = (int)DimenUtils.convertDpToPixel(90,mContext);
                listBubbleView.add(initSranslateBubbleView(-radius4/2,screenWidth-radius4*4/3,-radius4*2,-(radius4/2),radius4));

        return listBubbleView;
    }

    public void attachBubbleToParentView(){
        listBubbleView = getListBubbleView();
         for (int i=0;i<listBubbleView.size();i++){
             View childView = listBubbleView.get(i);
             addView(childView);
            childView.getAnimation().startNow();
         }
    }
    private View initSranslateBubbleView(float fromXDelta,float toXDelta,float fromYDelta,float toYDelta,int radius){
       final BubbleView mImageView = new BubbleView(mContext);
         mImageView.setMinimumHeight(radius*2);
         mImageView.setMinimumWidth(radius*2);
         TranslateAnimation animation = new TranslateAnimation(fromXDelta, toXDelta,fromYDelta, toYDelta);
          animation.setDuration(2000);//设置动画持续时间
        //  animation.setRepeatCount(0);//设置重复次数
        animation.setRepeatMode(Animation.REVERSE);
        animation.setFillAfter(true);
        animation.setInterpolator(new DecelerateInterpolator());
        mImageView.setAnimation(animation);

        return mImageView;
    }

    class BubbleView extends View{

        private Paint mPaint = new Paint();
        public BubbleView(Context context) {
            super(context);
            mPaint.setColor(0xffffffff);
            mPaint.setAlpha((int)(255*0.1f));
        }

        public BubbleView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public BubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);
            Log.d("getMinimumHeight",getMinimumHeight()+"");
            canvas.drawCircle(getMinimumHeight()/2,getMinimumHeight()/2,getMinimumHeight()/2,mPaint);
        }


    }
}
