package org.sphinx.engine;

import org.sphinx.util.Debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GameObject {
    private static final List<GameObject> GAME_OBJECT_LIST = new ArrayList<>();
    public static int gameObjectCount = 0;
    private final int Id;
    public String tag = "";
    public String name = "";
    private boolean isEnable = true;
    private boolean beforeIsEnable = false;
    public Transform transform = new Transform();
    private GameObject parent = null;
    private final List<GameObject> children = new ArrayList<>();

    public GameObject(){
        this.Id = gameObjectCount;
        gameObjectCount++;
        GAME_OBJECT_LIST.add(this);
        start();
    }

    public abstract void start();

    public abstract void update();

    public abstract void enable();

    public abstract void disable();

    static void gameObjectsUpdate(){
        int size = GAME_OBJECT_LIST.size();
        for (int i = 0; i < size; i++){
            if (GAME_OBJECT_LIST.get(i).isEnable()){
                GAME_OBJECT_LIST.get(i).update();
                if (SceneController.isLoadingNextScene){
                    break;
                }
            }
        }
    }

    static void gameObjectStatusUpdate(){
        for(GameObject gameObject : GAME_OBJECT_LIST){
            if (gameObject.isEnable != gameObject.beforeIsEnable){
                if (gameObject.isEnable){
                    gameObject.enable();
                }
                else {
                    gameObject.disable();
                }
                gameObject.beforeIsEnable = gameObject.isEnable;
            }
        }
    }
    static void gameObjectsRemoveAll(){
        Debug.log("GAME_OBJECT_LIST.isEmpty");
        while (!GAME_OBJECT_LIST.isEmpty()){
            GAME_OBJECT_LIST.get(0).destroy();
        }
    }
    public boolean isEnable(){
        if (parent==null){
            return isEnable && beforeIsEnable;
        }
        else {
            return parent.isEnable() && isEnable && beforeIsEnable;
        }
    }
    public void destroy(){
        Debug.log("\tID : "+ this.Id+" name : "+name+" tag : "+tag+" \n" + "release");
        GAME_OBJECT_LIST.remove(this);
        Mesh.destroy(Id);
    }

    public Vector2D getPosition(){
        if (parent == null){
            return new Vector2D(this.transform.position);
        }
        else {
            return parent.getPosition().added(this.transform.position);
        }
    }

    public Transform getTransform() {
        if (parent == null){
            return transform;
        }
        else {
            Transform transform1 = new Transform();
            transform1.position = this.transform.position.added(parent.getTransform().position);
            return transform1;
        }
    }

    public float getRotation(){
        return this.transform.rotation;
    }
    public void addRotation(float add){
        this.transform.rotation += add;
    }
    public Vector2D getScale(){
        return this.transform.scale;
    }
    public void setActive(boolean bool){
        this.isEnable = bool;
    }
    public void setParent(GameObject gameObject){
        if (parent != null) {
            this.parent.children.remove(this);
        }
        if (gameObject != null) {
            this.parent = gameObject;
            this.parent.children.add(this);
        }
    }
    public GameObject getParent(){
        return parent;
    }
    public List<GameObject> getAncestor(){
        List<GameObject> gameObjects = new ArrayList<>();
        if (parent != null) {
            gameObjects.add(parent);
            gameObjects.addAll(parent.getAncestor());
        }
        return gameObjects;
    }
    public List<GameObject> getChildren(){
        return children;
    }
    public static GameObject findGameObject(int id){
        for (GameObject gameObject : GAME_OBJECT_LIST){
            if (gameObject.Id == id){
                return gameObject;
            }
        }
        return null;
    }
    public static GameObject findGameObject(String name){
        for (GameObject gameObject : GAME_OBJECT_LIST){
            if (Objects.equals(gameObject.name, name)){
                return gameObject;
            }
        }
        return null;
    }
    public static List<GameObject> findGameObjects(String tag){
        List<GameObject> gameObjects = new ArrayList<>();
        for (GameObject gameObject : GAME_OBJECT_LIST){
            if (Objects.equals(gameObject.tag, tag)){
                gameObjects.add(gameObject);
            }
        }
        return gameObjects;
    }

    public List<Component> getComponent(Class<? extends Component> typeClazz) {
        return Component.getComponent(this, typeClazz);
    }
    public int getId() {
        return Id;
    }
}
