package game.resource;

import java.io.IOException;
import org.newdawn.slick.opengl.Texture;

public class ResourceLibrary {
    
    public static Texture dirt = null;
    public static Texture stone = null;
    
    public Texture getDirt() { return dirt; }
    
    public Texture getStone() { return stone; }
    
    public static void init() throws IOException {
        dirt = Loader.createTexture("dirt");
        stone = Loader.createTexture("stone");
    }
}