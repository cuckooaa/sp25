package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;

public class Main {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 40;

    private static TETile[][] world = new TETile[WIDTH][HEIGHT];
    private static TERenderer ter = new TERenderer();

    public static void main(String[] args) {
        // build your own world!
//        World world=new World();
        ter.initialize(WIDTH,HEIGHT);

        // Fill grid with NOTHING tiles.
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        char c; // Variable for saving the most recent character typed by the user.
        int count=0;

        outerLoop:
        while(true) {
            while (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                c = Character.toLowerCase(c);

                switch (c) {
                    case 'n':
                        newGame();
                        break outerLoop;
                    case 'l':
                        break;
                    case 'q':
                        System.exit(0);
                        break;
                    default:
                        break;
                }
            }

            renderMain();
            StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
            StdDraw.text(14, 24, "(n) New Game");
            StdDraw.text(14, 21, "(l) Load Game");
            StdDraw.text(14, 18, "(q) Quit Game");
            StdDraw.show();
            StdDraw.pause(20);
        }
    }

    private static void renderMain(){
        ter.renderFrame(world);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(new Font("Times New Roman", Font.BOLD, 36));
        StdDraw.text(14, 34, "build your own world");
    }

    private static void newGame(){
        StdDraw.clear();
        renderMain();
        StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
        StdDraw.text(14, 21, "Enter Seed Followed By s");
        StdDraw.show();

        char c;
        String s="";

        newGameLoop:
        while(true){
            while(StdDraw.hasNextKeyTyped()){
                c=StdDraw.nextKeyTyped();

                if(c=='s' || c=='S'){
                    World randomWorld=new World(Integer.parseInt(s));
                    break newGameLoop;
                }
                s+=c;
            }
            renderMain();
            StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
            StdDraw.text(14, 21, "Enter Seed Followed By s");
            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.text(14, 8, s);
            StdDraw.show();
            StdDraw.pause(20);
        }
    }

}
