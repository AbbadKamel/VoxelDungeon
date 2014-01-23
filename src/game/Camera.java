package game;

import game.util.Vector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Camera {

    private static Vector vector = new Vector();
    private static Vector rotation = new Vector();
    private static final float speed = 0.015f;
    
    public static float getCamX() { return vector.x(); }
    public static float getCamY() { return vector.y(); }
    public static float getCamZ() { return vector.z(); }

    public static void init() {
        Mouse.setGrabbed(true);
    }
    
    public static void update(int delta) {
        updateRotation(delta);
        updatePosition(delta);
        updatePerspective();
    }

    public static void updatePosition(int delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            vector.setX(vector.x()-(float)(Math.sin(-rotation.z()*Math.PI/180)*speed*delta));
            vector.setY(vector.y()-(float)(Math.cos(-rotation.z()*Math.PI/180)*speed*delta));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            vector.setX(vector.x()+(float)(Math.sin(-rotation.z()*Math.PI/180)*speed*delta));
            vector.setY(vector.y()+(float)(Math.cos(-rotation.z()*Math.PI/180)*speed*delta));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            vector.setX(vector.x()+(float)(Math.sin((-rotation.z()-90)*Math.PI/180)*speed*delta));
            vector.setY(vector.y()+(float)(Math.cos((-rotation.z()-90)*Math.PI/180)*speed*delta));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            vector.setX(vector.x()+(float)(Math.sin((-rotation.z()+90)*Math.PI/180)*speed*delta));
            vector.setY(vector.y()+(float)(Math.cos((-rotation.z()+90)*Math.PI/180)*speed*delta));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
            vector.setZ(vector.z()+(float)(speed*delta));
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            vector.setZ(vector.z()-(float)(speed*delta));
    }

    public static void updateRotation(int delta) {
        //Mouse Input for looking around...
        if (Mouse.isGrabbed()) {
            float mouseDX = Mouse.getDX()*0.128f;
            float mouseDY = Mouse.getDY()*0.128f;
            
            if (rotation.z()+mouseDX>=360)
                rotation.setZ(rotation.z()+mouseDX-360);
            else if (rotation.z()+mouseDX<0)
                rotation.setZ(rotation.z()+mouseDX+360);
            else
                rotation.setZ(rotation.z()+mouseDX);
            
            if (rotation.x()-mouseDY>=-89&&rotation.x()-mouseDY<=89)
                rotation.setX(rotation.x()-mouseDY);
            else if (rotation.x()-mouseDY<-89)
                rotation.setX(-89);
            else if (rotation.x()-mouseDY>89)
                rotation.setX(89);
        }
    }
    
    public static void updatePerspective() {
        GL11.glRotatef(rotation.x(),1,0,0);
        GL11.glRotatef(rotation.y(),0,0,1);
        GL11.glRotatef(rotation.z(),0,1,0);
        GL11.glTranslatef(-vector.x(),-vector.z()-2.0f,-vector.y());
    }
}