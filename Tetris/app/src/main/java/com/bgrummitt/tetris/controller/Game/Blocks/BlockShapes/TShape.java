package com.bgrummitt.tetris.controller.Game.Blocks.BlockShapes;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.bgrummitt.tetris.controller.Game.Blocks.Block;
import com.bgrummitt.tetris.controller.Game.Blocks.Shape;

public class TShape extends Shape {

    final static private String TAG = TShape.class.getSimpleName();
    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Block[] blocks = new Block[4];
    private int mBottomBlock;
    private int mSpaceSize;

    public TShape(int xSpawnPosition, int ySpawnPosition, int fallSpeed, int spaceSize, int color){
        super(xSpawnPosition, ySpawnPosition, fallSpeed, spaceSize);
        for(int i = 0; i < 3; i++){
            blocks[i] = new Block((xSpawnPosition - spaceSize) + i * spaceSize, ySpawnPosition, spaceSize, color);
        }
        blocks[3] = new Block(xSpawnPosition, ySpawnPosition + spaceSize, spaceSize, color);
        mBottomBlock = 3;
        mSpaceSize = spaceSize;
    }

    @Override
    public boolean isTouching(Shape shape) {
        for(Block block : blocks){
            if(shape.blockCheck(block)){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hitsFloor() {
        if(blocks[mBottomBlock].getY() + mSpaceSize >= screenHeight){
            return true;
        }
        return false;
    }

    @Override
    public boolean blockCheck(Block block) {
        for(Block myBlock : blocks){
            if(myBlock.isTouching(block)){
                return true;
            }
        }
        return false;
    }

    public void update(){
        for(Block i : blocks){
            i.update(0, movementSpeed);
        }
    }

    public void draw(Canvas canvas){
        for(Block i : blocks){
            i.draw(canvas);
        }
    }

}
