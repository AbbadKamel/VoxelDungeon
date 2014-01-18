/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

/**
 *
 * @author Masilan
 */
public class Chunk {
    Block[][][] b = new Block[16][16][64];
    double type;
    
    public void makeChunk() {
        for(int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 32; j++) {
                    type = (2*Math.random()) + 1;
                    switch ((int) type) {
                        case 1: b[i][j][k] = Block.CARPET;
                            break;
                        case 2: b[i][j][k] = Block.DIRT;
                            break;
                        case 3: b[i][j][k] = Block.STONE;
                            break;
                    }           
                }
                for (int k = 0; k < 64; j++) {
                    b[i][j][k] = Block.BLANK;
                }
            }
        }
    }
}
