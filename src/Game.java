public class Game {

  private final Grid grid;
  private int userRow;
  private int userCol;
  private int msElapsed;
  private int score;
  private int rating;
  private Location[] arrowMap = new Location[234];
  private int moves=0;
  private String arrowPic = "images/avoid.gif";
  private final int timesGet;
  private final int timesAvoid;
  //Not needed private final String userPic = "images/user.gif";
  private WavPlayer audio =new WavPlayer("videos/EnjoyYourself.wav"); 
  private final String bgPic = "images/danceBg.png"; 
  private final String toparrow = "images/top arrow.png";
  private final String downarrow = "images/downarrow.png";
  private final String leftarrow = "images/leftarrow.png";
  private final String rightarrow = "images/rightarrow.png";
  
  public Game() {

    grid = new Grid(5, 10);
    grid.setBackground(bgPic);
    rating= (grid.getNumRows()*arrowMap.length);//+(arrowMap.length*5);
    userRow = 3;
    userCol = 5;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    //grid.setImage(new Location(userRow, 0), userPic);
  }
  
  public void play() {
    fillArrowMap();
    while (!isGameOver()) {
      grid.pause(100);
      //handleKeyPress(); //Causes issues with handleCollision()
      if (msElapsed % 500 == 0) {
        populateTopEdge();
        handleCollision();
	moves++;
      }
      updateTitle();
      msElapsed += 100;
    }
    System.out.println("Here's your stats!\n Score:"+score+"\n Rating:"+rating);
    audio.pauseSound();
  }
  
  public int handleKeyPress(){
    //check last key pressed
    int key = grid.checkLastKeyPressed();
    System.out.println(key);
    return key;
  }
  
  public void fillArrowMap() {
    for(int i=0; i<arrowMap.length; i++) {
      arrowMap[i]= new Location(i-arrowMap.length, (int)(Math.random()*4));
    }
   }

  public void populateTopEdge(){
    for(int i=0; i<arrowMap.length; i++) {
      Location oldMap= new Location(arrowMap[i].getRow(), arrowMap[i].getCol());
      Location nextMap= new Location(-1, -1);
      if(i>0){nextMap= new Location(arrowMap[i-1].getRow(), arrowMap[i-1].getCol());}
      arrowMap[i].plusRow(1);
 
      if(arrowMap[i].getRow() >= 5){
        arrowMap[i].plusRow(arrowMap.length*-100);    //the 100 might need to be adjusted some other time.
        grid.setImage(oldMap, null);
      }
	    
      if(arrowMap[i].getRow() > 0){
        if(arrowMap[i].getCol() == 0){
          grid.setImage(arrowMap[i], toparrow);
      	  if(nextMap.getCol()!=0){grid.setImage(oldMap, null);}
        }
        if(arrowMap[i].getCol() == 1){
          grid.setImage(arrowMap[i], leftarrow);
          if(nextMap.getCol()!=1){grid.setImage(oldMap, null);}
        }
        if(arrowMap[i].getCol() == 2){
          grid.setImage(arrowMap[i], downarrow);
          if(nextMap.getCol()!=2){grid.setImage(oldMap, null);}
        }
        if(arrowMap[i].getCol() == 3){
          grid.setImage(arrowMap[i], rightarrow);
      	  if(nextMap.getCol()!=3){grid.setImage(oldMap, null);}
        }
      }
    }
  }
  
  public void handleCollision() {//final Location loc) {
    Location[] hitReg = new Location[4];
    
    //this for loop is optional and not crucial to the code.
    for(int i=0; i<hitReg.length; i++) {
      hitReg[i] = new Location(4, i);
      grid.setImage(hitReg[i], null); //null is just a place holder for now
    }

    int lastKeyPressed = handleKeyPress();
    //System.out.println("last in: "+lastKeyPressed);
    int lkp= -1;
    if(lastKeyPressed == 87) {lkp=0;}
    if(lastKeyPressed == 65) {lkp=1;}
    if(lastKeyPressed == 83) {lkp=2;}
    if(lastKeyPressed == 68) {lkp=3;}

    //System.out.println(lkp);

    for(int i=0; i<arrowMap.length; i++) {
      if(arrowMap[i].getRow() == 4 && arrowMap[i].getCol() == lkp) {
        score+=1000;
	rating+=arrowMap.length+(int)(arrowMap.length*.1);
	break;
      }
      else{
        score-=1;
	rating-=1;
      }
      //System.out.println(arrowMap[i]);
    }
  }
  
  public int getScore() {
    return score;
  }
  
  public void updateTitle() {
    grid.setTitle("Game:  " + getScore());
  }
  
  public boolean isGameOver() {
    return rating<=arrowMap.length*-5 || moves>arrowMap.length+grid.getNumRows();
  }
   
  public static void main(final String[] args) {
    final Game game = new Game();
    game.play();   
  }
}
