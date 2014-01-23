package game;

import game.world.World;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.glu.GLU;

public class Game {

    private final static int width = 800;
    private final static int height = 600;
    private final static int FRAME_RATE = 60;
    private World world;

    public ArrayList<Float> vertices;
    public ArrayList<Float> colorVertices;

    public static void main(String[] args) {
        try {
            Display.setDisplayMode(new DisplayMode(width,height));
            Display.create();
        } catch (LWJGLException e) {
            System.out.println(e);
        }
        Display.setTitle("Voxel Dungeons");
        Game game = new Game();
        try {
            game.init();
        } catch (IOException e) {
            System.out.println(e);
        }
        int delta = 0;
        /*
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24) {};
        UnicodeFont font = new UnicodeFont(awtFont);
        font.getEffects().add(new ColorEffect(java.awt.Color.white));
        font.addAsciiGlyphs();
        try {
            font.loadGlyphs();
        } catch (SlickException e) {
            System.out.println("Failed to load font: " + e);
        }
        */
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            long startTime = System.currentTimeMillis();
            
            game.clearScreen();
            Camera.update(delta);
            try {
                game.render();
            } catch (IOException e) {
                System.out.println(e);
            }
            Display.update();
            
            long endTime = System.currentTimeMillis();
            System.out.println("Actual (No VSync): " + (endTime - startTime));
            
            Display.sync(FRAME_RATE);
            
            endTime = System.currentTimeMillis();
            delta = (int)(endTime - startTime);
            System.out.println("Overall: " + delta + "\n");
        }
        Display.destroy();
        System.exit(0);
    }
    private int VBOVertexHandle;
    private int VBOColorHandle;

    public Game() { }
    
    private void init() throws IOException {
        world = new World(8,8);
        Camera.init();
        vertices = new ArrayList<Float>();
        colorVertices = new ArrayList<Float>();
        this.initialize3D();
        VBOVertexHandle = GL15.glGenBuffers();
        VBOColorHandle = GL15.glGenBuffers();
    }
    
    public void render() throws IOException {
        long st;
        long et;        
        
        if (!Camera.hasNotMoved()) {
            st = System.currentTimeMillis();
            vertices.clear();
            colorVertices.clear();
            et = System.currentTimeMillis();
            System.out.println((et-st) + ": Clearing old arraylists.");
            
            st = System.currentTimeMillis();
            world.render(vertices,colorVertices);
            et = System.currentTimeMillis();
            System.out.println((et-st) + ": Getting vertices from world.");
        }

        System.out.println("Vertices: " + vertices.size());

        st = System.currentTimeMillis();
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer(vertices.size());
        et = System.currentTimeMillis();
        System.out.println((et-st) + ": Creating position buffer.");
        
        st = System.currentTimeMillis();
        float[] floats = new float[colorVertices.size()];
        int i = 0;
        for (Float f : vertices) {
            floats[i] = f;
            i++;
        }
        et = System.currentTimeMillis();
        System.out.println((et-st) + ": Creating position array.");
        
        st = System.currentTimeMillis();
        VertexPositionData.put(floats);
        et = System.currentTimeMillis();
        System.out.println((et-st) + ": Adding position array to buffer.");
        
        st = System.currentTimeMillis();
        VertexPositionData.flip();
        et = System.currentTimeMillis();
        System.out.println((et-st) + ": Flipping position buffer.");
        
        st = System.currentTimeMillis();
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer(colorVertices.size());
        et = System.currentTimeMillis();
        System.out.println((et-st) + ": Creating color buffer.");
        
        st = System.currentTimeMillis();
        float[] colorFloats = new float[colorVertices.size()];
        int j = 0;
        for (Float f : colorVertices) {
            colorFloats[j] = f;
            j++;
        }
        et = System.currentTimeMillis();
        System.out.println((et-st) + ": Creating color array.");
        
        st = System.currentTimeMillis();
        VertexColorData.put(colorFloats);
        et = System.currentTimeMillis();
        System.out.println((et-st) + ": Adding color array to buffer.");
        
        st = System.currentTimeMillis();
        VertexColorData.flip();
        et = System.currentTimeMillis();
        System.out.println((et-st) + ": Flipping color buffer.");
        
        st = System.currentTimeMillis();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,VertexPositionData,GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOColorHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,VertexColorData,GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
        et = System.currentTimeMillis();
        System.out.println((et-st) + ": Binding buffers.");
        
        st = System.currentTimeMillis();
        GL11.glPushMatrix();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOVertexHandle);
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOColorHandle);
        GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0L);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, vertices.size()/3);
        GL11.glPopMatrix();
        et = System.currentTimeMillis();
        System.out.println((et-st) + ": Drawing bound buffers.");
    }

    public void clearScreen() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
    }

    public void initialize3D() {
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Allows 2D textures.
        GL11.glShadeModel(GL11.GL_SMOOTH); // Smoother textures.
        GL11.glClearColor(0.4f,0.6f,1.0f,0.0f); // BG color. 6698FF
        GL11.glClearDepth(1.0); // Buffer depth, allows objects to draw over things behind them.
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Depth testing (see above).
        GL11.glDepthFunc(GL11.GL_LEQUAL); // Type of depth testing.
        
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
        
        GL11.glMatrixMode(GL11.GL_PROJECTION); // Sets matrix mode to displaying pixels.
        GL11.glLoadIdentity(); // Loads the above matrix mode.
        
        // Sets default perspective location.                       Render Distances: Min   Max
        GLU.gluPerspective(45.0f,(float)Display.getWidth()/(float)Display.getHeight(),0.1f,300.0f);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Sets the matrix to displaying objects.
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT,GL11.GL_NICEST); // Something unimportant for quality.
    }
}
