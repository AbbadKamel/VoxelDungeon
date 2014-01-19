package game.resource;

import java.io.IOException;
import org.newdawn.slick.opengl.Texture;

public class ResourceLibrary {
    
    private static Texture dirt = null;
    private static Texture stone = null;
    private static Texture grass = null;
    
    public static Texture getDirt() { return dirt; }
    public static Texture getStone() { return stone; }
    public static Texture getGrass() { return grass; }
    
    public static void init() throws IOException {
        dirt = Loader.createTexture("dirt");
        stone = Loader.createTexture("stone");
        grass = Loader.createTexture("grass_top");
    }

    public static Texture getTextureByName(String name) throws IOException {
        switch (name) {
            case "dirt":
                return dirt;
            case "stone":
                return stone;
            case "grass":
                return grass;
            case "blank":
                return null;
            default:
                throw new IOException("Unhandled case @ getTextureByName().");
        }
    }
}