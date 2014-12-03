package com.gmail.takashi316.tminchart.storage;

import android.os.Environment;

import java.io.File;

/**
 * Created by sasaki on 2014/12/03.
 */
public class StorageRoots {
    public final File externalStorageRoot;
    public final File dataRoot;
    public final File sdRoot= new File("/storage/sdcard1/Android/data");

    public StorageRoots(){
        this.externalStorageRoot = Environment.getExternalStorageDirectory();
        this.dataRoot = Environment.getDataDirectory();
    }
}
