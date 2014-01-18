package game.resource;

import java.io.IOException;
import org.newdawn.slick.opengl.Texture;

public class ResourceLibrary {
    
    private static Texture dirt = null;
    private static Texture stone = null;
    
    public static Texture getDirt() { return dirt; }
    public static Texture getStone() { return stone; }
    
    public static void init() throws IOException {
        dirt = Loader.createTexture("dirt");
        stone = Loader.createTexture("stone");
    }

    public static Texture getTextureByName(String name) {
        switch (name) {
            case "dirt":
                return dirt;
            case "stone":
                return stone;
            default:
                return null;
        }
    }
}