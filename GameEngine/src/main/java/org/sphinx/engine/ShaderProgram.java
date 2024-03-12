package org.sphinx.engine;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;
import org.sphinx.util.Debug;
import org.sphinx.util.Utils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL40.*;

public class ShaderProgram {
    private final int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    private final Map<String,Integer> uniforms = new HashMap<>();

    protected static ShaderProgram defaultShader;

    ShaderProgram(){
        programId = glCreateProgram();
    }
    public void createVertexShader(String shaderCode){
        createShader(shaderCode, GL_VERTEX_SHADER);
    }
    public void createFragmentShader(String shaderCode){
        createShader(shaderCode, GL_FRAGMENT_SHADER);
    }
    public void createShader(String shaderCode, int shaderType) {
        int shaderId = glCreateShader(shaderType);
        if (shaderType == GL_VERTEX_SHADER) {
            vertexShaderId = shaderId;
        } else if (shaderType == GL_FRAGMENT_SHADER) {
            fragmentShaderId = shaderId;
        }
        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            Debug.err(("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024)));
        }
        glAttachShader(programId, shaderId);
    }

    public void link(){
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            Debug.err(("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024)));
        }
        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }
        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            Debug.err("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }
    }

    public void createUniform(String name){
        uniforms.put(name, glGetUniformLocation(programId, name));
    }
    public int getUniform(String name){
        return uniforms.get(name);
    }
    public void setUniform(String name, int value){
        glUniform1i(uniforms.get(name), value);
    }
    public void setUniform(String name, Matrix4f matrix,int size){
        try(MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(uniforms.get(name),false , matrix.get(stack.mallocFloat(size)));
        }
    }
    public void setUniform(String name, Color color){
        glUniform4f(uniforms.get(name), color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f,color.getAlpha()/255f);
    }
    public static void defaultShaderInit(){
        defaultShader = new ShaderProgram();
        defaultShader.createVertexShader(Utils.getFileContent("/vertex.vert"));
        defaultShader.createFragmentShader(Utils.getFileContent("/fragment.frag"));
        defaultShader.link();
        defaultShader.createUniform("matrix");
        defaultShader.createUniform("UIsign");
        defaultShader.createUniform("UIcolor");
        defaultShader.createUniform("isFlash");
        Debug.log("Default shader compiled successfully");
    }
    public static ShaderProgram getDefaultShader(){
        return defaultShader;
    }
    public void bind(){
        glUseProgram(programId);
    }
    public void unbind(){
        glUseProgram(0);
    }
}
