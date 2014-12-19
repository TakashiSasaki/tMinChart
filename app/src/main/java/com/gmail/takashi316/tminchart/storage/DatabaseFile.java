package com.gmail.takashi316.tminchart.storage;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by sasaki on 2014/12/03.
 */
public class DatabaseFile {
    Context context;
    private File databaseFile;
    private File databaseFileOnExternalStorage;
    private File databaseFileOnSdCard;

    final private StorageRoots storageRoots = new StorageRoots();

    public DatabaseFile(Context context, String database_file_name) throws FileNotFoundException {
        final Date current_time = new Date();
        this.context = context;
        final String packageName = context.getPackageName();
        final PublicExternalDirectories publicExternalDirectories = new PublicExternalDirectories();
        this.databaseFile = context.getDatabasePath(database_file_name);
        Log.d(this.getClass().getSimpleName(), "databaseFile = " + this.databaseFile.getAbsolutePath());
        if(!this.databaseFile.exists()){
            throw new FileNotFoundException(this.databaseFile.getAbsolutePath());
        }

        if(publicExternalDirectories.documents == null){
            if(publicExternalDirectories.downloads == null){
                this.databaseFileOnExternalStorage = null;
                Log.d(this.getClass().getSimpleName(), "no external directory");
            } else {
                this.databaseFileOnExternalStorage = new File(publicExternalDirectories.downloads, database_file_name + "."+ current_time.getTime());
                Log.d(this.getClass().getSimpleName(), "databaseFileOnExternalStorage = " + this.databaseFileOnExternalStorage);
            }
        } else {
            this.databaseFileOnExternalStorage = new File(publicExternalDirectories.documents, database_file_name + "."+ current_time.getTime());
            Log.d(this.getClass().getSimpleName(), "databaseFileOnExternalStorage = " + this.databaseFileOnExternalStorage);
        }//if

        final File sd_package_dir = new File(storageRoots.sdRoot, packageName);
        if(!sd_package_dir.isDirectory()) {
            Log.e(this.getClass().getSimpleName(), "Mkdir " + sd_package_dir);
            sd_package_dir.mkdirs();
        }//if
        if(!sd_package_dir.exists()){
            Log.e(this.getClass().getSimpleName(), "Can't find " + sd_package_dir);
        }
        if(!sd_package_dir.isDirectory()) {
            this.databaseFileOnSdCard = null;
            Log.d(this.getClass().getSimpleName(), "databaseFileOnSdCard = null");
        } else {
            this.databaseFileOnSdCard = new File(sd_package_dir, database_file_name + "." + current_time.getTime());
            Log.d(this.getClass().getSimpleName(), "databaseFileOnSdCard = " + this.databaseFileOnSdCard);
        }

        try {
            PrivateExternalDirectories privateExternalDirectories = new PrivateExternalDirectories(context);
        } catch (Exception e){
            Log.d(getClass().getSimpleName(), e.getMessage());
        }

    }//DatabaseFile

    static private byte[] getMd(File file) throws FileNotFoundException{
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int count = 0;
            while((count = fis.read(buffer))>0){
                md.update(buffer, 0, count);
            }//while
            return md.digest();
        } catch (NoSuchAlgorithmException e){
            return null;
        } catch (IOException e){
            return null;
        }//try
    }//getMd5

    private byte[] getMd() throws FileNotFoundException{
        return getMd(this.databaseFile);
    }

    private byte[] getMdOnExternalStorage() throws FileNotFoundException{
        return getMd(this.databaseFileOnExternalStorage);
    }

    private byte[] getMdOnSdCard() throws FileNotFoundException {
        return getMd(this.databaseFileOnSdCard);
    }

    private void copyTo(File dst_file) throws FileNotFoundException, IOException{
        Log.d(this.getClass().getSimpleName(), "copy from " + this.databaseFile);
        FileChannel src = new FileInputStream(this.databaseFile).getChannel();
        Log.d(this.getClass().getSimpleName(), "trying to copy to " + dst_file);
        Log.d(getClass().getSimpleName(), "parent directory of destination exists? " + dst_file.getParentFile().isDirectory());
        dst_file.createNewFile();
        Log.d(this.getClass().getSimpleName(), "created " + dst_file);
        FileChannel dst = new FileOutputStream(dst_file).getChannel();
        try {
            src.transferTo(0, src.size(), dst);
        } finally {
            src.close();
            dst.close();
        }

        byte[] src_md = getMd(this.databaseFile);
        byte[] dst_md = getMd(dst_file);
        if(!src_md.equals(dst_md)) throw new IOException("Message Digest is not identical.");
    }

    public void copyToExternalStorage() throws FileNotFoundException, IOException{
        copyTo(this.databaseFileOnExternalStorage);
    }

    public void copyToSdCard() throws FileNotFoundException, IOException{
        copyTo(this.databaseFileOnSdCard);
    }


}//DatabaseFile
