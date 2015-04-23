package com.gmail.takashi316.tminchart.face;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FaceRectangleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FaceRectangleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FaceRectangleFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FaceDetectionCamera faceDetectionCamera;

    private OnFragmentInteractionListener mListener;

    private SurfaceView surfaceViewCamera;
    private SurfaceView surfaceViewFaceRectangles;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FaceRectangleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FaceRectangleFragment newInstance(String param1, String param2) {
        FaceRectangleFragment fragment = new FaceRectangleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FaceRectangleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }//onCreate


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_face_rectangle, container, false);


        this.surfaceViewCamera = (SurfaceView) view.findViewById(R.id.surfaceViewCamera);

        this.surfaceViewFaceRectangles = (SurfaceView) view.findViewById(R.id.surfaceViewFaces);
        this.surfaceViewFaceRectangles.setZOrderOnTop(true);
        this.surfaceViewFaceRectangles.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        return view;
    }//onCreateView

    @Override
    public void onStart() {
        super.onStart();
        this.surfaceViewCamera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    faceDetectionCamera.getCamera().setPreviewDisplay(surfaceViewCamera.getHolder());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }//surfaceCreated

            @Override
            public void surfaceChanged(final SurfaceHolder holder, int format, int width, int height) {
                faceDetectionCamera.getCamera().startPreview();
            }//surfaceChanged

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                faceDetectionCamera.getCamera().stopPreview();
                //camera.stopFaceDetection();
                //camera.release();
            }//surfaceDestroyed
        });

        faceDetectionCamera.addCallback(new FaceDetectionCamera.Callback() {

            @Override
            public void callback(Camera.Face[] faces) {
                if (faces == null) return;
                if (faces.length != 1) return;
                //このあたりは ＠ITの記事からのパクリ。ありがとうございました。
                //http://www.atmarkit.co.jp/ait/articles/1201/18/news134_2.html
                Canvas canvas = surfaceViewFaceRectangles.getHolder().lockCanvas();
                if (canvas == null) {
                    Log.d(this.getClass().getSimpleName(), "Callback is skipped because of null canvas.");
                    return;
                }//if
                int saveCount = canvas.save();
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                Matrix matrix = new Matrix();
                matrix.setScale(-1, 1); //フロントカメラなので左右反転
                matrix.postScale(surfaceViewFaceRectangles.getWidth() / 2000f, surfaceViewFaceRectangles.getHeight() / 2000f);
                matrix.postTranslate(surfaceViewFaceRectangles.getWidth() / 2f, surfaceViewFaceRectangles.getHeight() / 2f);
                // 現在のマトリックスを保存
                // 顔認識のマトリックスをキャンバスに反映
                canvas.concat(matrix);
                // 矩形を描画
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(faces[0].rect, paint);
                // 保存したマトリックスを戻す
                canvas.restoreToCount(saveCount);
                surfaceViewFaceRectangles.getHolder().unlockCanvasAndPost(canvas);
            }//callback
        });//Callback
    }//onStart

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void setFaceDetectionCamera(FaceDetectionCamera face_detection_camera) {
        this.faceDetectionCamera = face_detection_camera;
    }//setFaceDetectionCamera
}
