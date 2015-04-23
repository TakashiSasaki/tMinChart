package com.gmail.takashi316.tminchart.face;

import android.hardware.Camera;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by sasaki on 2015/03/01.
 */
public class FaceDetectionCamera extends FrontCamera {

    DetectionResults detectionResults = new DetectionResults(15);

    public interface Callback {
        abstract public void callback(Camera.Face[] faces);
    }//Callback

    //private SurfaceView surfaceView;
    //private Callback callback;
    private ArrayList<Callback> callbacks;

    public void addCallback(Callback callback) {
        if (this.callbacks == null) {
            this.callbacks = new ArrayList<Callback>();
        }//if
        Log.d(this.getClass().getSimpleName(), "callback added");
        callbacks.add(callback);
    }//setCallback

    FaceDetectionCamera theInstance;

    public FaceDetectionCamera getTheInstance() {
        if (theInstance == null) {
            theInstance = new FaceDetectionCamera();
        }
        return theInstance;
    }

    private FaceDetectionCamera() {
        super();
        final int max_faces = this.getCamera().getParameters().getMaxNumDetectedFaces();
        Log.d(null, "max_faces = " + max_faces);

        this.getCamera().setFaceDetectionListener(new Camera.FaceDetectionListener() {
            // TechBoosterの記事をほぼ丸写し。ありがたく使わせていただきました。
            // http://techbooster.jpn.org/andriod/multimedia/10375/
            @Override
            public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                if (faces == null) return;
                if (faces.length == 0) return;
                final Camera.Face face = faces[0];

                detectionResults.add(faces[0].rect);

                // サポートされていなければ-1が常に返ってくる
                Log.d(null, "face id: " + face.id);

                // 顔検出の信頼度 1から100までの値が入っており、100が顔として信頼度が一番高い
                Log.d(null, "face score: " + face.score);

                // 検出された顔の範囲
                Log.d(null, "face rect: " + face.rect.left + "," + face.rect.top + " - "
                        + face.rect.right + "," + face.rect.bottom);

                // 以下はサポートされていなければnullが入ってくる
                if (face.mouth != null) {
                    Log.d(null, "face mouth: " + face.mouth.x + "," + face.mouth.y);
                    Log.d(null, "face leftEye: " + face.leftEye.x + "," + face.leftEye.y);
                    Log.d(null, "face rightEye: " + face.rightEye.x + "," + face.rightEye.y);
                }//if

                for (Callback callback : callbacks) {
                    callback.callback(faces);
                }//for
            }//onFaceDetection
        });
    }//FaceDetectionCamera

    public void startFaceDetection() {
        this.getCamera().startFaceDetection();
    }//startFaceDetection

    public void stopFaceDetection() {
        this.getCamera().stopFaceDetection();
    }//stopFaceDetection

    public DetectionResults getDetectionResults() {
        return this.detectionResults;
    }//getDetectionResults
}//FaceDetectionCamera

