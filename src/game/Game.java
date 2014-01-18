package game;

import game.resource.ResourceLibrary;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;

public class Game {

    private final static int width = 800;
    private final static int height = 600;
    private final static int FRAME_RATE = 60;
    private Camera camera;
    private Texture textureFloor;

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
        while (!Display.isCloseRequested()) {
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
        this.initialize3D();
        ResourceLibrary.init();
        textureFloor = ResourceLibrary.getDirt();
    }
    
    public void update() {
        camera.update();
    }
    
    public void render() {
        clearScreen();
        camera.translatePostion();

        textureFloor.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(0.0f,0.0f);
        GL11.glVertex3f(-1.0f,-1.0f,1.0f);
        GL11.glTexCoord2d(1.0f,0.0f);
        GL11.glVertex3f(1.0f,-1.0f,1.0f);
        GL11.glTexCoord2d(1.0f,1.0f);
        GL11.glVertex3f(1.0f,1.0f,1.0f);
        GL11.glTexCoord2d(0.0f,1.0f);
        GL11.glVertex3f(-1.0f,1.0f,1.0f);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(1.0f,0.0f);
        GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
        GL11.glTexCoord2d(1.0f,1.0f);
        GL11.glVertex3f(-1.0f,1.0f,-1.0f);
        GL11.glTexCoord2d(0.0f,1.0f);
        GL11.glVertex3f(1.0f,1.0f,-1.0f);
        GL11.glTexCoord2d(0.0f,0.0f);
        GL11.glVertex3f(1.0f,-1.0f,-1.0f);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(0.0f,1.0f);
        GL11.glVertex3f(-1.0f,1.0f,-1.0f);
        GL11.glTexCoord2d(0.0f,0.0f);
        GL11.glVertex3f(-1.0f,1.0f,1.0f);
        GL11.glTexCoord2d(1.0f,0.0f);
        GL11.glVertex3f(1.0f,1.0f,1.0f);
        GL11.glTexCoord2d(1.0f,1.0f);
        GL11.glVertex3f(1.0f,1.0f,-1.0f);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(1.0f,1.0f);
        GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
        GL11.glTexCoord2d(0.0f,1.0f);
        GL11.glVertex3f(1.0f,-1.0f,-1.0f);
        GL11.glTexCoord2d(0.0f,0.0f);
        GL11.glVertex3f(1.0f,-1.0f,1.0f);
        GL11.glTexCoord2d(1.0f,0.0f);
        GL11.glVertex3f(-1.0f,-1.0f,1.0f);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(1.0f,0.0f);
        GL11.glVertex3f(1.0f,-1.0f,-1.0f);
        GL11.glTexCoord2d(1.0f,1.0f);
        GL11.glVertex3f(1.0f,1.0f,-1.0f);
        GL11.glTexCoord2d(0.0f,1.0f);
        GL11.glVertex3f(1.0f,1.0f,1.0f);
        GL11.glTexCoord2d(0.0f,0.0f);
        GL11.glVertex3f(1.0f,-1.0f,1.0f);
        GL11.glEnd();


        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(0.0f,0.0f);
        GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
        GL11.glTexCoord2d(1.0f,0.0f);
        GL11.glVertex3f(-1.0f,-1.0f,1.0f);
        GL11.glTexCoord2d(1.0f,1.0f);
        GL11.glVertex3f(-1.0f,1.0f,1.0f);
        GL11.glTexCoord2d(0.0f,1.0f);
        GL11.glVertex3f(-1.0f,1.0f,-1.0f);
        GL11.glEnd();
    }

    public void clearScreen() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
    }

    public void initialize3D() {
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Allows 2D textures.
        GL11.glShadeModel(GL11.GL_SMOOTH); // Smoother textures.
        GL11.glClearColor(0.0f,0.0f,0.0f,0.0f); // BG color.
        GL11.glClearDepth(1.0); // Buffer depth,allows objects to draw over things behind them.
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Depth testing (see above).
        GL11.glDepthFunc(GL11.GL_LEQUAL); // Type of depth testing.
        GL11.glMatrixMode(GL11.GL_PROJECTION); // Sets matrix mode to displaying pixels.
        GL11.glLoadIdentity(); // Loads the above matrix mode.
        
        // Sets default perspective location.
        GLU.gluPerspective(45.0f,(float)Display.getWidth()/(float)Display.getHeight(),0.1f,100.0f);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Sets the matrix to displaying objects.
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT,GL11.GL_NICEST); // Something unimportant for quality.
    }
}
