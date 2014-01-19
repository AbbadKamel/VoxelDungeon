package game;

import game.resource.ResourceLibrary;
import java.util.ArrayList;
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

    public ArrayList<Float> render(int x, int y, int z) {
        float[] coords = new float[] {
            0.5f+x, 0.5f+z, -0.5f+y,
            -0.5f+x, 0.5f+z, -0.5f+y,
            -0.5f+x, 0.5f+z, 0.5f+y,
            0.5f+x, 0.5f+z, 0.5f+y,

            0.5f+x, -0.5f+z, 0.5f+y,
            -0.5f+x, -0.5f+z, 0.5f+y,
            -0.5f+x, -0.5f+z, -0.5f+y,
            0.5f+x, -0.5f+z, -0.5f+y,

            0.5f+x, 0.5f+z, 0.5f+y,
            -0.5f+x, 0.5f+z, 0.5f+y,
            -0.5f+x, -0.5f+z, 0.5f+y,
            0.5f+x, -0.5f+z, 0.5f+y,

            0.5f+x, -0.5f+z, -0.5f+y,
            -0.5f+x, -0.5f+z, -0.5f+y,
            -0.5f+x, 0.5f+z, -0.5f+y,
            0.5f+x, 0.5f+z, -0.5f+y,

            -0.5f+x, 0.5f+z, 0.5f+y,
            -0.5f+x, 0.5f+z, -0.5f+y,
            -0.5f+x, -0.5f+z, -0.5f+y,
            -0.5f+x, -0.5f+z, 0.5f+y,

            0.5f+x, 0.5f+z, -0.5f+y,
            0.5f+x, 0.5f+z, 0.5f+y,
            0.5f+x, -0.5f+z, 0.5f+y,
            0.5f+x, -0.5f+z, -0.5f+y
        };
        ArrayList<Float> coordsList = new ArrayList<Float>(coords.length);
        for (float f : coords)
            coordsList.add(Float.intBitsToFloat(Float.floatToRawIntBits(f)));
        return coordsList;
        /*
        texture.bind();
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
        */
    }
}
