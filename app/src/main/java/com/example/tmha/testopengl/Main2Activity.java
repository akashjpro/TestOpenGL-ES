package com.example.tmha.testopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Main2Activity extends AppCompatActivity {

    GLSurfaceView mGlSurfaceView;
    Torus mTorus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mGlSurfaceView = (GLSurfaceView) findViewById(R.id.torus_surface);
        mGlSurfaceView.setEGLContextClientVersion(2);

        mGlSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
                GLES20.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);
                mGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
                mTorus  = new Torus(getApplicationContext());
            }

            @Override
            public void onSurfaceChanged(GL10 gl10, int i, int i1) {
                GLES20.glViewport(0, 0, i, i1);
            }

            @Override
            public void onDrawFrame(GL10 gl10) {
                mTorus.draw();
            }
        });
    }
}
