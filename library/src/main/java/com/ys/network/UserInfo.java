package com.ys.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInfo implements Serializable {

    /**
     * mobile : 15011973901
     * userId : 45
     * username : 15011973901
     * token : 16d46ab293484b1f9b3d53220966724a
     */

    private String mobile;
    private int id;
    private String username;
    private String token;
    private String password;
    private int mgrade;
    private String nickname;
    private String avatar;
    private String gender;
    private String unionid;
    private String openId;
    private String rongYunToken;
    private String orderNo;
    private String alipay_url;
    private WXPayInfo wxpay_url;
    private int userType;
    private int isAudit;
    private int isNew;
    private int isFlag;
    private String gAName;
    private int status;
    private String payment;
    public String getgAName() {
        return gAName;
    }

    public void setgAName(String gAName) {
        this.gAName = gAName;
    }

    public int getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(int isFlag) {
        this.isFlag = isFlag;
    }

    public int getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(int isAudit) {
        this.isAudit = isAudit;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getId() {
        return id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMgrade() {
        return mgrade;
    }

    public void setMgrade(int mgrade) {
        this.mgrade = mgrade;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getRongYunToken() {
        return rongYunToken;
    }

    public void setRongYunToken(String rongYunToken) {
        this.rongYunToken = rongYunToken;
    }

    public String getAlipay_url() {
        return alipay_url;
    }

    public WXPayInfo getWxpay_url() {
        return wxpay_url;
    }

    public void setWxpay_url(WXPayInfo wxpay_url) {
        this.wxpay_url = wxpay_url;
    }

    public void setAlipay_url(String alipay_url) {
        this.alipay_url = alipay_url;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public class WXPayInfo implements Serializable {

        /**
         * msg : 订单生成成功
         * package : Sign=WXPay
         * appid : wx47e485dbc81c3807
         * sign : 96BD6C3E7CF17099570EFA965212176Fmtype
         * prepayid : wx201524463347682cd69037c60604631397
         * partnerid : 1513874101
         * noncestr : e5e580bb7e6f5e01ecf1be2c21a834e7
         * info : 200
         * timestamp : 1540020286
         */

        private String msg;
        @SerializedName("package")
        private String packageX;
        private String appid;
        private String sign;
        private String prepayid;
        private String partnerid;
        private String noncestr;
        private int info;
        private String timestamp;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public int getInfo() {
            return info;
        }

        public void setInfo(int info) {
            this.info = info;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
