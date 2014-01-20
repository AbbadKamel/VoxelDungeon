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
    private Camera camera;
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
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            /*
             * In the tutorial, it works when done like this.
             *
             * GL11.glTranslatef((float)Math.sin(RotateYaw/180*Math.PI), 0f, -4f);
             * GL11.glRotatef(45f, 0.4f, 1.0f, 0.1f);
             * GL11.glRotatef(RotateYaw, 1f, 1.0f, 1f)
            */
            //GL11.glTranslatef(0f, 0f, -4f);
            long startTime = System.currentTimeMillis();
            game.clearScreen();
            Camera.update(delta);
            try {
                game.render();
            } catch (IOException e) {
                System.out.println(e);
            }
            Display.update();
            Display.sync(FRAME_RATE);
            long endTime = System.currentTimeMillis();
            delta = (int)(endTime - startTime);
            //System.out.println(delta);
        }
        Display.destroy();
        System.exit(0);
    }
    private int VBOVertexHandle;
    private int VBOColorHandle;

    public Game() { }
    
    private void init() throws IOException {
        Camera.init();
        vertices = new ArrayList<Float>();
        colorVertices = new ArrayList<Float>();
        this.initialize3D();
        VBOVertexHandle = GL15.glGenBuffers();
        VBOColorHandle = GL15.glGenBuffers();
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
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer(24 * 3);
        VertexColorData.put(new float[] { 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1,1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1,1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1,1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1,1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1,1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, });
        VertexColorData.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,VertexPositionData,GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOColorHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,VertexColorData,GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
    }
    
    public void render() throws IOException {
        world.render(vertices,colorVertices);
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer(vertices.size());
        float[] floats = new float[vertices.size()];
        int i = 0;
        for (Float f : vertices) {
            floats[i] = Float.intBitsToFloat(Float.floatToIntBits(f));
            i++;
        }
        VertexPositionData.put(floats);
        VertexPositionData.flip();
        
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer(colorVertices.size());
        float[] colorFloats = new float[colorVertices.size()];
        int j = 0;
        for (Float f : colorVertices) {
            floats[j] = Float.intBitsToFloat(Float.floatToIntBits(f));
            j++;
        }
        VertexColorData.put(floats);
        VertexColorData.flip();
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertexPositionData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void clearScreen() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
    }

    public void initialize3D() {
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Allows 2D textures.
        GL11.glShadeModel(GL11.GL_SMOOTH); // Smoother textures.
        //GL11.glClearColor(0.4f,0.6f,1.0f,0.0f); // BG color. 6698FF
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
