package com.team.imagemarker.constants;

import android.graphics.Color;

/**
 * Created by Lmy on 2017/4/30.
 * email 1434117404@qq.com
 */

public class Constants {
    /**
     * 用户ID
     */
    public static int USER_ID = 0;

    /**
     * 服务器IP地址
     * 192.168.1.101 本机
     * 114.115.201.8 华为云服务器
     * 192.168.43.48 比赛场地Ip
     */
    public static String HOST_IP = "http://192.168.43.48";

    /**
     * 端口号
     */
    public static String HOST_PORT = ":8080";

    /**
     * 短信验证value_id
     */
    public static final String SMS_VERIFICATION_ID = "1d4c456254b5c";

    /**
     * 短信验证value_key
     */
    public static final String SMS_VERIFICATION_KEY = "6f5980d703c07b9ec90bc7cdd551bd8a";

    /**
     * QQ开放平台value_id
     */
    public static final String QQ_LOGIN_ID = "tencent1105819277";

    /**
     * 用户注册(手机号+密码)(请求参数：userPhone、userPassword)
     */
    public static final String USER_REGISTER = HOST_IP + HOST_PORT + "/look/app/SaveUser";

    /**
     * 用户登录（手机号 + 密码）(请求参数：userPhone、userPassword)
     */
    public static final String USER_LOGIN = HOST_IP + HOST_PORT + "/look/app/userLogin";

    /**
     * 用户第三方登录(请求参数：第三方账号、用户名、头像)
     */
    public static final String USER_LOGIN_OTHER = HOST_IP + HOST_PORT + "/look/app/otherLogin";

    /**
     * 用户密码重置(请求参数：userPhone、userPassword)
     */
    public static final String USER_PASSWORD_RESEAT = HOST_IP + HOST_PORT + "/look/app/ChangeUserPassword";

    /**
     * 用户手机判断时候已经注册过(请求参数：userPhone)
     */
    public static final String USER_PHONE_JUDGE = HOST_IP + HOST_PORT + "/look/app/JudgeUser";

    /**
     * 用户信息修改(请求参数：userId、userNick、userAge、userSex、userPhone、userObject、userHobby)
     */
    public static final String USER_UPDATE_MESSAGE = HOST_IP + HOST_PORT + "/look/app/ChangeUserInfo";

    /**
     * 用户退出登录
     */
    public static final String USER_EXIT = HOST_IP + HOST_PORT + "/look/app/outLogin";

    /**
     * 用户意见反馈
     */
    public static final String User_Center_Feed_Back = HOST_IP + HOST_PORT + "/look/app/AcceptAnOffer";

    /**
     * 删除历史记录()
     */
    public static final String USER_HISTORY_DELETE = HOST_IP + HOST_PORT + "/look/app/DeleteUserHistoryById";

    /**
     * 得到用户的历史记录(请求参数：userId)
     */
    public static final String USER_ALL_HISTORY = HOST_IP + HOST_PORT + "/look/app/GetUserHistory";

    /**
     * 得到用户当前的积分(请求参数：userId)
     */
    public static final String Get_User_Current_Integral = HOST_IP + HOST_PORT + "/look/app/getUserInteger";

    /**
     * 提交图片标签(请求参数：图组信息、userId)
     */
    public static final String USER_SUBMIT_TAG = HOST_IP + HOST_PORT + "/look/picture/judgeUserUpInageLabelInfo";

    /**
     * 保存图片标签(请求参数：图组信息、userId)
     */
    public static final String USER_SAVE_TAG = HOST_IP + HOST_PORT + "/look/picture/saveUserUpImageInfo";

    /**
     * 用户所有历史记录(请求参数：userId)
     */
    public static final String USER_HISTORY_DATA = "http://obs.myhwclouds.com/look.admin.info/historyRecord.txt";

    /**
     * 首页-随机推送(请求参数：无)
     */
    public static final String FirstPage_System_Push = HOST_IP + HOST_PORT + "/look/picture/randomPush";

    /**
     * 首页-领域推送(请求参数：userId)
     */
    public static final String FirstPage_Hobby_Push = HOST_IP + HOST_PORT + "/look/picture/DomainPush";

    /**
     * 兴趣导航-热门分类(请求参数：一级分类标题)
     */
    public static final String Hobby_Nav_Hot_Category = HOST_IP + HOST_PORT + "/look/picture/HotcategoriesPush";

    /**
     * 兴趣导航-猜你喜欢(请求参数：userId)
     */
    public static final String Hobby_Nav_Guess_You_Like = HOST_IP + HOST_PORT + "/look/picture/guessLikePush";

    /**
     * 兴趣导航-图片纠错:(请求参数：null)
     */
    public static final String Hobby_Nav_Img_Error = HOST_IP + HOST_PORT + "/look/picture/pushToUser";

    /**
     * 图片纠错-提交正确标签(请求参数：整个Model)
     */
    public static final String Img_Error_Collection = HOST_IP + HOST_PORT + "/look/acceptUserChange/";

