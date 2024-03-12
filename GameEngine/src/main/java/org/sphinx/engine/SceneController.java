package org.sphinx.engine;

import org.sphinx.util.Debug;

import java.util.ArrayList;
import java.util.List;

public class SceneController {
    public static final List<Scene> SCENE_LIST = new ArrayList<>();
    private static Scene activeScene = null;
    public static boolean isLoadingNextScene = false;
    private static int activeSceneId = 0;

    public static void register(Scene scene){
        SCENE_LIST.add(scene);
        Debug.log("Scene loader----Scene registration successful, total number of current scenes : "+SCENE_LIST.size());
    }

    public static void loadSceneIndex(int sceneId){
        isLoadingNextScene = true;
        Renderer.render();
        activeScene = SCENE_LIST.get(sceneId);
        Debug.log("Scene loader----load scenes ID : "+sceneId);
        activeSceneId = sceneId;
        startLoadScene();
    }

    public static void loadNextScene(){
        if (activeSceneId+1>SCENE_LIST.size()) return;
        isLoadingNextScene = true;
        Renderer.render();
        activeScene = SCENE_LIST.get(++activeSceneId);
        Debug.log("Scene loader----load the next scene，ID : "+activeSceneId);
        startLoadScene();
    }
    private static void startLoadScene(){
        cleanScene();
        activeScene.initScene();
    }

    public static void cleanScene(){
        Debug.log("Scene resources are being released");
        Renderer.setActiveCamera(null);
        Texture.destroyAllTexture();
        Render.destroyAllRender();
        GameTimer.destroyAllGameTimer();
        Animator.destroyAllAnimator();
        Collider.destroyAllCollider();
        GameObject.gameObjectsRemoveAll();
        System.gc();
    }
}
