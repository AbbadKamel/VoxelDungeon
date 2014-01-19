package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f vector = new Vector3f();
    private Vector3f rotation = new Vector3f();
    private Vector3f vectorPrevious = new Vector3f();
    private static Vector3f cameraPos = new Vector3f();
    private boolean moveForward = false;
    private boolean moveBackward = false;
    private boolean strafeLeft = false;
    private boolean strafeRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;
    private static final float speed = 0.3f;
    private World world;
    private int dy = 0;
    
    public static float getCamX() { return cameraPos.x; }
    public static float getCamY() { return cameraPos.z; }
    public static float getCamZ() { return cameraPos.y; }

    public Camera(Game game, World world) {
        this.world = world;
        vector.x = 32;
        vector.y = 8;
        vector.z = 32;
        Mouse.setGrabbed(true);
    }

    public void update() {
        //System.out.println(cameraPos);
        updatePrevious();
        input();
        updateVector();
    }

    public void updateVector() {
        //System.out.println(world.getHeight(Math.round(vector.x),Math.round(vector.z)));
        //System.out.println(dy);
        if (vector.y-0.001f>world.getHeight(Math.round(vector.x),Math.round(vector.z))) {
            System.out.println("Fallin'");
            dy -= 0.1f;
        } else if (vector.y+0.001f<world.getHeight(Math.round(vector.x),Math.round(vector.z))) {
            vector.y = world.getHeight(Math.round(vector.x),Math.round(vector.z))-0.002f;
            //System.out.println("Standin'");
            if (moveUp) {
                //System.out.println("Jumpin'");
                dy += 5.0f;
            }
        }
        System.out.println(dy);
        vector.y += dy;
        if (moveForward) {
            vector.x -= (float) (Math.sin(-rotation.y*Math.PI/180)*speed);
            vector.z -= (float) (Math.cos(-rotation.y*Math.PI/180)*speed);
        }
        if (moveBackward) {
            vector.x += (float) (Math.sin(-rotation.y*Math.PI/180)*speed);
            vector.z += (float) (Math.cos(-rotation.y*Math.PI/180)*speed);
        }
        if (strafeLeft) {
            vector.x += (float) (Math.sin((-rotation.y-90)*Math.PI/180)*speed);
            vector.z += (float) (Math.cos((-rotation.y-90)*Math.PI/180)*speed);
        }
        if (strafeRight) {
            vector.x += (float) (Math.sin((-rotation.y + 90)*Math.PI/180) * speed);
            vector.z += (float) (Math.cos((-rotation.y + 90)*Math.PI/180) * speed);
        }
        if (moveDown) {
            vector.y -= (float) (speed);
        }
        System.out.println(vector);
    }

    public void translatePostion() {
        // This is the code that changes 3D perspective to the camera's perspective.
        GL11.glRotatef(rotation.x, 1, 0, 0);
        GL11.glRotatef(rotation.y, 0, 1, 0);
        GL11.glRotatef(rotation.z, 0, 0, 1);
        
        // 2.0 is your height.
        GL11.glTranslatef(-vector.x, -vector.y-2.0f, -vector.z);
    }

    public void updatePrevious() {
        vectorPrevious.x = vector.x;
        vectorPrevious.y = vector.y;
        vectorPrevious.z = vector.z;
        cameraPos.x = vector.x;
        cameraPos.y = vector.y;
        cameraPos.z = vector.z;
    }

    public void input() {
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