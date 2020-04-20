package blocks;

import java.awt.*;
import java.util.Arrays;

public abstract class Block {
    private int[] block = {
            0,0,0,
            0,0,0,
            0,0,0,
    };
    private Color color = Color.DARK_GRAY;

    int[] getBlock() {
        return block;
    }

    Color getColor() {
        return color;
    }

    public String toString() {
        return "blocks.Block{" +
                "block=" + Arrays.toString(block) +
                ", color=" + color +
                '}';
    }
}
