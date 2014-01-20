package game.resource;

import java.io.IOException;
import org.newdawn.slick.opengl.Texture;

public class ResourceLibrary {
    
    private static Texture dirt = null;
    private static Texture stone_top = null;
    private static Texture stone_side = null;
    private static Texture grass_side = null;
    private static Texture grass_top = null;
    
    public static final byte STONE_SIDE = 1;
    public static final byte STONE_TOP = 2;
    public static final byte GRASS_SIDE = 3;
    public static final byte GRASS_TOP = 4;
    public static final byte DIRT = 5;

    public static void init() throws IOException {
        dirt = Loader.createTexture("dirt");
        stone_top = Loader.createTexture("stone_top");
        stone_side = Loader.createTexture("stone_side");
        grass_side = Loader.createTexture("grass_side");
        grass_top = Loader.createTexture("grass_top");
    }

    public static Texture getTexture(byte id) throws IOException {
        switch (id) {
            case DIRT:
                return dirt;
            case STONE_SIDE:
                return stone_side;
            case STONE_TOP:
                return stone_top;
            case GRASS_SIDE:
                return grass_side;
            case GRASS_TOP:
                return grass_top;
            default:
                throw new IOException("Unhandled case @ getTextureByName().");
        }
    }
}