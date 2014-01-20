package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private static Vector3f vector = new Vector3f();
    private static Vector3f rotation = new Vector3f();
    private static boolean moveForward = false;
    private static boolean moveBackward = false;
    private static boolean strafeLeft = false;
    private static boolean strafeRight = false;
    private static boolean moveUp = false;
    private static boolean moveDown = false;
    private static final float speed = 0.3f;
    
    public static float getCamX() { return vector.x; }
    public static float getCamY() { return vector.z; }
    public static float getCamZ() { return vector.y; }

    public static void init() {
        Mouse.setGrabbed(true);
    }

    public static void update(int delta) {
        input();
        updateVector(delta);
    }

    public static void updateVector(int delta) {
        double speedMultiplier = delta/20.0;
        if (moveForward) {
            vector.x -= (float) (Math.sin(-rotation.y*Math.PI/180)*speed)*speedMultiplier;
            vector.z -= (float) (Math.cos(-rotation.y*Math.PI/180)*speed)*speedMultiplier;
        }
        if (moveBackward) {
            vector.x += (float) (Math.sin(-rotation.y*Math.PI/180)*speed)*speedMultiplier;
            vector.z += (float) (Math.cos(-rotation.y*Math.PI/180)*speed)*speedMultiplier;
        }
        if (strafeLeft) {
            vector.x += (float) (Math.sin((-rotation.y-90)*Math.PI/180)*speed)*speedMultiplier;
            vector.z += (float) (Math.cos((-rotation.y-90)*Math.PI/180)*speed)*speedMultiplier;
        }
        if (strafeRight) {
            vector.x += (float) (Math.sin((-rotation.y + 90)*Math.PI/180) * speed)*speedMultiplier;
            vector.z += (float) (Math.cos((-rotation.y + 90)*Math.PI/180) * speed)*speedMultiplier;
        }
        if (moveUp) {
            vector.y += (float) (speed)*speedMultiplier;
        }
        if (moveDown) {
            vector.y -= (float) (speed)*speedMultiplier;
        }
        translatePosition();
    }

    public static void translatePosition() {
        // This is the code that changes 3D perspective to the camera's perspective.
        GL11.glRotatef(rotation.x, 1, 0, 0);
        GL11.glRotatef(rotation.y, 0, 1, 0);
        GL11.glRotatef(rotation.z, 0, 0, 1);
        
        // You're 2 units tall.
        GL11.glTranslatef(-vector.x, -vector.y - 2.0f, -vector.z);
    }

    public static void input() {
        //Keyboard Input for Movement
        moveForward = Keyboard.isKeyDown(Keyboard.KEY_W);
        moveBackward = Keyboard.isKeyDown(Keyboard.KEY_S);
        strafeLeft = Keyboard.isKeyDown(Keyboard.KEY_A);
        strafeRight = Keyboard.isKeyDown(Keyboard.KEY_D);
        moveUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        moveDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

        //Mouse Input for looking around...
        if (Mouse.isGrabbed()) {
            float mouseDX = Mouse.getDX()*0.8f*0.16f;
            float mouseDY = Mouse.getDY()*0.8f*0.16f;
            if (rotation.y + mouseDX >= 360) {
                rotation.y = rotation.y + mouseDX - 360;
            } else if (rotation.y + mouseDX < 0) {
                rotation.y = 360 - rotation.y + mouseDX;
            } else {
                rotation.y += mouseDX;
            }
            if (rotation.x - mouseDY >= -89 && rotation.x - mouseDY <= 89) {
                rotation.x += -mouseDY;
            } else if (rotation.x - mouseDY < -89) {
                rotation.x = -89;
            } else if (rotation.x - mouseDY > 89) {
                rotation.x = 89;
            }
        }
    }
}