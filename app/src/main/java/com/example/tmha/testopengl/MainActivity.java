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
       // mSurfaceView = new MyGLSurfaceView(this);
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
}
