package com.example.tmha.testopengl;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AnimationGLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new Render());
        setContentView(view);
    }

    public class  Render implements GLSurfaceView.Renderer{

        private Square square = new Square();
        private float angle = 0;

        @Override
        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
            // Set the background color to black ( rgba ).
            gl10.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
            // Enable Smooth Shading, default not really needed.
            gl10.glShadeModel(GL10.GL_SMOOTH);
            // Depth buffer setup.
            gl10.glClearDepthf(1.0f);
            // Enables depth testing.
            gl10.glEnable(GL10.GL_DEPTH_TEST);
            // The type of depth testing to do.
            gl10.glDepthFunc(GL10.GL_LEQUAL);
            // Really nice perspective calculations.
            gl10.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            // Sets the current view port to the new size.
            gl.glViewport(0, 0, width, height);
            // Select the projection matrix
            gl.glMatrixMode(GL10.GL_PROJECTION);
            // Reset the projection matrix
            gl.glLoadIdentity();
            // Calculate the aspect ratio of the window
            GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
                    100.0f);
            // Select the modelview matrix
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            // Reset the modelview matrix
            gl.glLoadIdentity();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
//            // Clears the screen and depth buffer.
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
////            // Replace the current matrix with the identity matrix
            gl.glLoadIdentity();
////            // Translates 10 units into the screen.
            gl.glTranslatef(0, 0, -10);
////
            gl.glPushMatrix();
            gl.glRotatef(angle, 0, 0, 1);
            gl.glColor4f(0.0f, 1.0f, 1.0f, 0.5f);
            square.draw(gl);
            gl.glPopMatrix();

            // SQUARE B
            // Save the current matrix
            gl.glPushMatrix();
//            // Rotate square B before moving it, making it rotate around A.
            gl.glRotatef(-angle, 0, 0, 1);
//            // Move square B.
            gl.glTranslatef(2, 0, 0);
//            // Scale it to 50% of square A
            gl.glScalef(.5f, .5f, .5f);
            // Draw square B.
            gl.glColor4f(1.0f, 0.0f, 1.0f, 0.5f);
            square.draw(gl);

            // SQUARE C
            // Save the current matrix
            gl.glPushMatrix();
            // Make the rotation around B
            gl.glRotatef(-angle, 0, 0, 1);
            gl.glTranslatef(2, 0, 0);
            // Scale it to 50% of square B
            gl.glScalef(.5f, .5f, .5f);
            // Rotate around it's own center.
            gl.glRotatef(angle*10, 0, 0, 1);
            // Draw square C.
            gl.glColor4f(1.0f, 0.0f, 0.0f, 0.5f);
            square.draw(gl);
            // Restore to the matrix as it was before C.
           // gl.glPopMatrix();

            gl.glPopMatrix();
            // Restore to the matrix as it was before B.
            gl.glPopMatrix();
            angle++;

        }
    }

    public class Square{
        private float vertices[] = {
                -1.0f,  1.0f, 0.0f, // Top Left
                -1.0f, -1.0f, 0.0f, // Bottom Left
                1.0f, -1.0f, 0.0f, // Bottom Right
                1.0f,  1.0f, 0.0f, // Top Right
        };

        //connect 6 vertices to 2 triangle
        private short[] indices = {0, 1, 2, 0, 2, 3};

        private FloatBuffer vertexBuffer;

        private ShortBuffer indexBuffer;

        public Square() {
            ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
            vbb.order(ByteOrder.nativeOrder());
            vertexBuffer = vbb.asFloatBuffer();
            vertexBuffer.put(vertices);
            vertexBuffer.position(0);

            ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
            ibb.order(ByteOrder.nativeOrder());
            indexBuffer = ibb.asShortBuffer();
            indexBuffer.put(indices);
            indexBuffer.position(0);
        }

        public void draw(GL10 gl){
            // Counter-clockwise winding.
            gl.glFrontFace(GL10.GL_CCW);
            // Enable face culling.
            gl.glEnable(GL10.GL_CULL_FACE);
            // What faces to remove with the face culling.
            gl.glCullFace(GL10.GL_BACK);

            // Enabled the vertices buffer for writing and to be used during
            // rendering.
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            // Specifies the location and data format of an array of vertex
            // coordinates to use when rendering.
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

            gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
                    GL10.GL_UNSIGNED_SHORT, indexBuffer);

            // Disable the vertices buffer.
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
            // Disable face culling.
            gl.glDisable(GL10.GL_CULL_FACE);
        }
    }

}
