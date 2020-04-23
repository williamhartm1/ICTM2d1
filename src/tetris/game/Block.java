package tetris.game;

import java.awt.*;

public class Block {
    private final Point points[];
    private final BlockType type;

    private Block(BlockType pieceType, Point[] points) {
        this.points = points;
        this.type = pieceType;
    }

    // Generate a random block
    public static Block getRandomBlock() {
        BlockType blockType = BlockType.getRandomBlock();
        return new Block(blockType, blockType.getPoints());
    }

    public static Block getBlock(BlockType blockType) {
        return new Block(blockType, blockType.getPoints());
    }

    public BlockType getType() {
        return type;
    }

    public Point[] getPoints() {
        return points;
    }
}
