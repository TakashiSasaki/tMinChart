package com.gmail.takashi316.tminchart.storage;

import android.content.Context;

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
    private String packageName;
    private File internal;
    private File privateExternal;
    private File publicExternal;
    private File sd;

    private StorageRoots storageRoots = new StorageRoots();
    private PublicExternalDirectories publicExternalDirectories = new PublicExternalDirectories();
    private PrivateExternalDirectories privateExternalDirectories;

    public DatabaseFile(Context context, String database_file_name) {
        final Date current_time = new Date();
        this.context = context;
        this.packageName = context.getPackageName();
        this.privateExternalDirectories = new PrivateExternalDirectories(context);
        this.internal = context.getDatabasePath(database_file_name);

        this.privateExternal = new File(privateExternalDirectories.documents, database_file_name + "."+ current_time.getTime());
        this.publicExternal = new File(publicExternalDirectories.documents, database_file_name + "."+current_time.getTime());

        final File sd_package_dir = new File(storageRoots.sdRoot, packageName);
        if(!sd_package_dir.isDirectory()) {
            sd_package_dir.mkdirs();
        }//if
        this.sd = new File(sd_package_dir, database_file_name + "."+ current_time.getTime());
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

    private byte[] getInternalMd() throws FileNotFoundException{
        return getMd(internal);
    }

    private byte[] getPrivateExternalMd() throws FileNotFoundException{
        return getMd(privateExternal);
    }

    private byte[] getPublicExternalMd() throws FileNotFoundException {
        return getMd(publicExternal);
    }

    private byte[] getSdMd() throws FileNotFoundException{
        return getMd(this.sd);
    }

    private void copyTo(File dst_file) throws FileNotFoundException, IOException{
        FileChannel src = new FileInputStream(internal).getChannel();
        dst_file.createNewFile();
        FileChannel dst = new FileOutputStream(dst_file).getChannel();
        try {
            src.transferTo(0, src.size(), dst);
        } finally {
            src.close();
            dst.close();
        }

        byte[] src_md = getMd(internal);
        byte[] dst_md = getMd(dst_file);
        if(!src_md.equals(dst_md)) throw new IOException("Message Digest is not identical.");
    }

    public void copyToPrivateExternal() throws FileNotFoundException, IOException{
        copyTo(privateExternal);
    }

    public void copyToPublicExternal() throws FileNotFoundException, IOException{
        copyTo(publicExternal);
    }

    public void copyToSd() throws FileNotFoundException, IOException{
        copyTo(sd);
    }


}//DatabaseFile
