package game;

import game.resource.ResourceLibrary;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public enum Block {

    DIRT("dirt", false),
    STONE("stone", false),
    BLANK("blank", true);
    
    public Texture texture;
    public boolean isTransparent;
    
    Block(String textureName, boolean isTransparent) {
        this.texture = ResourceLibrary.getTextureByName(textureName);
        this.isTransparent = isTransparent;
    } 

    public void render(int x, int y, int z) {
        if (isTransparent)
            return;
        texture.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(0.0f,0.0f);
        GL11.glVertex3f(-1.0f+x,-1.0f+z,1.0f+y);
        GL11.glTexCoord2d(1.0f,0.0f);
        GL11.glVertex3f(1.0f+x,-1.0f+z,1.0f+y);
        GL11.glTexCoord2d(1.0f,1.0f);
        GL11.glVertex3f(1.0f+x,1.0f+z,1.0f+y);
        GL11.glTexCoord2d(0.0f,1.0f);
        GL11.glVertex3f(-1.0f+x,1.0f+z,1.0f+y);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(1.0f,0.0f);
        GL11.glVertex3f(-1.0f+x,-1.0f+z,-1.0f+y);
        GL11.glTexCoord2d(1.0f,1.0f);
        GL11.glVertex3f(-1.0f+x,1.0f+z,-1.0f+y);
        GL11.glTexCoord2d(0.0f,1.0f);
        GL11.glVertex3f(1.0f+x,1.0f+z,-1.0f+y);
        GL11.glTexCoord2d(0.0f,0.0f);
        GL11.glVertex3f(1.0f+x,-1.0f+z,-1.0f+y);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(0.0f,1.0f);
        GL11.glVertex3f(-1.0f+x,1.0f+z,-1.0f+y);
        GL11.glTexCoord2d(0.0f,0.0f);
        GL11.glVertex3f(-1.0f+x,1.0f+z,1.0f+y);
        GL11.glTexCoord2d(1.0f,0.0f);
        GL11.glVertex3f(1.0f+x,1.0f+z,1.0f+y);
        GL11.glTexCoord2d(1.0f,1.0f);
        GL11.glVertex3f(1.0f+x,1.0f+z,-1.0f+y);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(1.0f,1.0f);
        GL11.glVertex3f(-1.0f+x,-1.0f+z,-1.0f+y);
        GL11.glTexCoord2d(0.0f,1.0f);
        GL11.glVertex3f(1.0f+x,-1.0f+z,-1.0f+y);
        GL11.glTexCoord2d(0.0f,0.0f);
        GL11.glVertex3f(1.0f+x,-1.0f+z,1.0f+y);
        GL11.glTexCoord2d(1.0f,0.0f);
        GL11.glVertex3f(-1.0f+x,-1.0f+z,1.0f+y);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(1.0f,0.0f);
        GL11.glVertex3f(1.0f+x,-1.0f+z,-1.0f+y);
        GL11.glTexCoord2d(1.0f,1.0f);
        GL11.glVertex3f(1.0f+x,1.0f+z,-1.0f+y);
        GL11.glTexCoord2d(0.0f,1.0f);
        GL11.glVertex3f(1.0f+x,1.0f+z,1.0f+y);
        GL11.glTexCoord2d(0.0f,0.0f);
        GL11.glVertex3f(1.0f+x,-1.0f+z,1.0f+y);
        GL11.glEnd();


        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(0.0f,0.0f);
        GL11.glVertex3f(-1.0f+x,-1.0f+z,-1.0f+y);
        GL11.glTexCoord2d(1.0f,0.0f);
        GL11.glVertex3f(-1.0f+x,-1.0f+z,1.0f+y);
        GL11.glTexCoord2d(1.0f,1.0f);
        GL11.glVertex3f(-1.0f+x,1.0f+z,1.0f+y);
        GL11.glTexCoord2d(0.0f,1.0f);
        GL11.glVertex3f(-1.0f+x,1.0f+z,-1.0f+y);
        GL11.glEnd();
    }
}
