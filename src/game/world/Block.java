package game.world;

import game.resource.ResourceLibrary;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Block {
    
    public static final byte GRASS = 0;
    public static final byte STONE = 1;
    public static final byte BLANK = 2;
    
    private Texture textureTop;
    private Texture textureSide;
    private boolean isTransparent;
    private Chunk chunk;
    private byte type;
    
    public boolean isTransparent() { return isTransparent; }
    
    public Block(byte blockName) throws IOException {
        type = blockName;
        switch (blockName) {
            case GRASS:
                isTransparent = false;
                textureTop = ResourceLibrary.getTexture(ResourceLibrary.GRASS_TOP);
                textureSide = ResourceLibrary.getTexture(ResourceLibrary.GRASS_SIDE);
                break;
            case STONE:
                isTransparent = false;
                textureTop = ResourceLibrary.getTexture(ResourceLibrary.STONE_TOP);
                textureSide = ResourceLibrary.getTexture(ResourceLibrary.STONE_SIDE);
                break;
            case BLANK:
                isTransparent = true;
                textureTop = null;
                textureSide = null;
                break;
            default:
                throw new IOException("Unhandled case @ Block constructor.");
        }
    }
    
    public void setMyChunk(Chunk chunk) {
        this.chunk = chunk;
    }
    
    public void render(int x, int y, int z) throws IOException {
        if (isTransparent)
            return;
        
        textureSide.bind();
        GL11.glBegin(GL11.GL_QUADS);
        // Side.
        if (!chunk.isBlock(x,y+1,z)) {
            GL11.glTexCoord2d(0.0f,0.0f);
            GL11.glVertex3f(-0.5f+x,-0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.5f,0.0f);
            GL11.glVertex3f(0.5f+x,-0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.5f,0.5f);
            GL11.glVertex3f(0.5f+x,0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.0f,0.5f);
            GL11.glVertex3f(-0.5f+x,0.5f+z,0.5f+y);
        }
        
        // Opposite side to above.
        if (!chunk.isBlock(x,y-1,z)) {
            GL11.glTexCoord2d(0.5f,0.0f);
            GL11.glVertex3f(-0.5f+x,-0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.5f,0.5f);
            GL11.glVertex3f(-0.5f+x,0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.0f,0.5f);
            GL11.glVertex3f(0.5f+x,0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.0f,0.0f);
            GL11.glVertex3f(0.5f+x,-0.5f+z,-0.5f+y);
        }
        // Bottom.
        if (!chunk.isBlock(x,y,z-1)) {
            GL11.glTexCoord2d(0.5f,0.5f);
            GL11.glVertex3f(-0.5f+x,-0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.0f,0.5f);
            GL11.glVertex3f(0.5f+x,-0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.0f,0.0f);
            GL11.glVertex3f(0.5f+x,-0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.5f,0.0f);
            GL11.glVertex3f(-0.5f+x,-0.5f+z,0.5f+y);
        }
        // Side.
        if (!chunk.isBlock(x+1,y,z)) {
            GL11.glTexCoord2d(0.5f,0.0f);
            GL11.glVertex3f(0.5f+x,-0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.5f,0.5f);
            GL11.glVertex3f(0.5f+x,0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.0f,0.5f);
            GL11.glVertex3f(0.5f+x,0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.0f,0.0f);
            GL11.glVertex3f(0.5f+x,-0.5f+z,0.5f+y);
        }
        
        // Opposite side to above.
        if (!chunk.isBlock(x-1,y,z)) {
            GL11.glTexCoord2d(0.0f,0.0f);
            GL11.glVertex3f(-0.5f+x,-0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.5f,0.0f);
            GL11.glVertex3f(-0.5f+x,-0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.5f,0.5f);
            GL11.glVertex3f(-0.5f+x,0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.0f,0.5f);
            GL11.glVertex3f(-0.5f+x,0.5f+z,-0.5f+y);
        }
        GL11.glEnd();

        textureTop.bind();
        GL11.glBegin(GL11.GL_QUADS);
        // Top.
        if (!chunk.isBlock(x,y,z+1)) {
            GL11.glTexCoord2d(0.0f,0.5f);
            GL11.glVertex3f(-0.5f+x,0.5f+z,-0.5f+y);
            GL11.glTexCoord2d(0.0f,0.0f);
            GL11.glVertex3f(-0.5f+x,0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.5f,0.0f);
            GL11.glVertex3f(0.5f+x,0.5f+z,0.5f+y);
            GL11.glTexCoord2d(0.5f,0.5f);
            GL11.glVertex3f(0.5f+x,0.5f+z,-0.5f+y);
        }
        GL11.glEnd();
    }
}
