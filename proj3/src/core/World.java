package core;

import edu.princeton.cs.algs4.Out;
import org.junit.jupiter.api.MethodOrderer;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import edu.princeton.cs.algs4.StdDraw;
import utils.RandomUtils.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import static utils.RandomUtils.uniform;


public class World {
    // build your own world!
    private static final int WIDTH = 70;
    private static final int HEIGHT = 40;
    private static int SEED=20040330;
    private static int numOfRoom=12;
    private static int maxSizeOfRoom=10;

    // Create grid of tiles (all null to begin with).
    private TETile[][] world = new TETile[WIDTH][HEIGHT];
    private TERenderer ter = new TERenderer();
    private Random random;
    private List<xyInRoom> roomList=new ArrayList<>();
    private xyInRoom avatar;

    public void loadAvatar(int x, int y) {
        avatar.x=x;
        avatar.y=y;
    }

    public void startGameLoop() {
        interactivity();
    }

    public void avatarSave(){
        String filename = "save.txt";
        Out out = new Out(filename);
        out.println(SEED+","+avatar.x+","+ avatar.y);
        out.close();
    }

    private class xyInRoom{
        int x;
        int y;

        public xyInRoom(int x,int y){
            this.x=x;
            this.y=y;
        }
    }

    // by gemini 3.0
    // ================= ADDED CODE START: VARIABLES =================
    private boolean lineOfSightEnabled = false; // Toggle for LOS
    private int currentTheme = 0; // 0: Default, 1: Dark, 2: Forest
    private static final int SIGHT_RADIUS = 6;

    // Define custom themes locally since we can't edit Tileset.java
    // Theme 1: Dark Dungeon
    private static final TETile DARK_WALL = new TETile('#', new Color(100, 100, 100), new Color(20, 20, 20), "dark wall", 101);
    private static final TETile DARK_FLOOR = new TETile('.', new Color(60, 60, 80), new Color(10, 10, 15), "dark floor", 102);

    // Theme 2: Forest
    private static final TETile FOREST_WALL = new TETile('♣', new Color(34, 139, 34), new Color(0, 50, 0), "tree", 103);
    private static final TETile FOREST_FLOOR = new TETile('"', new Color(50, 205, 50), new Color(139, 69, 19), "grass", 104);
    // ================= ADDED CODE END: VARIABLES =================

    //constructor
    public World(int seed) {
        ter.initialize(WIDTH,HEIGHT);

        SEED=seed;
        random=new Random(SEED);

        // Fill grid with NOTHING tiles.
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        for(int i=0;i<numOfRoom;i++) {
            drawRoom();
        }
        formHallWays();
        formWalls();

        avatar=new xyInRoom(roomList.getFirst().x,roomList.getFirst().y);
//        updateAvatar(0,0);
    }

    private void formWalls(){
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if((x==0 || x==WIDTH-1 || y==0 || y==HEIGHT-1)
                        && world[x][y] == Tileset.FLOOR){
                    world[x][y] = Tileset.WALL;
                }
            }
        }
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 1; y < HEIGHT; y++) {
                if(world[x][y-1]!=world[x][y]){
                    if(world[x][y-1]==Tileset.FLOOR) world[x][y]=Tileset.WALL;
                    else if(world[x][y]==Tileset.FLOOR) world[x][y-1]=Tileset.WALL;
                }
            }
        }
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 1; x < WIDTH; x++) {
                if(world[x-1][y]!=world[x][y]){
                    if(world[x-1][y]==Tileset.FLOOR) world[x][y]=Tileset.WALL;
                    else if(world[x][y]==Tileset.FLOOR) world[x-1][y]=Tileset.WALL;
                }
            }
        }
    }

    private void formHallWays(){
        Iterator<xyInRoom> hallWay=roomList.iterator();
        xyInRoom start=hallWay.next();
        xyInRoom end;
        while (hallWay.hasNext()){
            end=hallWay.next();
            drawHallWay(start,end);
//            formWalls(); //there's no difference between 'formWalls' here and in 'world' constructor.
            start=end;
        }
    }

    private void drawHallWay(xyInRoom s,xyInRoom e){
//        int breadth=uniform(random,3,5);
        int breadth=3;
        int sx=s.x,sy=s.y;
        int ex=e.x,ey=e.y;
        int unitX=sx>ex? -1 : 1;
        int unitY=sy>ey? -1 : 1;
        for(int h=sx;unitX*(ex-h)>=0;h+=unitX){
                world[h][sy] = Tileset.FLOOR;
        }
        for(int v=sy;unitY*(ey-v)>=0;v+=unitY){
                world[ex][v] = Tileset.FLOOR;
        }
    }
