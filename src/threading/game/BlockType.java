package threading.game;

import java.awt.*;
import java.util.Random;

public enum BlockType {
    O(p(-1, 0), p(0, 0),  p(-1, -1), p(0, -1)),
    I(p(-2, 0), p(-1, 0), p(0, 0),   p(1, 0)),
    S( p(0, 0),  p(1, 0),  p(-1, -1), p(0, -1)),
    Z( p(-1, 0), p(0, 0),  p(0, -1),  p(1, -1)),
    L( p(-1, 0), p(0, 0),  p(1, 0),   p(-1, -1)),
    J( p(-1, 0), p(0, 0),  p(1, 0),   p(1, -1)),
    T( p(-1, 0), p(0, 0),  p(1, 0),   p(0, -1));

    private static final Random random = new Random();
    private final Point points[];

    BlockType(Point... points) {
        this.points = points;
    }

    public static BlockType getRandomBlock() {
        return BlockType.values()[random.nextInt(BlockType.values().length)];
    }

    public Point[] getPoints() {
        return points;
    }

    private static Point p(int x, int y) {
        return new Point(x, y);
    }
}
