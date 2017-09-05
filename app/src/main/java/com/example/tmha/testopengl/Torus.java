package com.example.tmha.testopengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by tmha on 9/5/2017.
 */

public class Torus {

    private List<String> verticesList;
    private List<String> faceList;
    private FloatBuffer verticesBuffer;
    private ShortBuffer faceBuffer;
    private int program;

    public Torus(Context context) {
        verticesList = new ArrayList<>();
        faceList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(context.getAssets().open("torus.obj"));
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if (line.startsWith("v ")){
                    verticesList.add(line);
                }else if(line.startsWith("f ")){
                    faceList.add(line);
                }
            }

            scanner.close();

            //Create buffer for vertices
            ByteBuffer bufferVertices = ByteBuffer.allocateDirect(verticesList.size()* 3 * 4);// 3:(x,y,z), 4:(x : 4 byte)
            bufferVertices.order(ByteOrder.nativeOrder());
            verticesBuffer = bufferVertices.asFloatBuffer();

            for (String vertex : verticesList){
                String coords[] = vertex.split(" "); // split by space
                float x = Float.parseFloat(coords[1]);
                float y = Float.parseFloat(coords[2]);
                float z = Float.parseFloat(coords[3]);
                verticesBuffer.put(x);
                verticesBuffer.put(y);
                verticesBuffer.put(z);
            }

            verticesBuffer.position(0);

            ByteBuffer bufferFace = ByteBuffer.allocateDirect(faceList.size()* 3 * 2);// 3:(x,y,z), 2:(x : 2 byte)
            bufferFace.order(ByteOrder.nativeOrder());
            faceBuffer = bufferFace.asShortBuffer();

            for (String face : faceList){
                String vertexIndices[] = face.split(" "); // split by space
                Short vertex1 = Short.parseShort(vertexIndices[1]);
                Short vertex2 = Short.parseShort(vertexIndices[2]);
                Short vertex3 = Short.parseShort(vertexIndices[3]);
                faceBuffer.put((short) (vertex1 - 1));
                faceBuffer.put((short) (vertex2 - 1));
                faceBuffer.put((short) (vertex3 - 1));
            }

            faceBuffer.position(0);


            InputStream vertexShaderStream = context.getResources().openRawResource(R.raw.vertex_shader);
            String vertexShaderCode = IOUtils.toString(vertexShaderStream, Charset.defaultCharset());
            vertexShaderStream.close();


            InputStream fragmentShaderStream = context.getResources().openRawResource(R.raw.fragment_shader);
            String fragmentShaderCode = IOUtils.toString(fragmentShaderStream, Charset.defaultCharset());
            fragmentShaderStream.close();

            int vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
            GLES20.glShaderSource(vertexShader, vertexShaderCode);
            GLES20.glCompileShader(vertexShader);

            int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
            GLES20.glShaderSource(fragmentShader, fragmentShaderCode);
            GLES20.glCompileShader(fragmentShader);


            program = GLES20.glCreateProgram();
            GLES20.glAttachShader(program, vertexShader);
            GLES20.glAttachShader(program, fragmentShader);
            GLES20.glLinkProgram(program);
            GLES20.glUseProgram(program);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(){
        int position = GLES20.glGetAttribLocation(program, "position");
        GLES20.glEnableVertexAttribArray(position);
        GLES20.glVertexAttribPointer(position, 3, GLES20.GL_FLOAT, false, 3 * 4, verticesBuffer);

        float[] projectionMatrix = new float[16];
        float[] viewMatrix = new float[16];
        float[] productMatrix = new float[16];

        Matrix.frustumM(projectionMatrix, 0, -1, 1, -1, 1, 2, 9);

        Matrix.setLookAtM(viewMatrix, 0, 0, 3, -4, 0, 0, 0, 0, 1, 0f);

        Matrix.multiplyMM(productMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        int matrix = GLES20.glGetUniformLocation(program, "matrix");
        GLES20.glUniformMatrix4fv(matrix, 1, false, productMatrix, 0);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,
                faceList.size()*3, GLES20.GL_UNSIGNED_SHORT, faceBuffer);
        GLES20.glDisableVertexAttribArray(position);


    }
}
