package game;

import game.resource.ResourceLibrary;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Block {

    //DIRT("dirt", false),
    //STONE("stone", false),
    //BLANK("blank", true);
    
    public static final byte DIRT = 0;
    public static final byte STONE = 1;
    public static final byte BLANK = 2;
    
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
    
    public void render(int x, int y, int z) {
        if (isTransparent)
            return;
        
        texture.bind();
        
        if (chunk.isBlock(x,y+1,z)) {
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
        
        if (chunk.isBlock(x+1,y,z+1)) {
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
        
        if (chunk.isBlock(x,y,z+1)) {
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
        
        if (chunk.isBlock(x,y+1,z+1)) {
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
        
        if (chunk.isBlock(x+1,y+1,z)) {
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
        
        if (chunk.isBlock(x+1,y,z+1)) {
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
