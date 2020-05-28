package tetris.game;

/*
Sprites laden en opknippen zodat elk bloktype een andere kleur van de sprite krijgt
 */

import tetris.TetrisConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheetLoader {

    BufferedImage spriteSheet;

    int width;
    int height;
    int columns;
    BufferedImage[] sprites;

    public SpriteSheetLoader(int width, int height, int columns) throws IOException {
        //verwijzen naar juiste path voor de sprite
        TetrisConfig config = new TetrisConfig();
        spriteSheet = ImageIO.read(new File(config.getVariable("app.sprites")));
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


