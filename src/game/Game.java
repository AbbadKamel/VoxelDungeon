package game;

import game.resource.ResourceLibrary;
import java.io.IOException;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.glu.GLU;
//import org.newdawn.slick.opengl.Texture;

public class Game {

    private final static int width = 800;
    private final static int height = 600;
    private final static int FRAME_RATE = 60;
    public Camera camera;
    private World world;

    private int VBOVertexHandle;
    private int VBOColorHandle;
    
    //private Texture t;
    
    public static void main(String[] args) {
        try {
            Display.setDisplayMode(new DisplayMode(width,height));
            Display.create();
        } catch (LWJGLException e) {
            System.out.println(e);
        }
        Game game = new Game();
        try {
            game.init();
        } catch (IOException e) {
            System.out.println(e);
        }
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            game.render();
            game.update();
            Display.update();
            Display.sync(FRAME_RATE);
        }
        Display.destroy();
        System.exit(0);
    }

    public Game() {
        camera = new Camera(this);
    }
    
    private void init() throws IOException {
        //createVBO();
        initialize3D();
        ResourceLibrary.init();
        //t = ResourceLibrary.getDirt();
        world = new World();
        System.out.println("World initialized.");
    }
    
    public void update() {
        camera.update();
    }
    
    public void render() {
        clearScreen();
        camera.translatePostion();
        //world.render();
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer(3*24*16*64*16);
        VertexPositionData.put(world.getVertexPositions());
        VertexPositionData.flip();
        
        //FloatBuffer VertexColorData = BufferUtils.createFloatBuffer(24 * 3);
        //VertexColorData.flip();
        //VertexColorData.put(world.getVertexColorData());
        
        GL11.glPushMatrix();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOVertexHandle);
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
        //GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOColorHandle);
        //GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0L);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, 24);
        GL11.glPopMatrix();
        
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
        GLU.gluPerspective(45.0f,(float)Display.getWidth()/(float)Display.getHeight(),0.1f,100.0f);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Sets the matrix to displaying objects.
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT,GL11.GL_NICEST); // Something unimportant for quality.
    }

    private void createVBO() {
        VBOColorHandle = GL15.glGenBuffers();
        VBOVertexHandle = GL15.glGenBuffers();
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer(24 * 3);
        VertexPositionData.put(new float[] {
                        1.0f, 1.0f, -1.0f,
                        -1.0f, 1.0f, -1.0f,
                        -1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,

                        1.0f, -1.0f, 1.0f,
                        -1.0f, -1.0f, 1.0f,
                        -1.0f, -1.0f, -1.0f,
                        1.0f, -1.0f, -1.0f,

                        1.0f, 1.0f, 1.0f,
                        -1.0f, 1.0f, 1.0f,
                        -1.0f, -1.0f, 1.0f,
                        1.0f, -1.0f, 1.0f,

                        1.0f, -1.0f, -1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, 1.0f, -1.0f,
                        1.0f, 1.0f, -1.0f,

                        -1.0f, 1.0f, 1.0f,
                        -1.0f, 1.0f, -1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, -1.0f, 1.0f,

                        1.0f, 1.0f, -1.0f,
                        1.0f, 1.0f, 1.0f,
                        1.0f, -1.0f, 1.0f,
                        1.0f, -1.0f, -1.0f
                        });
        VertexPositionData.flip();
        
        //IntBuffer textureId;
        //GL11.glGenTextures(1,textureId);
        
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer(24 * 3);
        VertexColorData.put(new float[] { 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1,1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1,1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1,1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1,1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1,1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, });
        VertexColorData.flip();
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertexPositionData,
                        GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOColorHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertexColorData,
                        GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
}
