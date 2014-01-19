package game;

import java.util.ArrayList;

public class World {
    
    Chunk[][] chunks = new Chunk[16][16];
    
    public World() {
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                chunks[i][j] = new Chunk();
            }
        }
    }
    
    public Chunk getChunk(int x, int y) {
        return chunks[x][y];
    }
    
    public float[] getVertexPositions() {
        float[] vertices = new float[3*24*16*64*16];
        ArrayList<Float> verticesList = new ArrayList<Float>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                verticesList.addAll(chunks[i][j].render(i,j));
            }
        }
        int i = 0;
        for (Float f : verticesList) {
            vertices[i] = f;
        }
        return vertices;
    }
}
