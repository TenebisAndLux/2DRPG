package org.sphinx.engine;

public interface Scene {
     void initScene();
     static void finish(){
          SceneController.isLoadingNextScene = false;
     }
}
