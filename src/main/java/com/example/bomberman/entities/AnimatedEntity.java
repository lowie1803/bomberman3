/**
 * @author low_
 */
package com.example.bomberman.entities;

public abstract class AnimatedEntity extends Entity {
    protected int animate = 0;
    protected final int MAX_ANIMATE = 60;

    protected void animate() {
        animate++;
        animate %= MAX_ANIMATE;
    }

}
