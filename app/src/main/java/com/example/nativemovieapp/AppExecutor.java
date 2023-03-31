package com.example.nativemovieapp;


import java.util.concurrent.ScheduledThreadPoolExecutor;


//Class này để sử lí MultiThread, tạo 1 luồng mới khi gọi API tránh block UI.
public class AppExecutor {

    private static AppExecutor _ins;


    public static AppExecutor getInstance() {
        if (_ins == null)
            _ins = new AppExecutor();
        return _ins;
    }

    private final ScheduledThreadPoolExecutor networkIo = new ScheduledThreadPoolExecutor(5);

    public ScheduledThreadPoolExecutor getNetworkIo() {
        return networkIo;
    }
}
