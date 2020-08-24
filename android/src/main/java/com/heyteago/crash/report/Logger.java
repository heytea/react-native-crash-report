package com.heyteago.crash.report;

import android.content.Context;
import android.util.Log;

import com.heyteago.crash.report.data.CrashDB;
import com.heyteago.crash.report.data.dao.CrashDao;
import com.heyteago.crash.report.data.entity.CrashEntity;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Logger {
    private static String qiniuUrl;
    private static String auth;
    private static CrashDao crashDao;
    private static ExecutorService service = Executors.newSingleThreadExecutor();
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static void init(Context context, String qiniuUrl, String auth) {
        Logger.qiniuUrl = qiniuUrl;
        Logger.auth = auth;
        crashDao = CrashDB.getDb(context).crashDao();
    }

    public static void saveLog(String log) {
        if (crashDao == null) {
            Log.w("CrashReportLogger", "尚未进行初始化，请在RN中调用init()函数初始化");
            return;
        }
        CrashEntity crashEntity = new CrashEntity();
        crashEntity.setLog(log);
        crashEntity.setUploaded(false);
        crashDao.insertCrashEntities(crashEntity);
    }

    public static void uploadLog() {
        if (crashDao == null) {
            Log.w("CrashReportLogger", "尚未进行初始化，请在RN中调用init()函数初始化");
            return;
        }
        service.execute(new Runnable() {
            @Override
            public void run() {
                CrashEntity[] crashEntities = crashDao.findByIsUploaded(false);
                for (CrashEntity crashEntity : crashEntities) {
                    RequestBody requestBody = RequestBody.create(null, crashEntity.getLog());
                    Request request = new Request.Builder()
                            .url(qiniuUrl)
                            .addHeader("Authorization", auth)
                            .addHeader("Content-Type", "text/plain")
                            .post(requestBody)
                            .build();
                    try {
                        okHttpClient.newCall(request).execute();
                        crashEntity.setUploaded(true);
                        crashDao.updateCrashEntities(crashEntity);
                    } catch (IOException e) {
                        e.printStackTrace();
                        crashEntity.setUploaded(false);
                        crashDao.updateCrashEntities(crashEntity);
                    }
                }
            }
        });
    }
}
