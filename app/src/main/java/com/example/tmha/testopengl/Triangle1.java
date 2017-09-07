package com.example.tmha.testopengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by tmha on 9/7/2017.
 */

public class Triangle1 {
    // Source code of vertex shader
    private final String vsCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    // Source code of fragment shader
    private final String fsCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private int program;
    private int vertexShader;
    private int fragmentShader;
    private FloatBuffer vertexBuffer;
    private int vertexCount = 3;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {   // in counterclockwise order:
            0.0f,  1.0f, 0.0f, // top vertex
            1.0f,  0.0f, 0.0f, // bottom left
            -1.0f,  0.1f, 0.0f  // bottom right
    };

    // Set color of displaying object
    // with red, green, blue and alpha (opacity) values
    float color[] = { 1.0f, 0.0f, 0.0f, 1.0f };

    // Create a Triangle object
    Triangle1(){
        // create empty OpenGL ES Program, load, attach, and link shaders
        program = GLES20.glCreateProgram();
        vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vsCode);
        fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fsCode);
        GLES20.glAttachShader ( program, vertexShader );// add the vertex shader to program
        GLES20.glAttachShader(program, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(program);                  // creates OpenGL ES program executables
        GLES20.glUseProgram( program);                  // use shader program

        // initialize vertex byte buffer for shape coordinates with parameters
        // (number of coordinate values * 4 bytes per float)
        // use the device hardware's native byte order
        ByteBuffer bb = ByteBuffer.allocateDirect( triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(triangleCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
    } //Triangle Constructor

    public static int loadShader (int type, String shaderCode ) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // pass source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void draw() {
        // Add program to OpenGL ES environment
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glUseProgram(program);

        // get handle to vertex shader's attribute variable vPosition
        int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 0, vertexBuffer);

        // get handle to fragment shader's uniform variable vColor
        int colorHandle = GLES20.glGetUniformLocation(program, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
