package game;

import java.util.ArrayList;

public class Chunk {
    
    private Block[][][] blocks = new Block[16][16][64];
    
    public Chunk() {
        makeChunk();
    }
    
    public void makeChunk() {
        for(int i=0;i<16;i++) {
            for (int j=0;j<16;j++) {
                for (int k=0;k<64;k++) {
                    blocks[i][j][k] = Block.DIRT; 
               }
            }
        }
    }
    
    public ArrayList<Float> render(int dx, int dy) {
        ArrayList<Float> vertices = new ArrayList<Float>(); //[3*24*16*64*16];
        
        for(int i=0;i<16;i++) {
            for (int j=0;j<16;j++) {
                for (int k=0;k<64;k++) {
                    vertices.addAll(blocks[i][j][k].render(i+16*dx,j+16*dy,k));
                }
            }
        }
        return vertices;
    }
}
