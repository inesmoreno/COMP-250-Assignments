package assignment2;

public class Warehouse{
  
  protected Shelf[] storage;
  protected int nbShelves;
  public Box toShip;
  public UrgentBox toShipUrgently;
  static String problem = "problem encountered while performing the operation";
  static String noProblem = "operation was successfully carried out";
  
  public Warehouse(int n, int[] heights, int[] lengths){
    this.nbShelves = n;
    this.storage = new Shelf[n];
    for (int i = 0; i < n; i++){
      this.storage[i]= new Shelf(heights[i], lengths[i]);
    }
    this.toShip = null;
    this.toShipUrgently = null;
  }
  
  public String printShipping(){
    Box b = toShip;
    String result = "not urgent : ";
    while(b != null){
      result += b.id + ", ";
      b = b.next;
    }
    result += "\n" + "should be already gone : ";
    b = toShipUrgently;
    while(b != null){
      result += b.id + ", ";
      b = b.next;
    }
    result += "\n";
    return result;
  }
  
  public String print(){
    String result = "";
    for (int i = 0; i < nbShelves; i++){
      result += i + "-th shelf " + storage[i].print();
    }
    return result;
  }
  
  public void clear(){
    toShip = null;
    toShipUrgently = null;
    for (int i = 0; i < nbShelves ; i++){
      storage[i].clear();
    }
  }
  
  /**
   * initiate the merge sort algorithm
   */
  public void sort(){
    mergeSort(0, nbShelves -1);
  }
  
  /**
   * performs the induction step of the merge sort algorithm
   * @param start
   * @param end
   */
  protected void mergeSort(int start, int end){
    if (start<end){
      int mid = (start+end)/2;
      mergeSort(start, mid);
      mergeSort(mid+1, end);
      merge(start, mid, end);
      
    }
    
  }
  
  /**
   * performs the merge part of the merge sort algorithm
   * @param start
   * @param mid
   * @param end
   */
  protected void merge(int start, int mid, int end){
    Shelf[]helper= new Shelf[nbShelves];
    for(int i=start; i<=end; i++){
      if (storage[i].height<=0){
        throw new IllegalArgumentException ("You can't have a shelf with negative height");
      }
      if (storage[i].totalLength<=0){
        throw new IllegalArgumentException ("You can't have a shelf with negative length");
      }
      helper[i]=storage[i];
    }
    int i= start;
    int j= mid+1;
    int k= start;
    while(i<=mid && j<=end){
      if(helper[i].height <= helper[j].height){
        storage[k]= helper[i];
        i++;
      }else{
        storage[k]=helper[j];
        j++;
      }
      k++;
    }
    while(i<=mid){
      storage[k]=helper[i];
      k++;
      i++;
    }
  }
  
  /**
   * Adds a box is the smallest possible shelf where there is room available.
   * Here we assume that there is at least one shelf (i.e. nbShelves >0)
   * @param b
   * @return problem or noProblem
   */
  public String addBox (Box b){
    int i=0;
    while(i<nbShelves){
      if(storage[i].height>=b.height && storage[i].availableLength>=b.length){
        storage[i].addBox(b);
        return noProblem;
      }
      i++;
    }
    return problem ;
  }
  
  /**
   * Adds a box to its corresponding shipping list and updates all the fields
   * @param b
   * @return problem or noProblem
   */
  public String addToShip (Box b){
    if (b instanceof UrgentBox){
      if (toShipUrgently==null){
        toShipUrgently= (UrgentBox)b; 
        toShipUrgently.next=null;
        return noProblem;
      }else{
        b.next=toShipUrgently;
        toShipUrgently= (UrgentBox)b; 
        return noProblem;
      }     
    }else{
      if (toShip==null){
        toShip=b; 
        toShip.next=null;
        return noProblem;
      }else{
        b.next=toShip;
        toShip=b; 
        return noProblem;
      }
    }
  }
  
  /**
   * Find a box with the identifier (if it exists)
   * Remove the box from its corresponding shelf
   * Add it to its corresponding shipping list
   * @param identifier
   * @return problem or noProblem
   */
  public String shipBox (String identifier){
    int i=0;
    Box boxToShip=null;
    String returnMessage= problem;
    while (i<nbShelves){
      boxToShip= storage[i].removeBox(identifier);
      if(boxToShip!=null){
        break;
      }
      i++;
    }
    if(boxToShip==null){
      returnMessage=problem;
    }else{
      returnMessage= addToShip(boxToShip);
    }
    return returnMessage;
  }
  
  /**
   * if there is a better shelf for the box, moves the box to the optimal shelf.
   * If there are none, do not do anything
   * @param b
   * @param position
   */
  public void moveOneBox (Box b, int position){
    boolean canMove= false;
    for(int i=0;i<position;i++){
      if(storage[i].height>=b.height && storage[i].availableLength>=b.length){
        canMove=true;
      }
    }
    if(canMove){
      storage[position].removeBox(b.id);
      addBox(b);
    }
  }
  
  /**
   * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
   */
  public void reorganize (){
    for(int i=storage.length-1;i>=0;i--){
      Box boxToMove= storage[i].firstBox;
      while(boxToMove!=null){
        Box nextBoxToMove= boxToMove.next;
        moveOneBox(boxToMove,i);
        boxToMove=nextBoxToMove;
      }
    }  
  }
}