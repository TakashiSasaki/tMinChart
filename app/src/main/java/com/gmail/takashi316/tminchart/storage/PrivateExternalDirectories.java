package com.gmail.takashi316.tminchart.storage;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by sasaki on 2014/12/03.
 */
public class PrivateExternalDirectories {
    public final File parent;
    public final File documents;
    public final File downloads;
    public final File alarms;
    public final File dcim;
    public final File movies;
    public final File music;
    public final File notifications;
    public final File puctures;
    public final File podcasts;
    public final File ringtones;

    public PrivateExternalDirectories(Context context) throws IOException {
        // getExternalFilesDir is available on Android API Level 8 and above.
        this.parent = context.getExternalFilesDir(null);
        if(this.parent == null) {
            throw new IOException("Can't get private external directory.");
        }
        this.documents = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        this.alarms = context.getExternalFilesDir(Environment.DIRECTORY_ALARMS);
        this.dcim = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        this.downloads = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        this.movies = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        this.music = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        this.notifications = context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS);
        this.puctures = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        this.podcasts = context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS);
        this.ringtones = context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES);
    }
}
