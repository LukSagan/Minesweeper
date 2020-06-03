
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

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import gui.ButtonFieldListener;

public class GameStatistics {   
	// separate class so its easier to maintain
	// deals with statistics variables and writing to files, SQL
	
	//private final String dataFilePath = "src/data.txt";
	private final String dataFilePath = "data.txt";
	//private final String dataSerializedFilePath = "src/data.bin";
	private final String dataSerializedFilePath = "data.bin";
	protected int clicks;
	protected int flagsUsed;
	protected int gameWonLost;   // "0" lost, "1" won,  "2" game not started    default game is lost       int because I want to store it easily in text file
	
	protected int clickIterations;  // for counting average click time
	protected final double STAT_TIME_ACCURACY = 0.1F;
	protected static Clock clockGameStart;     // to get time in general
	
	protected String gameDate = "";
	
	protected double gameTime;
	protected String gameTimeRounded;   // accuracy 0,1sec
	protected double clickTime;   // accuracy 0,1sec
	
	protected double fastestClick;
	protected double slowestClick;
	protected double averageClick;
	
	protected long timeGameStarted;
	protected long timeGameEnded;
	protected long timeOfTheClick;     // for counting how long it takes between clicks
	protected long timeOfThePreviousClick;     // for counting how long it takes between clicks
	
	
	//below are thing read from data file
	protected int windowPositionXSetting = 0; //800
	protected int windowPositionYSetting = 0; //500
	protected boolean showStatisticsSetting = false;
	
	protected int gamesPlayedNumberHistory = 0;
	protected int gamesWonNumberHistory = 0;
	protected double averageTimeOfGameHistory = 0;
	
	protected ArrayList<GameWonStats> gamesStats = new ArrayList<GameWonStats>();
	protected GameWonStats gameLastStats;
	
	protected GameGames1_5StatsListener arrayGameStats5GamesListener;    // listener, sends information to Minefield to show in stats new set of times
	protected ButtonFieldListener textGameStatsNewRecordListener;    // listener, sends information to Minefield to show in stats comment about record (or resets that field)
	protected GameNewWonLostListener intGameOptionsStatsWindowListener;    // listener, sends information to Mainframe to show Stats pane
	protected GameNewWonLostListener intGameOptionsWindowPosXListener;    // listener, sends information to Mainframe about window position when starting game  "0" hide, "1" show
	protected GameNewWonLostListener intGameOptionsWindowPosYListener;
	protected GameObjListener objGameStatisticsListener;  // universal listener, sends ints, Strings, Arrays depending how its set up
	
	
	
	public GameStatistics(){
		resetCurrentGameStatistics();		
		
	}
	
	
	protected void resetCurrentGameStatistics(){
		clicks = 0;
		flagsUsed = 0;
		//gameWonLost = 0;
		gameWonLost = 2;
		
		clickIterations = 0;
		clockGameStart = null;
		
		gameTime = 0;
		clickTime = 0;
		fastestClick = 999;
		slowestClick = 0;
		averageClick = 999;
		
		timeGameStarted = 0;
		timeGameEnded = 0;
		timeOfTheClick = 0;
		timeOfThePreviousClick = 0;

	}
	
	public double averageClickTimeCounter(long iterations, double newTime){
		// count average click time
		
		double averageTime = 0;
		//System.out.println("    GameStatistics. averageClickTimeCounter    Counting     time: " + newTime + "  iteration: " + iterations);
		if(iterations==1){
			averageTime = newTime;			
		}else{
			//averageTime = (averageClick*iterations + newClickTime)/(iterations+1);			
			averageTime = (averageClick*(iterations-1) + newTime)/(iterations);
		}
		//averageTime = doubleAccuracyRounder(averageTime, statTimeAccuracy);  // this is for 0,1sec accuracy

		return averageTime;
	}
	
	
	public double averageGameTimeCounter(long iterations, double averageTimeofGameHistory, double newTime){
		// count average game time    takes time from history + recent game time
		
		double averageTime = 0;
		//System.out.println("    GameStatistics. averageGameTimeCounter    Counting     avg time history: " + averageTimeofGameHistory + "  new time: " + newTime + "   iteration: " + iterations);
		if(iterations==1){
			averageTime = newTime;			
		}else{
			//averageTime = (averageClick*iterations + newClickTime)/(iterations+1);			
			averageTime = (averageTimeofGameHistory*(iterations-1) + newTime)/(iterations);
		}		
		//averageTime = doubleAccuracyRounder(averageTime, statTimeAccuracy);  // this is for 0,1sec accuracy
		return averageTime;
	}
		
