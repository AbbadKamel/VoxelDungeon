package game.world;

import game.util.FloatArray;
import java.io.IOException;

public class Chunk {
    
    private static final int SIZE = 16;
    private static final int HEIGHT = 48;
    private static final int BEDROCK_HEIGHT = 16;
    
    private World world;
    
    private Block[][][] blocks = new Block[SIZE][SIZE][HEIGHT];

    public final int px;
    public final int py;
    
    public Chunk(int px, int py, World world) throws IOException {
        this.px = px;
        this.py = py;
        this.world = world;
        makeChunk();
    }
    
    public Block getBlock(int x, int y, int z) {
        return blocks[x][y][z];
    }
    
    public int getHeight(int x, int y) {
        int heightCounter = 0;
        while(!blocks[x][y][heightCounter].isTransparent()) {
            heightCounter++;
        }
        return heightCounter;
    }
    
    public void makeChunk() throws IOException {
        int [][] height = new int[SIZE][SIZE];
        int [][] iHeight = new int[SIZE-8][SIZE-8];
        int r = (int) (Math.random()*7);
        
        if (r==2) {
            generateCaves();
            return;
        }
        
        if(r==0 || r==3 || r==4) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    double[][] v = new double [4][4];
                    for (int l = 0; l < 4; l++) {
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
                    height[i][j] = (int) (avg+1);
                    
                    int random = 3;
                    
                    if (i<8 && j<8)
                    {
                        random = (int) (2*Math.random()+2);
                        iHeight[i][j] = random;
                    }
                }
            }
        } else if(r==1 || r==5 || r==6) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    double x = Math.abs(i-7);
                    double y = Math.abs(j-7);
                    double avg = ((1/(x+0.25)) + (1/(y+0.25)))/2;
                    if (x<1 && y<1) {
                        height[i][j] = (int) (12*Math.random()+1);
                    } else if (x<2 && y<2) {
                        height[i][j] = (int) (11*Math.random()+1);
                    } else if (x<3 && y<3) {
                        height[i][j] = (int) (10*Math.random()+1);
                    } else if (x<4 && y<4) {
                        height[i][j] = (int) (8*Math.random()+1);
                    } else if (x<5 && y<5) {
                        height[i][j] = (int) (5*Math.random()+1);
                    } else if (x<6 && y<6) {
                        height[i][j] = (int) (3*Math.random()+1);
                    } else if (x<7 && y<7) {
                        height[i][j] = (int) (2*Math.random()+1);
                    } else                 {
                        height[i][j] = (int) (1*Math.random()+1);
                    }
                }   
            }
        }
        
        int rand = (int) (2*Math.random());
        
        for(int i=0;i<SIZE;i++) {
            for (int j=0;j<SIZE;j++) {
                for (int k=0;k<BEDROCK_HEIGHT+height[i][j];k++) {
                    if(k < BEDROCK_HEIGHT)
                        setBlock(i,j,k,new Block(Block.STONE));
                    else
                        setBlock(i,j,k,new Block(Block.GRASS));
                }
                for (int k=HEIGHT-1;k>=(height[i][j]+BEDROCK_HEIGHT);k--) {
                    if(rand == 0) {
                        if(i>3 && i<12 && j>3 && j<12 && 
                                k > (height[i][j]+BEDROCK_HEIGHT+4) && 
                                k < (height[i][j]+BEDROCK_HEIGHT+4+iHeight[i-4][j-4])) {
                            setBlock(i,j,k,new Block(Block.GRASS));
                        } else {
                            setBlock(i,j,k,new Block(Block.BLANK));
                        }
                    } else {
                        setBlock(i,j,k,new Block(Block.BLANK));
                    }
                }
            }
        }
    }
    
    private void generateCaves() throws IOException {
        double chanceOfSpace = 0.55;
        boolean[][][] emptySpace = new boolean[16][16][16];
        for(int i=0;i<16;i++) {
            for (int j=0;j<16;j++) {
                for (int k=0;k<16;k++) {
                    emptySpace[i][j][k] = Math.random() < chanceOfSpace;
                }
            }
        }

        int smoothTimes = 7;
        boolean[][][] newEmptySpace = new boolean[16][16][16];
        
        int liveAmount = 13;
        for (int time=0;time<smoothTimes;time++) {
            for (int x=0;x<16;x++) {
                for (int y=0;y<16;y++) {
                    for (int z=0;z<16;z++) {
                        int air = 0;
                        int stone = 0;

                        for (int ox=-1;ox<2;ox++) {
                            for (int oy=-1;oy<2;oy++) {
                                for (int oz=-1;oz<2;oz++) {
                                    if (x+ox<0 || x+ox>=16 || y+oy<0 || y+oy>=16 || z+oz<0 || z+oz>=16)
                                        continue;
                                    if (emptySpace[x+ox][y+oy][z+oz])
                                        air++;
                                    else
                                        stone++;
                                }
                            }
                        }
                        if (stone >= liveAmount)
                            newEmptySpace[x][y][z] = false;
                        else
                            newEmptySpace[x][y][z] = stone != 0;
                        newEmptySpace[x][y][z] = stone >= air;
                    }
                }
            }
        }
        emptySpace = newEmptySpace;
        
        for(int i=0;i<SIZE;i++) {
            for (int j=0;j<SIZE;j++) {
                for (int k=0;k<HEIGHT;k++) {
                    if (k==0)
                        setBlock(i,j,k,new Block(Block.STONE));
                    else if(k<16)
                        if (emptySpace[i][j][k])
                            setBlock(i,j,k,new Block(Block.BLANK));
                        else
                            setBlock(i,j,k,new Block(Block.STONE));
                    else
                        setBlock(i,j,k,new Block(Block.BLANK));
                }
            }
        }
    }
    
    private void setBlock(int i, int j, int k, Block block) {
        block.setMyChunk(this);
        blocks[i][j][k] = block;
    }
    
    public void render(FloatArray vertices, FloatArray colorVertices) throws IOException {
        for(int i=0;i<SIZE;i++)
            for (int j=0;j<SIZE;j++)
                for (int k=HEIGHT-1;k>=0;k--)
                    blocks[i][j][k].render(i+16*px,j+16*py,k,vertices,colorVertices);
    }
    
    public boolean isBlock(int x, int y, int z) {
        x -= px*16;
        y -= py*16;
        if (x<0&&px==0 || y<0&&py==0 || z<0 || x>15&&px>=world.getWidth()-1 || y>15&&py>=world.getWidth()-1 || z>HEIGHT-1) {
            return false;
        }
        if (x<0) {
            return !world.getChunk(px-1,py).getBlock(15,y,z).isTransparent();
        } else if (y<0) {
            return !world.getChunk(px,py-1).getBlock(x,15,z).isTransparent();
        } else if (x>15) {
            return !world.getChunk(px+1,py).getBlock(0,y,z).isTransparent();
        } else if (y>15) {
            return !world.getChunk(px,py+1).getBlock(x,0,z).isTransparent();
        }
        return !getBlock(x,y,z).isTransparent();
    }
}
