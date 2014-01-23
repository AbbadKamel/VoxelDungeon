package game.world;

import game.Camera;
import java.io.IOException;
import java.util.ArrayList;

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
    
    public void render(ArrayList<Float> vertices, ArrayList<Float> colorVertices) throws IOException {
        int i = (int) (Camera.getCamX()/16);
        int j = (int) (Camera.getCamY()/16);
        
        for (int q=0;q<chunks.length;q++)
            for (int qq=0;qq<chunks[0].length;qq++)
                chunks[q][qq].render(vertices,colorVertices);
    }

    public int getWidth() {
        return chunks.length;
    }
}