	public String doubleAccuracyRounder(double number, double accuracy){
		// cuts off numbers after accuracy
		
		// works only with numbers under 99999999 (99 999 999)	
		if (number<99999999) return stringAccuracyRounder(Double.toString((double)(Math.round(number/accuracy)*accuracy)),accuracy);
		else return Double.toString(number);		
	}
	
	public String stringAccuracyRounder(String number, double accuracy){
		// cuts off numbers after accuracy and converts to String
		
		// find '.' or ',' index, check length of fractional part, round if needed and return
		//String string = Double.toString(number); // this fails with big numbers (other representation of numbers)
		//String string = String.format("%f", number);
		int dotIndex = 0;
		for(int i=0; i<number.length(); i++){
			if( (number.charAt(i) == ".".charAt(0)) || (number.charAt(i) == ",".charAt(0))    ) {
				dotIndex = i;
				break;
			}
		}
		
		int fractionalLength = number.length() - dotIndex - 1;
		////System.out.println("    GameStatistics. fractional length of " + number + " is: " + fractionalLength);
		//System.out.println("    GameStatistics. Number: " + number + "   dotIndex: " + dotIndex + "fractional length of " + number + " is: " + fractionalLength);
		
		accuracy = Math.log10((double)(1d/accuracy)) + 0.00001;
		////System.out.println("    GameStatistics. Accuracy (no places after sep) : " + Math.log10((double)(1d/accuracy)) + "   modulo: " + (long)Math.log10((double)(1d/accuracy))      );
		//System.out.println("    GameStatistics. Accuracy (no places after sep) : " + accuracy + "   modulo: " + (long)accuracy      );
		
		
		if(fractionalLength > (long)accuracy){
			//System.out.println("    GameStatistics. Retured string after rounding: " +  number.substring(0,  dotIndex + 1 + (int)accuracy  )   );
			return number.substring(0,  dotIndex + 1 + (int)accuracy  );
		}else if(fractionalLength == (long)accuracy){
			return number;			
		}else{
			// fractionalLength < accuracy
			for(int i=0; i<(accuracy-fractionalLength); i++){
				number = number + "0";				
			}
			return number;
		}
		
	}
	
	
	
	//protected void writeStatsToFile(int winX, int winY, String statsOption, int gamesPlayed, int gamesWon, double avgGameTime, double game1Time, int game1Clicks, String game1Date, double gameLastTime, int gameLastClicks, String gameLastDate){
	//protected void writeStatsToFile(int winX, int winY, String statsOption, int gamesPlayed, int gamesWon, double avgGameTime, double gameLastTime, int gameLastClicks, String gameLastDate){
	protected void writeStatsToFile( double gameLastTime, int gameLastClicks, String gameLastDate){
		
		File file = new File(dataFilePath);
		
		try (BufferedWriter br = new BufferedWriter(new FileWriter(file));){
			
			br.write("*** GAME OPTIONS ***");
			br.newLine();
			
			if(objGameStatisticsListener != null){
				objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.getWindowPosition));
				//System.out.println("      GameStatistics. listener get window pos");
			}
			
