package game.util;

import java.util.Random;

/** See http://mrl.nyu.edu/~perlin/noise/ */
public class Perlin {
    
    private int random[];
    private int[][] data;
        
    public Perlin(int width, int height) {
        random = new int[512];
        Random r = new Random();
        for (int i=0;i<512;i++) {
            random[i] = r.nextInt(256);
        }
        
        data = new int[width][height];
        for (int i=0;i<width;i++) {
            for (int j=0;j<height;j++) {
                data[i][j] = (int) (255*(noise(10.0*i/width,10.0*j/height,0)-1)/(2));
            }
        }
        
        double minValue = 0;
        double maxValue = 0;
        
        for (int i=0;i<width;i++) {
            for (int j=0;j<height;j++) {
                minValue = Math.min(data[i][j], minValue);
                maxValue = Math.max(data[i][j], maxValue);
            }
        }
                
        for (int i=0;i<width;i++) {
            for (int j=0;j<height;j++) {
                data[i][j] = (int) (255*(data[i][j]-minValue)/(maxValue-minValue));
            }
        }
    }
    
    public int getNoise(int x, int y) {
        return data[x][y];
    }
    
    private double noise(double x, double y, double z) {
        int X = (int) Math.floor(x) & 255;                                      // FIND UNIT CUBE THAT
        int Y = (int) Math.floor(y) & 255;                                      // CONTAINS POINT.
        int Z = (int) Math.floor(z) & 255;
        
        x -= Math.floor(x);                                                     // FIND RELATIVE X,Y,Z
        y -= Math.floor(y);                                                     // OF POINT IN CUBE.
        z -= Math.floor(z);
        
        double u = applyFunction(x);                                            // COMPUTE FADE CURVES
        double v = applyFunction(y);                                            // FOR EACH OF X,Y,Z.
        double w = applyFunction(z);
        
        int A = random[X] + Y, AA = random[A] + Z, AB = random[A + 1] + Z,      // HASH COORDINATES OF
                B = random[X + 1] + Y, BA = random[B] + Z, BB = random[B + 1] + Z; // THE 8 CUBE CORNERS,

        return lerp(w, lerp(v, lerp(u, grad(random[AA], x, y, z),               // AND ADD
                grad(random[BA], x - 1, y, z)),                                 // BLENDED
                lerp(u, grad(random[AB], x, y - 1, z),                          // RESULTS
                grad(random[BB], x - 1, y - 1, z))),                            // FROM  8
                lerp(v, lerp(u, grad(random[AA + 1], x, y, z - 1),              // CORNERS
                grad(random[BA + 1], x - 1, y, z - 1)),                         // OF CUBE
                lerp(u, grad(random[AB + 1], x, y - 1, z - 1),
                grad(random[BB + 1], x - 1, y - 1, z - 1))));
    }

    private static double applyFunction(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private static double grad(int hash, double x, double y, double z) {
        int h = hash & 15;                                                      // CONVERT LO 4 BITS OF HASH CODE
        double u = h < 8 ? x : y,                                               // INTO 12 GRADIENT DIRECTIONS.
                v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }
}