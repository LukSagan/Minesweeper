
/*
 * 

copyright 2020 £ukasz Sagan

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>

Niniejszy program jest wolnym oprogramowaniem; mo¿esz go
rozprowadzaæ dalej i/lub modyfikowaæ na warunkach Powszechnej
Licencji Publicznej GNU, wydanej przez Fundacjê Wolnego
Oprogramowania - wed³ug wersji 3 tej Licencji lub (wed³ug twojego
wyboru) którejœ z póŸniejszych wersji.

Niniejszy program rozpowszechniany jest z nadziej¹, i¿ bêdzie on
u¿yteczny - jednak BEZ JAKIEJKOLWIEK GWARANCJI, nawet domyœlnej
gwarancji PRZYDATNOŒCI HANDLOWEJ albo PRZYDATNOŒCI DO OKREŒLONYCH
ZASTOSOWAÑ. W celu uzyskania bli¿szych informacji siêgnij do     Powszechnej Licencji Publicznej GNU.

Z pewnoœci¹ wraz z niniejszym programem otrzyma³eœ te¿ egzemplarz
Powszechnej Licencji Publicznej GNU (GNU General Public License);
jeœli nie - zobacz <http://www.gnu.org/licenses/>.
 * 
 */

package model;

import java.time.Clock;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import gui.ButtonFieldListener;

//sysout spaces      
public class Game extends GameStatistics {
	//implements ActionListener
	
	private static int[][] fieldsElementsLocation;    // stores where are mines, numbers, blanks
	// Minefield is 9x9  [0-8;0-8]
	// 0 - blank,    1-8 numer of mineas near field,      9 mine
	
	private static int[][] fieldsRevealed;    // stores if field was clicked and revealed, so code wont do any action
	// 0 not revealed,  1 revealed
	
	private static int[][] fieldsFlagged;    // stores if field was clicked and revealed, so code wont do any action
	// 0 not revealed,  1 revealed
	
	
	private static int[][] surroundinFieldsLocation = {     // array that helps find surrounding fields, most fields have 8 neighbours
			{-1,  1},
			{ 0,  1},
			{ 1,  1},
			{-1,  0},
			{ 1,  0},
			{-1, -1},
			{ 0, -1},
			{ 1, -1},			
	};
	
	// surroundinFieldsLocation   help
	//  1   2   3
	//  4   f   5
	//  6   7   8
	
	
	private static boolean activateNewGameButton = false;    // prevents accidental starting new game, starting new game while other is ongoing is possible only through menu option
	private static boolean gameFinished = true;    // tracks when game gets finished (won or lost)
	private static boolean firstClickDone = false;  // tracks if player left clicked field in new game (this is exactly when game starts)
	
	private static int counterNonMineFieldsRevealed = 0;     // counts number of fields revealed, so game can know, when only mine fields were left unrevealed, so game is won
	private static final int DEFAULT_MAX_MINES = 10;
	private static int flagsUsed = 0;     // this is for counting   max mines - flags used   shown later as remaining mines (ones that are not flagged) 
	private int gameTimeGUITimer = 0;  // counts time (seconds) to send to display game time in GUI 
	
	//static Clock clockGameStart;     // ???? for deletion, using parent now
	//private Timer gameTimer;    // for making timer after first click in new game
	private TimerTask gameTimerTask;
	
	private ButtonFieldListener textGameFieldRevealingListener;    // listener, sends information to Minefield about fields to reveal
	private GameNewWonLostListener intGameNewWonLostListener;    // listener, sends information to Toolbar to change faceico button icon
	private ButtonFieldListener textGameRemainingMinesListener;    // listener, sends information to Toolbar to change remaining mines counter
	private ButtonFieldListener textGameGUITimerListener;    // listener, sends information to Toolbar to change timer/clock
	

