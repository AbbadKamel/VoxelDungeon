package game;

import java.io.IOException;
import java.util.ArrayList;

public class World {
    
    private static int width;
    private static int length;
    private Chunk[][] chunks;
    
    public World(int x, int y) throws IOException {
        chunks = new Chunk[x][y];
        length = x;
        width = y;
        createWorld();
    }
    
    public void createWorld() throws IOException {
        for (int i=0;i<chunks.length;i++)
            for (int j=0;j<chunks[0].length;j++)
                chunks[i][j] = new Chunk(i,j);
    }
    
    public void render(ArrayList<Float> vertices, ArrayList<Float> colorVertices) {
        int i = (int) (Camera.getCamX()/16);
        int j = (int) (Camera.getCamY()/16);
                
        if (i<chunks.length && j<chunks[0].length && i>0 && j>0)
            chunks[i][j].render(vertices,colorVertices);

        if (i<chunks.length && (j+1)<chunks[0].length && i>0 && (j+1)>0)
            chunks[i][j+1].render(vertices,colorVertices);

        if (i<chunks.length && (j-1)<chunks[0].length && i>0 && (j-1)>0)
            chunks[i][j-1].render(vertices,colorVertices);

        if ((i+1)<chunks.length && j<chunks[0].length && (i+1)>0 && j>0)
            chunks[i+1][j].render(vertices,colorVertices);

        if ((i+1)<chunks.length && (j+1)<chunks[0].length && (i+1)>0 && (j+1)>0)
            chunks[i+1][j+1].render(vertices,colorVertices);

        if ((i+1)<chunks.length && (j-1)<chunks[0].length && (i+1)>0 && (j-1)>0)
            chunks[i+1][j-1].render(vertices,colorVertices);

        if ((i-1)<chunks.length && j<chunks[0].length && (i-1)>0 && j>0)
            chunks[i-1][j].render(vertices,colorVertices);

        if ((i-1)<chunks.length && (j+1)<chunks[0].length && (i-1)>0 && (j+1)>0)
            chunks[i-1][j+1].render(vertices,colorVertices);

        if ((i-1)<chunks.length && (j-1)<chunks[0].length && (i-1)>0 && (j-1)>0)
            chunks[i-1][j-1].render(vertices,colorVertices);
    }
}
