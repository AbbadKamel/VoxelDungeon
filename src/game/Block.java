package game;

public enum Block {

DIRT("dirt", false),
    STONE("stone", false),
    CARPET("carpet", false),
    BLANK("blank", true);
    
    public String texture;
    public boolean isTransparent;
    Block(String texture, boolean isTransparent) {
        this.texture = texture;
        this.isTransparent = isTransparent;
    } 
}
