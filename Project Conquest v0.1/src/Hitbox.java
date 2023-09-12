public class Hitbox {
    Location location;
    int width;
    int height;
    public Hitbox(Location loc, int width, int height){
        this.location = new Location(loc.x, loc.y);
        this.width = width;
        this.height = height;
    }

    /**
     * return true if two hitboxes are touching or overlapping
     * @param other Hitbox
     * @return true if touching
     */
    public boolean isTouching(Hitbox other){
        //if the right edge of the hitbox extends past the left of the other for both boxes, they are touching or overlapping
        if(this.location.x + this.width >= other.location.x && other.location.x + other.width >= this.location.x){
            //check the y component
            if(this.location.y + this.height >= other.location.y && other.location.y + other.height >= this.location.x){
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Check to see if a given Location falls within this Hitbox
     * @param loc the Location being checked
     * @return true if loc falls within this hitbox
     */
    public boolean isInHitbox(Location loc){
        if(loc.x >= location.x && loc.x <= location.x+width){
            if(loc.y >= location.y && loc.y <= location.y+height){
                return true;
            }
        }
        return false;
    }
}
