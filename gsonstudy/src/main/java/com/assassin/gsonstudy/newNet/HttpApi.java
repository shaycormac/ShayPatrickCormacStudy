package com.assassin.gsonstudy.newNet;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/7/14 11:16
 * @Version: 1.0 
 * @Description:  兼容类
 */

public class HttpApi<T>
{
    //请求网络所依附的activity或者fragment
    private Object object;
    private MyCallback<T> callback;

    public HttpApi(Object object, MyCallback<T> callback) 
    {
        this.object = object;
        this.callback = callback;
    }

    private static final String TAG = "Net";
    //    public static final String HOST = "121.41.38.32:3000";
    public static final String HOST = "218.4.189.196:8008";
    public static final String PROTOCOL_HOST = "http://" + HOST;

    //新的host 超哥
//    public static final String NEW_HOST = "http://172.16.55.125:8090";
    public static final String NEW_HOST = "http://mobile.goldmantis.com:7090";//正式

    //    public static final String ZhongAnURL = "https://mp.zhongan.com/DEV_ALI9E6Y8O/tuan/xieyingshuang/user/v2-indexPage.html?source=goldMantis&certiNo=659001199401110611&phone=18259713978";
    public static final String ZhongAnURL = "https://mp.zhongan.com/DEV_ALI9E6Y8O/tuan/yisonli/user/v2-indexPage.html?source=goldMantis";
    public final static String GET_UNREAD_EMAILSID = PROTOCOL_HOST + "/Universal/EmailGet.ashx?RequestType=2";//获取邮件sid
    public final static String GET_UNREAD_EMAILCOUNT = PROTOCOL_HOST + "/Universal/EmailGet.ashx?RequestType=1";//获取未读邮件个数
    public final static String GET_UNREAD_NEWEMAIL = PROTOCOL_HOST + "/Universal/EmailGet.ashx?RequestType=3";//获取新邮件
    public static final String GET_NEWS_LIST = PROTOCOL_HOST + "/OA/Notice/AndroidNoticeList.ashx";//新闻
    public static final String SEARCH_ENTRUSET_PEOPLE = PROTOCOL_HOST + "/Controls/UserSelectCheck.ashx";//关键字搜索审批委托人
    public static final String GET_MY_INITIATE_LIST = PROTOCOL_HOST + "/OA/WorkSpace/OwnerWorkflow.ashx";//我发起的审批列表
    public static final String GET_MY_MANAGE_LIST = PROTOCOL_HOST + "/OA/WorkSpace/OwnerHandleWorkflow.ashx";//我处理的审批列表
    public static final String GET_DAIBAN_LSIT_URL = PROTOCOL_HOST + "/OA/WorkSpace/AndroidTodoWorkList.ashx";//我的待办list
    public static final String GET_CHULIHENPI_LSIT_URL = PROTOCOL_HOST + "/OA/WorkSpace/OwnerHandleWorkflow.ashx";//我处理的审批list
    public static final String GET_FAQISHENPI_LSIT_URL = PROTOCOL_HOST + "/OA/WorkSpace/OwnerWorkflow.ashx";//我发起的审批list Img_Url
    public static final String GET_DAIBAN_GET_COUNT = PROTOCOL_HOST + "/WF/WorkListCount.ashx";//获取待办个数

    public static final String GET_DAIBAN_LISTDETAIL_URL = PROTOCOL_HOST + "/WF/UniversalForm.ashx";//我的待办详情 url 不为1的时候使用
    public static final String GET_DAIBAN_LISTDETAIL_REPROTURL = PROTOCOL_HOST + "/OA/DE/PlanEdit.ashx";//我的待办详情 url

    //http://172.16.55.125:8090/api/UserAPI/GetUserInfosByUserID?userID=30730
    public static final String GET_USER_INFO = NEW_HOST + "/api/UserAPI/GetUserInfosByUserID";

    public static final String GET_SHENPI_ADVICEDETIAL = PROTOCOL_HOST + "/WF/CheckToken.ashx";//审批建议的获得

    //    public static final String LOGIN_URL = PROTOCOL_HOST + "/Login.ashx";
    //新的登录接口 http://172.16.55.29/api/gm/Login/LogOn
    public static final String LOGIN_URL = NEW_HOST+"/api/login/LogOn";

    public static final String GET_DEFAULT_CONTACTS = NEW_HOST + "/api/UserAPI/GetInitContact"; //获取默认的联系人
    public static final String ADD_CONTACTS = NEW_HOST + "/api/UserAPI/Add"; //添加联系人
    public static final String DELETE_CONTACTS = NEW_HOST + "/api/UserAPI/Delete"; //删除联系人
    public static final String GET_CONTACTS_BY_USERNAME = NEW_HOST + "/api/UserAPI/GetContactByUserName"; //根据条件查询的联系人
    public static final String GET_COLLECTION_CONTACT = NEW_HOST + "/api/UserAPI/GetCollectionContact"; //获取收藏的联系人
    public static final String GET_MY_SALARY = NEW_HOST + "/api/UserAPI/GetEmpSalary"; //获取我的工资条
    public static final String GET_RONG_TOKEN = NEW_HOST + "/api/SMSAPI/GetRongCloudToken"; //获取融云Token

