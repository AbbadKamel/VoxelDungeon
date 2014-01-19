package game;

public class Chunk {
    Block[][][] blocks = new Block[16][16][64];
    
    public void makeChunk() {
        for(int i=0;i<16;i++) {
            for (int j=0;j<16;j++) {
                for (int k=0;k<32;k++) {
                    int type = (int)(Math.random()*2);
                    switch (type) {
                        case 0: blocks[i][j][k] = Block.DIRT;
                            break;
                        case 1: blocks[i][j][k] = Block.STONE;
                            break;
                        default:
                            break;
                    }
                }
                for (int k=32;k<64;k++)
                    blocks[i][j][k] = Block.BLANK;
            }
        }
    }
    
    public void render() {
        for(int i=0;i<16;i++) {
            for (int j=0;j<16;j++) {
                for (int k=0;k<64;k++) {
                    blocks[i][j][k].render(i*2,j*2,k*2);
                }
            }
        }
    }
}
