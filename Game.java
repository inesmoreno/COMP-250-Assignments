package assignment4Game;

import java.io.*;

public class Game {
  
  public static int play(InputStreamReader input){
    BufferedReader keyboard = new BufferedReader(input);
    Configuration c = new Configuration();
    int columnPlayed = 3; int player;
    
    // first move for player 1 (played by computer) : in the middle of the grid
    c.addDisk(firstMovePlayer1(), 1);
    int nbTurn = 1;
    
    while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
      player = nbTurn %2 + 1;
      if (player == 2){
        columnPlayed = getNextMove(keyboard, c, 2);
      }
      if (player == 1){
        columnPlayed = movePlayer1(columnPlayed, c);
      }
      System.out.println(columnPlayed);
      c.addDisk(columnPlayed, player);
      if (c.isWinning(columnPlayed, player)){
        c.print();
        System.out.println("Congrats to player " + player + " !");
        return(player);
      }
      nbTurn++;
    }
    return -1;
  }
  
  public static int getNextMove(BufferedReader keyboard, Configuration c, int player){
    c.print();
    System.out.println ("In which column do you want to place your disk?");
    try{
      int move = Integer.parseInt(keyboard.readLine());
        
      while(move>6 || move<0 || c.available[move] >=6){
        System.out.println ("Can't move there, please enter another column");
        move= Integer.parseInt(keyboard.readLine());
      }
      return move;
    }catch (IOException e){
    }
    return -1; // DON'T FORGET TO CHANGE THE RETURN
  }
  
  public static int firstMovePlayer1 (){
    return 3;
  }
  
  public static int movePlayer1 (int columnPlayed2, Configuration c){
    int moveAI= 0;
    //int last =columnPlayed2;
    if (c.canWinNextRound(1) != -1) { moveAI = c.canWinNextRound(1);  return moveAI;}
    else if (c.canWinTwoTurns(1) != -1) { moveAI = c.canWinTwoTurns(1);  return moveAI;}
    else if (c.available[columnPlayed2]<=5) { moveAI=columnPlayed2;  return moveAI;}
    else { 
      int shift=1; 
      while(columnPlayed2-shift>0 || columnPlayed2+shift<7){
        if(columnPlayed2-shift>0 && c.available[columnPlayed2-shift]<=5){ moveAI=columnPlayed2-shift;  return moveAI;}
        if (columnPlayed2+shift<7 && c.available[columnPlayed2+shift]<=5){ moveAI=columnPlayed2+shift;  return moveAI;}
        shift++;
      }
    }
    
    
    return 0; // DON'T FORGET TO CHANGE THE RETURN
  }
  
}
