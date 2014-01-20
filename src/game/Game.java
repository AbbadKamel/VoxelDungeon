package game;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.glu.GLU;

public class Game {

    private int VBOVertexHandle;
    private int VBOColorHandle;
    
    public static void main(String[] args) throws LWJGLException {
        Game game = new Game();
        game.start();
    }
    
    public void start() {
        try {
            createWindow();
            init3D();
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    DisplayMode displayMode;

    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640 && d[i].getHeight() == 480
                    && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle("COPY-PASTED TUTORIAL");
        Display.create();
    }

    private void init3D() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f,0.0f,0.0f,0.0f);
        GL11.glClearDepth(1.0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        GLU.gluPerspective(45.0f,(float)displayMode.getWidth()/(float)displayMode.getHeight(),0.1f,300.0f);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT,GL11.GL_NICEST);
    }

    private void run() {
        initVBO();
        float RotateYaw = 0;
        while (!Display.isCloseRequested()) {
            try {
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                GL11.glLoadIdentity();

                GL11.glTranslatef((float) Math.sin(RotateYaw / 180 * Math.PI),0f,-4f);
                GL11.glRotatef(45f,0.4f,1.0f,0.1f);
                GL11.glRotatef(RotateYaw,1f,1.0f,1f);
                RotateYaw += 2;
                DrawVBO();
                Display.update();
                Display.sync(60);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        Display.destroy();

    }

    private void DrawVBO() {
        GL11.glPushMatrix();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOVertexHandle);
        GL11.glVertexPointer(3,GL11.GL_FLOAT,0,0L);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOColorHandle);
        GL11.glColorPointer(3,GL11.GL_FLOAT,0,0L);
        GL11.glDrawArrays(GL11.GL_QUADS,0,24);
        GL11.glPopMatrix();
    }

    private void initVBO() {
        VBOColorHandle = GL15.glGenBuffers();
        VBOVertexHandle = GL15.glGenBuffers();
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer(24*3);
        VertexPositionData.put(new float[]{
            1.0f,1.0f,-1.0f,
            -1.0f,1.0f,-1.0f,
            -1.0f,1.0f,1.0f,
            1.0f,1.0f,1.0f,
            1.0f,-1.0f,1.0f,
            -1.0f,-1.0f,1.0f,
            -1.0f,-1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f,1.0f,1.0f,
            -1.0f,1.0f,1.0f,
            -1.0f,-1.0f,1.0f,
            1.0f,-1.0f,1.0f,
            1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f,1.0f,-1.0f,
            1.0f,1.0f,-1.0f,
            -1.0f,1.0f,1.0f,
            -1.0f,1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,1.0f,
            1.0f,1.0f,-1.0f,
            1.0f,1.0f,1.0f,
            1.0f,-1.0f,1.0f,
            1.0f,-1.0f,-1.0f
        });
        VertexPositionData.flip();
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer(24*3);
        VertexColorData.put(new float[]{1,1,0,1,0,1,0,0,1,0,1,1,1,1,0,1,0,1,0,0,1,0,1,1,1,1,0,1,0,1,0,0,1,0,1,1,1,1,0,1,0,1,0,0,1,0,1,1,1,1,0,1,0,1,0,0,1,0,1,1,1,1,0,1,0,1,0,0,1,0,1,1,});
        VertexColorData.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,VertexPositionData,GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOColorHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,VertexColorData,GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
    }
}
