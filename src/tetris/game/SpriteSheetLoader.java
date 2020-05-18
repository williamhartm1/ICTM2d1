package tetris.game;

import tetris.TetrisConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheetLoader {
<<<<<<< HEAD

    BufferedImage spriteSheet = ImageIO.read(new File("C:\\Users\\leens\\Documents\\ICT\\Periode_4\\KBS\\Code_git\\ICTM2d1\\src\\tetris\\sprites\\sprites.png"));
=======
    BufferedImage spriteSheet;
>>>>>>> master

    int width;
    int height;
    int columns;
    BufferedImage[] sprites;

    public SpriteSheetLoader(int width, int height, int columns) throws IOException {
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


