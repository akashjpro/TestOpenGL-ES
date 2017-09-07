package com.example.tmha.testopengl;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private GLSurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create GLSurfaceView instance and set it as the setContentView for this Activity
//        mSurfaceView = new MyGLSurfaceView(this);
//        setContentView(mSurfaceView);
        setContentView(R.layout.activity_main);

    }

    public void rotateClick(View view) {
        startActivity(new Intent(this, AnimationGLActivity.class));
    }


    public void cubeClick(View view) {
        startActivity(new Intent(this, Graphic3DActivity.class));
    }

    public void blenderClick(View view) {
        startActivity(new Intent(this, BlenderCubeActivity.class));
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        // The following call pauses the rendering thread.
//        // If your OpenGL application is memory intensive,
//        // you should consider de-allocating objects that
//        // consume significant memory here.
//        mSurfaceView.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // The following call resumes a paused rendering thread.
//        // If you de-allocated graphic objects for onPause()
//        // this is a good place to re-allocate them.
//        mSurfaceView.onResume();
//    }
}
