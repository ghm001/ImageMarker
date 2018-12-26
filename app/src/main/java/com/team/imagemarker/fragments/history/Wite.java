package com.team.imagemarker.fragments.history;

import com.team.loading.SweetAlertDialog;

import java.util.TimerTask;

/**
 * Created by Lmy on 2017/6/18.
 * email 1434117404@qq.com
 */

public class Wite extends TimerTask {
    private SweetAlertDialog sDialog;

    public Wite(SweetAlertDialog sDialog){
            this.sDialog = sDialog;
        }

    @Override
    public void run() {
        sDialog.dismiss();
    }
}