/**
 * I tried using this method to make it be more distributed,
 * but in fact the uniform distribution make the links between rooms interweaved each other,
 * which leads the final breath of hallway exceed the limit.
 */
//    private void formRooms(){
//        int nums=numOfRoom;
//        while(nums>0){
//            int startX=uniform(random,0,WIDTH-maxSizeOfRoom);
//            int startY=uniform(random,0,HEIGHT-maxSizeOfRoom);
//            int area=0;
//            if(startX<8 && startY<5) area=1;
//            else if((startX>=18 && startX<26) && (startY<5)) area=2;
//            else if((startX>=36 && startX<44) && (startY<5)) area=3;
//            else if((startX>=54 && startX<62) && (startY<5)) area=4;
//            else if((startX<8) && (startY>=13 && startY<18)) area=5;
//            else if((startX>=18 && startX<26) && (startY>=13 && startY<18)) area=6;
//            else if((startX>=36 && startX<44) && (startY>=13 && startY<18)) area=7;
//            else if((startX>=54 && startX<62) && (startY>=13 && startY<18)) area=8;
//            else if((startX<8) && (startY>=26 && startY<31)) area=9;
//            else if((startX>=18 && startX<26) && (startY>=26 && startY<31)) area=10;
//            else if((startX>=36 && startX<44) && (startY>=26 && startY<31)) area=11;
//            else if((startX>=54 && startX<62) && (startY>=26 && startY<31)) area=12;
//
//            if(!map.containsKey(area)){
//                map.put(area,1);
//                drawRoom(startX,startY);
//                nums--;
//            }
//            else continue;
//
//        }
//    }

    private void drawRoom(){
        int startX=uniform(random,0,WIDTH-maxSizeOfRoom);
        int startY=uniform(random,0,HEIGHT-maxSizeOfRoom);
        int sizeX=uniform(random,5,maxSizeOfRoom);
        int sizeY=uniform(random,5,maxSizeOfRoom);

        for(int x=startX;x<startX+sizeX;x++){
            for(int y=startY;y<startY+sizeY;y++){
                world[x][y]=Tileset.FLOOR;
            }
        }
        int x=uniform(random,startX+2,startX+sizeX-2);
        int y=uniform(random,startY+2,startY+sizeY-2);
        roomList.add(new xyInRoom(x,y));
    }

    private void updateAvatar(int nx,int ny){
        if(world[avatar.x+nx][avatar.y+ny]==Tileset.FLOOR){
            world[avatar.x][avatar.y] = Tileset.FLOOR;
            avatar.x += nx;
            avatar.y += ny;
            world[avatar.x][avatar.y] = Tileset.AVATAR;
        }
    }

    private void movement(char c){
        c = Character.toLowerCase(c);
        switch (c) {
            case 'w':
                updateAvatar(0,1);
                break;
            case 's':
                updateAvatar(0,-1);
                break;
            case 'a':
                updateAvatar(-1,0);
                break;
            case 'd':
                updateAvatar(1,0);
                break;
            default:
                break;
        }
    }

    private void interactivity(){
        updateAvatar(0,0);

        char c;
        while(true){
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();
            int tileX = (int) mouseX;
            int tileY = (int) mouseY;

            String hudText = "";

            if (tileX >= 0 && tileX < WIDTH && tileY >= 0 && tileY < HEIGHT) {
                TETile tile = world[tileX][tileY];
                hudText = tile.description();
            } else {
                hudText = "Space";
            }

            while (StdDraw.hasNextKeyTyped()){
                c=StdDraw.nextKeyTyped();

                if(c==':'){
                    outer:
                    while(true) {
                        while (StdDraw.hasNextKeyTyped()) {
                            char c1 = StdDraw.nextKeyTyped();
                            if (c1 == 'q' || c1 == 'Q') {
                                avatarSave();
                                System.exit(0);
                            }else break outer;
                        }
                    }
                }

                movement(c);

                // by gemini 3.0
                // === INSERT THIS CODE ===
                if (c == 'v') { // Toggle Vision
                    lineOfSightEnabled = !lineOfSightEnabled;
                }
                if (c == 't') { // Toggle Theme
                    currentTheme = (currentTheme + 1) % 3;
                }
                // ========================

            }

            // ter.renderFrame(world); // Comment out the old render

            // by gemini 3.0
            // === INSERT THIS CODE ===
            renderFrameWithEffects();  // Use the new render with LOS and Themes
            // ========================

            StdDraw.setPenColor(Color.GREEN);
            StdDraw.setFont(new Font("Arial", Font.PLAIN, 16));
            StdDraw.text(4, HEIGHT - 2, "This is "+hudText);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.textRight(69, HEIGHT - 1, "V: Toggle Line of Sight on/off.");
            StdDraw.textRight(69, HEIGHT - 3, "T: Cycle through themes (Default -> Dark -> Forest).");
            StdDraw.textRight(69, HEIGHT - 5, "W/A/S/D: Move (Existing).");
            StdDraw.textRight(69, HEIGHT - 7, ":Q: Save and Quit (Existing).");
            StdDraw.show();
            StdDraw.pause(20);
        }

    }


    // by gemini 3.0
    // ================= ADDED CODE START: HELPER METHODS =================

    /**
     * This is the custom render function that handles both Themes and Line of Sight.
     * It creates a temporary view of the world to send to the renderer.
     */
    private void renderFrameWithEffects() {
        TETile[][] frame = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                boolean visible = true;

                // 1. Handle Line of Sight Logic
                if (lineOfSightEnabled) {
                    if (!isTileVisible(x, y)) {
                        visible = false;
                    }
                }

                // 2. Handle Theme and Visibility
                if (visible) {
                    frame[x][y] = applyTheme(world[x][y]);
                } else {
                    frame[x][y] = Tileset.NOTHING;
                }
            }
        }

        // Render the processed frame instead of the raw world
        ter.renderFrame(frame);
    }

    /**
     * Converts a standard tile to the currently selected theme tile.
     */
    private TETile applyTheme(TETile original) {
        if (currentTheme == 0) return original; // Default

        if (original.description().equals("wall")) { // Assuming standard wall description is "wall"
            if (currentTheme == 1) return DARK_WALL;
            if (currentTheme == 2) return FOREST_WALL;
        } else if (original.description().equals("floor")) { // Assuming standard floor description is "floor"
            if (currentTheme == 1) return DARK_FLOOR;
            if (currentTheme == 2) return FOREST_FLOOR;
        }
        // If it's the avatar or nothing, keep it as is
        return original;
    }

    /**
     * Checks if a tile at (targetX, targetY) is visible from the avatar.
     */
    private boolean isTileVisible(int targetX, int targetY) {
        int ax = avatar.x;
        int ay = avatar.y;

        // 1. Check Distance (Circle optimization)
        double distance = Math.sqrt(Math.pow(targetX - ax, 2) + Math.pow(targetY - ay, 2));
        if (distance > SIGHT_RADIUS) {
            return false;
        }

        // 2. Raycast (Line of Sight)
        // We walk from avatar to target. If we hit a wall, the target is blocked.
        // Using simple interpolation
        double steps = Math.max(Math.abs(targetX - ax), Math.abs(targetY - ay));

        for (double i = 1; i < steps; i++) {
            double t = i / steps;
            int currentX = (int) Math.round(ax + t * (targetX - ax));
            int currentY = (int) Math.round(ay + t * (targetY - ay));

            // If we hit a wall before reaching the target, vision is blocked
            // Note: We check if it is a WALL.
            if (world[currentX][currentY].description().equals("wall")) {
                return false;
            }
        }
        return true;
    }
    // ================= ADDED CODE END: HELPER METHODS =================


}
