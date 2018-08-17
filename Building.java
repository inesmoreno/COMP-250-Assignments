package assignment3;

public class Building {
  
  OneBuilding data;
  Building older;
  Building same;
  Building younger;
  
  public Building(OneBuilding data){
    this.data = data;
    this.older = null;
    this.same = null;
    this.younger = null;
  }
  
  public String toString(){
    String result = this.data.toString() + "\n";
    if (this.older != null){
      result += "older than " + this.data.toString() + " :\n";
      result += this.older.toString();
    }
    if (this.same != null){
      result += "same age as " + this.data.toString() + " :\n";
      result += this.same.toString();
    }
    if (this.younger != null){
      result += "younger than " + this.data.toString() + " :\n";
      result += this.younger.toString();
    }
    return result;
  }
  
  public Building addBuilding (OneBuilding b){
    if (this.data == null){
      Building returnBuilding = new Building (this.data);
      return returnBuilding;
    }
    if (b.yearOfConstruction<this.data.yearOfConstruction){
      if (this.older == null){
        this.older = new Building(b);
        return this;
      }else{
        (this.older).addBuilding(b);
      }
    }else if (b.yearOfConstruction>this.data.yearOfConstruction){
      if (this.younger == null){
        this.younger = new Building (b);
        return this;
      }else{
        (this.younger).addBuilding(b);
      }
    }else{
      if (b.height>this.data.height){
        OneBuilding helper= this.data;
        this.data=b;
        this.addBuilding(helper);
      }else if (this.same == null){
        this.same = new Building(b);
        return this;
      }else{
        (this.same).addBuilding(b);
      }
    }
    
    return this; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
  }
  
  public Building addBuildings (Building b){
    if(b.data==null){
      return this;
    }
    this.addBuilding (b.data);
    if (b.older != null){
      this.addBuildings(b.older);
    }
    if (b.same != null){
      this.addBuildings (b.same);
    }
    if (b.younger != null){
      this.addBuildings(b.younger);
    }
    return this; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
  }
  
  public Building removeBuilding (OneBuilding b){
    if(b==null){
      return this;
    }
    if (this.data.equals(b)) {
      if (this.same != null) {
        this.data = this.same.data;
        this.same = this.same.same;
        return this;
        
      } else if (this.older != null){
        Building helper = this.younger;
        this.data = this.older.data;
        this.same = older.same;
        this.younger = this.older.younger;
        this.older = this.older.older;
        if (helper != null){
          this.addBuildings(helper);
        }
      } else {
        return this.younger;
      }
    } else {
      
      if (this.older != null) {
        this.older = this.older.removeBuilding(b);
      }
      if (this.same != null){
        this.same = this.same.removeBuilding(b);
      }
      if (this.younger != null) {
        this.younger = this.younger.removeBuilding(b);
      }
    }
    return this;
  }
  
  
  
  public int oldest(){
    if(this.older!=null){
      return this.older.oldest();
    }
    return this.data.yearOfConstruction; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
  }
  
  public int highest(){
    if(this.older!=null && this.older.data.height>=this.data.height){
      return this.older.highest();
    }
    if (this.younger!=null && this.younger.data.height>=this.data.height){
      return this.younger.highest();
    }   
    return this.data.height; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
  }
  
  public OneBuilding highestFromYear (int year){
    if(this.data.yearOfConstruction==year){
      return this.data;
    }else if (year<this.data.yearOfConstruction && this.older !=null){
      return this.older.highestFromYear(year);
    }else if (year>this.data.yearOfConstruction && this.younger !=null){
      return this.younger.highestFromYear(year);
    }else{
      return null;
    }
    // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
  }
  
  public int numberFromYears (int yearMin, int yearMax){
    if (yearMin>yearMax){
      return 0;
    }
    int counter=0;
    int diff = yearMax - yearMin;
    for (int i=0; i<=diff; i++){
      counter= counter + countYear(yearMin+i);
    }
    
    return counter; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
  }
  
  // helper for numberFromYears
  public int countYear (int year){
    int counter=0;
    if(this.data.yearOfConstruction==year){
      counter++;
      if (this.same !=null){
        counter= counter + this.same.countYear(year);
      }
    }else if (year<this.data.yearOfConstruction && this.older !=null){
      counter = counter+ this.older.countYear(year);
    }else if (year>this.data.yearOfConstruction && this.younger !=null){
      counter = counter + this.younger.countYear(year);
    }else{
      return 0;
    }
    return counter;
  }
  
  public int[] costPlanning (int nbYears){
    int array[]=new int[nbYears];
    for (int i=0; i<nbYears;i++){
      array[i]= costOneYear (2018+i);
    }
    return array; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
  }
  
  // helper for costPlanning
  public int costOneYear (int year){
    int counter=0;
    if(this.data.yearForRepair==year){
      counter = counter + this.data.costForRepair;
    }
    if (this.same !=null){
      counter= counter + this.same.costOneYear(year);
    }
    if (this.older !=null){
      counter = counter+ this.older.costOneYear(year);
    }
    if (this.younger !=null){
      counter = counter + this.younger.costOneYear(year);
    }
    return counter;
  }
  
}
