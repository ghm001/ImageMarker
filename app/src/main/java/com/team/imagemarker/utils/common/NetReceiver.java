package com.team.imagemarker.utils.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.team.imagemarker.utils.ToastUtil;

/**
 * Created by Lmy on 2017/5/30.
 * email 1434117404@qq.com
 */

public class NetReceiver extends BroadcastReceiver {
    private   String netType=""; //网络类型
    private ToastUtil toastUtil = new ToastUtil();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action= intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            boolean isConnected = NetUtils.isNetworkConnected(context);
            boolean isConnectedTypeMobile=NetUtils.isMobileConnected(context);
            boolean isConnectedTypeWifi=NetUtils.isWifiConnected(context);
            if (isConnected) {
                if(isConnectedTypeWifi&&NetUtils.getConnectedType(context)==1){
                    this.setNetType("wifinet");
                }else if(isConnectedTypeMobile&&NetUtils.getConnectedType(context)==0){
                    this.setNetType("mobilenet");
                }
                toastUtil.Long(context, "网络已连接").show();
            } else {
                toastUtil.Long(context, "网络好像走丢了").show();
                this.setNetType("nonet");
            }
        }
    }
    public String getNetType() {
        return netType;
    }
    public void setNetType(String netType) {
        this.netType = netType;
    }
}
