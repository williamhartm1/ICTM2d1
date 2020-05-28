package tetris.game;

/*
Blok maken met een willekeurig bloktype;
Roteren van blok;
 */

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

    public Block rotateRight() {
        return new Block(type, rotateRight(points));
    }
    
    public Block rotateLeft() {
        return new Block(type, rotateLeft(points));
    }


    //blok linksom of rechtsom draaien afhankelijk van parameters
    private Point[] rotate(Point toRotate[], int x, int y) {
        Point rotated[] = new Point[4];

        for (int i = 0; i < 4; i++) {
            int temp = toRotate[i].x;
            rotated[i] = new Point(x * toRotate[i].y, y * temp);
        }

        return rotated;
    }

    private Point[] rotateRight(Point toRotate[]) {
        return rotate(toRotate, -1, 1);
    }

    private Point[] rotateLeft(Point toRotate[]) {
        return rotate(toRotate, 1, -1);
    }

}
