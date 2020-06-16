package assignment2;

public class Shelf {
  
  protected int height;
  protected int availableLength;
  protected int totalLength;
  protected Box firstBox;
  protected Box lastBox;
  
  public Shelf(int height, int totalLength){
    this.height = height;
    this.availableLength = totalLength;
    this.totalLength = totalLength;
    this.firstBox = null;
    this.lastBox = null;
  }
  
  protected void clear(){
    availableLength = totalLength;
    firstBox = null;
    lastBox = null;
  }
  
  public String print(){
    String result = "( " + height + " - " + availableLength + " ) : ";
    Box b = firstBox;
    while(b != null){
      result += b.id + ", ";
      b = b.next;
    }
    result += "\n";
    return result;
  }
  
  /**
   * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
   * @param b
   */
  public void addBox(Box b){
    availableLength= availableLength-b.length;
    if(lastBox==null){
      firstBox= lastBox= b;
      b.previous=b.next=null;
    }else{
      lastBox.next =b;
      b.previous=lastBox;
      b.next=null;
      lastBox=b;
    }
  }
  
  /**
   * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
   * If not, do not do anything to the Shelf and return null.
   * @param identifier
   * @return
   */
  public Box removeBox(String identifier){
    Box returnBox=null;
    if(firstBox==null){
      return null;
    }
    Box currentBox=firstBox;
    if(currentBox.id.equals(identifier)){
      if(currentBox.next==null){
        returnBox= currentBox;
        availableLength= availableLength+returnBox.length;
        clear();
        return returnBox;
      }else{
        returnBox= currentBox;
        availableLength= availableLength+returnBox.length;
        firstBox=firstBox.next;
        firstBox.previous=null;
        if(firstBox.next==null){
          lastBox=firstBox;
        }
        returnBox.previous=returnBox.next=null;
        return returnBox;
      }
    }
    while (!currentBox.id.equals(identifier)){
      currentBox=currentBox.next;
      if(currentBox==null){
        return null;
      }else{
        if(currentBox.id.equals(identifier)){
          returnBox= currentBox;
          availableLength= availableLength+returnBox.length;
          if(currentBox==lastBox){
            currentBox.previous.next=null;
            lastBox=currentBox.previous;
          }else{
            currentBox.previous.next=currentBox.next;
            currentBox.next.previous=currentBox.previous;
          }
          returnBox.previous=returnBox.next=null;
          return returnBox;
          
        }   
      } 
    }
    return null;
  }
}
