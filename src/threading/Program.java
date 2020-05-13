package threading;

import threading.game.Game;
import threading.game.SpriteSheetLoader;

import java.io.IOException;

class Program {

    public static void main(String[] args) throws IOException {
        SpriteSheetLoader sprites = new SpriteSheetLoader(20, 20,  6, "src/threading/game/sprites.png");
        Game game = new Game();
        Gui gui = new Gui(game, sprites);

        Server server = new Server();

        Thread guiThread = new Thread(gui, "Gui thread");
        Thread serverThread = new Thread(server, "Server thread");

        guiThread.start();
        serverThread.start();
    }
}