			//System.out.println("      GameStatistics after listener.  winPosX: " + windowPositionXSetting + " winPosY: " + windowPositionYSetting);
			//br.write("[window positionX]: " + winX);
			br.write("[window positionX]: " + windowPositionXSetting);
			br.newLine();
			//br.write("[window positionY]: " + winY);
			br.write("[window positionY]: " + windowPositionYSetting);
			br.newLine();
			//br.write("[show statistics]: " + statsOption);  // this option makes game show/hide statistics pane with game start 
			if(showStatisticsSetting)br.write("[show statistics]: " + "yes");  // this option makes game show/hide statistics pane with game start
			else br.write("[show statistics]: " + "no");  // this option makes game show/hide statistics pane with game start
			br.newLine();
			br.newLine();
			
			
			br.write("*** TOTAL GAMES STATISTICS ***");
			br.newLine();
			//br.write("[games played]: " + gamesPlayed);
			br.write("[games played]: " + gamesPlayedNumberHistory);
			br.newLine();
			//br.write("[games won]: " + gamesWon);
			br.write("[games won]: " + gamesWonNumberHistory);
			br.newLine();
			//br.write("[average time of game]: " + avgGameTime);
			br.write("[average time of game]: " + averageTimeOfGameHistory);
			br.newLine();
			br.newLine();
			
			
			//  best 5 times of won games + clicks + date, 
			br.write("*** BEST 5 GAMES STATISTICS ***");
			br.newLine();
			br.newLine();			
			
