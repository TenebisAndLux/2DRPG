package org.sphinx.engine;

public class Sprite extends Render {

    private Mesh mesh;
    private ShaderProgram shaderProgram = ShaderProgram.defaultShader;
    private Texture texture;
    public UsageType type;
    public boolean isFlashing = false;
    public Vector2D offset = new Vector2D();
    float[] vertices = null;
    float[] texCoords = new float[]{
            0, 0,
            -1, 0,
            -1, 1,
            0, 1
    };
    float[] uiTexCoords = new float[]{

        1, 1,
         0, 1,
         0, 0,
        1, 0,
    };
    public Sprite(GameObject gameObject, Texture texture,UsageType type){
        super(gameObject,Render.Type.sprite);
        this.texture = texture;
        mSprite(gameObject,type);
    }
    public Sprite(GameObject gameObject, String path, UsageType type){
        super(gameObject,Render.Type.sprite);
        this.texture = new Texture(path);
        mSprite(gameObject,type);
    }
    public Sprite(GameObject gameObject,float[] vertices, UsageType type){
        super(gameObject,Render.Type.sprite);
        this.vertices = vertices;
        mSprite(gameObject,type);
    }
    private void mSprite(GameObject gameObject, UsageType type){
        if (vertices == null)
            vertices = new float[]{
                    texture.getWidth()/2f, texture.getHeight()/2f,
                    -texture.getWidth()/2f, texture.getHeight()/2f,
                    -texture.getWidth()/2f, -texture.getHeight()/2f,
                    texture.getWidth()/2f, -texture.getHeight()/2f,
            };

        if (type == UsageType.UI)
            this.mesh = new Mesh(vertices, uiTexCoords);
        else
            this.mesh = new Mesh(vertices, texCoords);
        Mesh.MESH_MAP.put(gameObject.getId(),mesh);
        this.type = type;
    }
    public Mesh getMesh() {
        return mesh;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    public void setShaderProgram(ShaderProgram shaderProgram){
        this.shaderProgram = shaderProgram;
    }
}