	//private int[] minesLocation;	// stores location of mines        NOT USED ????
	
	
	
	
	// 1. fill field with blanks
	// 2. find mines
	// 3. find numbers around mines
	
	
	private void fillFieldsWithBlanks() {
		// method to fill fieldsElementsLocation with blanks
		
		fieldsElementsLocation = new int[9][9];       // maybe create them with object, and just reset here? ????
		fieldsRevealed = new int[9][9];
		fieldsFlagged = new int[9][9];
		
		// field is 9x9  [0-8;0-8]
		for (int i=0; i<9; i++ ){			
			for (int j=0; j<9; j++ ){
				fieldsElementsLocation[i][j] = 0;				
				fieldsRevealed[i][j] = 0;
				fieldsFlagged[i][j] = 0;
			}			
		}
	}
	
	
	private void generateMinesLocation() {
		// method to generate location of mines,        other method will calculate numbers (tips) around those mines
		
		int mineGenerationMode = 1;    // for testing purposes. 1 is normal generating, 2+ are specific generations
		// some modes:   4  1click win
		
		switch(mineGenerationMode){
			case 1:
				for (int i=0; i<10; i++ ){	// finding 10 mines,		no point in finding 1st mine, just more code
					
					generateMineLocation();			
				}				
				break;
			case 2:
				fieldsElementsLocation[0][0] = 9;    // this is for manually setting up mines locations
				fieldsElementsLocation[1][1] = 9;    // straight line
				fieldsElementsLocation[2][2] = 9;
				fieldsElementsLocation[3][3] = 9;
				fieldsElementsLocation[4][4] = 9;
				fieldsElementsLocation[5][5] = 9;
				fieldsElementsLocation[6][6] = 9;
				fieldsElementsLocation[7][7] = 9;
				fieldsElementsLocation[8][8] = 9;		 
				fieldsElementsLocation[0][8] = 9;    // #10th				
				break;
			case 3:				
				fieldsElementsLocation[0][1] = 9;    // this is for manually setting up mines locations
				fieldsElementsLocation[0][2] = 9;;   // shows all numbers  to test visibility
				fieldsElementsLocation[0][2] = 9;
				fieldsElementsLocation[0][3] = 9;
				fieldsElementsLocation[1][1] = 9;
				fieldsElementsLocation[1][3] = 9;
				fieldsElementsLocation[2][1] = 9;
				fieldsElementsLocation[2][2] = 9;
				fieldsElementsLocation[2][3] = 9;		 
				fieldsElementsLocation[3][3] = 9;    // #10th
				fieldsElementsLocation[4][1] = 9;
				fieldsElementsLocation[4][2] = 9;
				fieldsElementsLocation[4][3] = 9;
				fieldsElementsLocation[5][3] = 9;
				fieldsElementsLocation[6][2] = 9;
				fieldsElementsLocation[6][3] = 9;
				fieldsElementsLocation[7][3] = 9;
				fieldsElementsLocation[8][2] = 9;
				fieldsElementsLocation[8][3] = 9;
				fieldsElementsLocation[8][4] = 9;
				fieldsElementsLocation[0][6] = 9;
				fieldsElementsLocation[2][6] = 9;
				fieldsElementsLocation[3][6] = 9;
				fieldsElementsLocation[3][8] = 9;
				fieldsElementsLocation[4][6] = 9;
				fieldsElementsLocation[5][6] = 9;
				fieldsElementsLocation[5][7] = 9;
				break;
			case 4:				
				fieldsElementsLocation[2][3] = 9;    // this is for manually setting up mines locations
				fieldsElementsLocation[3][2] = 9;    // 1 click win
				fieldsElementsLocation[2][2] = 9;
				fieldsElementsLocation[3][3] = 9;
				fieldsElementsLocation[4][4] = 9;
				fieldsElementsLocation[5][5] = 9;
				fieldsElementsLocation[6][6] = 9;
				fieldsElementsLocation[6][5] = 9;
				fieldsElementsLocation[5][6] = 9;		 
				fieldsElementsLocation[0][8] = 9;    // #10th	
				break;
			case 5:				
				fieldsElementsLocation[3][2] = 9;    // this is for manually setting up mines locations
				fieldsElementsLocation[3][3] = 9;    // 2 click win
				fieldsElementsLocation[3][4] = 9;
				fieldsElementsLocation[4][1] = 9;
				fieldsElementsLocation[4][2] = 9;
				fieldsElementsLocation[4][3] = 9;
				fieldsElementsLocation[4][4] = 9;
				fieldsElementsLocation[5][3] = 9;
				fieldsElementsLocation[5][4] = 9;		 
				fieldsElementsLocation[6][3] = 9;    // #10th
				break;
			case 6:				
				fieldsElementsLocation[0][1] = 9;    // this is for manually setting up mines locations
				fieldsElementsLocation[0][8] = 9;    // 2 click win
				fieldsElementsLocation[1][0] = 9;
				fieldsElementsLocation[1][1] = 9;
				fieldsElementsLocation[1][8] = 9;
				fieldsElementsLocation[2][0] = 9;				
				fieldsElementsLocation[7][0] = 9;
				fieldsElementsLocation[7][8] = 9;
				fieldsElementsLocation[8][0] = 9;		 
				fieldsElementsLocation[8][8] = 9;    // #10th
				break;
		}
	}


	private void generateMineLocation() {
		// method for method generateMinesLocation. finds location for 1 mine
		
		// infinite loop protection ????
				
		Random random = new Random();		// recursion wont create too many objects in bad situation? memory leak????
		int mineX = random.nextInt(9);		// 9 x 9 field   this will find random between 0-8
		int mineY = random.nextInt(9);
		
		if ( fieldsElementsLocation[mineX][mineY] != 9 ){
			fieldsElementsLocation[mineX][mineY] = 9;	// 9 is for mine
			//System.out.println("      Game. Mine loc. x+y : " + mineX + "." + mineY);
		}else{			
			generateMineLocation();
		}		
	}
	
	
	
	private void generateNumbersAroundMines() {
		// method for finding numbers for fields
		
		// looks like around 50% of fields are filled with numbers or mines
		// go through each field, check if nearby are mines and count them
		
		// could also make it other way. while generating mines code could increment fields around mine. Prob. max 80 increments for 10 mines. Checkin each field does over 470 checks each new game
		
		
		//fieldsElementsLocation[0][0] = 9;     // 9 is for mine
		
		
		int amountOfChecks = 0;   // counts how many checks code does for generating numbers around mines
		for (int i=0; i<9; i++ ){			
			for (int j=0; j<9; j++ ){    // conditions to go through each field
				//fieldsElementsLocation[i][j] = 0;			
				
				
				
				if (fieldsElementsLocation[i][j] != 9){
					int amountOfNearbyMines = 0;   // counts nearby mines
					
					for (int k=0; k<surroundinFieldsLocation.length; k++){   // goes through each nearby field
						
						if ( (i+surroundinFieldsLocation[k][0]>=0) && (i+surroundinFieldsLocation[k][0]<=8) && (j+surroundinFieldsLocation[k][1]>=0) && (j+surroundinFieldsLocation[k][1]<=8) ){    // conditions to not check fields that dont exist
							
							if ( fieldsElementsLocation[i+surroundinFieldsLocation[k][0]][j+surroundinFieldsLocation[k][1]] == 9 ){  // added as separate condition to make it more readable
								
								amountOfNearbyMines ++;   // increase number of nearby mines if there are any
								
							}
							
							amountOfChecks ++;
						}
						
						
					}
					
					fieldsElementsLocation[i][j] = amountOfNearbyMines;   // write number of nearby mines to the field
				}
				
			}			
		}
		System.out.println("      Game. Checks for generating numbers around mines: " + amountOfChecks);
	}


	
	
