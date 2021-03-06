package com.example.tmha.testopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class BlenderCubeActivity extends AppCompatActivity {

    GLSurfaceView mGlSurfaceView;
    Torus mTorus;
    Triangle mTriangle;
    Square mSquare;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mGlSurfaceView = (GLSurfaceView)findViewById(R.id.torus_surface);

        mGlSurfaceView.setEGLContextClientVersion(2);

        mGlSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
                mGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
              //  mTorus = new Torus(getApplicationContext());
               // mTriangle = new Triangle();
                mSquare = new Square();
            }

            @Override
            public void onSurfaceChanged(GL10 gl10, int width, int height) {
                GLES20.glViewport(0,0, width, height);
                float ratio = (float) width / height;

                // this projection matrix is applied to object coordinates
                // in the onDrawFrame() method
                Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
            }

            @Override
            public void onDrawFrame(GL10 gl10) {
                float[] scratch = new float[16];
                // Set the camera position (View matrix)
                Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 1.0f);

                // Calculate the projection and view transformation
                Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);


                //mTorus.draw();
//                mTriangle.draw(mMVPMatrix);

//                Matrix.setRotateM(mRotationMatrix, 0, 90.0f, 0, 0, -1.0f);
//                Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

               // mTriangle.draw(mMVPMatrix);
                mSquare.draw(mMVPMatrix);

//                Matrix.setIdentityM(mRotationMatrix, 0);
//                Matrix.translateM(mRotationMatrix, 0, 0.0f, 1.0f, 0.0f);
//                Matrix.rotateM(mRotationMatrix, 0, 180.0f, 0, 0, 1f);
//                 Matrix.translateM(mRotationMatrix, 0, 0.0f, 1.0f, 0.0f);
//                Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
//                mTriangle.draw(scratch);


            }
        });
    }
}