    //http://172.16.55.125:8090/api/UserAPI/SetUserModule?json=""
    public static final String POST_SET_APPLICATION_LIST = NEW_HOST + "/api/UserAPI/SetUserModule";//设置应用列表
    public static final String GET_MODEL_APP_LIST = NEW_HOST + "/api/UserAPI/GetModuleApplication";//获取应用列表

    public static final String POST_ADD_SCHEDULE = NEW_HOST + "/api/ScheduleAPI/Add";//添加日程
    public static final String POST_UPDATE_SCHEDULE = NEW_HOST + "/api/ScheduleAPI/Update";//更新日程

    //    http://172.16.55.125:8090/api/ScheduleAPI/GetShowList?userid=40245&date=2017-06-15&sortType=1
    public static final String GET_SCHEDULE_LIST = NEW_HOST + "/api/ScheduleAPI/GetShowList";//日程列表

    //http://172.16.55.125:8090/api/ScheduleAPI/GetModel?scheduleId=1
    public static final String GET_SCHEDULE_DETIAL = NEW_HOST + "/api/ScheduleAPI/GetModel";//日程详情

    //    http://172.16.55.125:8090/api/ScheduleAPI/UpdateStatus?id=1&status=1
    public static final String UPDATE_SCHEDULE_STATE = NEW_HOST + "/api/ScheduleAPI/UpdateStatus";//更新日程状态

    //    http://172.16.55.125:8090/api/ScheduleAPI/GetDateList?userid=40245&startDate=2017-05-14&endDate=2017-06-15
    public static final String GET_SCHEDULE_DATE = NEW_HOST + "/api/ScheduleAPI/GetDateList";//获取前后一个月有日程的日期

    //    http://172.16.55.125:8090/api/MemorandumAPI/Add?userid=18814&Title=test&Content=test
    public static final String POST_ADD_NEW_REMINDER = NEW_HOST + "/api/MemorandumAPI/Add";//添加备忘
    //    http://172.16.55.125:8090/api/MemorandumAPI/GetShowList?userid=18814
    public static final String GET_REMINDER_LIST = NEW_HOST + "/api/MemorandumAPI/GetShowList";//获取备忘列表
    //    http://172.16.55.125:8090/api/MemorandumAPI/UPdate?ID=1&UserId=18814&Title=test&Content=test
    public static final String UPDATE_REMINDER = NEW_HOST + "/api/MemorandumAPI/UPdate";//更新备忘
    //    http://172.16.55.125:8090/api/MemorandumAPI/Delete?id=1 备忘录删除接口 POST
    public static final String DELETE_REMINDER = NEW_HOST + "/api/MemorandumAPI/Delete";//删除备忘

    //    http://172.16.55.125:8090/api/AMAssetsAPI/GetAssets?userId=18814
    public static final String GET_ASSET_LIST = NEW_HOST + "/api/AMAssetsAPI/GetAssets";//获取固定资产列表
    public static final String POST_UPLOAD_SCHEDULE_PHOTO = NEW_HOST + "/api/FileUploadApi/UploadFile";//添加日程上传图片
    public static final String GET_USERINFO_BY_JOBCODE = NEW_HOST + "/api/UserAPI/GetContactByJobCode";//获取个人信息
    public static final String GET_ATTENDANCE_BY_JOB_CODE = NEW_HOST + "/api/USERAPI/GetWorkAttendance";//获取我的考勤信息
    //    public static final String GET_AD_BANNER = "http://172.16.55.29:8012/api/ContentAPI/GetList";//获取首页广告Banne
    public static final String GET_AD_BANNER = NEW_HOST+"/api/ContentAPI/GetList";//获取首页广告Banner
    public static final String GET_NEW_VERSION = NEW_HOST+"/api/login/GetVersion";//获取更新版本信息


    //post请求
    public void post( String path, HttpParams params) 
    {
        /*if(!NetWorkUtils.isNetworkConnected(ErpApp.getContext())){
            ToastUtil.showShort("无网络连接,请检查网络设置");
        }*/
        OkGo.<T>post(path)
                .tag(object)
                .params(params)
                .execute(callback);
    }

    //get请求
    public  void get(String path, HttpParams params) 
    {
        /*if(!NetWorkUtils.isNetworkConnected(ErpApp.getContext())){
            ToastUtil.showShort("无网络连接,请检查网络设置");
        }*/
        OkGo.<T>get(path)
                .tag(object)
                .params(params)
                .execute(callback);
    }
    
    /**post带有表单的请求
     * 添加日程 (请求头是application_json)
     * @param path        路径
     * @param requestJson 携带的实体表单转换成String字符串后的内容
     */
    public void postWithForm(String path, String requestJson) {
        OkGo.<T>post(path)
                .headers("Content-Type", "application/json")
                .upJson(requestJson)
                .tag(object)
                .execute(callback);
    }
    
    /*************************************请求的方法体**********************************************************/
    
    public void getJsonObject()
    {
        get(Urls.URL_JSONARRAY, null);
    }
    
}

