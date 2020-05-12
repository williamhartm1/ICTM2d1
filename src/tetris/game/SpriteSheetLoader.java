package tetris.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheetLoader {
    BufferedImage spriteSheet = ImageIO.read(new File("game/sprites.png"));

    int width;
    int height;
    int columns;
    BufferedImage[] sprites;

    public SpriteSheetLoader(int width, int height, int columns) throws IOException {
        this.width = width;
        this.height = height;
        this.columns = columns;
        sprites = new BufferedImage[columns];

            for(int j = 0; j < columns; j++) {
                sprites[j] = spriteSheet.getSubimage(j * width, 0, width, height);
            }
    }

    public BufferedImage getSprite(int column) {
        return sprites[column];
    }
}
