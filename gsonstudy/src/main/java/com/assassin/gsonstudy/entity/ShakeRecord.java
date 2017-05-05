package com.assassin.gsonstudy.entity;

/**摇一摇摇到的奖品展示
 * @author 作者： adminstrator(范方方)
 * @datetime 创建时间：2016-07-05 14:30 GMT+8
 * @email 邮箱： 574583006@qq.com
 */
public class ShakeRecord 
{
    /**
     * 奖品名称
     */
    public String awardName;
    /**
     * 奖品获取时间
     */
    public String createTime;
    /**
     * 奖品展示的图片
     */
    public String picUrl;
    /**
     * 奖品详情跳到的H5页面
     */
    public String url;
    /**
     * 奖品来源： 0：摇一摇，免费 1：摇一摇，1pu币 2：摇一摇，2pu币 5：摇一摇，5pu币
     */
    public int source;
    /**
     * 奖品获取途径 0:实物 1:奖券
     */
    public int type;
    
}
