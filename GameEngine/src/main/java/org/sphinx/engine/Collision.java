package org.sphinx.engine;

public interface Collision {
    void onTriggerEnter(Collider collider);
    void onTriggerUpdate(Collider collider);
    void onTriggerExit(Collider collider);
    void onCollisionEnter(Collider collider);
 }
