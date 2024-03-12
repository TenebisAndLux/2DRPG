package org.sphinx.engine;

import org.sphinx.util.Debug;

import java.util.*;

public class Animator extends Component{

    public enum Policy {
        instant,finished
    }
    public enum Type{
        loop,once
    }
    private final Sprite currentSprite;

    private final Map<String,List<Texture>> texturesMap = new HashMap<>();
    private final Map<String,Double> timeIntervalMap = new HashMap<>();
    private final Map<String, Policy> policyMap = new HashMap<>();
    private final Map<String, Type> typeMap = new HashMap<>();

    private String nextAction = null;
    private String currentAction = null;
    private String lastAction = null;
    private boolean isFinished = false;
    private int currentTexture = 0;
    GameTimer timer = new GameTimer();

    public Animator(GameObject gameObject,Sprite sprite){
        super(gameObject, Animator.class);
        this.currentSprite = sprite;
    }

    public void createAction(String name, double timeInterval, Policy policy,Type type){
        texturesMap.put(name,new ArrayList<>());
        mCreateAction(name, timeInterval, policy,type);
    }

    public void createAction(String name, double timeInterval, Policy policy, Type type,List<Texture> textures){
        texturesMap.put(name,textures);
        mCreateAction(name, timeInterval, policy,type);
    }
    private void mCreateAction(String name, double timeInterval, Policy policy,Type type){
        timeIntervalMap.put(name,timeInterval);
        policyMap.put(name, policy);
        typeMap.put(name,type);
        if (currentAction == null){ nextAction = name; currentAction = name;lastAction = name;}
    }

    public void addTexture(String name, Texture texture){
        texturesMap.get(name).add(texture);
    }

    public void setAction(String name){
        if (!texturesMap.containsKey(name) || currentAction.equals(name)) return;
        lastAction = currentAction;
        if (policyMap.get(name) == Policy.instant){
            timer.reset();
            currentTexture = 0;
            currentAction = name;
            nextAction = name;
        }
        else if (policyMap.get(name) == Policy.finished){
            nextAction = name;
        }
        isFinished = false;
    }

    static void update(){
        for (List<Component> animators : components.get(Animator.class).values()){
            for (Component animator : animators){
                ((Animator)animator).animatorsUpdate();
            }
        }
    }
    private void animatorsUpdate(){
        try {
            if(timer.time > timeIntervalMap.get(currentAction)){
                if (!Objects.equals(nextAction, currentAction)){
                    currentAction = nextAction;
                    currentTexture = 0;
                }
                else {
                    if (++currentTexture == texturesMap.get(currentAction).size()){
                        if (typeMap.get(currentAction)==Type.loop){
                            currentTexture = 0;
                        }
                        else if (typeMap.get(currentAction) == Type.once){
                            currentTexture--;
                            isFinished = true;
                        }
                    }
                    //currentTexture =(currentTexture+1) % texturesMap.get(currentAction).size();
                }
                timer.reset();
            }
            currentSprite.setTexture(texturesMap.get(currentAction).get(currentTexture));
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    static void destroyAllAnimator(){
        Debug.log("Animator----releasing animator");
        components.get(Animator.class).clear();
    }

    public void destroy(){
        components.get(Animator.class).remove(gameObject.getId());
    }

    public boolean isFinished() {
        return isFinished;
    }

    public String getCurrentAction() {
        return currentAction;
    }

    public String getLastAction() {
        return lastAction;
    }
}