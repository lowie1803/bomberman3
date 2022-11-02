/**
 * @author low_
 */
package com.example.bomberman.entities.cell.item;

import com.example.bomberman.entities.cell.Cell;
import com.example.bomberman.graphics.Sprite;

public abstract class Item extends Cell {
    public Item(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }
}
