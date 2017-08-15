package android.cx.com.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.cx.com.list_card.CardsShowView;
import android.cx.com.list_card.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {
    private CardsShowView mItemShowView;
    private LinearLayout linearLayout;
    private  ObjectAnimator objectAnimator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        mItemShowView  = (CardsShowView)findViewById(R.id.items_layput);
        mItemShowView.setOpenItemSpace(0);
        initChild();
        linearLayout = (LinearLayout)findViewById(R.id.ll_input);
        mItemShowView.setStateChangeListener(mStateChangeListener);

        linearLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                showKeyboard();
            }
        },600);
    }


    public  void initChild(){
        List<View> childList = new ArrayList<View>();
        for(int i=0;i<5;i++){
            //  ImageView mImageView = new ImageView(mContext);
            View itemView =  LayoutInflater.from(this).inflate(R.layout.layout_bank_card_item,null);
            switch (i){
                case 0:
                    //   mImageView.setBackgroundColor(Color.BLUE);
                    itemView.setBackgroundResource(R.mipmap.red);
                    break;
                case 1:
                    //   mImageView.setBackgroundColor(Color.GREEN);
                    itemView.setBackgroundResource(R.mipmap.greem);
                    break;
                case 2:
                    //  mImageView.setBackgroundColor(Color.RED);
                    itemView.setBackgroundResource(R.mipmap.blue);
                    break;
                case 3:
                    //  mImageView.setBackgroundColor(Color.WHITE);
                    itemView.setBackgroundResource(R.mipmap.greem);
                    break;
                case 4:
                    //   mImageView.setBackgroundColor(Color.YELLOW);
                    itemView.setBackgroundResource(R.mipmap.blue);
                    break;
            }
            childList.add(itemView);

        }
        mItemShowView.setChildList(childList);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(linearLayout.findViewById(R.id.edit_text), 0);
    }
    private void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private CardsShowView.StateChangeListener mStateChangeListener = new CardsShowView.StateChangeListener() {
        @Override
        public void stateChange(int state,int cardViewId) {
            if(objectAnimator==null){
                objectAnimator = ObjectAnimator.ofFloat(linearLayout,"translationY",linearLayout.getBottom());
            }
            switch (state){
                case CardsShowView.ITEM_CLOSE:
                    objectAnimator.setDuration(600);
                    objectAnimator.reverse();
                    break;
                case CardsShowView.ITEM_OPEN:
                    closeKeyboard();
                    objectAnimator.setDuration(1000);
                    objectAnimator.start();
                    break;
            }
        }
    };
}
