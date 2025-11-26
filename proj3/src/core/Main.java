package core;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.io.File;

public class Main {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 35;

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
                        loadWorld();
                        break outerLoop;
                    case 'q':
                        System.exit(0);
                        break;
                    default:
                        break;
                }
            }

            renderMain();
            StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
            StdDraw.text(14, 19, "(n) New Game");
            StdDraw.text(14, 16, "(l) Load Game");
            StdDraw.text(14, 13, "(q) Quit Game");
            StdDraw.show();
            StdDraw.pause(20);
        }
    }

    private static void loadWorld() {
        String filename = "save.txt";
        File file = new File(filename);

        In in = new In(file);
        if (in.hasNextLine()) {
            String String = in.readLine();
            String[] stringArray = String.split(",");
            int seed=Integer.parseInt(stringArray[0]);
            World randomWorld=new World(seed);
            randomWorld.loadAvatar(Integer.parseInt(stringArray[1]),Integer.parseInt(stringArray[2]));
            randomWorld.startGameLoop();
        } else {
            System.out.println("nothing been saved");
        }
    }

    private static void renderMain(){
        ter.renderFrame(world);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(new Font("Times New Roman", Font.BOLD, 36));
        StdDraw.text(14, 29, "build your own world");
    }

    private static void newGame(){
        StdDraw.clear();
        renderMain();
        StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
        StdDraw.text(14, 19, "Enter Seed Followed By s");
        StdDraw.show();

        char c;
        String s="";

        newGameLoop:
        while(true){
            while(StdDraw.hasNextKeyTyped()){
                c=StdDraw.nextKeyTyped();

                if(c=='s' || c=='S'){
                    World randomWorld=new World(Integer.parseInt(s));
                    randomWorld.startGameLoop();
                    break newGameLoop;
                }
                s+=c;
            }
            renderMain();
            StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
            StdDraw.text(14, 19, "Enter Seed Followed By s");
            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.text(14, 3, s);
            StdDraw.show();
            StdDraw.pause(20);
        }
    }

}
