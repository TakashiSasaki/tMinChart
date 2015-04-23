package com.gmail.takashi316.tminchart.face;

import android.hardware.Camera;

/**
 * Created by sasaki on 2015/02/26.
 */
public class FrontCamera {
    private int frontCameraId = -1;
    static boolean created = false;
    static Camera camera;

    protected FrontCamera()  {
        //if(created) throw new RuntimeException("FrontCamera already created.");
        //created = true;
        final int n_camera = Camera.getNumberOfCameras();
        Camera.CameraInfo camera_info = new Camera.CameraInfo();
        for (int i = 0; i < n_camera; ++i) {
            Camera.getCameraInfo(i, camera_info);
            if (camera_info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                this.frontCameraId = i;
            }//if
        }//for
    }//FrontCamera

    final public Camera getCamera(){
        if(this.camera == null) {
            this.camera = Camera.open(this.frontCameraId);
        }
        return camera;
    }//getCamera

    public int getMaxNumDetectedFaces() {
        return this.getCamera().getParameters().getMaxNumDetectedFaces();
    }//getMaxNumDetectedFaces

    public void startPreview() {
        this.getCamera().startPreview();
    }//startPreview

    public void release() {
        this.getCamera().release();
        this.camera = null;
    }//release
}//FrontCamera
