package game.world;

import game.util.FloatArray;
import game.util.Frustum;
import java.io.IOException;

public class World {
    
    private static Chunk[][] chunks;
    
    public World(int x, int y) throws IOException {
        chunks = new Chunk[x][y];
        createWorld();
    }
    
    public Chunk getChunk(int i, int j) {
        if (i>=0 && j>=0 && i<chunks.length && j<chunks[0].length)
            return chunks[i][j];
        return null;
    }
    
    public int getHeight(int x, int y) {
        return chunks[x/16][y/16].getHeight(x%16,y%16);
    }
    
    public boolean isBlock(int x, int y, int z) {
        return chunks[x/16][y/16].isBlock(x,y,z);
    }
    
    public void createWorld() throws IOException {
        for (int i=0;i<chunks.length;i++)
            for (int j=0;j<chunks[0].length;j++)
                chunks[i][j] = new Chunk(i,j,this);
    }
    
    public void render(FloatArray vertices, FloatArray colorVertices) throws IOException {
        for (int i=0;i<chunks.length;i++)
            for (int j=0;j<chunks[0].length;j++)
                if (Frustum.isCubeInFrustum(i*16+8,j*16+8,8,8) || Frustum.isCubeInFrustum(i*16+8,j*16+8,24,8)
                         || Frustum.isCubeInFrustum(i*16+8,j*16+8,40,8))
                    chunks[i][j].render(vertices,colorVertices);
    }

    public int getWidth() {
        return chunks.length;
    }
}
