package game.resource;

import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.ResourceLoader;

public class Loader {
    
    public static Texture createTexture(String name) throws IOException {
        return TextureLoader.getTexture("PNG",
                ResourceLoader.getResourceAsStream("resources/" + name + ".png"),SGL.GL_NEAREST);
    }
}