	public Game() {
		super();    //calling superclass constructor		
		
		newGame();
		readStatsFromFile();
		//showStatsFromFile();  // ???? for testing purposes only
		
		
		/*
		if(arrayGameStats5GamesListener != null){
			arrayGameStats5GamesListener.setEmitted(gamesStats);			
		}
		
		if(intGameOptionsWindowPosXListener != null){
			//System.out.println("      Game. Game n");
			intGameOptionsWindowPosXListener.numberEmitted(windowPositionXSetting);			
		}
		
		if(intGameOptionsWindowPosYListener != null){
			intGameOptionsWindowPosYListener.numberEmitted(windowPositionYSetting);			
		}
		
		if(intGameOptionsStatsWindowListener != null){
			intGameOptionsStatsWindowListener.numberEmitted(1);			
		}
		*/
		
		
		TimerTask gameTimerTask = new TimerTask(){    // packing listeners to initialize GUI

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				//System.out.println("      Game. Running );
				
				if(arrayGameStats5GamesListener != null){
					arrayGameStats5GamesListener.setEmitted(gamesStats);			
				}
				
				if(intGameOptionsWindowPosXListener != null){
					//System.out.println("      Game. Game n");
					intGameOptionsWindowPosXListener.numberEmitted(windowPositionXSetting);			
				}
				
				if(intGameOptionsWindowPosYListener != null){
					intGameOptionsWindowPosYListener.numberEmitted(windowPositionYSetting);			
				}
				
				if(intGameOptionsStatsWindowListener != null){
					if(showStatisticsSetting){
						intGameOptionsStatsWindowListener.numberEmitted(1);	
					}else{
						intGameOptionsStatsWindowListener.numberEmitted(0);	
					}							
				}
				
				if(objGameStatisticsListener != null){ // overall stats send to stats panel		
					String precentDivide0Safety = "0";
					if(gamesWonNumberHistory>0){
						precentDivide0Safety = doubleAccuracyRounder(((double)gamesWonNumberHistory/(double)gamesPlayedNumberHistory*100d), 0.1d);
						
					}
					String[] stringArray = {Integer.toString(gamesPlayedNumberHistory), 
							Integer.toString(gamesWonNumberHistory), 
							//doubleAccuracyRounder(((double)gamesWonNumberHistory/(double)gamesPlayedNumberHistory*100d), 0.1d),
							precentDivide0Safety,
							//Double.toString(  doubleAccuracyRounder(((double)gamesWonNumberHistory/(double)gamesPlayedNumberHistory*100d), 0.1d)  ),
							//Double.toString((double)((double)gamesWonNumberHistory/(double)gamesPlayedNumberHistory*100)), 
							//new DecimalFormat("##.#").format(  (double)gamesWonNumberHistory/(double)gamesPlayedNumberHistory*100d     ),
							Double.toString(averageTimeOfGameHistory)};
					//objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.overallStatsStats, null, 0, 0, stringArray, null));
					objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.overallStatsStats, null, 0, 0, stringArray));
				}
				
				if(textGameStatsNewRecordListener != null){
					textGameStatsNewRecordListener.textEmitted("Powodzenia!");				
				}
				
