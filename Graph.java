package assignment4Graph;

public class Graph {
  
  boolean[][] adjacency;
  int nbNodes;
  
  public Graph (int nb){
    this.nbNodes = nb;
    this.adjacency = new boolean [nb][nb];
    for (int i = 0; i < nb; i++){
      for (int j = 0; j < nb; j++){
        this.adjacency[i][j] = false;
      }
    }
  }
  
  public void addEdge (int i, int j){
    if (i>=0&&i<this.nbNodes&&j>=0&&j<this.nbNodes){
      this.adjacency[i][j]=true;
      this.adjacency[j][i]=true;
    }else{
      throw new IllegalArgumentException ("One or both nodes don't exist");
    }
    
  }
  
  public void removeEdge (int i, int j){
    if (i>=0&&i<this.nbNodes&&j>=0&&j<this.nbNodes){
      this.adjacency[i][j]=false;
      this.adjacency[j][i]=false;
    }else{
      throw new IllegalArgumentException ("One or both nodes don't exist");
    }
  }
  
  public int nbEdges(){
    int countEdges=0;
    int countSelfloops=0;
    for (int i = 0; i < nbNodes; i++){
      for (int j = 0; j < nbNodes; j++){
        if(i==j && this.adjacency[i][j]){
          countSelfloops++;
        }else if(this.adjacency[i][j]){
          countEdges++;
        }
      }
    }
    return countEdges/2 + countSelfloops; // DON'T FORGET TO CHANGE THE RETURN
  }
  public boolean cycle (int start){
    if (adjacency[start][start]) return false;
    boolean[][]checkedEdges = new boolean [nbNodes][nbNodes];
    int[]traversedNodes= new int[nbNodes];
    for(int i=0; i<nbNodes;i++){
      for (int j=0; j<nbNodes; j++){
        checkedEdges[i][j]=adjacency[i][j];
      }
    }
    traversedNodes[start]=start+1;
    return helperCycle (start,start, checkedEdges, traversedNodes, false);
  }
  public boolean helperCycle(int node, int start, boolean[][]checkedEdges, int[]traversedNodes, boolean isInCycle){
    if (isInCycle) return true;
    for(int i=0; i<nbNodes; i++){
      if(checkedEdges[node][i]){
        if(traversedNodes[i]==start+1) return true;
        checkedEdges[node][i]=checkedEdges[i][node]=false;
        traversedNodes[i]=i+1;
        isInCycle= helperCycle (i, start, checkedEdges, traversedNodes, isInCycle);
      }
    }
    return isInCycle;
  }
  public int shortestPath(int start, int end){
    if (start == end && cycle(start))System.out.println("Technically in this case I should return the length of the shortest cycle but I couldn't do that with my implementation");
    boolean[]traversedNodes= new boolean[nbNodes];
    return bfs(start, end, traversedNodes); 
  }
  
  //bfs 
  public int bfs(int start, int end, boolean[] traversedNodes){
    int[] queue ={};
    int[] distFromStart = new int[nbNodes];
    for(int i=0;i<nbNodes;i++){
      distFromStart[i] = nbNodes+1;
    }
    
    distFromStart[start]=0;
    traversedNodes[start]=true;
    queue = addToQueue(start,queue);
    while (queue.length != 0){
      int v = queue[0];
      queue = dequeue(queue);
      for(int i=0; i<nbNodes;i++){
        if(adjacency[v][i] && !traversedNodes[i]){
          traversedNodes[i]= true;
          distFromStart[i]=distFromStart[v] + 1;
          queue = addToQueue(i, queue);
        }
      }
    }
    return distFromStart[end];
  }
  
  //helper methods for queue
  public int[] addToQueue (int node, int[]queue){
    int[]temp = new int[queue.length+1];
    if (queue.length !=0){
      for (int i=0; i<queue.length;i++){
        temp[i] =queue[i];
      }
    }
    temp[temp.length-1]= node;
    return temp;
  }
  public int[] dequeue(int[]queue){
    if(queue.length !=0){
      int[]temp= new int [queue.length-1];
      for(int i=0;i<temp.length;i++){
        temp[i] = queue[i+1];
      }
      return temp;
    }
    return queue;
  }
  
  
}
