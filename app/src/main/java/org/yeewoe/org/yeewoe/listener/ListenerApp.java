package org.yeewoe.org.yeewoe.listener;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;

import com.afollestad.appthemeengine.ATE;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import org.yeewoe.listener.BuildConfig;
import org.yeewoe.listener.CrashHandler;
import org.yeewoe.listener.R;
import org.yeewoe.listener.RxBus;
import org.yeewoe.listener.dataloader.SongLoader;
import org.yeewoe.listener.event.MediaUpdateEvent;
import org.yeewoe.listener.injector.component.ApplicationComponent;
import org.yeewoe.listener.injector.component.DaggerApplicationComponent;
import org.yeewoe.listener.injector.module.ApplicationModule;
import org.yeewoe.listener.injector.module.NetworkModule;
import org.yeewoe.listener.mvp.model.Song;
import org.yeewoe.listener.permission.PermissionManager;
import org.yeewoe.listener.util.ListenerUtil;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yeewoe on 2016/10/4.
 */

public class ListenerApp extends Application {

    private static Context sContext;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

//        initLeakCanary();
//        setCrashHandler();
//        initStetho();
//        setStrictMode();
        setupInjector();
        initImageLoader();
        PermissionManager.init(this);
        updataMedia();
        setupATE();
    }

//    private void initLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
//    }

    private void setStrictMode() {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }

    private void setCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance(this);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }

//    private void initStetho() {
//        Stetho.initializeWithDefaults(this);
//    }

    private void setupInjector() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(this)).build();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration localImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(localImageLoaderConfiguration);
    }

    public static Context getContext() {
        return sContext;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    //应用启动时通知系统刷新媒体库,
    private void updataMedia() {
        //版本号的判断  4.4为分水岭，发送广播更新媒体库
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (ListenerUtil.isMarshmallow() && !PermissionManager.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                return;
            }
            SongLoader.getAllSongs(this)
                    .map(new Func1<List<Song>, String[]>() {
                        @Override
                        public String[] call(List<Song> songList) {
                            List<String> folderPath = new ArrayList<String>();
                            int i = 0;
                            for (Song song : songList) {
                                folderPath.add(i, song.path);
                                i++;
                            }
                            return folderPath.toArray(new String[0]);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<String[]>() {
                        @Override
                        public void call(String[] paths) {
                            MediaScannerConnection.scanFile(getContext(), paths, null,
                                    new MediaScannerConnection.OnScanCompletedListener() {
                                        @Override
                                        public void onScanCompleted(String path, Uri uri) {
                                            if (uri == null) {
                                                RxBus.getInstance().post(new MediaUpdateEvent());
                                            }
                                        }
                                    });
                        }
                    });
        } else {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
                    + Environment.getExternalStorageDirectory())));
        }

    }

    private void setupATE() {
        if (!ATE.config(this, "light_theme").isConfigured()) {
            ATE.config(this, "light_theme")
                    .activityTheme(R.style.AppThemeLight)
                    .coloredNavigationBar(false)
                    .usingMaterialDialogs(true)
                    .commit();
        }
        if (!ATE.config(this, "dark_theme").isConfigured()) {
            ATE.config(this, "dark_theme")
                    .activityTheme(R.style.AppThemeDark)
                    .coloredNavigationBar(false)
                    .usingMaterialDialogs(true)
                    .commit();
        }
    }
}
