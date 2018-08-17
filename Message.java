package assignment1;

public class Message {
  
  public String message;
  public int lengthOfMessage;
  
  public Message (String m){
    message = m;
    lengthOfMessage = m.length();
    this.makeValid();
  }
  
  public Message (String m, boolean b){
    message = m;
    lengthOfMessage = m.length();
  }
  
  /**
   * makeValid modifies message to remove any character that is not a letter and turn Upper Case into Lower Case
   */
  public void makeValid(){
    char[] messageChar = new char [lengthOfMessage];
    for (int i=0; i<lengthOfMessage; i++){
      char charToStore = message.charAt(i);
      if (message.charAt(i)>='A' && message.charAt(i)<='Z'){
        charToStore= (char) (message.charAt(i)+32);
        messageChar[i]=charToStore;
      }else if(message.charAt(i)<'a'|| message.charAt(i)>'z'){
        charToStore= (char) 0;
        messageChar[i] = charToStore;
      }else{
        messageChar[i] = charToStore;
      }
    }
    String newMessage="";
    for (int i=0; i<lengthOfMessage;i++){
      if(messageChar[i]!= (char)0){
        newMessage=newMessage + messageChar[i];
      }
    }
    message=newMessage;
    lengthOfMessage=newMessage.length();   
  }
  
  /**
   * prints the string message
   */
  public void print(){
    System.out.println(message);
  }
  
