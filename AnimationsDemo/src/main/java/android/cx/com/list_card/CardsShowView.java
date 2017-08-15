package android.cx.com.list_card;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujs on 2017/6/6.
 * 邮箱：725459481@qq.com
 */

public class CardsShowView extends RelativeLayout implements View.OnTouchListener{
    private List<View> childList = new ArrayList<View>();
    private Context mContext;
    private  int CLOSE_ITEM_SPACE  = 20;
    private  int DEFAULT_OPEN_ITEM_SPACE = 20;
    private int openItemSpace;
    public static final int ITEM_CLOSE = 0x23;
    public static final int ITEM_OPEN = 0x123;
    private final int START_DELAY = 100;
    private int viewStatus = ITEM_CLOSE;
    private int mTouchSlop ;
    private int viewHeight,openContentHeight;
    private int paddingTop;
    private int cardWidth,cardHeight;
    private  StateChangeListener stateChangeListener;

    public CardsShowView(@NonNull Context context) {
        this(context,null);

    }
    public CardsShowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CardsShowView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        CLOSE_ITEM_SPACE = (int)convertDpToPixel(8,context);
        DEFAULT_OPEN_ITEM_SPACE = (int)convertDpToPixel(10,context);
        openItemSpace = DEFAULT_OPEN_ITEM_SPACE;
        cardWidth = (int)convertDpToPixel(600,context);
        cardHeight = (int)convertDpToPixel(150,context);
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        paddingTop = getPaddingTop();
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOnTouchListener(this);
        setFocusable(true);
        requestFocus();
        this.post(new Runnable() {
            @Override
            public void run() {
                try{
                    viewHeight = getMeasuredHeight()-paddingTop - getPaddingBottom();

                }catch (ClassCastException e){
                    e.printStackTrace();
                }
                if(getChildCount()!=childList.size()){
                    addAllCardsView();
                }
            }
        });
    }




    /**
     * 将密度单位转化成像素单位
     * @param dp
     * @param context
     * @return
     */
    private  float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


    /**
     * 初始化卡片的长宽和展开后间距的参数
     * @param cardHeight  若为-1 表示使用默认值
     * @param cardWidth  若为-1 表示使用默认值
     * @param openItemSpace  若为-1 表示使用默认值
     */
    public void initParams(int cardHeight,int cardWidth,int openItemSpace){
        if(this.cardHeight!=-1)
            this.cardHeight = cardHeight;
        if(this.cardWidth!=-1)
            this.cardWidth = cardWidth;
        if(this.openItemSpace!=-1)
            this.openItemSpace = openItemSpace;
    }
    public List<View> getChildList() {
        return childList;
    }

    public void setChildList(List<View> childList) {
        this.childList = childList;
        if(getChildCount()!=childList.size()){
            addAllCardsView();
        }
    }

    public void setCardWidth(int cardWidth) {
        this.cardWidth = cardWidth;
    }

    public void setCardHeight(int cardHeight) {
        this.cardHeight = cardHeight;
    }


    /**
     * 设置每个card的间隔
     * @param openItemSpace
     */
    public void setOpenItemSpace(int openItemSpace) {
        this.openItemSpace = openItemSpace;
    }

    /**
     * 监听卡片打开和关闭的动画
     * @param stateChangeListener
     */
    public void setStateChangeListener(StateChangeListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }




