package game.world;

import game.util.FloatArray;
import game.util.Frustum;
import game.util.Perlin;
import java.io.IOException;

public class Chunk {
    
    private static final int SIZE = 16;
    private static final int HEIGHT = 48;
    private static final int BEDROCK_HEIGHT = 16;
    
    private World world;
    
    private Block[][][] blocks = new Block[SIZE][SIZE][HEIGHT];

    public final int px;
    public final int py;
    
    public Chunk(int px, int py, World world, Perlin perlin) {
        this.px = px;
        this.py = py;
        this.world = world;
        makeChunk(perlin);
    }
    
    public void init() {
        for(int i=0;i<SIZE;i++)
            for (int j=0;j<SIZE;j++)
                for (int k=HEIGHT-1;k>=0;k--)
                    blocks[i][j][k].init(i+16*px,j+16*py,k);
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
    
    public void makeChunk(Perlin perlin) {
        if (Math.random() < 0.5) {
            for(int i=0;i<SIZE;i++) {
                for (int j=0;j<SIZE;j++) {
                    int x = 16*px + i;
                    int y = 16*py + j;

                    int height = perlin.getNoise(x,y)/8+1;//(int)(Math.random()*2)+1;

                    for (int k=0;k<BEDROCK_HEIGHT;k++)
                        setBlock(i+16*px,j+16*py,k,new Block(Block.STONE));
                    for (int k=BEDROCK_HEIGHT;k<BEDROCK_HEIGHT+height;k++)
                        setBlock(i+16*px,j+16*py,k,new Block(Block.GRASS));
                    for (int k=BEDROCK_HEIGHT+height;k<HEIGHT;k++)
                        setBlock(i+16*px,j+16*py,k,new Block(Block.AIR));
                }
            }
        } else {
            generateCaves();
        }
    }
    
    private void generateCaves() {
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
                        setBlock(i+px*16,j+py*16,k,new Block(Block.STONE));
                    else if(k<16)
                        if (emptySpace[i][j][k])
                            setBlock(i+px*16,j+py*16,k,new Block(Block.AIR));
                        else
                            setBlock(i+px*16,j+py*16,k,new Block(Block.STONE));
                    else
                        setBlock(i+px*16,j+py*16,k,new Block(Block.AIR));
                }
            }
        }
    }
    
    private void setBlock(int i, int j, int k, Block block) {
        block.setMyChunk(this);
        blocks[i-16*px][j-16*py][k] = block;
    }
    
    public void render(FloatArray vertices, FloatArray colorVertices) throws IOException {
        for(int i=0;i<SIZE;i++)
            for (int j=0;j<SIZE;j++)
                for (int k=HEIGHT-1;k>=0;k--)
                    blocks[i][j][k].render(i+16*px,j+16*py,k,vertices,colorVertices);
    }
    
    public void renderNoCheck(FloatArray vertices, FloatArray colorVertices) throws IOException {
        for(int i=0;i<SIZE;i++)
            for (int j=0;j<SIZE;j++)
                for (int k=HEIGHT-1;k>=0;k--)
                    blocks[i][j][k].renderNoFrustumCheck(i+16*px,j+16*py,k,vertices,colorVertices);
    }
    
    public boolean isBlockNoCheck(int x, int y, int z) {
        return !blocks[x][y][z].isTransparent();
    }
    
    public boolean isBlock(int x, int y, int z) {
        x -= px*16;
        y -= py*16;
        
        if (x<0&&px<=0 || y<0&&py<=0 || z<0 || z>HEIGHT-1
                || x>SIZE-1&&px>=world.getWidth()-1 || y>SIZE-1&&py>=world.getWidth()-1)
            return false;
        
        try {
            return !blocks[x][y][z].isTransparent();
        } catch (ArrayIndexOutOfBoundsException e) {} // Squelch the exception
        
        if (x<0 && y<0) {
            return !world.getChunk(px-1,py-1).getBlock(15,15,z).isTransparent();
        } else if (x>15 && y<0) {
            return !world.getChunk(px+1,py-1).getBlock(0,15,z).isTransparent();
        } else if (x<0 && y>15) {
            return !world.getChunk(px-1,py+1).getBlock(15,0,z).isTransparent();
        } else if (x>15 && y>15) {
            return !world.getChunk(px+1,py+1).getBlock(0,0,z).isTransparent();
        } else if (x<0) {
            return !world.getChunk(px-1,py).getBlock(15,y,z).isTransparent();
        } else if (y<0) {
            return !world.getChunk(px,py-1).getBlock(x,15,z).isTransparent();
        } else if (x>15) {
            return !world.getChunk(px+1,py).getBlock(0,y,z).isTransparent();
        } else if (y>15) {
            return !world.getChunk(px,py+1).getBlock(x,0,z).isTransparent();
        }
        
        return false;
    }

    public int inFrustum() {
        return Frustum.sphereInFrustum(16*px+8,16*py+8,24,24);
    }
}
