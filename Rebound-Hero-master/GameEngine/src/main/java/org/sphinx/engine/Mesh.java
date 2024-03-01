package org.sphinx.engine;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL40.*;

public class Mesh {
    protected static final Map<Integer,Mesh> MESH_MAP = new HashMap<>();
    private final int vaoId;
    private final List<Integer> vboIdList = new ArrayList<>();
    private int vertexCount;

    Mesh(float[] vertices, float[] texCoords){
        FloatBuffer vertexBuffer = null;
        FloatBuffer coordBuffer = null;
        vaoId = glGenVertexArrays();
        try {
            glBindVertexArray(vaoId);
            int vboId = glGenBuffers();
            vboIdList.add(vboId);
            glBindBuffer(GL_ARRAY_BUFFER,vboId);
            vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
            vertexBuffer.put(vertices).flip();
            glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(0);
            glVertexAttribPointer(0, 2,GL_FLOAT, false, 0, 0);
            vertexCount = vertices.length/2;

            vboId = glGenBuffers();
            vboIdList.add(vboId);
            glBindBuffer(GL_ARRAY_BUFFER,vboId);
            coordBuffer = MemoryUtil.memAllocFloat(texCoords.length);
            coordBuffer.put(texCoords).flip();
            glBufferData(GL_ARRAY_BUFFER, coordBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(1);
            glVertexAttribPointer(1, 2,GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }
        catch (RuntimeException e){
            e.printStackTrace();
        }
        finally {
            MemoryUtil.memFree(vertexBuffer);
            MemoryUtil.memFree(coordBuffer);
        }
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void bind(){
        glBindVertexArray(vaoId);
    }
    public void unbind(){
        glBindVertexArray(0);
    }

    public void destroy(){
        glDeleteVertexArrays(vaoId);
        while (!vboIdList.isEmpty()){
            glDeleteBuffers(vboIdList.get(0));
            vboIdList.remove(0);
        }
    }

    public static void destroy(int id){
        if (MESH_MAP.containsKey(id)) {
            glDeleteVertexArrays(MESH_MAP.get(id).vaoId);
            while (!MESH_MAP.get(id).vboIdList.isEmpty()) {
                glDeleteBuffers(MESH_MAP.get(id).vboIdList.get(0));
                MESH_MAP.get(id).vboIdList.remove(0);
            }
        }
    }
    public static void destroyAllMesh(){
        MESH_MAP.forEach((key,mesh)-> {
            glDeleteVertexArrays(mesh.vaoId);
            while (!mesh.vboIdList.isEmpty()){
                glDeleteBuffers(mesh.vboIdList.get(0));
                mesh.vboIdList.remove(0);
            }
        });

    }

}
