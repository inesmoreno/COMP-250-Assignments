package assignment4Game;

public class Configuration {
  
  public int[][] board;
  public int[] available;
  boolean spaceLeft;
  
  public Configuration(){
    board = new int[7][6];
    available = new int[7];
    spaceLeft = true;
  }
  
  public void print(){
    System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
    System.out.println("+---+---+---+---+---+---+---+");
    for (int i = 0; i < 6; i++){
      System.out.print("|");
      for (int j = 0; j < 7; j++){
        if (board[j][5-i] == 0){
          System.out.print("   |");
        }
        else{
          System.out.print(" "+ board[j][5-i]+" |");
        }
      }
      System.out.println();
    }
  }
  
  public void addDisk (int index, int player){
    board[index][available[index]]=player;
    available[index]++;
    int noSpace = 0;
  for (int i = 0; i < 7; i++) {
   if (available[i] == 6) noSpace++;
  }
  if (noSpace == 7) spaceLeft = false;
  }
  
  //helper method removeDisk
  public void removeDisk (int index){
    board[index][available[index]-1]=0;
    available[index]--;
  }
  
  
  public boolean isWinning (int lastColumnPlayed, int player){
    int position = available[lastColumnPlayed]-1;
    //check horizontal line
    if (lastColumnPlayed >= 3) {
      if (this.board[lastColumnPlayed-3][position] == player && board[lastColumnPlayed-2][position] == player && board[lastColumnPlayed-1][position] == player) return true;
    }
    if (lastColumnPlayed >= 2 && lastColumnPlayed <= 5) {
      if (board[lastColumnPlayed-2][position] == player && board[lastColumnPlayed-1][position] == player && board[lastColumnPlayed+1][position] == player) return true;
    }
    if (lastColumnPlayed >= 1 && lastColumnPlayed <= 4) {
      if (board[lastColumnPlayed-1][position] == player && board[lastColumnPlayed+1][position] == player && board[lastColumnPlayed+2][position] == player) return true;
    }
    if (lastColumnPlayed <= 3) {
      if (board[lastColumnPlayed+1][position] == player && board[lastColumnPlayed+2][position] == player && board[lastColumnPlayed+3][position] == player) return true;
    }
    
    //check vertical line
    if(position >= 3) {
      if (board[lastColumnPlayed][position-1] == player && board[lastColumnPlayed][position-2] == player && board[lastColumnPlayed][position-3] == player) return true;
    }
    
    //check diagonal
    if (lastColumnPlayed >= 3) {
      if (position <= 2) {
        if (board[lastColumnPlayed-3][position+3] == player && board[lastColumnPlayed-2][position+2] == player && board[lastColumnPlayed-1][position+1] == player) return true;
      } else {
        if (board[lastColumnPlayed-3][position-3] == player && board[lastColumnPlayed-2][position-2] == player && board[lastColumnPlayed-1][position-1] == player) return true;
      }
    }
    if (lastColumnPlayed >= 2 && lastColumnPlayed <= 5) {
      if (position <= 3 && position > 0) {
        if (board[lastColumnPlayed-2][position+2] == player && board[lastColumnPlayed-1][position+1] == player && board[lastColumnPlayed+1][position-1] == player) return true;
      }
      if (position <= 4 && position > 1){
        if (board[lastColumnPlayed-2][position-2] == player && board[lastColumnPlayed-1][position-1] == player && board[lastColumnPlayed+1][position+1] == player) return true;
      }
    }
    if (lastColumnPlayed >= 1 && lastColumnPlayed <= 4) {
      if (position <= 4 && position > 1) {
        if (board[lastColumnPlayed-1][position+1] == player && board[lastColumnPlayed+1][position-1] == player && board[lastColumnPlayed+2][position-2] == player) return true;
      }
      if (position <= 3 && position > 0){
        if (board[lastColumnPlayed-1][position-1] == player && board[lastColumnPlayed+1][position+1] == player && board[lastColumnPlayed+2][position+2] == player) return true;
      }
    }
    if (lastColumnPlayed < 3) {
      if (position <= 2) {
        if (this.board[lastColumnPlayed+3][position+3] == player && board[lastColumnPlayed+2][position+2] == player && board[lastColumnPlayed+1][position+1] == player) return true;
      } else {
        if (board[lastColumnPlayed+3][position-3] == player && board[lastColumnPlayed+2][position-2] == player && board[lastColumnPlayed+1][position-1] == player) return true;
      }
    }
    return false;
  }
  
  public int canWinNextRound (int player){
    for(int i=0; i<7; i++){
      if (available[i]<6){
        addDisk(i,player);
        if(isWinning(i,player)){
          removeDisk(i);
          return i;
        }
        removeDisk(i);
      }
    }
    return -1; // DON'T FORGET TO CHANGE THE RETURN
  }
  
  public int canWinTwoTurns (int player){
    int opponent=0;
    if (player==1) opponent=2;
    if (player==2) opponent=1;
    
    for(int i=0; i<7; i++){
      if (available[i]<6){
        addDisk(i,player);
        if( canWinNextRound(player) != -1){
          int a= canWinNextRound(player);
          addDisk(a,opponent);
          if ( canWinNextRound(player) != -1 ) { removeDisk(a); removeDisk(i); return i;}
          if(isWinning(a,opponent)){removeDisk(a); break;}
          removeDisk(a);
        }else{
          for(int j=0; j<7;j++){
            if (available[j]<6){
              addDisk(j,opponent);
              if(isWinning(i,opponent)){removeDisk(j); break;}
              if (canWinNextRound(player)!= -1){removeDisk(j); removeDisk(i); return i;}
              removeDisk(j);
            }
          }
        }
        removeDisk(i);
      }
      
    }
    return -1; // DON'T FORGET TO CHANGE THE RETURN
  }
  
}
