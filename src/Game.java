/* Game Coders:Raphael Reyes, Geovanni Valentin, Yara Abdelrahman 
 
Description of game: As coders we decided to make a rendition of Dance Dance Revolution. 
Our game has been designed to be played on the computer. 
We made our theme as a dance game. We had coded in the DDR background.
We managed to use the "A,S,D,W" keys to let the player play with these buttons. 
As long as the player doesn't miss an arrow 10 times, then they can continue playing until they lose all their lives.
The game is relatively fast and you can keep playing until the song ends 
We added in the song so that the arrows can go with the best of said song.
 
*/

public class Game {

  private final Grid grid;
  private int userRow;
  private int userCol;
  private int msElapsed;
  //private final String userPic = "images/user.gif";  
  private int score;
  private int rating;
  private Location[] arrowMap = new Location[234];
  private int moves=0;
  private String arrowPic = "images/avoid.gif";
  private final int timesGet;
  private final int timesAvoid;
  private WavPlayer audio =new WavPlayer("videos/EnjoyYourself.wav"); 
  private final String bgPic = "images/danceBg.png"; 
  private final String uparrow = "images/up.png";
  private final String downarrow = "images/down.png";
  private final String leftarrow = "images/left.png";
  private final String rightarrow = "images/right.png";
  //private final String white = "images/white tile.png";
  private final Color white = new Color(255, 255, 255);
  private final int hitRow; 
  private final int numArrows = 4;

  public Game() {
    
    grid = new Grid(9, 16);
    //grid.setBackground(bgPic);
    grid.setMovableBackground(bgPic, 700, 0, .5, .5);
    grid.fullscreen();
    rating= (grid.getNumRows()*arrowMap.length);//+(arrowMap.length*5);
   // userRow = 3;
   // userCol = 5;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    hitRow = grid.getNumRows()-1;
    updateTitle();
    highlight();
    //grid.setImage(new Location(userRow, 0), userPic);
  }

  public void highlight(){
   for(int c = 0; c < numArrows; c++){
    Location loc = new Location(hitRow -1 , c);
    grid.setColor(loc, new Color(255, 165, 0));//orange

    Location black = new Location(hitRow, c);
    grid.setColor(black, new Color(0, 0, 0));//black

   }

   for(int r = 0; r<hitRow; r++){
      for(int c = 0; c < numArrows; c++){
       Location loc = new Location(r, c);
       grid.setColor(loc, white);
     }
    }
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

    
  // //set "w" key to move the plane up
  //   if(key == 87){
  //       //check case where out of bounds
  //       if(!(userRow < 1)){
  //         userRow--;
  //       }
  //       //change the field for userrow
  //       //shift the user picture up in the array
  //       final Location loc = new Location(userRow, 0);
  //       grid.setImage(loc, userPic);
  //       final Location oldLoc = new Location(userRow+1, 0);
  //       grid.setImage(oldLoc, null);



  // }
  //   //if I push down arrow, then plane goes down
  //     if(key == 83){
  //       if(!(userRow > grid.getNumRows()-2)){
  //         userRow++;
  //       }

  //       final Location loc = new Location(userRow, 0);
  //       grid.setImage(loc, userPic);
        
  //       final Location oldLoc = new Location(userRow-1, 0);
  //       grid.setImage(oldLoc, null);
  //     }

  //      //set A key to move to the left of plane 
  //      if(key == 65){
  //       if(!(userCol < 1)){
  //         userCol--;
  //       }
  //       Location loc = new Location(0, userCol);
  //       grid.setImage(loc, userPic);

  //       Location oldlLoc = new Location(0,userCol);
  //       grid.setImage(loc, null);
  //     } 

  //   //set D key to move to the right of plane
  //    if(key == 68){

  //       if(!(userCol > grid.getNumCols()-2)){
  //         userCol++;
  //       }
  //       Location loc = new Location(0,userCol);
  //       grid.setImage(loc, userPic);

  //       Location oldLoc = new Location(0,userCol-1);
  //       grid.setImage(loc, null);
  //     }
   return key;

  }
  public void fillArrowMap() {
    for(int i=0; i<arrowMap.length; i++) {
      arrowMap[i]= new Location(i-arrowMap.length, (int)(Math.random()*numArrows));
    }
   }

  public void populateTopEdge(){
    for(int i=0; i<arrowMap.length-1; i++) {
      Location oldMap= new Location(arrowMap[i].getRow(), arrowMap[i].getCol());
      Location nextMap= new Location(-1, -1);
      if(i>0){nextMap= new Location(arrowMap[i-1].getRow(), arrowMap[i-1].getCol());}
      arrowMap[i].plusRow(1);
 
      if(arrowMap[i].getRow() >= hitRow +1){
        arrowMap[i].plusRow(arrowMap.length*-100);    //the 100 might need to be adjusted some other time.
        //grid.setImage(oldMap, null);
      }
	    
      if(arrowMap[i].getRow() > 0){
        if(arrowMap[i].getCol() == 0){
          grid.setImage(arrowMap[i], leftarrow);
      	  if(nextMap.getCol()!=0){grid.setImage(oldMap, null);}
        }
        if(arrowMap[i].getCol() == 1){
          grid.setImage(arrowMap[i], uparrow);
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
    

    int lastKeyPressed = handleKeyPress();
    //System.out.println("last in: "+lastKeyPressed);
    int lkp= -1;
    if(lastKeyPressed == 65) {lkp=0;}
    if(lastKeyPressed == 87) {lkp=1;}
    if(lastKeyPressed == 83) {lkp=2;}
    if(lastKeyPressed == 68) {lkp=3;}

    //System.out.println(lkp);
    for(int c = 0; c < numArrows; c++){
      Location loc = new Location(hitRow -1, c);
      grid.setColor(loc, new Color(255, 165, 0));

      Location black = new Location(hitRow, c);
       grid.setColor(black, new Color(0, 0, 0));//black
  
    }
    for(int i=0; i<arrowMap.length; i++) {
      if(lkp != -1){
        Location targetLoc = new Location(hitRow, lkp);
        if(arrowMap[i].getRow() == hitRow && arrowMap[i].getCol() == lkp) {
          grid.setColor(targetLoc, new Color(0, 255, 0)); //green
          score+=1000;
          rating+=arrowMap.length+(int)(arrowMap.length*.1);
            break;
        }
       else{
        grid.setColor(targetLoc, new Color(255, 0, 0)); //red
        score-=1;
        	rating-=1;
        }
         //System.out.println(arrowMap[i]);
      }
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