				//lastGameLabel.setText("Ostani¹ grê " + stringArray[0] + ". Czas: " + stringArray[1] + ". Klikniêæ: " + stringArray[2] + 
				//		". Œredni czas klikniêcia: " + stringArray[3] + ". Najszybszy: " + stringArray[4] + 
				//		". Najwolniejszy: " + stringArray[5] + "U¿yto " + stringArray[0] + " flag.");
				
				
				if(objGameStatisticsListener != null){ // last game stats send to stats panel		
					/*
					String[] stringArray = {gameLastStats.readPastGameStatsDate(), 
							Double.toString(gameLastStats.readPastGameStatsTime()), 
							Integer.toString(gameLastStats.readPastGameStatsClicks()), 
							"-1",
							"",
							"",
							""};
					*/
					String[] stringArray = {gameLastStats.readPastGameStatsDate(), 
							doubleAccuracyRounder(gameLastStats.readPastGameStatsTime(), 0.1d),
							Integer.toString(gameLastStats.readPastGameStatsClicks()), 
							"-1",
							"",
							"",
							""};
					//objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.lastGameStats, null, 0, 0, null, stringArray));
					objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.lastGameStats, null, 0, 0, stringArray));
					
					//objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.lastGameStats, null, 0, 0, stringArray));
				}
				
				if(objGameStatisticsListener != null){ // move window to location from file
					int[] arrayIntsPom = {windowPositionXSetting, windowPositionYSetting};					
					objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.setWindowPosition, arrayIntsPom));					
				}
				
				
			}
		};
		
		Timer gameTimer = new Timer();			
		try {
			gameTimer.schedule(gameTimerTask, 100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
	public void newGame() {
		System.out.println("      Game. ****   New game resetter   ****");

		fillFieldsWithBlanks();
		generateMinesLocation();
		generateNumbersAroundMines();
		
		activateNewGameButton = false;
		gameFinished = true;
		//System.out.println("      Game.       gameFinished becomes true        newGame method ");
		
		firstClickDone = false;
		
		counterNonMineFieldsRevealed = 0;
		flagsUsed = 0;
		gameTimeGUITimer = 0;

		TimerTask gameTimerTask = new TimerTask(){    // ???? make this a method

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				//System.out.println("      Game. Running );
				if (intGameNewWonLostListener != null) {	
					//System.out.println("      Game. Game new won lost listener. Number: 1");
					intGameNewWonLostListener.numberEmitted(1);
				}		
				
				if (textGameRemainingMinesListener != null){
					//System.out.println("      Game. Game remaining mines listener  STRING. Flags used: " + flagsUsed);
					textGameRemainingMinesListener.textEmitted("010");
				}
				
				if (textGameGUITimerListener != null){
					textGameGUITimerListener.textEmitted("000");
				}
				
				
				
			}
		};
		
		Timer gameTimer = new Timer();			
		try {
			gameTimer.schedule(gameTimerTask, 100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		resetCurrentGameStatistics();   // data times, counters
		
		
		
		
		
		
		
		//testerNumberIntDouble();  // for testing purposes only ????
	}
	
	
	
	public void fieldLeftClicked(String text){
		// this will check field after clicking. if it should be blank, number, mine or if it was clicked before
		// this will send back information to Minefield about field type, so Minefield can change graphics
		
		//System.out.println("      Game. Running minefieldFieldClicked. Clicked field: " + text);
		
		int x = Integer.parseInt(String.valueOf(text.charAt(0)));
		int y = Integer.parseInt(String.valueOf(text.charAt(2)));
		if(  (gameFinished) && (gameWonLost==2) ){ // maybe should have added variable game stage: pre click, after click, finished
			gameFinished = false;
			//System.out.println("      Game.       gameFinished becomes false      field 1st left clicked ");
		}
		
		
		//System.out.println("      Game. Running minefieldFieldClicked. Type of field from Game.fields: " + fieldsElementsLocation[x][y]);
		
		//changeMinefieldField();
		
		if (      (fieldsRevealed[x][y] == 0) && (fieldsFlagged[x][y] == 0) && (!gameFinished)        ) {    // !gameFinished prevents clicking unflagged mine fields after game is won
			if (textGameFieldRevealingListener != null) {
				//System.out.println("      Game. text field listener");
				
				
				if (!firstClickDone){
					System.out.println("      Game. First click");
					firstClickDone = true;
					
					
					clockGameStart = Clock.systemDefaultZone();
					//System.out.println("      Game. Current absolute time in milis: " + clockGameStart.millis());
					////System.out.println("      Game. Current absolute time in milis: " + clockGameStart.millis() + "     after casting: " + (int)doubleAccuracyRounder(clockGameStart.millis(), 100));
					////System.out.println("      Game. Current absolute time in milis: " + clockGameStart.millis() + "     as double: " + doubleAccuracyRounder(clockGameStart.millis(), 100));
					////clockGameStart.mi
					
					//System.out.println("      Game. Current game date: " + clockGameStart);
					//System.out.println("      Game. Current game date: " + clockGameStart.toString());
					//System.out.println("      Game. Current instant game date: " + clockGameStart.instant());
					
					
					timeGameStarted = clockGameStart.millis();
					timeOfTheClick = timeGameStarted;
					timeOfThePreviousClick = timeOfTheClick;
					
					// making timer for GUI timer 
					gameTimerTask = new TimerTask(){    // ???? make this a method

						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							//System.out.println("      Game. Running timer every second: " + clock.millis());
							gameTimeGUITimer ++;
							
							if (textGameGUITimerListener != null){
								//textGameGUITimerListener.textEmitted(Integer.toString(gameTimeGUITimer));  // ???? old one, this didnt have '0's formatting
								textGameGUITimerListener.textEmitted(formatIntTo3DigitText(gameTimeGUITimer));
							}					
						}
					};
					
					Timer gameTimer = new Timer();			
					try {
						gameTimer.scheduleAtFixedRate(gameTimerTask, 1000, 1000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
					
				}else{
					timeOfThePreviousClick = timeOfTheClick;// moved it up here, so times are more accurate when game is done (especially visible in 2 clicks game)  (there was difference 0,01sec. Sometimes even with accuracy 0,1sec rounding could show this bug)
					timeOfTheClick = clockGameStart.millis();		
				}
				
					
				//textGameMethodListener.textEmitted("1.1.6");				
				fieldsRevealed[x][y] = 1;   // sets field to 'revealed' to not run code for revealed fields
				
				clicks ++;
				//System.out.println("      Game. Clicks counter: " + clicks);			
				
				
				//timeOfThePreviousClick = timeOfTheClick;   // ???? moved it up, so times are more accurate when game is done in 2 clicks  (there was difference 0,01sec. Sometimes even with accuracy 0,1sec rounding could show this bug)
				//timeOfTheClick = clockGameStart.millis();
				if(  (timeOfTheClick-timeOfThePreviousClick)>10  ){
					clickTime = (double)( ((double)timeOfTheClick-(double)timeOfThePreviousClick)/1000   );
					//System.out.println("      Game. Click time: " + clickTime);
					
					if(clickTime<fastestClick){
						fastestClick = clickTime;					
					}
					
					if(clickTime>slowestClick){
						slowestClick = clickTime;					
					}					
					
				}
				
				
				
				
				
				
				
				// below conditions if clicked on blank, mine or number field
				if (fieldsElementsLocation[x][y] == 0){
					// clicked on blank field   reveal other surrounding blank fields until met number fields
					
					// reveal itself
					// reveal surrounding numbers
					// call same method on other surrounding blank fields
					
					//System.out.println("      Game. blank field");
					counterNonMineFieldsRevealed++;
					blankFieldsRevealer(x,y);
					
					//System.out.println("      Game. counterNonMineFieldsRevealed: " + counterNonMineFieldsRevealed);

				}else if (fieldsElementsLocation[x][y] == 9) {
					// clicked on mine field 
					
					// Game Lost    reveal all fields, clicked mine is in other colour than other mines
					// block clicking other fields
					// unlock new game button
					// remove prompt 'are you sure' from 'new game' from menu
					// something to option 'end game'?

					//System.out.println("      Game. mine field");
					gameFinished = true;
					//System.out.println("      Game.       gameFinished becomes true      game lost");
					
					gameWonLost = 0;
					//textGameFieldRevealingListener.textEmitted(x + "." + y + "." + fieldsElementsLocation[x][y]);
					textGameFieldRevealingListener.textEmitted(x + "." + y + ".r");  // 'r' code, to correctly display clicked mine
					
					System.out.println();
					System.out.println("      Game.  ^^^^^^^  Game Lost!!!  ^^^^^^^");
					
					for (int i = 0; i < 9; i++) {  // reveal reamaining fields
						for (int j = 0; j < 9; j++) { // conditions to go through each field
							if (fieldsRevealed[i][j] == 0) {
								//fieldsElementsLocation[i][j] = 0;
								
								
								
								
								if (fieldsFlagged[i][j] == 0) {
									// reveal not flagged fields
									
									fieldsRevealed[i][j] = 1; // sets field to 'revealed' to not run code for revealed fields
									textGameFieldRevealingListener.textEmitted(i + "." + j + "." + fieldsElementsLocation[i][j]);
									
									//fieldsElementsLocation[i][j] = amountOFNearbyMines;   // write number of nearby mines to the field
								} else if (     (fieldsFlagged[i][j] == 1) && (fieldsElementsLocation[i][j] == 9)     ){
									// fields correctly flagged
									
									
									
								} else if (     (fieldsFlagged[i][j] == 1) && (fieldsElementsLocation[i][j] != 9)     ){
									// fields incorrectly flagged
									
									fieldsRevealed[i][j] = 1; // sets field to 'revealed' to not run code for revealed fields
									textGameFieldRevealingListener.textEmitted(i + "." + j + "." + fieldsElementsLocation[i][j] + "r");
								}
							
							
							
							
							}
						}
					}
					
					
					if (intGameNewWonLostListener != null) {						
						intGameNewWonLostListener.numberEmitted(3);
						//System.out.println("      Game. Game 'new won lost' listener");
					}
					
					gameFinishedActions();
					
				}else{
					// clicked on number field
					
					//System.out.println("      Game. number field");
					// ???? not needed? no action to do
					
					textGameFieldRevealingListener.textEmitted(x + "." + y + "." + fieldsElementsLocation[x][y]);
					counterNonMineFieldsRevealed++;
					//System.out.println("      Game. counterNonMineFieldsRevealed: " + counterNonMineFieldsRevealed);
				}
				

				if (counterNonMineFieldsRevealed == (9*9-10)){
					// condition game won and do action
					
					System.out.println();
					System.out.println("      Game.  *******  Game Won!!!  *******");
					gameFinished = true;
					//System.out.println("      Game.       gameFinished becomes true     game won");
					
					gameWonLost = 1;
					
					
					for (int i=0; i<9; i++ ){	// showing unrevealed mines after game was won		
						for (int j=0; j<9; j++ ){
							if (  (fieldsRevealed[i][j] == 0) && (fieldsFlagged[i][j] == 0)   ){
								//System.out.println("      Game. Mines unrevealed:" + i + "." + j );
								textGameFieldRevealingListener.textEmitted(i + "." + j + ".g");  // 'g' code, to correctly display unrevealed remaining mines
							}
						}			
					}

					
					if (intGameNewWonLostListener != null) {						
						intGameNewWonLostListener.numberEmitted(2);
						//System.out.println("      Game. Game 'new won lost' listener");
					}
					
					gameFinishedActions();
					
				}
					
				
				/*
				// ???? maybe leave this as learning option?
				int fieldRevealingMode = 2; // for testing purposes. changes amount of fields discovered after clicking 1 field. '1' is like normal game, '2' shows all fields (easy check mines, numbers, blanks)

				switch (fieldRevealingMode) {
					case 1:
						textGameFieldRevealingListener.textEmitted(x + "." + y + "." + fieldsElementsLocation[x][y]);
	
						break;
					case 2:
						for (int i = 0; i < 9; i++) {
							for (int j = 0; j < 9; j++) { // conditions to go through each field
								//fieldsElementsLocation[i][j] = 0;			
	
								textGameFieldRevealingListener.textEmitted(i + "." + j + "." + fieldsElementsLocation[i][j]);
	
								//fieldsElementsLocation[i][j] = amountOFNearbyMines;   // write number of nearby mines to the field
	
							}
						}
						gameFinished = true;
						gameFinishedActions();
						break;
				}
				*/
				
			} 
		}		
		
	}
	
	
	public void fieldRightClicked(String text){
		// this is for marking/unmarking fields with flags, making fields impossible to be left clicked
		// edit flagged fields array and send to Minefield to change field look
		
		int x = Integer.parseInt(String.valueOf(text.charAt(0)));
		int y = Integer.parseInt(String.valueOf(text.charAt(2)));

		if (   (fieldsRevealed[x][y] == 0)  && (  (!gameFinished) || (gameWonLost==2)       )   ) {   // !gameFinished prevents flagging fields after game is won
			
			if (fieldsFlagged[x][y] == 0) {  // here actions to do when trying to flag non-flag field
				
				//System.out.println("      Game. Field right clicked. Clicked field: " + x + "." + y);
				
				if (     (textGameFieldRevealingListener != null) && (textGameRemainingMinesListener != null)    ) {
					
					fieldsFlagged[x][y] = 1;
					if (flagsUsed < 999){
						flagsUsed ++;
					}
					//System.out.println("      Game. Field right clicked. Clicked field: " + x + "." + y);
					textGameFieldRevealingListener.textEmitted(x + "." + y + "." + fieldsElementsLocation[x][y] + "1");
					sendListenerRemaingMines(flagsUsed);

				}
				
				
				
				
			} else if (fieldsFlagged[x][y] == 1) {  // here actions to do when trying to flag flagged field
				
				//System.out.println("      Game. Field right clicked. Clicked field: " + x + "." + y);
				
				if (     (textGameFieldRevealingListener != null) && (textGameRemainingMinesListener != null)    ) {

					fieldsFlagged[x][y] = 0;
					if (flagsUsed > -999){
						flagsUsed --;      // bad comment?   technically this cant go under 0, since it requires clicking on flagged field 1st  
					}		
					//System.out.println("      Game. Field right clicked. Clicked field: " + x + "." + y);
					
					textGameFieldRevealingListener.textEmitted(x + "." + y + "." + fieldsElementsLocation[x][y] + "0");
					sendListenerRemaingMines(flagsUsed);
				}
			}
			
			
			//System.out.println("      Game. Flags used: " + flagsUsed);
		}
	}
	
	
	
	
	
	
	
	
	private void blankFieldsRevealer(int x, int y){
		// this will reveal blank fields, surrounding blank fields and number fields
		
		textGameFieldRevealingListener.textEmitted(x + "." + y + "." + fieldsElementsLocation[x][y]);
		
		//int fieldsAroundBlankField = 0;    // used only to check how many operations are done
		//System.out.println("blank space");
		for (int k=0; k<surroundinFieldsLocation.length; k++){   // goes through each nearby field
			//System.out.println("test 2");
			
			int a = x+surroundinFieldsLocation[k][0];       // ???? maybe later can be send in listener as number directly without creating those variables
			int b = y+surroundinFieldsLocation[k][1];
			
			//int fieldsChecked = 0;   // add this as global, reset when method called in main method
			
			if ( (a>=0) && (a<=8) && (b>=0) && (b<=8)  ){    // conditions to not check fields that dont exist
				
				//fieldsAroundBlankField ++;
				//System.out.println("      Game. Field around blank field nr: " + fieldsAroundBlankField);
				
				
				if (   (fieldsRevealed[a][b] == 0) && (fieldsFlagged[a][b] == 0)   ){   // condition to skip revealed fields and flagged fields
					if ( (fieldsElementsLocation[a][b] > 0) && (fieldsElementsLocation[a][b] < 9)  ){  // 							
						// revealing number fields surrounding blank fields
						//System.out.println("      Game. Field with number near blank field");
						
						fieldsRevealed[a][b] = 1;   // sets field to 'revealed' to not run code for revealed fields
						counterNonMineFieldsRevealed++;
						textGameFieldRevealingListener.textEmitted(a + "." + b + "." + fieldsElementsLocation[a][b]);   // sends information to Minefield to reveal field with number
					} else if (   fieldsElementsLocation[a][b] == 0   ) {
						// revealing blank fields
						
						fieldsRevealed[a][b] = 1;
						counterNonMineFieldsRevealed++;
						blankFieldsRevealer(a,b);			
					}
				}
				
				
			}
			
			
		}
				
	}
	
	
	public void sendListenerRemaingMines(int flagsUsed){
		// calls text format to send to listener and sends listener
		
		int remainingMines = DEFAULT_MAX_MINES - flagsUsed;
		//System.out.println("      Game. Listener. Remaining mines: " + remainingMines);
		textGameRemainingMinesListener.textEmitted(formatIntTo3DigitText(remainingMines));
	}
	
	public String formatIntTo3DigitText(int number){
		// formats text to send to listener
		
		String numberString = "";
		if (number > 999){
			// 1000+
			numberString = " 999";
		} else if (number > 99){
			// 100+  999
			numberString = " " + String.valueOf(number);
		} else if (number > 9){
			// 10+  99
			numberString = " 0" + String.valueOf(number);
		} else if (number >= 0){
			// 0+ 9
			numberString = " 00" + String.valueOf(number);
		} else if (number >= -9){
			// -1 -9
			numberString = "-00" + String.valueOf(Math.abs(number));
		} else if (number >= -99){
			// -10   -99
			numberString = "-0" + String.valueOf(Math.abs(number));
		} else if (number >= -999){
			// -100   -999
			numberString = "-" + String.valueOf(Math.abs(number));
		} else {
			// -1000-
			numberString = "-999";
		}
		return numberString;
	}	
	
	private void gameFinishedActions(){
		// actions done when game is either won or lost
		// this only does actions depite game won or lost,  things that are done when game is won or game is lost are done in fieldLeftClicked
		// set times,   check times for ladder,   refresh stats panel,   write stats to file
		
		gameTimerTask.cancel();
		timeGameEnded = timeOfTheClick;   // this is more accurate time. bypasses time lost for revealing fields.      was timeGameEnded = clockGameStart.millis();
		gameTime = (double)(((double)timeGameEnded-(double)timeGameStarted)/1000);
		if(clicks>1){     // counting average click time
			averageClick = gameTime/(clicks-1);
		}else{
			averageClick = 0;
			fastestClick = 0;
		}
		System.out.println();
		System.out.println("      Game. Game finished, Played for: " + gameTime + "sec      time in milisec.: " + (timeGameEnded-timeGameStarted) + "      flags used: " + flagsUsed);
		System.out.println("      Game.    clicks done: " + clicks + "   fastest click: " + fastestClick + "   slowest click: " + slowestClick + "   avg click: " + averageClick);
		// System.out.println("      Game.    avg click: " + averageClick + "    avg click by game/clicks: " + (gameTime/(clicks-1)));  // ????
		
		
		
		gameTimeRounded = doubleAccuracyRounder(gameTime, 0.1d);		
		System.out.println("      Game. Last game time: " + gameTime + ", but after rounding: " + gameTimeRounded);		
				
		gamesPlayedNumberHistory++;		
		averageTimeOfGameHistory = averageGameTimeCounter(gamesPlayedNumberHistory, averageTimeOfGameHistory, Double.parseDouble(gameTimeRounded));
		averageTimeOfGameHistory = Double.parseDouble(   doubleAccuracyRounder(   averageTimeOfGameHistory, 0.1d  )   );  // used 2 lines, so its easier to read
		//System.out.println("      Game. Size of gamesStats" + gamesStats.size());
		
		int wasGameLostWonRecord = 0;  // 0 lost,  1 won ,   2 record
		
		/*
		Clock clock = Clock.systemUTC();
		//Clock clock = Clock.systemDefaultZone(); // same result as above
		System.out.println("      Game. Current instant game date: " + clock.instant());
		System.out.println("      Game. Current instant game date: " + clock.instant().atZone(ZoneId.systemDefault())    );
		System.out.println("      Game. Current instant game date: "
				+ "Day: " + clock.instant().atZone(ZoneId.systemDefault()).getDayOfMonth()
				+ " Month: " + clock.instant().atZone(ZoneId.systemDefault()).getMonthValue()
				+ " Year: " + clock.instant().atZone(ZoneId.systemDefault()).getYear()
				+ "   all together: " + clock.instant().atZone(ZoneId.systemDefault()).getDayOfMonth()
				+ "." + clock.instant().atZone(ZoneId.systemDefault()).getMonthValue()
				+ "." + clock.instant().atZone(ZoneId.systemDefault()).getYear()  );
		
		
		//clock.instant().atZone(ZoneId.systemDefault());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
		String dateString = formatter.format(  clock.instant().atZone(ZoneId.systemDefault())      );
		//System.out.println("      Game. Current instant game date: " + formatter.format(  clock.instant().atZone(ZoneId.systemDefault())      )     );
		System.out.println("      Game. Current instant game date: " + dateString     );
		*/
		
		
		Clock clock = Clock.systemUTC();
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY.MM.dd");
		gameDate = formatter.format(  clock.instant().atZone(ZoneId.systemDefault())      );;
		
		
		
		
		if( gameWonLost == 1 ){	// game was won, check times, add last game to arrayGameStats with last 5 best games amd recent one
			gamesWonNumberHistory++;
			//boolean wasThereRecord = false;
			wasGameLostWonRecord = 1;
			//System.out.println("      Game. Comparing times. Last game time: " + gameTime + "    worst game time: "  + gamesStats.get(4).readPastGameStatsTime() + "    best game time: " + gamesStats.get(0).readPastGameStatsTime());
			//if( gameTime < game5Stats.readPastGameStatsTime() ){			
			if( gameTime < gamesStats.get(4).readPastGameStatsTime() ){ // game was won and record
				// last game had better time then previous 5th game, so add to list sort and save 5 ones
				//System.out.println("      Game. LAST GAME was better than previous 5th one");
				// ???? sort games list, call stats viewer listener if option is on
				
				
				//System.out.println("      Game. Last game time: " + gameTime + "  best past game time: " + gamesStats.get(0).readPastGameStatsTime());
				//if( gameTime < game1Stats.readPastGameStatsTime() ){
				
				
				
				
				
				
				if( gameTime < gamesStats.get(0).readPastGameStatsTime() ){
					// last game had better time then previous 1st game, so show something in game ????
					
					//System.out.println("      Game. LAST GAME was better than previous 1st one.   NEW RECORD !!");
					if(textGameStatsNewRecordListener != null){
						//System.out.println("      Game. NEW RECORD listener !! Time: " + gameTime);						
						textGameStatsNewRecordListener.textEmitted("Nowy rekord!  Czas:  " + gameTimeRounded + " sek.");
					}					
					//wasThereRecord = true;
					wasGameLostWonRecord = 2;
				}else{ // game was won, wasnt record, but got on best 5 list					
					if(textGameStatsNewRecordListener != null){
						textGameStatsNewRecordListener.textEmitted("Twój wynik wszed³ na listê.");				
					}					
				}
				
				//System.out.println("      Game. Game was won. Adding last game to games Array. Time:  " + gameTimeRounded + "  clicks:" + clicks + "   + some DATE");
				//addRecentGameToArray(gameTime, clicks, "2020.05.30");
				
				//addRecentGameToArray(gameTimeRounded, clicks, "2020.05.30");
				addRecentGameToArray(gameTimeRounded, clicks, gameDate);
				
				
				
				if(arrayGameStats5GamesListener != null){ // best 5 games send to stats panel
					
					//System.out.println("      Game.  Game was won. Sending best 5 games to statsPanel");
					/*
					for(GameWonStats someGame: gamesStats){  // prints best 5 games in console
						System.out.println(someGame);						
					}
					*/
					
					arrayGameStats5GamesListener.setEmitted(gamesStats);			
				}
			}else{ // game was won, didnt get on best 5 list, so just send text to statsPanel
				if(textGameStatsNewRecordListener != null){
					textGameStatsNewRecordListener.textEmitted("Twój wynik nie wszed³ do rankingu. Powodzenia nastêpnym razem!");				
				}
			}
			
			
			
			/*
			//if( gameTime < gamesStats.get(0).readPastGameStatsTime() ){   // this is for showing  dialog game lost/won/record
			if( wasThereRecord ){   // this is for showing  dialog game lost/won/record
				// last game had better time then previous 1st game   RECORD
				
				//System.out.println("      Game. LAST GAME was better than previous 1st one.   NEW RECORD !!");
				if(objGameStatisticsListener != null){					
					objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.showGameWonLostPopUp, ObjListener.messageGameWonRecord));
				}				
			}else{ // game was won, wasnt record, but got on best 5 list					
				if(objGameStatisticsListener != null){	   // this is for showing  dialog game lost/won/record				
					objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.showGameWonLostPopUp, ObjListener.messageGameWon));
				}		
			}
			*/
			
			
			
		}else{ // game was lost, so just send text to statsPanel
			System.out.println("      Game.  Last game time: " + gameTimeRounded);			
			if(textGameStatsNewRecordListener != null){
				textGameStatsNewRecordListener.textEmitted("Powodzenia!");
			}
			
			/*
			if(objGameStatisticsListener != null){					
				objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.showGameWonLostPopUp, ObjListener.messageGameLost));
			}
			*/
			
		}
		
		
		
		
		
		gameTime = Double.parseDouble(  doubleAccuracyRounder(gameTime, 0.1d)  );  // rounding gameTime do 1 decimal 
		//gameLastStats = new GameWonStats(gameTime, clicks, "2020.05.01");
		gameLastStats = new GameWonStats(gameTime, clicks, gameDate);
		
		
		if(objGameStatisticsListener != null){ // overall stats send to stats panel		
			String[] stringArray = {Integer.toString(gamesPlayedNumberHistory), 
					Integer.toString(gamesWonNumberHistory), 
					//Double.toString((double)((double)gamesWonNumberHistory/(double)gamesPlayedNumberHistory*100)),
					doubleAccuracyRounder(((double)gamesWonNumberHistory/(double)gamesPlayedNumberHistory*100), 0.1d),
					//Double.toString(  doubleAccuracyRounder(((double)gamesWonNumberHistory/(double)gamesPlayedNumberHistory*100), 0.1f)  ),
					Double.toString(averageTimeOfGameHistory)};
			
			//objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.overallStatsStats, null, 0, 0, stringArray, null));
			objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.overallStatsStats, null, 0, 0, stringArray));
		}
		
		
		
		if(objGameStatisticsListener != null){ // last game stats send to stats panel			
			String gameWonLostS = "przegrano";
			if( gameWonLost == 1 ) gameWonLostS = "wygrano";
			
			/*
			String[] stringArray = {gameWonLostS, 
					Double.toString(gameTime), 
					Integer.toString(clicks), 
					Double.toString(averageClick),
					Double.toString(fastestClick),
					Double.toString(slowestClick),
					Integer.toString(flagsUsed)};
			*/
			String[] stringArray = {gameWonLostS,
					doubleAccuracyRounder(gameTime, 0.1d),
					Integer.toString(clicks), 
					doubleAccuracyRounder(averageClick, 0.1d),
					doubleAccuracyRounder(fastestClick, 0.1d),
					doubleAccuracyRounder(slowestClick, 0.1d),
					Integer.toString(flagsUsed)};
			//objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.lastGameStats, null, 0, 0, null, stringArray));
			objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.lastGameStats, null, 0, 0, stringArray));
		}
		
		//writeStatsToFile(800, 500, "no", gamesPlayedNumberHistory, gamesWonNumberHistory, (double)25.5, (double)21.6, 42, "2020.04.30", gameTime, clicks, "2020.05.01");
		// yes no
		//writeStatsToFile(800, 500, "yes", gamesPlayedNumberHistory, gamesWonNumberHistory, (double)25.5, gameTime, clicks, "2020.05.01");
		//writeStatsToFile(800, 500, "yes", gamesPlayedNumberHistory, gamesWonNumberHistory, averageTimeOfGameHistory, gameTime, clicks, "2020.05.01");
		//writeStatsToFile(gameTime, clicks, "2020.05.01");
		writeStatsToFile(gameTime, clicks, gameDate);
		
		
		
		
		if(objGameStatisticsListener != null){
			if( wasGameLostWonRecord == 0 ){
				objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.showGameWonLostPopUp, ObjListener.messageGameLost));
			}else if( wasGameLostWonRecord == 1 ){
				// this is for showing  dialog game lost/won/record				
				objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.showGameWonLostPopUp, ObjListener.messageGameWon));
			}else{
				objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.showGameWonLostPopUp, ObjListener.messageGameWonRecord));
			}
		}
		

		
		
		
		// ???? for now, later 1st click will reset timer to "000"
		/*
		if (textGameTimerListener != null){
			textGameTimerListener.textEmitted("000");
		}
		*/
		System.out.println("");
		System.out.println("");
	}
	
	
	
	public boolean returnIfGameIsNotRunning(){    // to allow action when clicking 'new game' buttons
		// through Controller to Mainframe		
		//System.out.println("      Game. returnIfGameIsNotRunning: " + gameFinished);
		return gameFinished;
	}
	
	public void resetGamesStats(){
		// reset stats of current game, best 5 games,     write to file   and refresh stats panel
		
		//System.out.println("      Game. Reseting stats");		
		resetStatsCurrentGameAndBest5();
		//writeStatsToFile(800, 500, "yes", gamesPlayedNumberHistory, gamesWonNumberHistory, (double)25.5, gameTime, clicks, "2020.05.01");
		//writeStatsToFile(800, 500, "yes", gamesPlayedNumberHistory, gamesWonNumberHistory, averageTimeOfGameHistory, gameTime, clicks, "2020.05.01");
		
		//writeStatsToFile(gameTime, clicks, "2020.05.01");
		writeStatsToFile(999.9d, 0, "2020.05.01");
		
		readStatsFromFile();
		
		if(objGameStatisticsListener != null){ // last game stats send to stats panel	
			String[] stringArray = {gameLastStats.readPastGameStatsDate(), 
					doubleAccuracyRounder(gameLastStats.readPastGameStatsTime(), 0.1d),
					Integer.toString(gameLastStats.readPastGameStatsClicks()), 
					"-1",
					"",
					"",
					""};
			objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.lastGameStats, null, 0, 0, stringArray));
		}
		
		if(textGameStatsNewRecordListener != null){  // send to stats panel
			textGameStatsNewRecordListener.textEmitted("Powodzenia!");				
		}
		
		if(arrayGameStats5GamesListener != null){ // best 5 games stats send to stats panel	
			arrayGameStats5GamesListener.setEmitted(gamesStats);
		}
		
		if(objGameStatisticsListener != null){ // overall stats send to stats panel		
			String[] stringArray = {Integer.toString(gamesPlayedNumberHistory), 
					Integer.toString(gamesWonNumberHistory), 
					//doubleAccuracyRounder(((double)gamesWonNumberHistory/(double)gamesPlayedNumberHistory*100), 0.1d),
					"0",
					//Double.toString(averageTimeOfGameHistory)};
					"999.9"};
			objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.overallStatsStats, null, 0, 0, stringArray));
		}
		
		
		
	}
	
	
	public void remoteFinishGame(){
		// used when new game interrupts existing game
		// System.out.println("      Game. remoteFinishGame run");
		
		
		gameFinished = true;		
		gameWonLost = 0;
		gameTimerTask.cancel();
		
	}
	

	
	public void testMessage(){
		// this will check field after clicking. if it should be blank, number, mine or if it was clicked before
		
		System.out.println("      Game. Running testMessage. ");		
	}
	
	
	
	
	public void setStringListenerFieldRevealing(ButtonFieldListener listener){
		this.textGameFieldRevealingListener = listener;		
	}
		
	public void setIntListenerGameNewWonLost(GameNewWonLostListener listener){
		this.intGameNewWonLostListener = listener;		
	}
		
	public void setStringListernerRemainingMines(ButtonFieldListener listener){
		this.textGameRemainingMinesListener = listener;		
	}	
	
	public void setStringListernerGameGUITimer(ButtonFieldListener listener){
		this.textGameGUITimerListener = listener;			
	}
		
	
	
	public void testerNumberIntDouble(){
		String testString;   // testing isInt and isDouble  String methods
		
		testString = "10";
		if(isInt(testString))System.out.println("this is INT: " + testString);
		else System.out.println("this is NOT   INT: " + testString);
		
		testString = "-10";
		if(isInt(testString))System.out.println("this is INT: " + testString);
		else System.out.println("this is NOT   INT: " + testString);
		
		testString = "0";
		if(isInt(testString))System.out.println("this is INT: " + testString);
		else System.out.println("this is NOT   INT: " + testString);
		
		testString = "1846565";
		if(isInt(testString))System.out.println("this is INT: " + testString);
		else System.out.println("this is NOT   INT: " + testString);
		
		testString = "18465f65";
		if(isInt(testString))System.out.println("this is INT: " + testString);
		else System.out.println("this is NOT   INT: " + testString);
		
		testString = "-18465f65";
		if(isInt(testString))System.out.println("this is INT: " + testString);
		else System.out.println("this is NOT   INT: " + testString);
		System.out.println("");
		
		
		// FLOATS
		
		testString = "-18465f65";
		if (isDouble(testString))   System.out.println("this is double: " + testString);
		else System.out.println("this is NOT   double: " + testString);
		
		testString = "-18465.f65";
		if (isDouble(testString))   System.out.println("this is double: " + testString);
		else System.out.println("this is NOT   double: " + testString);
		
		testString = "-18465.65";
		if (isDouble(testString))   System.out.println("this is double: " + testString);
		else System.out.println("this is NOT   double: " + testString);
		
		testString = "18465f65";
		if (isDouble(testString))   System.out.println("this is double: " + testString);
		else System.out.println("this is NOT   double: " + testString);
		
		testString = "1846565";
		if (isDouble(testString))   System.out.println("this is double: " + testString);
		else System.out.println("this is NOT   double: " + testString);
		
		testString = "18.46565";
		if (isDouble(testString))   System.out.println("this is double: " + testString);
		else System.out.println("this is NOT   double: " + testString);
		
		
		testString = "18.465.65";
		if (isDouble(testString))   System.out.println("this is double: " + testString);
		else System.out.println("this is NOT   double: " + testString);
		
	}
	
	

	/*
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		System.out.println("      Game. PRE text field listener");
		
		if(textGameMethodListener != null ){
			System.out.println("      Game. text field listener");

			//textFieldListener.textEmitted("Hello\n");
			
			//textFieldListener.textEmitted(clicked.getText());
			textGameMethodListener.textEmitted("Game. funkcja testowa");
		}
		
		
	}
	*/
	
	
	
	
	
	

	
}
