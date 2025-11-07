package Lab9;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * Draws a world initially full of trees.
 */
public class Task1 {
    private static int WIDTH=30;
    private static int HEIGHT=20;
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

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[30][15];
        fillWithTrees(world);

        ter.renderFrame(world);
    }
}