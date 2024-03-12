package org.sphinx.engine;

import org.joml.Matrix4f;

public class Transformation {
    private static final Matrix4f matrix = new Matrix4f();

    public static Matrix4f getWorldMatrix(Transform transform,Camera camera){
        Vector2D topLeft = new Vector2D(camera.getPosition().x - camera.getWidth()/2f * camera.getZoom(),
                camera.getPosition().y + camera.getHeight()/2f * camera.getZoom());
        Vector2D bottomRight = new Vector2D(camera.getPosition().x + camera.getWidth()/2f * camera.getZoom(),
                camera.getPosition().y - camera.getHeight()/2f * camera.getZoom());
        Vector2D d = camera.transform.position.normalized();
        return matrix.identity()
                .ortho2D(topLeft.x,bottomRight.x,bottomRight.y,topLeft.y)
                .translate(transform.position.x,transform.position.y,0)
                .rotateZ(transform.rotation)
                .scaleXY(transform.scale.x,transform.scale.y);
    }
    public static Matrix4f getUIMatrix(Transform transform){
        return matrix.identity()
                .ortho2D(0,WindowController.getInstance().getWindowWidth(), WindowController.getInstance().getWindowHeight(), 0 )
                .translate(transform.position.x,transform.position.y,0)
                .rotateZ(transform.rotation)
                .scaleXY(transform.scale.x,transform.scale.y);
    }
}
