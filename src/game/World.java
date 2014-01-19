package game;

public class World {
    
    Chunk[][] chunks = new Chunk[16][16];
    
    public void createWorld() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                chunks[i][j] = new Chunk();
            }
        }
    }
    
    public void render() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                chunks[i][j].render();
            }
        }
    }
}
