package game.util;

public class Vector {
    
    private float x;
    private float y;
    private float z;
    
    public float x() { return x; }
    public float y() { return y; }
    public float z() { return z; }
    
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }
    
    public Vector() {
        this(0,0,0);
    }
    
    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector add(Vector v1, Vector v2) {
        return new Vector(v1.x+v2.x,v1.y+v2.y,v1.z+v2.z);
    }
}