			for(int i=0; i<5; i++){ // ???? showing games after sorting, later hide this
				//System.out.println("      GameStats. Games after sorting by time.    " + (gamesStats.indexOf(value)+1) + ".   " + value);
								
				br.write("** GAME " + (i+1) + " **");
				br.newLine();
				br.write("[time]: " + gamesStats.get(i).readPastGameStatsTime());
				br.newLine();
				br.write("[clicks]: " + gamesStats.get(i).readPastGameStatsClicks());
				br.newLine();
				br.write("[date]: " + gamesStats.get(i).readPastGameStatsDate());
				br.newLine();
				br.newLine();
			}
			
			
			br.write("** LAST GAME **");
			br.newLine();
			br.write("[time]: " + gameLastTime);
			br.newLine();
			br.write("[clicks]: " + gameLastClicks);
			br.newLine();
			br.write("[date]: " + gameLastDate);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("      GameStats. ERROR. Couldnt write to file: " + file.toString());
			// ???? make warning somewhere in game window? 
		}
		
		
		// adding games stats to serializable class and writing that class to file
		
		GameStatisticsSerialized gameStatsSerialized = new GameStatisticsSerialized(windowPositionXSetting, windowPositionYSetting, showStatisticsSetting, gamesPlayedNumberHistory, gamesWonNumberHistory, averageTimeOfGameHistory, gamesStats, gameLastStats);
		//System.out.println("\n      GameStats. Printing serialized object game stats before writing");
		//System.out.println(gameStatsSerialized);
				
		try (FileOutputStream fs = new FileOutputStream(dataSerializedFilePath)){  //extension doesnt matter. can be .bin or .dat
			
			ObjectOutputStream os = new ObjectOutputStream(fs);			
			os.writeObject(gameStatsSerialized);					
			os.close();				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {  //error for example when we cant write to that file
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		
	}
	
	
	public void writeSettingsToFile(){
		// saves only windows position and  option to show or not statistics window on startup
		// read file to ArrayList<String>,  rewrite stuff from arrayList to file, change settings lines
		
		File file = new File(dataFilePath);
		//System.out.println("      GameStats. File length :" + file.length());   // ???? useless
		
		ArrayList<String> fileContents = new ArrayList<String>();		
		try(BufferedReader br = new BufferedReader(new FileReader(file));){ // read file to ArrayList<String>
			String line;
			
			while( (line = br.readLine()) != null ){
				//fileContents.set(i, line);
				fileContents.add(line);
				//System.out.println("      GameStats. writeSettingsToFile   contents of read file: " + fileContents.get(fileContents.size()-1));
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("      GameStats. ERROR. Couldnt find file " + file.toString());
			// ????
			resetStatsCurrentGameAndBest5();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("      GameStats. ERROR. Couldnt find file IOException" + file.toString());
			// ????
			//resetStatsCurrentGameAndBest5();
		}
			
		
		
		
		
	
		try (BufferedWriter br = new BufferedWriter(new FileWriter(file));){ // rewrite stuff from arrayList to file, change settings lines
			
			int i=0;
			if(objGameStatisticsListener != null){  // make mainframe change variables of window position
				objGameStatisticsListener.objectEmitted(new ObjListener(ObjListener.getWindowPosition));
				//System.out.println("      GameStatistics. listener get window pos");
			}
			
			
			for(String line: fileContents){			
				//[window positionX]: 512
				//[window positionY]: 153
				//[show statistics]: yes
				
				if (  line.startsWith("[window positionX]:")  ){
					//System.out.println("      GameStats. writeSettingsToFile    Found '[window positionX]:' in array ");
					//br.write("[window positionX]: 300");
					br.write("[window positionX]: " + windowPositionXSetting);
				}else if(  line.startsWith("[window positionY]:")  ){
					//br.write("[window positionY]: 100");
					br.write("[window positionY]: " + windowPositionYSetting);
				}else if(  line.startsWith("[show statistics]:")  ){
					//br.write("[show statistics]: yes");
					if(showStatisticsSetting)br.write("[show statistics]: " + "yes");  // this option makes game show/hide statistics pane with game start
					else br.write("[show statistics]: " + "no");  // this option makes game show/hide statistics pane with game start
				}else{
					br.write(fileContents.get(i));
				}
				br.newLine();
				i++;
			}			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("      GameStats. ERROR. Couldnt write to file: " + file.toString());
			// ???? make warning somewhere in game window? 
		}
		
		
		
		
		
		// adding games stats to serializable class and writing that class to file
		
		GameStatisticsSerialized gameStatsSerialized = new GameStatisticsSerialized(windowPositionXSetting, windowPositionYSetting, showStatisticsSetting, gamesPlayedNumberHistory, gamesWonNumberHistory, averageTimeOfGameHistory, gamesStats, gameLastStats);
		//System.out.println("\n      GameStats. Printing serialized object game stats before writing");
		//System.out.println(gameStatsSerialized);
				
		try (FileOutputStream fs = new FileOutputStream(dataSerializedFilePath)){  //extension doesnt matter. can be .bin or .dat
			
			ObjectOutputStream os = new ObjectOutputStream(fs);			
			os.writeObject(gameStatsSerialized);					
			os.close();				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {  //error for example when we cant write to that file
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
	protected void readStatsFromFile(){
		
		File file = new File(dataFilePath);
		//System.out.println("      GameStats. File length :" + file.length());   // ???? useless
		
		try(BufferedReader br = new BufferedReader(new FileReader(file));){
			String line;
			
			//sdfsdf
			gamesStats = new ArrayList<GameWonStats>();  // creating new array  for global variable
			
			while( (line = br.readLine()) != null ){
				//System.out.println(line);	
				
				if(  line.compareTo("*** GAME OPTIONS ***")== 0  ){
					//System.out.println("      GameStats. Found game options in file");
					//System.out.println(line);
					
					
					for (int i=0; i<3; i++ ){
						line = br.readLine();
						switch(i){
							case 0:
								//windowPositionXSetting
								
								//System.out.println("      GameStats. options file WinX: " + line.substring(20));
								// ???? check if those are ints
								windowPositionXSetting = convertStringToInt(line.substring(20), "GAME OPTIONS");
								break;
							case 1:
								//windowPositionYSetting
								
								//System.out.println(line);
								windowPositionYSetting = convertStringToInt(line.substring(20), "GAME OPTIONS");
								break;
							case 2:
								//showStatisticsSetting
								
								//System.out.println("      GameStats. options file show stats: " + line.substring(19));
								if( line.substring(19).compareTo("yes") == 0 ){
									showStatisticsSetting = true;
								}else{
									showStatisticsSetting = false;
								}
								break;
						}
					}
					
				
				}else if(  line.compareTo("*** TOTAL GAMES STATISTICS ***")== 0  ){
					//System.out.println(line);
					
					for (int i=0; i<3; i++ ){
						line = br.readLine();
						switch(i){
							case 0:
								//games played
								
								//System.out.println("      GameStats. options file games played: " + line.substring(16));
								gamesPlayedNumberHistory = convertStringToInt(line.substring(16), "GAME STATISTICS");
								break;
							case 1:
								//games won
								
								//System.out.println("      GameStats. options file games won: " + line.substring(13));
								gamesWonNumberHistory = convertStringToInt(line.substring(13), "GAME STATISTICS");
								break;
							case 2:
								//average time of game
								
								//System.out.println("      GameStats. options file average time of game: " + line.substring(24));
								averageTimeOfGameHistory = convertStringToDouble(line.substring(24), "GAME STATISTICS");
								break;
						}
					}
					
					if(  (gamesPlayedNumberHistory == -987654312) || (gamesWonNumberHistory == -987654312) || (averageTimeOfGameHistory == -987654312)  ){
						// somehow reset stats
						
					}
					
					
					
				}else if(  line.compareTo("** GAME 1 **")== 0 || (line.compareTo("** GAME 2 **")== 0) || (line.compareTo("** GAME 3 **")== 0) || (line.compareTo("** GAME 4 **")== 0) || (line.compareTo("** GAME 5 **")== 0) ){
					//System.out.println(line);
					
					readSingleGameOfBest5StatsFromFile(br, line.substring(3, 9));
					
					//gamesStats.add(new GameWonStats(gameTime, gameClicks, gameDate));
					
					
					/*
				}else if(  line.compareTo("** GAME 2 **")== 0  ){
					//System.out.println(line);
					
					String gameNumber = line.substring(3, 9);
					double gameTime = 0;
					int gameClicks = 0;
					String gameDate = "";
					
					for (int i=0; i<3; i++ ){
						line = br.readLine();
						switch(i){
							case 0:
								//game time
								gameTime = convertStringToDouble(line.substring(8), gameNumber);
								break;
							case 1:
								//game clicks
								gameClicks = convertStringToInt(line.substring(10), gameNumber);
								break;
							case 2:
								//game date
								gameDate = line.substring(8);
								break;
						}
					}
					gamesStats.add(new GameWonStats(gameTime, gameClicks, gameDate));
					
				}else if(  line.compareTo("** GAME 3 **")== 0  ){
					//System.out.println(line);
					
					String gameNumber = line.substring(3, 9);
					double gameTime = 0;
					int gameClicks = 0;
					String gameDate = "";
					
					for (int i=0; i<3; i++ ){
						line = br.readLine();
						switch(i){
							case 0:
								//game time
								gameTime = convertStringToDouble(line.substring(8), gameNumber);
								break;
							case 1:
								//game clicks
								gameClicks = convertStringToInt(line.substring(10), gameNumber);
								break;
							case 2:
								//game date
								gameDate = line.substring(8);
								break;
						}
					}
					gamesStats.add(new GameWonStats(gameTime, gameClicks, gameDate));
					
				}else if(  line.compareTo("** GAME 4 **")== 0  ){
					//System.out.println(line);
					
					String gameNumber = line.substring(3, 9);
					double gameTime = 0;
					int gameClicks = 0;
					String gameDate = "";
					
					for (int i=0; i<3; i++ ){
						line = br.readLine();
						switch(i){
							case 0:
								//game time
								gameTime = convertStringToDouble(line.substring(8), gameNumber);
								break;
							case 1:
								//game clicks
								gameClicks = convertStringToInt(line.substring(10), gameNumber);
								break;
							case 2:
								//game date
								gameDate = line.substring(8);
								break;
						}
					}
					gamesStats.add(new GameWonStats(gameTime, gameClicks, gameDate));
					
				}else if(  line.compareTo("** GAME 5 **")== 0  ){
					//System.out.println(line);
					
					String gameNumber = line.substring(3, 9);
					double gameTime = 0;
					int gameClicks = 0;
					String gameDate = "";
					
					for (int i=0; i<3; i++ ){
						line = br.readLine();
						switch(i){
							case 0:
								//game time
								gameTime = convertStringToDouble(line.substring(8), gameNumber);
								break;
							case 1:
								//game clicks
								gameClicks = convertStringToInt(line.substring(10), gameNumber);
								break;
							case 2:
								//game date
								gameDate = line.substring(8);
								break;
						}
					}
					gamesStats.add(new GameWonStats(gameTime, gameClicks, gameDate));
					*/	
				}else if(  line.compareTo("** LAST GAME **")== 0  ){
					//System.out.println(line);
					String gameNumber = line.substring(3, 12);
					double gameTime = 0;
					int gameClicks = 0;
					String gameDate = "";
					
					for (int i=0; i<3; i++ ){
						line = br.readLine();
						switch(i){
							case 0:
								//game time
								gameTime = convertStringToDouble(line.substring(8), gameNumber);
								break;
							case 1:
								//game clicks
								gameClicks = convertStringToInt(line.substring(10), gameNumber);
								break;
							case 2:
								//game date								
								gameDate = line.substring(8);
								break;
						}
					}
					gameLastStats = new GameWonStats(gameTime, gameClicks, gameDate);
					//gameLastStats.writePastGameStats(gameTime, gameClicks, gameDate);
					

				//}else{System.out.println("      GameStats. Didnt find anything in file");
					
				}
				
				
				
				
			} // this ends reading file
			
			boolean anyGame1_5IncorrectData = false;  // checking if any game1-5 from file has incorrect data
			for(GameWonStats value: gamesStats){
				if(  (value.readPastGameStatsTime() == -987654312) || (value.readPastGameStatsClicks() == -987654312)  ){
					anyGame1_5IncorrectData = true;
					break;					
				}				
			}			
			//if(  ( windowPositionXSetting == -987654312) || ( windowPositionYSetting == -987654312) || (gamesPlayedNumberHistory == -987654312) || (gamesWonNumberHistory == -987654312) || (averageTimeOfGameHistory == -987654312) || (anyGame1_5IncorrectData) || (gameLastStats.readPastGameStatsTime() == -987654312) || (gameLastStats.readPastGameStatsClicks() == -987654312)  ){
			/*
			if(  ( windowPositionXSetting == -987654312) || ( windowPositionYSetting == -987654312) || (gamesPlayedNumberHistory == -987654312) || (gamesWonNumberHistory == -987654312) || (averageTimeOfGameHistory == -987654312) || (anyGame1_5IncorrectData)   ){
				// condition if any stat read from file was corrupted      then reset stats
				
				resetStatsCurrentGameAndBest5();
				
			}
			*/
			
			if(  ( windowPositionXSetting == -987654312) || ( windowPositionYSetting == -987654312) || (gamesPlayedNumberHistory == -987654312) || (gamesWonNumberHistory == -987654312) || (averageTimeOfGameHistory == -987654312) || (anyGame1_5IncorrectData) || (gameLastStats == null)   ){
				// condition if any stat read from file was corrupted      then reset stats
				System.out.println("      GameStats. R");
				resetStatsCurrentGameAndBest5();
				writeStatsToFile(999.9d, 0, "2000.01.01");
				
			}else if(  (gameLastStats.readPastGameStatsTime() == -987654312) || (gameLastStats.readPastGameStatsClicks() == -987654312)  ){
				
				resetStatsCurrentGameAndBest5();
				writeStatsToFile(999.9d, 0, "2000.01.01");
			}
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("      GameStats. Reading file ERROR.    Couldnt find file " + file.toString());
			// ????
			resetStatsCurrentGameAndBest5();
			writeStatsToFile(999.9d, 0, "2000.01.01");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("      GameStats. Reading file ERROR.    IOException" + file.toString());
			// ????
			resetStatsCurrentGameAndBest5();
			writeStatsToFile(999.9d, 0, "2000.01.01");
		}
		
		sortGamesStatsArray(); // ???? sorting list. list in file should already be sorted, but its in case some1 played with file
		
		/*// print, to check fast if correct list is read
		for(GameWonStats value: gamesStats){
			System.out.println("zzzzz: " + value.readPastGameStatsClicks());
			
		}*/
		
		
		
		// reading serialized object with games stats
		
		try(FileInputStream fi = new FileInputStream(dataSerializedFilePath)){
			
			ObjectInputStream os = new ObjectInputStream(fi);			
			GameStatisticsSerialized GameStatisticsSerialized = (GameStatisticsSerialized)os.readObject();			
			//System.out.println(GameStatisticsSerialized);			
			os.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	protected void addRecentGameToArray(String time, int clicks, String date){
		// adds just finished game to array, then sorts list by game time
		
		//GameWonStats recentGameStats = new GameWonStats(time, clicks, date);
		GameWonStats recentGameStats = new GameWonStats(Double.parseDouble(time), clicks, date);
		
		//gamesStats.add(recentGameStats); // this will add game to list and list will grow infinitely
		
		// cases to so list size doesnt exceed 5-6
		if( gamesStats.size() == 5 ){
			// case when starting new game
			gamesStats.add(recentGameStats);			
		}else if( gamesStats.size() == 6 ){
			gamesStats.remove(5);
			gamesStats.add(recentGameStats);
			
		}
		//System.out.println("      GameStats. Size of gamesStats" + gamesStats.size());		
		sortGamesStatsArray();
		/*
		for(GameWonStats value: gamesStats){ // ???? showing games after sorting, later hide this
			System.out.println("      GameStats. Games after sorting by time.    " + (gamesStats.indexOf(value)+1) + ".   " + value);
			
		}
		*/
	}
	
	protected void sortGamesStatsArray(){
		// sort list of games by game time
		
		Collections.sort(gamesStats, new Comparator<GameWonStats>(){
		// making own comparator to sort objects in array by game time
			
			@Override
			public int compare(GameWonStats g1, GameWonStats g2) {
				// TODO Auto-generated method stub
				
				if( g1.readPastGameStatsTime() > g2.readPastGameStatsTime() ){
					return 1;					
				}else if( g1.readPastGameStatsTime() < g2.readPastGameStatsTime() ){
					return -1;					
				}
				return 0; // ???? what happens, when games have same times, but different dates. new one should be discarded
			}
		});		
	}
	
	private void readSingleGameOfBest5StatsFromFile(BufferedReader br, String line){
		// reads stats of single game (from best 5) and adds it to ArrayList
		
		String gameNumber = line; // for making actions when reading file goes wrong
		double gameTime = 0;
		int gameClicks = 0;
		String gameDate = "";
		
		for (int i=0; i<3; i++ ){
			try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(i){
				case 0:
					//game time
					
					//System.out.println("      GameStats. options file game 1 time: " + line.substring(8));
					gameTime = convertStringToDouble(line.substring(8), gameNumber);
					break;
				case 1:
					//game clicks
					
					//System.out.println(line);								
					gameClicks = convertStringToInt(line.substring(10), gameNumber);
					break;
				case 2:
					//game date
					
					//System.out.println("      GameStats. options file show stats: " + line.substring(19));
					gameDate = line.substring(8);
					break;
			}
		}
		gamesStats.add(new GameWonStats(gameTime, gameClicks, gameDate));
		
	}
	
	protected void resetStatsCurrentGameAndBest5(){  // resets stats of current game and best 5 games
		//windowPositionXSetting = 0;
		//windowPositionYSetting = 0;
		//showStatisticsSetting = false;
		gamesPlayedNumberHistory = 0;
		gamesWonNumberHistory = 0;
		averageTimeOfGameHistory = 999.9d;  // ???? average 0 taken into average will lowr
		
		/*
		System.out.println("      GameStats. Array GameWonStats  before 'reseting it'");
		for(GameWonStats someGame: gamesStats){
			System.out.println(someGame);			
		}
		*/
		
		
		// games 1-5
		//dgfghgfh doestnt work adds games instead of changing
		
		if(gamesStats.size() == 0){   // this handles when reading stats from file, but there is no file, so creating 'reset data'
			gamesStats = new ArrayList<GameWonStats>();  // creating new array  for global variable
			
			for(int i=0; i<5; i++){
				gamesStats.add(new GameWonStats(999.9d, 0, "2000.01.01"));	
			}
		}else{
			for(int i=0; i<5; i++){
				gamesStats.get(i).writePastGameStats(999.9d, 0, "2000.01.01");		
			}
		}
		
		
		
		/*
		System.out.println("      GameStats. Array GameWonStats  after 'reseting it'");
		for(GameWonStats someGame: gamesStats){
			System.out.println(someGame);			
		}
		*/
		
		
		
		gameLastStats = new GameWonStats(999.9d, 0, "2000.01.01");		
	}
	
	public void setWindowPostionVariables(Point point){
		windowPositionXSetting = point.x;
		windowPositionYSetting = point.y;		
	}
	
	
	public boolean isInt(String str) {
		//checks if string is an int
		
		if (str == null)
			return false;
		
		char[] data = str.toCharArray();
		
		if (data.length <= 0)
			return false;

	    int index = 0;
		if (data[0] == '-' && data.length > 1)
			index = 1;
		
		for (int i=0; i< (data.length - index); i++) {
			if (data[i+index] < '0' || data[i+index] > '9') // Character.isDigit() can go here too.
				return false;
		}
		return true;
	}
	
	
	public boolean isDouble(String str) {
		//checks if string is a double
		
		if (str == null)
			return false;
		
		char[] data = str.toCharArray();
		
		if (data.length <= 0)
			return false;

	    int index = 0;
		if (data[0] == '-' && data.length > 1)
			index = 1;
		boolean wasThereDeciamlSeparator = false;
		for (int i=0; i< (data.length - index); i++) {
			if (data[i+index] < '0' || data[i+index] > '9') 
				if (  (data[i+index] == '.') && !wasThereDeciamlSeparator ){
					wasThereDeciamlSeparator = true;
					
				}else{
					return false;
				}	
		}		
		return true;
	}
		
	public double convertStringToDouble(String text, String gameNumber){		
		if(isDouble(text)){
			try {
				return Double.parseDouble(text);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -987654312;
			}									
		}else{
			// string failed to be a number, depending on what called it, do appropriate actions
			return -987654312;
		}
		// ???? break something, to not read game stats
		//return 0;
	}
	
	public int convertStringToInt(String text, String gameNumber){		
		if(isInt(text)){
			try {
				return Integer.parseInt(text);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -987654312;
			}									
		}else{
			// string failed to be a number, depending on what called it, do appropriate actions
			return -987654312;
		}
		// ???? break something, to not read game stats
		//return 0;
	}
	
	public void changeShowStatisticsSetting(boolean setting){
		if(setting) showStatisticsSetting = true;
		else showStatisticsSetting = false;		
	}
	
	
	
	
	
	
	public void setArrayListenerGameStats5Games(GameGames1_5StatsListener listener){
		this.arrayGameStats5GamesListener = listener;
	}
	
	public void setStringListenerGameStatsNewRecord(ButtonFieldListener listener){
		this.textGameStatsNewRecordListener = listener;
	}
	
	public void setIntListernerGameOptionsStatsWindow(GameNewWonLostListener listener){
		this.intGameOptionsStatsWindowListener = listener;
	}
	
	public void setIntListernerGameOptionsWindowPosX(GameNewWonLostListener listener){
		this.intGameOptionsWindowPosXListener = listener;
	}
	
	public void setIntListernerGameOptionsWindowPosY(GameNewWonLostListener listener){
		this.intGameOptionsWindowPosYListener = listener;
	}	
	
	public void setObjListenerGameStatistics(GameObjListener listener){
		this.objGameStatisticsListener = listener;
	}
	
	
	
	protected void showStatsFromFile(){
		// method for showing stats read from file in console. for testing purposes only
		
		System.out.println("Game. Stats read from file");
		System.out.print("    ** Settings    window PositionXSetting: " + windowPositionXSetting);
		System.out.print("    window PositionYSetting: " + windowPositionYSetting);
		if(showStatisticsSetting)System.out.println("    show StatisticsSetting: YES");
		else System.out.println("    show StatisticsSetting: NO");
		
		System.out.print("    ** Total games statistics    games PlayedNumberHistory: " + gamesPlayedNumberHistory);
		System.out.print("    games WonNumberHistory: " + gamesWonNumberHistory);
		System.out.println("    average TimeOfGameHistory: " + averageTimeOfGameHistory);
		
		for(int i=0; i<5; i++){
			System.out.print("    ** Game " + (i+1) + "stats    time: " + gamesStats.get(i).readPastGameStatsTime());
			System.out.print("    clicks: " + gamesStats.get(i).readPastGameStatsClicks());
			System.out.println("    date: " + gamesStats.get(i).readPastGameStatsDate());
		}		
	}
	
	
	

}
