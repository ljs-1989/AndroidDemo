package android.cx.com.paopao;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.math.BigDecimal;

/**
 * Created by liujs on 2017/6/16.
 * 邮箱：725459481@qq.com
 */

public class TranslateCircle implements ICircle{
    private int mCircleColor;
    private int startCircleY,startCircleX;
    private int endCircleY,endCircleX;
    private int moveY,moveX,moveStub=2;
    private int mCurRadius;// 当前半径
    private int mMaxRadius;// 最大半径
    private BubbleView bubbleView;
    private boolean isPause = false;
    private int  moveStubY,moveStubX;
    private Paint  mPaint = new Paint();

    private TranslateCircle(){

    }

    private TranslateCircle(Builder builder) {
        mCircleColor = builder.mCircleColor;
        startCircleY = builder.circleY;
        startCircleX = builder.circleX;
        endCircleX = builder.endCircleX;
        endCircleY = builder.endCircleY;
        mCurRadius = builder.mCurRadius;
        mMaxRadius = builder.mMaxRadius;
        if(builder.moveStub!=0){
            this.moveStub = builder.moveStub;
        }
        bubbleView = builder.bubbleView;

        initParams();
    }

    private void initParams(){
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mCircleColor);
        mPaint.setAntiAlias(true);//抗锯齿
        moveY = endCircleY-startCircleY ;
        moveX = endCircleX - startCircleX;
        if(isCanMove()){
            if(moveX<0&&moveY>0){
                moveStubX = -moveStub;
                moveStubY  = moveY- new BigDecimal(Math.abs(moveX)-moveStub).multiply(new BigDecimal(moveY))
                        .divide(new BigDecimal(Math.abs(moveX)),0,BigDecimal.ROUND_HALF_UP)
                        .intValue();
            }else if(moveX<0&&moveY<0){
                moveStubX = -moveStub;
                moveStubY  = -(moveY- new BigDecimal(Math.abs(moveX)-moveStub).multiply(new BigDecimal(moveY))
                        .divide(new BigDecimal(Math.abs(moveX)),0,BigDecimal.ROUND_HALF_UP)
                        .intValue());
            }else if(moveX>0&&moveY>0){
                moveStubX = moveStub;
                moveStubY  = moveY- new BigDecimal(Math.abs(moveX)-moveStub).multiply(new BigDecimal(moveY))
                        .divide(new BigDecimal(Math.abs(moveX)),0,BigDecimal.ROUND_HALF_UP)
                        .intValue();
            }else if(moveX>0&&moveY<0){
                moveStubX = moveStub;
                moveStubY  = -(moveY- new BigDecimal(Math.abs(moveX)-moveStub).multiply(new BigDecimal(moveY))
                        .divide(new BigDecimal(Math.abs(moveX)),0,BigDecimal.ROUND_HALF_UP)
                        .intValue());
            }else if(moveX==0&&moveY<0){
                moveStubY = -moveStub;
            }else if(moveY==0&&moveX<0){
                   moveStubX = -moveStub;
            }
        }
    }

    public  boolean isCanMove(){
        if(moveX==0 && Math.abs(startCircleY-endCircleY)>moveStub){
            return  true;
        }
        if(moveY==0 && Math.abs(startCircleX-endCircleX) > moveStub){
            return true;
        }
        if(Math.abs(startCircleY-endCircleY) <= moveStub ||
                Math.abs(startCircleX-endCircleX) <= moveStub){
              return false;
        }

        return true;
    }
    @Override
    public void drawCircle(Canvas canvas){
           canvas.drawCircle(startCircleX, startCircleY, mCurRadius, mPaint);
           if(isCanMove()){
               if(moveX!=0&&moveY!=0){
                   startCircleX +=  moveStubX;
                   startCircleY +=  moveStubY;
                   Log.d("circle move","moveStubX= "+moveStub+"moveStubY="+moveStubY);
               }else if(moveX==0){
                   startCircleY += moveStub;
               }else if(moveY==0){
                   startCircleX += moveStub;
               }
           } else if(bubbleView!=null) {
              /* if(startCircleX!=endCircleX||startCircleY!=endCircleY){
                   startCircleX = endCircleX;
                   startCircleY = endCircleY;
               }*/
              if(!isPause){
                  isPause = true;
                  ++BubbleDrawer.pauseCircleSize;
                  if(bubbleView.getCurDrawer().getDrawCircleSize()==BubbleDrawer.pauseCircleSize){
                      bubbleView.onDrawPause();
                      bubbleView.onDrawDestroy();
                  }
              };
        }
        Log.d("circle item","startCircleX= "+startCircleX+"startCircleY="+startCircleY);
    }

    public static final class Builder {
        private int mCircleColor;
        private int circleY;
        private int endCircleY;
        private int endCircleX;
        private int circleX;
        private int mCurRadius;
        private int mMaxRadius;
        private int moveStub;
        private BubbleView bubbleView ;

        public Builder() {
        }
       public Builder setBubbleView(BubbleView bubbleView){
           this.bubbleView = bubbleView;
           return this;
       }
        public Builder mCircleColor(int val) {
            mCircleColor = val;
            return this;
        }
        public Builder endCircleCenter(int x,int y){
            endCircleX = x;
            endCircleY = y;
            return this;
        }
        public Builder circleCenter(int x,int y) {
            circleX = x;
            circleY = y;
            return this;
        }

        public Builder mCurRadius(int val) {
            mCurRadius = val;
            return this;
        }

        public Builder mMaxRadius(int val) {
            mMaxRadius = val;
            return this;
        }
       public Builder moveStub(int moveStub){
           this.moveStub = moveStub;
           return  this;
       }

        public TranslateCircle build() {
            return new TranslateCircle(this);
        }
    }
}
