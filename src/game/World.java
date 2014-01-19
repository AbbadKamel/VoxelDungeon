package game;

public class World {
    
    Chunk[][] chunks;
    
    public World(int x, int y) {
        chunks = new Chunk[x][y];
        createWorld();
    }
    
    public void createWorld() {
        for (int i=0;i<chunks.length;i++)
            for (int j=0;j<chunks[0].length;j++)
                chunks[i][j] = new Chunk(i,j);
    }
    
    public void render() {
        for (int i=0;i<chunks.length;i++)
            for (int j=0;j<chunks[0].length;j++)
                chunks[i][j].render();
    }
}
