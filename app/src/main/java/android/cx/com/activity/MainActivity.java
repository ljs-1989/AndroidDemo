package android.cx.com.activity;

import android.content.Intent;
import android.cx.com.red_packet.DiscountListBean;
import android.cx.com.red_packet.GetRedPacketDialog;
import android.cx.com.list_card.R;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
private GetRedPacketDialog getRedPacketDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.btn_bubble).setOnClickListener(clickListener);
        this.findViewById(R.id.btn_card).setOnClickListener(clickListener);
        this.findViewById(R.id.btn_red_packet).setOnClickListener(clickListener);
    }

private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_bubble:
                    Intent intent = new Intent(MainActivity.this,QiPaoctivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_card:
                    Intent intent2 = new Intent(MainActivity.this,CardActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.btn_red_packet:
                     getRedPacketDialog = new GetRedPacketDialog(MainActivity.this);
                    DiscountListBean.discount redPacketDiscount = new   DiscountListBean.discount();
                    redPacketDiscount.setMoney(12);
                    redPacketDiscount.setName("限惠赢");
                    redPacketDiscount.setExpireTime("2018-3-2");
                    redPacketDiscount.setEffectTime("2017-3-2");
                    getRedPacketDialog.initRedPacketData(redPacketDiscount);
                    getRedPacketDialog.setGetRedPacketButtonClickListener(this);

                    getRedPacketDialog.show();
                    break;
                case R.id.btn_change_state_action:
                    //1、网络请求兑换红包
                    //  ..........

                   if(getRedPacketDialog!=null){
                       //2、红包兑换中状态
                       getRedPacketDialog.setStatus(GetRedPacketDialog.RUNNING_STATUS);

                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               //3、红包兑换成功状态
                               getRedPacketDialog.setStatus(GetRedPacketDialog.OPEN_STATUS);
                               //4、红包兑换失败状态
                               //   getRedPacketDialog.setStatus(GetRedPacketDialog.RESET_STATUS);
                           }
                       },500);

                   }
                    break;
            }

        }
    };
}
