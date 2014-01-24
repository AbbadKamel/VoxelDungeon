package game.world;

import game.Camera;
import game.util.FloatArray;
import game.util.Frustum;
import java.io.IOException;

public class Block {
    
    public static final byte GRASS = 0;
    public static final byte STONE = 1;
    public static final byte BLANK = 2;
    
    private boolean isTransparent;
    private Chunk chunk;
    private byte type;
    
    public boolean isTransparent() { return isTransparent; }
    
    public Block(byte blockName) throws IOException {
        type = blockName;
        switch (blockName) {
            case GRASS:
                isTransparent = false;
                break;
            case STONE:
                isTransparent = false;
                break;
            case BLANK:
                isTransparent = true;
                break;
            default:
                throw new IOException("Unhandled case @ Block constructor.");
        }
    }
    
    public void setMyChunk(Chunk chunk) {
        this.chunk = chunk;
    }
    
    private void addFloats(float x, float y, float z, float r, float g, float b,
            FloatArray vertices, FloatArray colorVertices) {
        vertices.add(x);
        vertices.add(y);
        vertices.add(z);
        colorVertices.add(r);
        colorVertices.add(g);
        colorVertices.add(b);
    }
    
    public void render(int x, int y, int z, FloatArray vertices, FloatArray colorVertices) {
        if (isTransparent)
            return;
        
        if (Frustum.isCubeInFrustum(x,y,z))
            return;
        
        float r = 0;
        float g = 0;
        float b = 0;
        
        if (type == GRASS) {
            r = 0.09f;
            g = 0.9f;
            b = 0.09f;
        } else if (type == STONE) {
            r = 0.4f;
            g = 0.4f;
            b = 0.4f;
        }
        
        // Top.
        if (Camera.getCamZ()>z && !chunk.isBlock(x,y,z+1)) {
            addFloats(-0.5f+x,0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(-0.5f+x,0.5f+z,0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(0.5f+x,0.5f+z,0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(0.5f+x,0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices);
        }
        
        if (type == GRASS) {
            r = 0.06f;
            g = 0.6f;
            b = 0.06f;
        } else if (type == STONE) {
            r = 0.2f;
            g = 0.2f;
            b = 0.2f;
        }
        
        // Side.
        if (Camera.getCamY()>y && !chunk.isBlock(x,y+1,z)) {
            addFloats(-0.5f+x,-0.5f+z,0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(0.5f+x,-0.5f+z,0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(0.5f+x,0.5f+z,0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(-0.5f+x,0.5f+z,0.5f+y,r,g,b,vertices,colorVertices);
        }
        
        // Opposite side to above.
        if (Camera.getCamY()<y && !chunk.isBlock(x,y-1,z)) {
            addFloats(-0.5f+x,-0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(-0.5f+x,0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(0.5f+x,0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(0.5f+x,-0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices);
        }
        
        // Side.
        if (Camera.getCamX()>x && !chunk.isBlock(x+1,y,z)) {
            addFloats(0.5f+x,-0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(0.5f+x,0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(0.5f+x,0.5f+z,0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(0.5f+x,-0.5f+z,0.5f+y,r,g,b,vertices,colorVertices);
        }
        
        // Opposite side to above.
        if (Camera.getCamX()<x && !chunk.isBlock(x-1,y,z)) {
            addFloats(-0.5f+x,-0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(-0.5f+x,-0.5f+z,0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(-0.5f+x,0.5f+z,0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(-0.5f+x,0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices);
        }
        
        // Bottom.
        if (Camera.getCamZ()<z && !chunk.isBlock(x,y,z-1)) {
            addFloats(-0.5f+x,-0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(0.5f+x,-0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(0.5f+x,-0.5f+z,0.5f+y,r,g,b,vertices,colorVertices);
            addFloats(-0.5f+x,-0.5f+z,0.5f+y,r,g,b,vertices,colorVertices);
        }
    }
}
