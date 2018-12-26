package com.team.imagemarker.activitys.integral;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.utils.common.SizeUtils;
import com.team.imagemarker.utils.luckdraw.LuckPanLayout;
import com.team.imagemarker.utils.luckdraw.RotatePan;

import java.util.Timer;
import java.util.TimerTask;

public class LuckDrawActivity extends Activity implements RotatePan.AnimationEndListener{
    private RotatePan rotatePan;
    private LuckPanLayout luckPanLayout;
    private ImageView goBtn;
    private String[] strs = {"华为手机","谢谢惠顾","iPhone 6s","mac book","魅族手机","小米手机"};
    private Dialog dialog;
    private View dialogMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck_draw);
        luckPanLayout = (LuckPanLayout) findViewById(R.id.luckpan_layout);
        luckPanLayout.startLuckLight();
        rotatePan = (RotatePan) findViewById(R.id.rotatePan);
        rotatePan.setAnimationEndListener(this);
        goBtn = (ImageView)findViewById(R.id.go);

        luckPanLayout.post(new Runnable() {
            @Override
            public void run() {
                int height =  getWindow().getDecorView().getHeight();
                int width = getWindow().getDecorView().getWidth();

                int backHeight = 0;

                int MinValue = Math.min(width,height);
                MinValue -= SizeUtils.dip2px(LuckDrawActivity.this,10)*2;
                backHeight = MinValue/2;

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) luckPanLayout.getLayoutParams();
                lp.width = MinValue;
                lp.height = MinValue;

                luckPanLayout.setLayoutParams(lp);

                MinValue -= SizeUtils.dip2px(LuckDrawActivity.this,28)*2;
                lp = (RelativeLayout.LayoutParams) rotatePan.getLayoutParams();
                lp.height = MinValue;
                lp.width = MinValue;

                rotatePan.setLayoutParams(lp);

                lp = (RelativeLayout.LayoutParams) goBtn.getLayoutParams();
                lp.topMargin += backHeight;
                lp.topMargin -= (goBtn.getHeight()/2);
                goBtn.setLayoutParams(lp);

                getWindow().getDecorView().requestLayout();
            }
        });
    }

    public void rotation(View view){
        rotatePan.startRotate(-1);
        luckPanLayout.setDelayTime(100);
        goBtn.setEnabled(false);
    }

    @Override
    public void endAnimation(int position) {
        goBtn.setEnabled(true);
        luckPanLayout.setDelayTime(300);
        dialog = new Dialog(this);

        if (isFinishing()) {
            return;
        }
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        dialogMessage = LayoutInflater.from(this).inflate(R.layout.dialog_alter, null);
        TextView operateMessage = (TextView) dialogMessage.findViewById(R.id.operate_message);
        if(strs[position].equals("谢谢惠顾")){
            operateMessage.setText(strs[position]);
        }else{
            operateMessage.setText("恭喜您获得" + strs[position]);
        }
        builder1.setView(dialogMessage);
        dialog = builder1.create();
        dialog.show();
        Timer timer = new Timer();
        timer.schedule(new Wait(), 2000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    class Wait extends TimerTask {
        @Override
        public void run() {
            dialog.dismiss();
            Intent intent = new Intent();
            LuckDrawActivity.this.setResult(RESULT_OK, intent);
            LuckDrawActivity.this.finish();
            LuckDrawActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}
