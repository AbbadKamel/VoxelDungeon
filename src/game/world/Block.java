package game.world;

import game.Camera;
import game.util.FloatArray;
import game.util.Frustum;

public class Block {
    
    public static final byte GRASS = 0;
    public static final byte STONE = 1;
    public static final byte AIR = 2;
    
    private boolean isTransparent;
    private Chunk chunk;
    private byte type;
    
    boolean zpxp;
    boolean zpyp;

    boolean zpxn;
    boolean zpyn;

    boolean znxp;
    boolean znyp;

    boolean znxn;
    boolean znyn;

    boolean zpxpyp;
    boolean zpxnyp;
    boolean zpxpyn;
    boolean zpxnyn;

    boolean znxpyp;
    boolean znxnyp;
    boolean znxpyn;
    boolean znxnyn;

    boolean xpyp;
    boolean xnyp;
    boolean xpyn;
    boolean xnyn;
    
    public boolean isTransparent() { return isTransparent; }
    
    public Block(byte blockName) {
        type = blockName;
        switch (blockName) {
            case GRASS:
                isTransparent = false;
                break;
            case STONE:
                isTransparent = false;
                break;
            case AIR:
                isTransparent = true;
                break;
            default:
                System.out.println("The program has a bug @Block:33");
                System.exit(-1);
        }
    }
    
