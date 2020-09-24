package com.hanabi.apircvdemo;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class FileManager {


    private String download(String link, String path) throws IOException {

        ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(link).openStream());

        path = String.format("%s%s.html", path, System.currentTimeMillis());

        File file = new File(path);
        file.getParentFile().mkdirs();
        file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);

        long maxSize;

        if (Build.VERSION.SDK_INT > 23) {
            maxSize = Long.MAX_VALUE;
        } else {
            maxSize = 8 * 1024 * 1024 * 1024;
        }

        FileChannel writer = fileOutputStream.getChannel();
        writer.transferFrom(readableByteChannel, 0, maxSize);

        return path;

    }

    public void download(String link, String path, FileDownloadCallBack callBack) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String pathSave = download(link, path);
                    callBack.onSuccess(pathSave);
                } catch (IOException e) {
                    e.printStackTrace();
                    callBack.onFailure(e);
                }
            }
        });
        thread.start();
    }

    public interface FileDownloadCallBack {
        void onSuccess(String path);

        void onFailure(Exception e);
    }
}
