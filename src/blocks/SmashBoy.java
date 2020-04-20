package blocks;

import java.awt.*;
import java.util.Arrays;

public class SmashBoy extends Block {
    private int[] block = {
            1,1,0,
            1,1,0,
            0,0,0,
    };

    private Color color = Color.YELLOW;

    public int[] getBlock() {
        return this.block;
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" +
                "block=" + Arrays.toString(block) +
                ", color=" + color +
                '}';
    }
}
