package game.world;

import game.util.FloatArray;
import game.util.Perlin;
import java.io.IOException;

public class World {
    
    private static Chunk[][] chunks;
    private Perlin perlin;
    
    public World(int x, int y, Perlin perlin) {
        chunks = new Chunk[x][y];
        this.perlin = perlin;
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
    
    public void createWorld() {
        for (int i=0;i<chunks.length;i++)
            for (int j=0;j<chunks[0].length;j++)
                chunks[i][j] = new Chunk(i,j,this,perlin);
    }
    
    public void render(FloatArray vertices, FloatArray colorVertices) throws IOException {
        for (int i=0;i<chunks.length;i++) {
            for (int j=0;j<chunks[0].length;j++) {
                int points = chunks[i][j].inFrustum();
                if (points == 2) {
                    // Chunk is entirely within frustum.
                    chunks[i][j].renderNoCheck(vertices,colorVertices);
                } else if (points == 0) {
                    // Chunk is entirely outside frustum.
                } else {
                    // Chunk is partial.
                    chunks[i][j].render(vertices,colorVertices);
                }
            }
        }
    }

    public int getWidth() {
        return chunks.length;
    }
}
