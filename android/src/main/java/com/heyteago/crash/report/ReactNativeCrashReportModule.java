package com.heyteago.crash.report;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class ReactNativeCrashReportModule extends ReactContextBaseJavaModule implements Thread.UncaughtExceptionHandler {

    private final ReactApplicationContext reactContext;

    public ReactNativeCrashReportModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ReactNativeCrashReport";
    }

    @ReactMethod
    public void init(String qiniuUrl, String auth) {
        Logger.init(this.reactContext, qiniuUrl, auth);
        Thread.setDefaultUncaughtExceptionHandler(this);
        Logger.uploadLog();
    }

    @ReactMethod
    public void reportLog(String log) {
        Logger.saveLog(log);
    }

    @ReactMethod
    public void testCrash() {
        throw new RuntimeException("this is test crash");
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        String stackTraceString = Log.getStackTraceString(throwable);
        Logger.saveLog(stackTraceString);
    }
}
