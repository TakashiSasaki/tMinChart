package com.gmail.takashi316.tminchart.storage;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by sasaki on 2014/12/03.
 */
public class PublicExternalDirectories {
    public final File documents;
    public final File notifications;
    public final File alarms;
    public final File dcim;
    public final File downloads;
    public final File movies;
    public final File music;
    public final File pictures;
    public final File ringtones;
    public final File podcasts;

    PublicExternalDirectories(){
        // getExternalStoragePublicDirectory is supported on Android API level 8 and above.
        File documents;
        try {
            documents = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        } catch (NoSuchFieldError e ){
            Log.e(getClass().getSimpleName(), e.getMessage());
            documents = null;
        }
        this.documents = documents;
        if(!this.documents.exists()){
            this.documents.mkdirs();
        }
        this.alarms = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        this.dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        this.downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        this.movies = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        this.music = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        this.notifications = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
        this.pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        this.podcasts = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);
        this.ringtones = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
        //this.externalFilesDirsDocuments = context.getExternalFilesDirs(Environment.DIRECTORY_DOCUMENTS);
    }
}
