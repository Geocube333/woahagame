import java.util.*;

public class Game {

  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private Location[] arrowMap = new Location[20];
  private String arrowPic = "images/avoid.gif";
  private String userPic = "images/user.gif"; 
  private String bgPic = "images/danceBg.png"; 
  
  public Game() {

    grid = new Grid(5, 10);
    grid.setBackground(bgPic);
    userRow = 3;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    grid.setImage(new Location(userRow, 0), userPic);
  }
  
  public void play() {

    fillArrowMap();
    while (!isGameOver()) {
      grid.pause(100);
      handleKeyPress();
      if (msElapsed % 300 == 0) {
        scrollLeft();
        populateTop();
      }
      updateTitle();
      msElapsed += 100;
    }
  }
  
  public void handleKeyPress(){

    //check last key pressed
    int key = grid.checkLastKeyPressed();
    System.out.println(key);

    //set "w" key to move the plane up
    if(key == 87){
        //check case where out of bounds
        if(!(userRow < 1)){
          userRow--;
        }
        //change the field for userrow
        //shift the user picture up in the array
        Location loc = new Location(userRow, 0);
        grid.setImage(loc, userPic);
        
        Location oldLoc = new Location(userRow+1, 0);
        grid.setImage(oldLoc, null);

  }
    //if I push down arrow, then plane goes down
      if(key == 83){
        if(!(userRow > grid.getNumRows()-2)){
          userRow++;
        }

        Location loc = new Location(userRow, 0);
        grid.setImage(loc, userPic);
        
        Location oldLoc = new Location(userRow-1, 0);
        grid.setImage(oldLoc, null);
      }

      //DEBUG OPTIONS:

      //Force a populateTop with P:
      //
      //if(key == 80){
      //  populateTop();
      //}
  }

  public void fillArrowMap() {
   for(int i=0; i<arrowMap.length; i++) {
     arrowMap[i]= new Location(i-20, (int)(Math.random()*4));
   }
  }
  
  public void populateTop(){
    //Location arrowLoc= new Location(0, (int)(Math.random()*4));
    //grid.setImage(arrowLoc, arrowPic);
    for(int i=0; i<arrowMap.length; i++) {
      //System.out.println(arrowMap[i]);
      Location oldMap= new Location(arrowMap[i].getRow(), arrowMap[i].getCol());
      arrowMap[i].plusRow(1);
      //if(arrowMap[i].getRow() > 5){
      //  arrowMap = ArrayUtils.removeElement(arrowMap, i);
      //}
      if(arrowMap[i].getRow() > 0){
        grid.setImage(arrowMap[i], arrowPic);
	grid.setImage(oldMap, null);
      }
    }
  }
  
  public void scrollLeft(){
  
  }
  
  public void handleCollision(Location loc) {

  }
  
  public int getScore() {
    return 0;
  }
  
  public void updateTitle() {
    grid.setTitle("Game:  " + getScore());
  }
  
  public boolean isGameOver() {
    return false;
  }
    
  public static void main(String[] args) {
    Game game = new Game();
    game.play();   
  }
}
