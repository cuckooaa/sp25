package Lab9;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.Random;

import static org.eclipse.jetty.util.Pool.StrategyType.RANDOM;

/**
 * Draws a world initially full of trees.
 */
public class Task2 {
    private static int WIDTH=30;
    private static int HEIGHT=20;

    private static final long SEED = 0512;
    private static final Random RANDOM = new Random(SEED);
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

        drawSquare(world,RandomUtils.uniform(rand, 3, 17), RandomUtils.uniform(rand, 5, 16),5,randomTile());
    }

    private static TETile randomTile() {
        // The following call to nextInt() uses a bound of 3 (this is not a seed!) so
        // the result is bounded between 0, inclusive, and 3, exclusive. (0, 1, or 2)
        int tileNum = RANDOM.nextInt(3);
        return switch (tileNum) {
            case 0 -> Tileset.WALL;
            case 1 -> Tileset.WALL;
            default -> Tileset.FLOWER;
        };
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[30][15];
        fillWithTrees(world);
//        drawSquare(world, 10, 7, 5, Tileset.FLOWER);
        for(int i=0;i<5;i++){
            addRandomSquare(world,RANDOM);
        }
        ter.renderFrame(world);
    }
}
