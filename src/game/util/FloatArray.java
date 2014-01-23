package game.util;

import java.util.Arrays;

public class FloatArray {
    
    private float[] arr;
    public int pos;
    
    public FloatArray(int len) {
        this.pos = 0;
        this.arr = new float[len];
    }
    
    public float[] getArr() {
        return arr;
    }
    
    public int size() {
        return arr.length;
    }
    
    public int getPos() {
        return pos;
    }
    
    public void add(float element) {
        if (pos>=arr.length)
            throw new ArrayIndexOutOfBoundsException("Attempting to write to position "
                    + pos + " of " + arr.length + ".");
        addNoCheck(element);
    }
    
    private void addNoCheck(float element) {
        arr[pos] = element;
        pos++;
    }
    
    public float[] getValues() {
        return Arrays.copyOf(arr,pos);
    }
    
    public void addAll(FloatArray other) {
        for (int i=0;i<other.pos;i++) {
            addNoCheck(other.element(i));
        }
    }
    
    public void clear() {
        arr = new float[arr.length];
        pos = 0;
    }

    public float element(int pos) {
        return arr[pos];
    }
}