  /**
   * tests if two Messages are equal
   */
  public boolean equals(Message m){
    if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage){
      return true;
    }
    return false;
  }
  
  /**
   * caesarCipher implements the Caesar cipher : it shifts all letter by the number 'key' given as a parameter.
   * @param key
   */
  public void caesarCipher(int key){
    String newMessage="";
    if (key>=0){
      for(int i=0;i<lengthOfMessage;i++){
        char[]shiftedChar=new char[lengthOfMessage];
        if((message.charAt(i)+key%26)<='z'){
          shiftedChar[i]=(char)(message.charAt(i)+key%26);
          newMessage=newMessage+shiftedChar[i];
        }else{
          shiftedChar[i]= message.charAt(i);
          int newkey =key-('z'-shiftedChar[i]);
          shiftedChar[i]=(char)(96+newkey%26);
          newMessage=newMessage+shiftedChar[i];
        }
      }
    }else{
      for(int i=0;i<lengthOfMessage;i++){
        char[]shiftedChar=new char[lengthOfMessage];
        if((message.charAt(i)+key%26)>='a'){
          shiftedChar[i]=(char)(message.charAt(i)+key%26);
          newMessage=newMessage+shiftedChar[i];
        }else{
          shiftedChar[i]= message.charAt(i);
          int newkey =key+(shiftedChar[i]-'a');
          shiftedChar[i]=(char)(123+newkey%26);
          newMessage=newMessage+shiftedChar[i];
        }
      }
      
    }
    message=newMessage;
  }
  
  public void caesarDecipher(int key){
    this.caesarCipher(- key);
  }
  
  /**
   * caesarAnalysis breaks the Caesar cipher
   * you will implement the following algorithm :
   * - compute how often each letter appear in the message
   * - compute a shift (key) such that the letter that happens the most was originally an 'e'
   * - decipher the message using the key you have just computed
   */
  public void caesarAnalysis(){
    int countA=0;int countB=0;int countC=0; int countD=0;int countE=0;int countF=0;int countG=0;int countH=0;
    int countI=0;int countJ=0;int countK=0;int countL=0;int countM=0; int countN=0; int countO=0; int countP=0;
    int countQ=0;int countR=0; int countS=0; int countT=0;int countU=0;int countV=0;int countW=0;int countX=0;
    int countY=0;int countZ=0;
    for(int i=0;i<lengthOfMessage;i++){
      if(message.charAt(i)=='a'){
        countA++;
      }else if(message.charAt(i)=='b'){
        countB++;
      }else if(message.charAt(i)=='c'){
        countC++;
      }else if(message.charAt(i)=='d'){
        countD++;
      }else if(message.charAt(i)=='e'){
        countE++;
      }else if(message.charAt(i)=='f'){
        countF++;
      }else if(message.charAt(i)=='g'){
        countG++;
      }else if(message.charAt(i)=='h'){
        countH++;
      }else if(message.charAt(i)=='i'){
        countI++;
      }else if(message.charAt(i)=='j'){
        countJ++;
      }else if(message.charAt(i)=='k'){
        countK++;
      }else if(message.charAt(i)=='l'){
        countL++;
      }else if(message.charAt(i)=='m'){
        countM++;
      }else if(message.charAt(i)=='n'){
        countN++;
      }else if(message.charAt(i)=='o'){
        countO++;
      }else if(message.charAt(i)=='p'){
        countP++;
      }else if(message.charAt(i)=='q'){
        countQ++;
      }else if(message.charAt(i)=='r'){
        countR++;
      }else if(message.charAt(i)=='s'){
        countS++;
      }else if(message.charAt(i)=='t'){
        countT++;
      }else if(message.charAt(i)=='u'){
        countU++;
      }else if(message.charAt(i)=='v'){
        countV++;
      }else if(message.charAt(i)=='w'){
        countW++;
      }else if(message.charAt(i)=='x'){
        countX++;
      }else if(message.charAt(i)=='y'){
        countY++;
      }else{
        countZ++;
      }
    }
    int[] frequency = {countA, countB, countC, countD, countE, countF, countG, countH, countI, countJ, countK, countL,
      countM, countN, countO, countP, countQ, countR, countS, countT, countV, countW, countX, countY, countZ};
    int max =frequency[0];
    char maxchar ='a';
    for (int i=0; i<frequency.length;i++){
      if(frequency[i]>max){
        max=frequency[i];
        maxchar= (char) (i+97);
      }
    }
    int key;
    if(maxchar+(maxchar-'e')=='e'){
      key= maxchar-'e';
    }else{
      key = -(maxchar-'e');
    }
    
    this.caesarCipher(key); 
  }
  
  /**
   * vigenereCipher implements the Vigenere Cipher : it shifts all letter from message by the corresponding shift in the 'key'
   * @param key
   */
  public void vigenereCipher (int[] key){
    String newMessage="";
    if (lengthOfMessage<key.length){
      char shiftedChar;
      for(int i=0; i<lengthOfMessage;i++){
        if (key[i]>=0){
          if((message.charAt(i)+key[i]%26)<='z'){
            shiftedChar=(char)(message.charAt(i)+key[i]%26);
            newMessage=newMessage+shiftedChar;
          }else{
            shiftedChar = message.charAt(i);
            int newkey =key[i]-('z'-shiftedChar);
            shiftedChar =(char)(96+newkey%26);
            newMessage=newMessage+shiftedChar;
          }
        }else{
          if((message.charAt(i)+key[i]%26)>='a'){
            shiftedChar=(char)(message.charAt(i)+key[i]%26);
            newMessage=newMessage+shiftedChar;
          }else{
            shiftedChar= message.charAt(i);
            int newkey =key[i]+(shiftedChar-'a');
            shiftedChar=(char)(123+newkey%26);
            newMessage=newMessage+shiftedChar;
          }
        }
      }
    }else{
      int count=0;
      for(int i=0; i<lengthOfMessage;i++){
        
        char shiftedChar;
        if(count<key.length){
          if (key[count]>=0){
            
            if((message.charAt(i)+key[count]%26)<='z'){
              shiftedChar=(char)(message.charAt(i)+key[count]%26);
              newMessage=newMessage+shiftedChar;
              
            }else{
              shiftedChar = message.charAt(i);
              int newkey =key[count]-('z'-shiftedChar);
              shiftedChar =(char)(96+newkey%26);
              newMessage=newMessage+shiftedChar;
            }
          }else{
            if((message.charAt(i)+key[count]%26)>='a'){
              shiftedChar=(char)(message.charAt(i)+key[count]%26);
              newMessage=newMessage+shiftedChar;
              
            }else{
              shiftedChar= message.charAt(i);
              int newkey =key[count]+(shiftedChar-'a');
              shiftedChar=(char)(123+newkey%26);
              newMessage=newMessage+shiftedChar;
            }
          }
          count++;
          
        }
        if(count==key.length){
          count=0;
          
        }
      }
    }
    message=newMessage;
    
  }
  
  /**
   * vigenereDecipher deciphers the message given the 'key' according to the Vigenere Cipher
   * @param key
   */
  public void vigenereDecipher (int[] key){
    int[]newkey= new int[key.length];
    for(int i=0;i<newkey.length;i++){
      newkey[i]=-key[i];
    }
    this.vigenereCipher(newkey);
  }
  
  /**
   * transpositionCipher performs the transition cipher on the message by reorganizing the letters and eventually adding characters
   * @param key
   */
  public void transpositionCipher (int key){
    // We can't create a valid array for this method if the input key is 0 or a negative number, so we should check the input first
    if(key<=0){
      System.out.println("Your key should be a positive non negative integer");
    }
    char[][] arraymessage;
    if(lengthOfMessage<=key){
      arraymessage= new char[1][key];
    }else if ((lengthOfMessage-key)%key==0){
      arraymessage= new char[lengthOfMessage/key][key];
    }else{
      arraymessage= new char[lengthOfMessage/key+1][key];
    }
    int index=0;
    for(int i=0;i<arraymessage.length;i++){
      for (int j=0;j<arraymessage[i].length;j++){
        if(index<lengthOfMessage){
          arraymessage[i][j]=message.charAt(index);
          index++;    
        }else{
          arraymessage[i][j]='*';
        }
      }
    }
    char[][] transposedarray= new char[arraymessage[0].length][arraymessage.length];
    for(int i=0;i<arraymessage[0].length;i++){
      for (int j=0;j<arraymessage.length;j++){
        transposedarray[i][j]=arraymessage[j][i];
      }
    }
    String newmessage="";
    for(int i=0;i<transposedarray.length;i++){
      for (int j=0;j<transposedarray[i].length;j++){
        newmessage=newmessage+transposedarray[i][j];         
      }
    }
    message=newmessage;
    lengthOfMessage= newmessage.length();
  }
  
  /**
   * transpositionDecipher deciphers the message given the 'key'  according to the transition cipher.
   * @param key
   */
  public void transpositionDecipher (int key){
    // We can't create a valid array for this method if the input key is 0 or a negative number, so we should check the input first
    if(key<=0){
      System.out.println("Your key should be a positive non negative integer");
    }
    char[][] arraymessage;
    if(lengthOfMessage<=key){
      arraymessage= new char[1][key];
    }else {
      arraymessage= new char[key][lengthOfMessage/key];
    }
    int index=0;
    for(int i=0;i<arraymessage.length;i++){
      for (int j=0;j<arraymessage[i].length;j++){
        arraymessage[i][j]=message.charAt(index);
        index++;   
      }
    }
    char[][] translatedarray= new char[arraymessage[0].length][arraymessage.length];
    
    for(int i=0;i<arraymessage[0].length;i++){
      for (int j=0;j<arraymessage.length;j++){
        translatedarray[i][j]=arraymessage[j][i];
      }
    }
    String newmessage="";
    for(int i=0;i<translatedarray.length;i++){
      for (int j=0;j<translatedarray[i].length;j++){
        if(translatedarray[i][j] != '*'){
          newmessage=newmessage+translatedarray[i][j]; 
        }
      }
    }
    message=newmessage;
    lengthOfMessage= newmessage.length();
  }
  
}
