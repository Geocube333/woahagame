package apcsa;
public class Location {

  private int row;
  private int col;
  
  public Location(int r, int c) {
    row = r;
    col = c;
  }
  
  public int getRow() {
    return row;
  }
  
  public int getCol() {
    return col;
  }
  
  public void plusRow(int add) {
    row+=add;
  }

  public void plusCol(int add) {
    row+=add;
  }

  public boolean equals(Location otherLoc) {
    return row == otherLoc.getRow() && col == otherLoc.getCol();
  }
  
  public String toString() {
    return "(" + row + ", " + col + ")";
  }


  
} 
