package android.cx.com.paopao;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mixiaoxiao
 * @revision xiarui 16/09/27
 * @description 绘制圆形浮动气泡及设定渐变背景的绘制对象
 */
public class BubbleDrawer {

    /*===== 图形相关 =====*/
    private GradientDrawable mGradientBg;       //渐变背景
    private Paint mPaint; //抗锯齿画笔
    private int mWidth, mHeight;//上下文对象
    private ArrayList<ICircle> mBubbles; //存放气泡的集合
    private int[] mGradientColors = {};              //渐变颜色数组
    public static int pauseCircleSize;
    /**
     * 构造函数
     *
     * @param context 上下文对象 可能会用到
     */
    public BubbleDrawer(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubbles = new ArrayList<ICircle>();
        pauseCircleSize = 0;
    }




    /**
     * 设置显示悬浮气泡的范围
     * @param width 宽度
     * @param height 高度
     */
    void setViewSize(int width, int height) {
        if (this.mWidth != width && this.mHeight != height) {
            this.mWidth = width;
            this.mHeight = height;
            if (this.mGradientBg != null) {
                mGradientBg.setBounds(0, 0, width, height);
            }
        }

    }
  public int getDrawCircleSize(){
      if(mBubbles!=null){
        return  mBubbles.size();
      }
      return 0;
  }
    /**
     * 添加一个气泡
     * @param circle
     */
   public synchronized void addCircleItem(ICircle circle){
       mBubbles.add(circle);
   }

    /**
     * 删除某个气泡
     * @param index
     */
   public synchronized void removeCircleItem(int index){
       mBubbles.remove(index);
   }

   public List<ICircle> getAllCircles(){
       return  mBubbles;
   }
    /**
     * 删除气泡
     * @param circle
     */
   public synchronized void removeCircleItem(ICircle circle){
       mBubbles.remove(circle);
   }

    /**
     * 用画笔在画布上画气泡
     * @param canvas 画布
     *
     */
    private  void drawCircleBubble(Canvas canvas ){
        //循环遍历所有设置的圆形气泡
        for (ICircle bubble : this.mBubbles) {
            bubble.drawCircle(canvas);
        }
    }

    /**
     * 画背景 画所有的气泡
     *
     * @param canvas 画布
     * @param alpha  透明值
     */
    void drawBgAndBubble(Canvas canvas, float alpha) {
        drawGradientBackground(canvas, alpha);
        drawCircleBubble(canvas);
    }

    /**
     * 设置渐变背景色
     *
     * @param gradientColors 渐变色数组 必须 >= 2 不然没法渐变
     */
    public void setBackgroundGradient(int[] gradientColors) {
        this.mGradientColors = gradientColors;
    }

    /**
     * 获取渐变色数组
     * @return 渐变色数组
     */
    private int[] getBackgroundGradient() {
        return mGradientColors;
    }

    /**
     * 绘制渐变背景色
     *
     * @param canvas 画布
     * @param alpha  透明值
     */
    private void drawGradientBackground(Canvas canvas, float alpha) {
        if (mGradientBg == null) {
            //设置渐变模式和颜色
            mGradientBg = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, getBackgroundGradient());
            //规定背景宽高 一般都为整屏
            mGradientBg.setBounds(0, 0, mWidth, mHeight);
        }
        //然后开始画
        mGradientBg.setAlpha(Math.round(alpha * 255f));
        mGradientBg.draw(canvas);
    }

}
