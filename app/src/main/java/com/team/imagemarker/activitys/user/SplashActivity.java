package com.team.imagemarker.activitys.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.team.imagemarker.R;
import com.team.imagemarker.activitys.home.HomeActivity;
import com.team.imagemarker.activitys.home.MainActivity;
import com.team.imagemarker.db.UserDbHelper;

/**
 * 启动页
 * Created by Lmy on 2017/8/27.
 * email 1434117404@qq.com
 */

public class SplashActivity extends Activity {
    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;

    private ImageView splashImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImg = (ImageView) findViewById(R.id.splash_img);

        UserDbHelper.setInstance(this);

        animateImage();
    }

    private void animateImage() {

        //表示从1f --> 1.13f 的变化缩放过程
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(splashImg, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(splashImg, "scaleY", 1f, SCALE_END);

        //表示多个动画的协同工作
        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        //对动画的监听,动画结束后立马跳转到主页面上
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = null;
                if(UserDbHelper.getInstance().getLoginState()){
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }else{
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                }
                startActivity(intent);
                SplashActivity.this.finish();
                SplashActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
