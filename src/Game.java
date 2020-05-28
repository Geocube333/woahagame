public class Game {

  private final Grid grid;
  private int userRow;
  private int msElapsed;
  private final int timesGet;
  private final int timesAvoid;
  private final String userPic = "images/user.gif"; 
  private final String bgPic = "images/danceBg.png"; 
  private final String toparrow = "images/top arrow.png";
  private final String downarrow = "images/downarrow.png";
  private final String leftarrow = "images/leftarrow.png";
  private final String rightarrow = "images/rightarrow.png";
  private Location[] arrowMap = new Location[20];
  
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
      if (msElapsed % 1000 == 0) {
        scrollLeft();
        populateTopEdge();
      }
      updateTitle();
      msElapsed += 100;
    }
  }
  
  public void handleKeyPress(){

    //check last key pressed
    final int key = grid.checkLastKeyPressed();
    System.out.println(key);

    //set "w" key to move the plane up
    if(key == 87){
        //check case where out of bounds
        if(!(userRow < 1)){
          userRow--;
        }
        //change the field for userrow
        //shift the user picture up in the array
        final Location loc = new Location(userRow, 0);
        grid.setImage(loc, userPic);
        
        final Location oldLoc = new Location(userRow+1, 0);
        grid.setImage(oldLoc, null);

  }
    //if I push down arrow, then plane goes down
      if(key == 83){
        if(!(userRow > grid.getNumRows()-2)){
          userRow++;
        }

        final Location loc = new Location(userRow, 0);
        grid.setImage(loc, userPic);
        
        final Location oldLoc = new Location(userRow-1, 0);
        grid.setImage(oldLoc, null);
      }

  }
  public void fillArrowMap() {
    for(int i=0; i<arrowMap.length; i++) {
      arrowMap[i]= new Location(i-20, (int)(Math.random()*4));
    }
   }

  public void populateTopEdge(){
    for(int i=0; i<arrowMap.length; i++) {
      Location oldMap= new Location(arrowMap[i].getRow(), arrowMap[i].getCol());
      arrowMap[i].plusRow(1);

      if(arrowMap[i].getRow() > 0){
        grid.setImage(arrowMap[i], toparrow);
      	grid.setImage(oldMap, null);

        grid.setImage(arrowMap[i], rightarrow);
      	grid.setImage(oldMap, null);

        grid.setImage(arrowMap[i], downarrow);
        grid.setImage(oldMap, null);
        
        grid.setImage(arrowMap[i], leftarrow);
      	grid.setImage(oldMap, null);



    //final Location arrowLoc1= new Location(0, (int)(Math.random()*4));
    //grid.setImage(arrowLoc1, toparrow);
    //final Location arrowLoc2= new Location(0, (int)(Math.random()*4));
    //grid.setImage(arrowLoc2, downarrow);
    //final Location arrowLoc3= new Location(0, (int)(Math.random()*4));
    //grid.setImage(arrowLoc3, leftarrow);
    //final Location arrowLoc4= new Location(0, (int)(Math.random()*4));
    //grid.setImage(arrowLoc4, rightarrow);
      }
    }
  }
  
  public void scrollLeft(){

  }
  
  public void handleCollision(final Location loc) {

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
   
  public static void main(final String[] args) {
    final Game game = new Game();
    game.play();   
  }
}