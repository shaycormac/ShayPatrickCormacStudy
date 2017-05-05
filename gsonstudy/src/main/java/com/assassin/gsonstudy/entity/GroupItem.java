package com.assassin.gsonstudy.entity;

/**部落列表的数据
 * @author 作者： adminstrator(范方方)
 * @datetime 创建时间：2016-05-30 14:44 GMT+8
 * @email 邮箱： 574583006@qq.com
 * 
 */
public class GroupItem {

    public String assnId;// 社团ID
    public String name;// 社团名称
    public String categoryName;// 分类名称
    public String departName;// 部门名称
    public String icon;// 头像
    public String background;// 背景
    public String subSchoolName;// 学院
    public int personNum;// 人数
    public int vitality;// 活力值

    @Override
    public String toString() {
        return "GroupItem{" +
                "assnId=" + assnId +
                ", name='" + name + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", departName='" + departName + '\'' +
                ", icon='" + icon + '\'' +
                ", background='" + background + '\'' +
                ", subSchoolName='" + subSchoolName + '\'' +
                ", personNum=" + personNum +
                ", vitality=" + vitality +
                '}';
    }
}
