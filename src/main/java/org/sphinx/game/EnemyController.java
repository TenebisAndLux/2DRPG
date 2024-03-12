package org.sphinx.game;

import org.sphinx.engine.GameObject;
import org.sphinx.util.Utils;

public class EnemyController extends GameObject {
    Utils.ObjectPool objectPool;
    public static int enemyCount = 0;
    @Override
    public void start() {
        this.name = "EnemyPool";
        this.tag = "ObjectPool";
        objectPool = new Utils.ObjectPool(Enemy.class,10);
        enemyCount = 0;
    }

    @Override
    public void update() {
        if (enemyCount <5){
            objectPool.get();
        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
