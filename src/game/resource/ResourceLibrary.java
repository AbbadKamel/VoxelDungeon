package game.resource;

import java.io.IOException;
import org.newdawn.slick.opengl.Texture;

public class ResourceLibrary {
    
    private static Texture dirt = null;
    private static Texture stone_top = null;
    private static Texture stone_side = null;
    private static Texture grass_side = null;
    private static Texture grass_top = null;
    
    public static Texture getDirt() { return dirt; }
    public static Texture getStone_top() { return stone_top; }
    public static Texture getGrass_side() { return grass_side; }
    public static Texture getGrass_top() { return grass_top; }
    
    public static void init() throws IOException {
        dirt = Loader.createTexture("dirt");
        stone_top = Loader.createTexture("stone_top");
        stone_side = Loader.createTexture("stone_side");
        grass_side = Loader.createTexture("grass_side");
        grass_top = Loader.createTexture("grass_top");
    }

    public static Texture getTextureByName(String name) throws IOException {
        switch (name) {
            case "dirt":
                return dirt;
            case "stone_side":
                return stone_side;
            case "stone_top":
                return stone_top;
            case "grass_side":
                return grass_side;
            case "grass_top":
                return grass_top;
            case "blank":
                return null;
            default:
                throw new IOException("Unhandled case @ getTextureByName().");
        }
    }
}