    /**
     * 兴趣导航-自由搜索(请求参数：搜索内容)
     */
    public static final String Hobby_Nav_Free_Search = HOST_IP + HOST_PORT + "/look/app/search";

    /**
     * 美图浏览-每日推送(请求参数：无)
     */
    public static final String Img_Scan_Every_Day_Push = HOST_IP + HOST_PORT + "/look/app/EveryDayPicturePush";

    /**
     * 美图浏览-领域推送(请求参数：userId)
     */
    public static final String Img_Scan_Every_Marjor_Push = HOST_IP + HOST_PORT + "/look/app/UserInfoPush";

    /**
     * 美图浏览-猜你喜欢(请求参数：userId)
     */
    public static final String Img_Scan_Guess_You_Like = HOST_IP + HOST_PORT + "/look/app/LikeFromFinish";

    /**
     * 美图浏览-我的收藏(请求参数：userId)
     */
    public static final String Img_Scan_My_Collection = HOST_IP + HOST_PORT + "/look/app/GetUserCollection";

    /**
     * 美图浏览-图组详情-一键收藏(请求参数：userId、ImageUrl、ImageId)
     */
    public static final String Img_Scan_Dtail_Group_Collection_Img = HOST_IP + HOST_PORT + "/look/app/collectionPicture";

    /**
     * 美图浏览-图组详情-用户评论(请求参数：comment对象)
     */
    public static final String Img_Scan_Detail_Group_User_Comment = HOST_IP + HOST_PORT + "/look/app/SaveUserComment";

    /**
     * 任务情况-次数统计(请求参数：userId)
     */
    public static final String Task_Manager_All_Num = HOST_IP + HOST_PORT + "/look/app/GetUserTaskInfo";

    /**
     * 任务情况-近一周详细情况(请求参数：userId)
     */
    public static final String Task_Manager_Every_Week_Detail = HOST_IP + HOST_PORT + "/look/app/SevenDaysHistory";


    /**
     * 任务情况-近一周每天图片数量(请求参数：userId)
     */
    public static final String Task_Manager_Every_Day_Num = HOST_IP + HOST_PORT + "/look/app/GetUserFinishByDayNum";

    /**
     * 积分商城-用户基本信息(请求参数：userId)
     */
    public static final String Integral_Shopping_User_Information = HOST_IP + HOST_PORT + "/look/app/userIntegerInfo";

    /**
     * 积分商城-积分明细(请求参数：userId)
     */
    public static final String Integral_Shopping_Score = HOST_IP + HOST_PORT + "/look/app/GetUserIngraHistory";

    /**
     * 积分商城-积分前三名(请求参数：userId)
     */
    public static final String Integral_Shopping_Top_Three = HOST_IP + HOST_PORT + "/look/app/GetUserRank";

    /**
     * 积分商城-所有用户积分排行(请求参数：无)
     */
    public static final String Integral_Shopping_All_User_Rank = HOST_IP + HOST_PORT + "/look/app/GetUserRanktwo";

    /**
     * 积分商城-兑换商品(请求参数：userId)
     */
    public static final String Integral_Shopping_Exchange_Commodity = HOST_IP + HOST_PORT + "/look/app/UserIntegrareduce";

    /**
     * 兴趣社区-筛选内容(请求字段：兴趣Id)
     */
    public static final String Hobby_Commity_Search_Content = HOST_IP + HOST_PORT + "/look/app/getNewShare";

    /**
     * 兴趣社区-删除用户的某一条内容(请求参数：itemId)
     */
    public static final String Hobby_Commity_Delete_item_Content = HOST_IP + HOST_PORT + "/look/app/deleteOneShare";

    /**
     * 兴趣社区-加载内容(请求参数：最后一条itemId)
     */
    public static final String Hobby_Commity_Get_User_Item_Content = HOST_IP + HOST_PORT + "/look/app/getSomeShare";

    /**
     * 兴趣社区-发表一条内容(请求参数：完整的Share对象)
     */
    public static final String Hobby_Commity_User_Send_Item_Content = HOST_IP + HOST_PORT + "/look/app/saveShare  ";

    /**
     * 界面数据展示地址(模拟数据)
     */
    public static final String GUESS_YOU_LIKE_DATA = "http://obs.myhwclouds.com/look.admin.info/guessYouLike.txt";//兴趣导航-猜你喜欢

    /**
     * 测试数据
     */
    public static String Test_Img1 = "http://139.199.23.142:8080/TestShowMessage1/marker/shopping/img1.jpg";

    /**
     * App中标签tag的颜色
     */
    public static final int[] tagColors = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#F6BC7E"),

            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#F6BC7E")
    };

    /**
     * banner页数据(模拟数据)
     */
    public static final String[] bannerImg = {
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner1.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner2.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner3.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner4.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner5.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner6.jpg"
    };
}
