package game;

import game.resource.ResourceLibrary;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Block {
    
    public static final byte DIRT = 0;
    public static final byte STONE = 1;
    public static final byte GRASS_SIDE = 2;
    public static final byte GRASS_TOP = 3;
    public static final byte BLANK = 4;
    
    public Texture texture;
    public boolean isTransparent;
    private Chunk chunk;
    
    public Block(byte blockName) throws IOException {
        boolean transparent;
        String textureName;
        switch (blockName) {
            case DIRT:
                transparent = false;
                textureName = "dirt";
                break;
            case STONE:
                transparent = false;
                textureName = "stone";
                break;
            case GRASS_SIDE:
                transparent = false;
                textureName = "grass_side";
                break;
            case GRASS_TOP:
                transparent = false;
                textureName = "grass_top";
                break;
            case BLANK:
                transparent = true;
                textureName = "blank";
                break;
            default:
                throw new IOException("Unhandled case @ Block constructor.");
        }
        
        this.texture = ResourceLibrary.getTextureByName(textureName);
        this.isTransparent = transparent;
        
    } 
    
    public void setMyChunk(Chunk chunk) {
        this.chunk = chunk;
    }
    
    public void render(int x, int y, int z) throws IOException {
        if (isTransparent)
            return;
        
        if (texture == ResourceLibrary.getTextureByName("dirt"))
        {
            this.texture = ResourceLibrary.getTextureByName("Grass_side");
        }
        texture.bind();
        // Side.
        if (!chunk.isBlock(x,y+1,z)) {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2d(0.0f,0.0f);
            GL11.glVertex3f(-0.5f+x,-0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.5f,0.0f);
            GL11.glVertex3f(0.5f+x,-0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.5f,0.5f);
            GL11.glVertex3f(0.5f+x,0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.0f,0.5f);
            GL11.glVertex3f(-0.5f+x,0.5f+z,0.5f+y);
            GL11.glEnd();
        }
        
        
        // Opposite side to above.
        if (!chunk.isBlock(x,y-1,z)) {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2d(0.5f,0.0f);
            GL11.glVertex3f(-0.5f+x,-0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.5f,0.5f);
            GL11.glVertex3f(-0.5f+x,0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.0f,0.5f);
            GL11.glVertex3f(0.5f+x,0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.0f,0.0f);
            GL11.glVertex3f(0.5f+x,-0.5f+z,-0.5f+y);
            GL11.glEnd();
        }
        
        if (texture == ResourceLibrary.getTextureByName("Grass_side"))
        {
            this.texture = ResourceLibrary.getTextureByName("grass_top");
        }
        texture.bind();
        // Top.
        if (!chunk.isBlock(x,y,z+1)) {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2d(0.0f,0.5f);
            GL11.glVertex3f(-0.5f+x,0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.0f,0.0f);
            GL11.glVertex3f(-0.5f+x,0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.5f,0.0f);
            GL11.glVertex3f(0.5f+x,0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.5f,0.5f);
            GL11.glVertex3f(0.5f+x,0.5f+z,-0.5f+y);
            GL11.glEnd();
        }
        
        if (texture == ResourceLibrary.getTextureByName("grass_top"))
        {
            this.texture = ResourceLibrary.getTextureByName("dirt");
        }
        texture.bind();
        // Bottom.
        if (!chunk.isBlock(x,y,z-1)) {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2d(0.5f,0.5f);
            GL11.glVertex3f(-0.5f+x,-0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.0f,0.5f);
            GL11.glVertex3f(0.5f+x,-0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.0f,0.0f);
            GL11.glVertex3f(0.5f+x,-0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.5f,0.0f);
            GL11.glVertex3f(-0.5f+x,-0.5f+z,0.5f+y);
            GL11.glEnd();
        }
        
        if (texture == ResourceLibrary.getTextureByName("dirt"))
        {
            this.texture = ResourceLibrary.getTextureByName("Grass_side");
        }
        texture.bind();
        // Side.
        if (!chunk.isBlock(x+1,y,z)) {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2d(0.5f,0.0f);
            GL11.glVertex3f(0.5f+x,-0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.5f,0.5f);
            GL11.glVertex3f(0.5f+x,0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.0f,0.5f);
            GL11.glVertex3f(0.5f+x,0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.0f,0.0f);
            GL11.glVertex3f(0.5f+x,-0.5f+z,0.5f+y);
            GL11.glEnd();
        }
        
        // Opposite side to above.
        if (!chunk.isBlock(x-1,y,z)) {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2d(0.0f,0.0f);
            GL11.glVertex3f(-0.5f+x,-0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.5f,0.0f);
            GL11.glVertex3f(-0.5f+x,-0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.5f,0.5f);
            GL11.glVertex3f(-0.5f+x,0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.0f,0.5f);
            GL11.glVertex3f(-0.5f+x,0.5f+z,-0.5f+y);
            GL11.glEnd();
        }
    }
}
