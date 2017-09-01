package com.example.tmha.testopengl;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private GLSurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create GLSurfaceView instance and set it as the setContentView for this Activity
        mSurfaceView = new MyGLSurfaceView(this);
        setContentView(mSurfaceView);

    }
}
