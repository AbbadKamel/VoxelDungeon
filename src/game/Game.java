package game;

import game.util.FloatArray;
import game.util.Frustum;
import game.util.Perlin;
import game.world.World;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class Game {

    private final static int width = 800;
    private final static int height = 600;
    private final static int FRAME_RATE = 60;
    private World world;
    
    private static TrueTypeFont font;

    public FloatArray vertices;
    public FloatArray colorVertices;

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
            //System.out.println(e);
        }
        int delta = 0;
        
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, true);

        // load font from file
        try {
        InputStream inputStream = ResourceLoader.getResourceAsStream("DroidSans.ttf");

        Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        awtFont2 = awtFont2.deriveFont(24f); // set font size

        } catch (FontFormatException e) {
        e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE,null,ex);
        }
        
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
            System.out.println("Overall: " + delta + " | FPS: " + 1000/delta + "\n");
        }
        Display.destroy();
        System.exit(0);
    }
    private int VBOVertexHandle;
    private int VBOColorHandle;

    public Game() { }
    
    private void init() throws IOException {
        Perlin p = new Perlin(128,128);
        world = new World(8,8,p);
        Camera.init();
        vertices = new FloatArray(600000);
        colorVertices = new FloatArray(600000);
        this.initialize3D();
        VBOVertexHandle = GL15.glGenBuffers();
        VBOColorHandle = GL15.glGenBuffers();
    }
    
    public void render() throws IOException {
        long st;
        long et;
        
        if (!Camera.hasNotMoved()) {
            Frustum.updateFrustum();

            vertices.clear();
            colorVertices.clear();

            st = System.currentTimeMillis();
            world.render(vertices,colorVertices);
            et = System.currentTimeMillis();
            System.out.println((et-st) + ": Getting vertices from world.");
        }
        
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer(vertices.size());
        VertexPositionData.put(vertices.getValues());
        VertexPositionData.flip();
        
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer(colorVertices.size());
        VertexColorData.put(colorVertices.getValues());
        VertexColorData.flip();
        
        System.out.println("Vertices: " + vertices.getPos() + ": " + Arrays.toString(colorVertices.getNumValues(24)));
        
        st = System.currentTimeMillis();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,VertexPositionData,GL15.GL_STREAM_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOColorHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,VertexColorData,GL15.GL_STREAM_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
        et = System.currentTimeMillis();
        System.out.println((et-st) + ": Binding buffers.");
        
        GL11.glPushMatrix();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOVertexHandle);
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOColorHandle);
        GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0L);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, vertices.size()/3);
        GL11.glPopMatrix();
        
        // Set it to 2D mode.
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, width, height, 0.0, -1.0, 10.0);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        
        // This is for the text.
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        // Render 2D.
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glColor4d(0.5f,0.5f,0.5f,0.5f);
            font.drawString(100, 50, "TEXT IS AWESOME");
        GL11.glEnd();
        
        // Back to 3D mode.
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
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