/*    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if(!childList.contains(child)){
            childList.add(child);
        }
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if(!childList.contains(child)){
            childList.add(child);
        }
        super.addView(child, params);
    }*/

    private void addAllCardsView(){
        removeAllViews();
        if(getScrollY()!=0){
            setScrollY(0);
        }

        for(int i = 0;i< childList.size();i++){
            RelativeLayout.LayoutParams layoutParams =  new RelativeLayout.LayoutParams(cardWidth,cardHeight);
            layoutParams.topMargin = CLOSE_ITEM_SPACE*(childList.size()-1)- CLOSE_ITEM_SPACE*i+ paddingTop;
            childList.get(i).setOnClickListener(clickListener);
            childList.get(i).setTag(i);
            addView(childList.get(i),i,layoutParams);
        }
    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(childList.size()<=1)return;
            if(viewStatus == ITEM_OPEN){
                if(stateChangeListener!=null)
                    stateChangeListener.stateChange(ITEM_CLOSE,v.getId());
                //先收起item
                closeItemView((int)v.getTag());
                viewStatus = ITEM_CLOSE;
            }else {
                if(stateChangeListener!=null)
                    stateChangeListener.stateChange(ITEM_OPEN,v.getId());
                requestLayout();
                openItemView();
                viewStatus = ITEM_OPEN;
                openContentHeight = childList.get(0).getMeasuredHeight()*childList.size()+ openItemSpace*(childList.size()-1);
            }
        }
    };


    private  float lastOffY,lastY,offY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = event.getY();
                lastOffY = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                offY = lastY - event.getY();
                if(viewStatus==ITEM_OPEN&&Math.abs(offY)>mTouchSlop){
                    if((canScrollDown()&&offY<0)||(canScrollUp()&&offY>0)){
                        int scrollY = (int)(offY+lastOffY);
                        //     Log.d("move scrollY",""+scrollY);
                        //   Log.d("move offY",""+offY);
                        if(scrollY<0){scrollY = 0;}
                        setScrollY(scrollY);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return super.onTouchEvent(event);
    }


    public boolean canScrollUp(){
        boolean canScrollUp = false;
        if(getChildCount()>0&&openContentHeight>viewHeight){
            canScrollUp =  (openContentHeight-getScrollY()+paddingTop) - viewHeight > 0;
            if(!canScrollUp){//当不能再向上滑动时，重新定义滑动起点，防止向下滑动时没有反应
                lastY = lastY - offY;
                lastOffY = getScrollY();
            }
            // Log.d("canScrollUp",canScrollUp+"");
        }
        return canScrollUp;
    };

    public boolean canScrollDown(){
        boolean canScrollDown = false;
        if(getChildCount()>0&&openContentHeight>viewHeight){
            canScrollDown = getScrollY() >  0;
            if(!canScrollDown){
                lastY = lastY - offY;
                lastOffY = getScrollY();
            }
            //  Log.d("getScrollY",""+getScrollY());
        }
        return canScrollDown;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float offY = lastY - event.getY();
                if(offY!=0&&viewStatus==ITEM_OPEN&&Math.abs(offY)>mTouchSlop){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }


    /**
     * 卡片展开
     */
    private void openItemView(){
        for(int i=1;i<childList.size();i++){
            int closeSpace = CLOSE_ITEM_SPACE*(childList.size()-1-i);
            int distance = childList.get(i-1).getMeasuredHeight()*(childList.size()-i) - closeSpace
                    + openItemSpace*(childList.size()-i) ;
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(childList.get(i-1),"translationY",distance);
            objectAnimator.setStartDelay(START_DELAY*(i-1));
            objectAnimator.setInterpolator(new AccelerateInterpolator());
            objectAnimator.start();
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    requestLayout();
                }
            });
        }
    }

    /**
     * 卡片收缩，重新排序
     * @param clickViewTag
     */
    private void closeItemView(final int clickViewTag){
        for(int i=childList.size()-1;i>0;i--){
            int startY = CLOSE_ITEM_SPACE*(childList.size() - i);
            int distance = childList.get(i-1).getMeasuredHeight()*(childList.size()-i) - startY + openItemSpace*(childList.size()-i);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(childList.get(i-1),"translationY",distance, 0);
            objectAnimator.setStartDelay(START_DELAY*(childList.size()-(i+1)));
            objectAnimator.setInterpolator(new DecelerateInterpolator());
            if(i==1){
                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //重新排序
                        if(clickViewTag!= (int)childList.get(childList.size()-1).getTag()){
                            View  clickView =  childList.get(clickViewTag);
                            childList.remove(clickView);
                            childList.add(clickView);
                        }
                        //重新绘制
                        addAllCardsView();
                    }
                });
            }
            objectAnimator.start();
        }
    }
    public  interface StateChangeListener{
        public abstract  void stateChange(int state,int selectCardId);
    }
}
