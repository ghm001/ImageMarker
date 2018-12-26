package com.team.imagemarker.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * 解决Android7.0手机拍照、打开文件程序崩溃问题
 * Created by Lmy on 2017/8/26.
 * email 1434117404@qq.com
 */

public class FileUtils {
    private static String APP_DIR_NAME = "imagemarker";
    private static String FILE_DIR_NAME = "files";
    private static String mRootDir;
    private static String mAppRootDir;
    private static String mFileDir;

    public static void init() {
        mRootDir = getRootPath();
        if (mRootDir != null && !"".equals(mRootDir)) {
            mAppRootDir = mRootDir + "/" + APP_DIR_NAME;
            mFileDir = mAppRootDir + "/" + FILE_DIR_NAME;
            File appDir = new File(mAppRootDir);
            if (!appDir.exists()) {
                appDir.mkdirs();
            }
            File fileDir = new File(mAppRootDir + "/" + FILE_DIR_NAME);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        } else {
            mRootDir = "";
            mAppRootDir = "";
            mFileDir = "";
        }
    }

    public static String getFileDir() {
        return mFileDir;
    }

    public static String getRootPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath(); // filePath:  /sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data"; // filePath:  /data/data/
        }
    }

    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.team.imagemarker.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}