    public void init(int x, int y, int z) {
        zpxp = chunk.isBlock(x+1,y,z+1);
        zpyp = chunk.isBlock(x,y+1,z+1);

        zpxn = chunk.isBlock(x-1,y,z+1);
        zpyn = chunk.isBlock(x,y-1,z+1);

        znxp = chunk.isBlock(x+1,y,z-1);
        znyp = chunk.isBlock(x,y+1,z-1);

        znxn = chunk.isBlock(x-1,y,z-1);
        znyn = chunk.isBlock(x,y-1,z-1);

        zpxpyp = chunk.isBlock(x+1,y+1,z+1);
        zpxnyp = chunk.isBlock(x-1,y+1,z+1);
        zpxpyn = chunk.isBlock(x+1,y-1,z+1);
        zpxnyn = chunk.isBlock(x-1,y-1,z+1);

        znxpyp = chunk.isBlock(x+1,y+1,z-1);
        znxnyp = chunk.isBlock(x-1,y+1,z-1);
        znxpyn = chunk.isBlock(x+1,y-1,z-1);
        znxnyn = chunk.isBlock(x-1,y-1,z-1);

        xpyp = chunk.isBlock(x+1,y+1,z);
        xnyp = chunk.isBlock(x-1,y+1,z);
        xpyn = chunk.isBlock(x+1,y-1,z);
        xnyn = chunk.isBlock(x-1,y-1,z);
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
    
    private void addVertex(float x, float y, float z, float r, float g, float b,
            FloatArray vertices, FloatArray colorVertices, float level) {
        r = r-level/8;
        g = g-level/8;
        b = b-level/8;
        addFloats(x,y,z,r,g,b,vertices,colorVertices);
    }
    
    public void render(int x, int y, int z, FloatArray vertices, FloatArray colorVertices) {
        if (isTransparent)
            return;
        if (Frustum.isSphereInFrustum(x,y,z,0.86602540379f))
            renderNoChecks(x,y,z,vertices,colorVertices);
    }
    
    public void renderNoFrustumCheck(int x, int y, int z, FloatArray vertices, FloatArray colorVertices) {
        if (!isTransparent)
            renderNoChecks(x,y,z,vertices,colorVertices);
    }
    
    private int getAO(boolean side1, boolean side2, boolean corner) {
        if(side1 && side2) {
            return 3;
        }
        return (side1?1:0) + (side2?1:0) + (corner?1:0);
    }
    
    private void renderNoChecks(int x, int y, int z, FloatArray vertices, FloatArray colorVertices) {
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
                
        // Z Positive.
        if (Camera.getCamZ()>z && !chunk.isBlock(x,y,z+1)) {
            addVertex(-0.5f+x,0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices,getAO(zpxn,zpyn,zpxnyn));
            addVertex(-0.5f+x,0.5f+z,0.5f+y,r,g,b,vertices,colorVertices,getAO(zpxn,zpyp,zpxnyp));
            addVertex(0.5f+x,0.5f+z,0.5f+y,r,g,b,vertices,colorVertices,getAO(zpxp,zpyp,zpxpyp));
            addVertex(0.5f+x,0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices,getAO(zpxp,zpyn,zpxpyn));
        }
        
        // Y positive
        if (Camera.getCamY()>y && !chunk.isBlock(x,y+1,z)) {
            addVertex(-0.5f+x,-0.5f+z,0.5f+y,r,g,b,vertices,colorVertices,getAO(znxn,znyp,znxnyp));
            addVertex(0.5f+x,-0.5f+z,0.5f+y,r,g,b,vertices,colorVertices,getAO(znxp,znyp,znxpyp));
            addVertex(0.5f+x,0.5f+z,0.5f+y,r,g,b,vertices,colorVertices,getAO(zpxp,zpyp,zpxpyp));
            addVertex(-0.5f+x,0.5f+z,0.5f+y,r,g,b,vertices,colorVertices,getAO(zpxn,zpyp,zpxnyp));
        }
        
        // Y negative
        if (Camera.getCamY()<y && !chunk.isBlock(x,y-1,z)) {
            addVertex(-0.5f+x,-0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices,getAO(znxn,znyn,znxnyn));
            addVertex(-0.5f+x,0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices,getAO(zpxn,zpyn,zpxnyn));
            addVertex(0.5f+x,0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices,getAO(zpxp,zpyn,zpxpyn));
            addVertex(0.5f+x,-0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices,getAO(znxp,znyn,znxpyn));
        }
        
        // X Positive.
        if (Camera.getCamX()>x && !chunk.isBlock(x+1,y,z)) {
            addVertex(0.5f+x,-0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices,getAO(znxp,znyn,znxpyn));
            addVertex(0.5f+x,0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices,getAO(zpxp,zpyn,zpxpyn));
            addVertex(0.5f+x,0.5f+z,0.5f+y,r,g,b,vertices,colorVertices,getAO(zpxp,zpyp,zpxpyp));
            addVertex(0.5f+x,-0.5f+z,0.5f+y,r,g,b,vertices,colorVertices,getAO(znxp,znyp,znxpyp));
        }
        
        // X Negative.
        if (Camera.getCamX()<x && !chunk.isBlock(x-1,y,z)) {
            addVertex(-0.5f+x,-0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices,getAO(znxn,znyn,znxnyn));
            addVertex(-0.5f+x,-0.5f+z,0.5f+y,r,g,b,vertices,colorVertices,getAO(znxn,znyp,znxnyp));
            addVertex(-0.5f+x,0.5f+z,0.5f+y,r,g,b,vertices,colorVertices,getAO(zpxn,zpyp,zpxnyp));
            addVertex(-0.5f+x,0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices,getAO(zpxn,zpyn,zpxnyn));
        }
        
        // Z Negative.
        if (Camera.getCamZ()<z && !chunk.isBlock(x,y,z-1)) {
            addVertex(-0.5f+x,-0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices,getAO(znxn,znyn,znxnyn));
            addVertex(0.5f+x,-0.5f+z,-0.5f+y,r,g,b,vertices,colorVertices,getAO(znxp,znyn,znxpyn));
            addVertex(0.5f+x,-0.5f+z,0.5f+y,r,g,b,vertices,colorVertices,getAO(znxp,znyp,znxpyp));
            addVertex(-0.5f+x,-0.5f+z,0.5f+y,r,g,b,vertices,colorVertices,getAO(znxn,znyp,znxnyp));
        }
    }
}
