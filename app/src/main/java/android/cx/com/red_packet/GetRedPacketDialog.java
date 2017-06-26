package android.cx.com.red_packet;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.cx.com.list_card.R;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liujs on 2017/6/7.
 * 邮箱：725459481@qq.com
 */

public class GetRedPacketDialog extends Dialog {
    private Context mContext;
    private View.OnClickListener onClickListener;
    private TextView moneyText,redPacketDes;
    private ImageButton imageButton,imageCancleBtn;
    public static final int RUNNING_STATUS = 0X1;
    public static final int OPEN_STATUS = 0X12;
    public static final int RESET_STATUS = 0X14;
    private DiscountListBean.discount redPacketDiscount;
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({RUNNING_STATUS,OPEN_STATUS,RESET_STATUS})
    public @interface  RED_PACKET_STATUS{

    }


    public GetRedPacketDialog(Context context) {
        this(context, R.style.red_packet_dialog_style);

    }

    public GetRedPacketDialog(Context context, int themeResId) {
        super(context, R.style.red_packet_dialog_style);
        mContext = context;
    }


    public void initRedPacketData(DiscountListBean.discount redPacketDiscount) {
        if(redPacketDiscount!=null){
            this.redPacketDiscount = redPacketDiscount;
        }
    }

    public void setGetRedPacketButtonClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_red_packet_dialog);
      //  initDialogSize();
        imageCancleBtn = (ImageButton) this.findViewById(R.id.cancle_button);
        imageCancleBtn.setOnClickListener(clickListener);
        imageButton =  (ImageButton) this.findViewById(R.id.btn_change_state_action);
        imageButton.setOnClickListener(clickListener);
        moneyText = (TextView)this.findViewById(R.id.tv_money);
        redPacketDes = (TextView)this.findViewById(R.id.tv_redpacket_desc);
        if(redPacketDiscount!=null){
            moneyText.setText(getMoneySpannableString(redPacketDiscount.getMoney()+"¥"));
            redPacketDes.setText("投资"+redPacketDiscount.getName()+"获得\n"+"有效期：" + redPacketDiscount.getEffectTime() + "至" + redPacketDiscount.getExpireTime()
                    +"\n点击后兑换到账户余额");
        }

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cancle_button:
                case R.id.btn_sure:
                    resetView();
                    GetRedPacketDialog.this.cancel();
                    break;
                case R.id.btn_change_state_action:
                    if(onClickListener!=null){
                        onClickListener.onClick(v);
                    }
                    break;
            }

        }
    };

    /**
     * 红包的三种状态：打开中、打开、重置（打开失败回到原始状态）
     * @param redPacketStatus
     */
    public void setStatus(@RED_PACKET_STATUS int redPacketStatus){
        switch (redPacketStatus){
            case RUNNING_STATUS:
                getRedPacketRunning();
                break;
            case RESET_STATUS:
                resetView();
                break;
            case OPEN_STATUS:
                imageCancleBtn.setVisibility(View.GONE);
                changeToOpen();
                break;
        }
    }
    private  void getRedPacketRunning(){
      AnimationDrawable mAnimationDrawable =  (AnimationDrawable) imageButton.getDrawable();
       if(!mAnimationDrawable.isRunning()) {
           mAnimationDrawable.start();
       }
    }
    private void resetView(){
        AnimationDrawable mAnimationDrawable =  (AnimationDrawable) imageButton.getDrawable();
            mAnimationDrawable.stop();
        imageButton.setImageResource(R.drawable.get_red_packet_anim_drawable);
    }
    private void changeToOpen(){
        //换背景图，有红色变成白色
        this.findViewById(R.id.ll_detail).setBackgroundResource(R.mipmap.red_packet_open_bg);
        //隐藏相关view
        moneyText.setVisibility(View.GONE);
        redPacketDes.setVisibility(View.GONE);
        //显示并初始化相关view
        ViewStub mViewStub = (ViewStub) findViewById(R.id.red_packet_open_view);
          View openView =  mViewStub.inflate();
        TextView moneyTv = (TextView) openView.findViewById(R.id.tv_red_packet_money);
        if(redPacketDiscount!=null){
            moneyTv.setText(getMoneySpannableString(redPacketDiscount.getMoney()+"¥ "));
        }
        openView.findViewById(R.id.btn_sure).setOnClickListener(clickListener);
        //停止旋转动画
        AnimationDrawable mAnimationDrawable =  (AnimationDrawable) imageButton.getDrawable();
        if(mAnimationDrawable!=null)
             mAnimationDrawable.stop();
        //开启上移动画
      ObjectAnimator mObjectAnimator =   ObjectAnimator.ofFloat(imageButton,"translationY",imageButton.getY(),-imageButton.getMeasuredHeight()/4)
                .setDuration(400);
           mObjectAnimator.start();

    }

    private SpannableString getMoneySpannableString(String str) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new AbsoluteSizeSpan(30, true),0, str.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),0, str.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    };

}
