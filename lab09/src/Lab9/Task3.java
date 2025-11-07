package Lab9;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.awt.*;
import java.io.File;
import java.util.Random;

import static org.eclipse.jetty.util.Pool.StrategyType.RANDOM;

/**
 * Draws a world initially full of trees.
 */
public class Task3 {
    private static int WIDTH=30;
    private static int HEIGHT=20;

    private static final long SEED = 0512;
    private static Random RANDOM = new Random(SEED);
    /**
     * Fills the entire 2D world with the Tileset.TREE tile.
     */
    private static void fillWithTrees(TETile[][] world) {
        for(int x=0;x< world.length;x++){
            for(int y=0;y<world[0].length;y++){
                world[x][y]=Tileset.TREE;
            }
        }
        return;
    }

    private static void drawSquare(TETile[][] world, int startX, int startY, int size, TETile tile){
        for(int x=startX;x<size+startX;x++){
            for(int y=startY;y>startY-size;y--){
                world[x][y]=tile;
            }
        }
    }

    private static void addRandomSquare(TETile[][] world, Random rand){
        drawSquare(world,RandomUtils.uniform(rand, 0, 26), RandomUtils.uniform(rand, 4, 15),RandomUtils.uniform(rand, 2, 6),randomTile());
    }

    private static TETile randomTile() {
        // The following call to nextInt() uses a bound of 3 (this is not a seed!) so
        // the result is bounded between 0, inclusive, and 3, exclusive. (0, 1, or 2)
        int tileNum = RANDOM.nextInt(3);
        return switch (tileNum) {
            case 0 -> Tileset.WALL;
            case 1 -> Tileset.WATER;
            default -> Tileset.FLOWER;
        };
    }

    public static void countSave(int count){
        String filename = "count.txt";
        Out out = new Out(filename);
        out.println(count);
        out.close();
    }

    public static int countLoad(){
        String filename = "count.txt";
        File file = new File(filename);
        int count=0;

        // Step 1: Check if the file exists and read the saved name
        if (file.exists()) {
            In in = new In(file);
            if (in.hasNextLine()) {
                String countString = in.readLine();
                count=Integer.parseInt(countString);
            } else {
                System.out.println("nothing been saved");
            }
        } else {
            System.out.println("nothing been saved");
        }
        return count;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[30][15];
        fillWithTrees(world);

        char c; // Variable for saving the most recent character typed by the user.
        int count=0;
        String text="Number of squares: ";
        StdDraw.textLeft(1,17,text);
        // This outer infinite-loop allows the game to continue indefinitely, until the user quits.
        while (true) {
            while (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                c = Character.toLowerCase(c);

                // Switch statements can be useful to replace long if-else statements!
                switch (c) {
                    case 'd':
                        addRandomSquare(world, RANDOM);
                        count++;
                        break;
                    case 's':
                        countSave(count);
                        break;
                    case 'l':
                        fillWithTrees(world);
                        int loadCount=countLoad();
                        RANDOM=new Random(SEED);
                        for(int i=0;i<loadCount;i++){
                            addRandomSquare(world, RANDOM);
                        }
                        count=loadCount;
                        break;
                    case 'q':
                        System.exit(0); // Closes the game window and quits the game.
                        break;
                    default:
                        break;
                }
            }
            ter.renderFrame(world);
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(1,17,text+count);
            StdDraw.show();
            StdDraw.pause(2);
        }
    }
}
