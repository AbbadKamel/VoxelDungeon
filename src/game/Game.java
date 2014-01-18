package game;

import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.ResourceLoader;

public class Game {

    private final static int width = 800;
    private final static int height = 600;
    private final static int FRAME_RATE = 60;
    private Camera camera;
    private Texture texureWhite;
    private Texture texureFloor;

    public static void main(String[] args) {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
        } catch (LWJGLException e) {
            System.out.println(e);
        }
        Game game = new Game();
        game.initialize3D();
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
        try {
            loadTextures();
        } catch (IOException e) {
            System.out.println("Failed to load textures.");
        }
    }

    private void loadTextures() throws IOException {
        texureWhite = TextureLoader.getTexture("JPG",
                ResourceLoader.getResourceAsStream("resources/white.jpg"),SGL.GL_NEAREST);
        texureFloor = TextureLoader.getTexture("JPG",
                ResourceLoader.getResourceAsStream("resources/floor.jpg"),SGL.GL_NEAREST);
    }

    public void render() {
        clearScreen();
        camera.translatePostion();

        texureFloor.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0,0);
        GL11.glVertex3f(0,0,0);

        GL11.glTexCoord2f(1,0);
        GL11.glVertex3f(10,0,0);

        GL11.glTexCoord2f(1,1);
        GL11.glVertex3f(10,0,10);

        GL11.glTexCoord2f(0,1);
        GL11.glVertex3f(0,0,10);
        GL11.glEnd();
    }

    public void update() {
        camera.update();
    }

    public void initialize3D() {
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Allows 2D textures.
        GL11.glShadeModel(GL11.GL_SMOOTH); // Smoother textures.
        GL11.glClearColor(0.0f,0.0f,0.0f,0.0f); // BG color.
        GL11.glClearDepth(1.0); // Buffer depth, allows objects to draw over things behind them.
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Depth testing (see above).
        GL11.glDepthFunc(GL11.GL_LEQUAL); // Type of depth testing.
        GL11.glMatrixMode(GL11.GL_PROJECTION); // Sets matrix mode to displaying pixels.
        GL11.glLoadIdentity(); // Loads the above matrix mode.
        
        // Sets default perspective location.
        GLU.gluPerspective(45.0f,(float)Display.getWidth()/(float)Display.getHeight(),0.1f,100.0f);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Sets the matrix to displaying objects.
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT,GL11.GL_NICEST); // Something unimportant for quality.
    }

    public void clearScreen() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
    }
}
