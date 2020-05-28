public class Game {

  private Grid grid;
  private int userRow;
  private int userCol;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private String arrowPic = "images/avoid.gif";
  private String userPic = "images/user.gif"; 
  private String bgPic = "images/danceBg.png"; 
  
  public Game() {

    grid = new Grid(5, 10);
    grid.setBackground(bgPic);
    userRow = 3;
    userCol = 5;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    grid.setImage(new Location(userRow, 0), userPic);
  }
  
  public void play() {

    while (!isGameOver()) {
      grid.pause(100);
      handleKeyPress();
      if (msElapsed % 300 == 0) {
        scrollLeft();
        populateRightEdge();
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
        
        Location oldLoc = new Location(userRow+1, 0);
        grid.setImage(oldLoc, null);
      }

     //set A key to move to the left of plane 
      if(key == 65){
        if(!(userCol < 1)){
          userCol--;
        }
        Location loc = new Location(0, userCol);
        grid.setImage(loc, userPic);

        Location oldlLoc = new Location(0,userCol);
        grid.setImage(loc, null);
      } 

    //set D key to move to the right of plane
     if(key == 68){
     
        if(!(userCol > grid.getNumCols()-2)){
          userCol++;
        }
        Location loc = new Location(0,userCol);
        grid.setImage(loc, userPic);
 
        Location oldLoc = new Location(0,userCol-1);
        grid.setImage(loc, null);
      }

      
      

  }
  
  public void populateRightEdge(){
    Location arrowLoc= new Location(0, (int)(Math.random()*(4)+1));
    grid.setImage(arrowLoc, arrowPic);

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