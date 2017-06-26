package android.cx.com.red_packet;

import java.io.Serializable;
import java.util.List;

/**
 * 卡券列表
 * Created by Administrator on 2016/2/23.
 */
public class DiscountListBean implements Serializable {


    private static final long serialVersionUID = -4759828117112371326L;
    public List<discount> list;

    public static class discount implements Serializable{

        private int id;//卡券id

        private String name;//卡券名称

        private String otherName;//卡券别称

        private double money;//卡券金额

        private double fees;//卡券优惠费率

        private double quota;//卡券投资限额

        private String code;//随机码

        private String takeTime;//领取时间

        private String effectTime;//卡券生效时间

        private String expireTime;//卡券过期时间

        private String description;//描述

        private int status;//卡券状态:0.待激活 1.已激活 2.已使用 3. 已失效

        private int type;//卡券类别:1.金包 2.抵价券 3.折扣券 4.现金红包 5.提现券 6.加息券
        private int overLap; // 是否与会员特权收益叠加 0 不， 1：是

        private boolean isSelect;//是否选中：0.未选中 1.选中

        public boolean isOverlap() {
            return overLap == 1;
        }


        public int getOverlap() {
            return overLap;
        }

        public void setOverlap(int overlap) {
            this.overLap = overlap;
        }

        public int getId() {
            return id;
        }


        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOtherName() {
            return otherName;
        }

        public void setOtherName(String otherName) {
            this.otherName = otherName;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public double getFees() {
            return fees;
        }

        public void setFees(double fees) {
            this.fees = fees;
        }

        public double getQuota() {
            return quota;
        }

        public void setQuota(double quota) {
            this.quota = quota;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTakeTime() {
            return takeTime;
        }

        public void setTakeTime(String takeTime) {
            this.takeTime = takeTime;
        }

        public String getEffectTime() {
            return effectTime;
        }

        public void setEffectTime(String effectTime) {
            this.effectTime = effectTime;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean isSelected() {
            return this.isSelect;
        }

    }
}
