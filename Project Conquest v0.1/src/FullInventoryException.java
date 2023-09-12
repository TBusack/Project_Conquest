/*
    Exception thrown if a given inventory is already full and an attempt is made to add an item
 */
public class FullInventoryException extends Exception{
    public FullInventoryException(){
        //do nothing
    }
}
