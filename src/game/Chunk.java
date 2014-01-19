package game;

public class Chunk {
    Block[][][] blocks = new Block[16][16][64];
    
    public void makeChunk() {
        
        int [][] height = new int[16][16];
        
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                double[][] v = new double [4][4];
                for (int l = 0; l < 4; l++)
                {
                    int k = (int) (4*Math.random());
                    switch (k) {
                        case 0: v[l][0] = 1;
                            v[l][1] = 0;
                            break;
                        case 1: v[l][0] = 0;
                            v[l][1] = 1;
                            break;
                        case 2: v[l][0] = -1;
                            v[l][1] = 0;
                            break;
                        case 3: v[l][0] = 0;
                            v[l][1] = -1;
                            break;
                    }
                }
                
                double x = Math.random();
                double y = Math.random();

                v[0][2] = x;
                v[0][3] = y;
                v[1][2] = 1-x;
                v[1][3] = y;
                v[2][2] = x;
                v[2][3] = 1-y;
                v[3][2] = 1-x;
                v[3][3] = 1-y;
                
                double a, b, c, d;
                
                a = (v[0][0] * v[0][2]) + (v[0][1] * v[0][3]);
                b = (v[1][0] * v[1][2]) + (v[1][1] * v[1][3]);
                c = (v[2][0] * v[2][2]) + (v[2][1] * v[2][3]);
                d = (v[3][0] * v[3][2]) + (v[3][1] * v[3][3]);
                
                a = (3*a*a) - (2*a*a*a);
                b = (3*b*b) - (2*b*b*b);
                c = (3*c*c) - (2*c*c*c);
                d = (3*d*d) - (2*d*d*d);
                
                double avg = ((a+b+c+d)/4);
                height[i][j] = (int) avg;
            }
        }
        
        for(int i=0;i<16;i++) {
            for (int j=0;j<16;j++) {
                for (int k=0;k<8+height[i][j];k++) {
                    if(k < (8+height[i][j])-4) 
                        blocks[i][j][k] = Block.STONE;
                    else
                        blocks[i][j][k] = Block.DIRT;
                    
                }
                for (int k=64-1;k>=height[i][j]+8;k--)
                    blocks[i][j][k] = Block.BLANK;
            }
        }
    }
    
    public void render() {
        for(int i=0;i<16;i++) {
            for (int j=0;j<16;j++) {
                for (int k=0;k<64;k++) {
                    blocks[i][j][k].render(i,j,k);
                }
            }
        }
    }
}
