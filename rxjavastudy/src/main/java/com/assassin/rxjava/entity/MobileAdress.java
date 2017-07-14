package com.assassin.rxjava.entity;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/7/11 14:42
 * @Version:
 * @Description:
 */

public class MobileAdress {
    /**
     * result : {"mobilenumber":"1302167","mobilearea":"山东 青岛市","mobiletype":"联通如意通卡","areacode":"0532","postcode":"266000"}
     * error_code : 0
     * reason : Succes
     */

    public ResultBean result;
    public int error_code;
    public String reason;

    public static class ResultBean {
        /**
         * mobilenumber : 1302167
         * mobilearea : 山东 青岛市
         * mobiletype : 联通如意通卡
         * areacode : 0532
         * postcode : 266000
         */

        public String mobilenumber;
        public String mobilearea;
        public String mobiletype;
        public String areacode;
        public String postcode;
    }
}
