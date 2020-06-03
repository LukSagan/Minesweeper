
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

package controller;

import java.awt.Point;
import java.util.ArrayList;

import gui.ButtonFieldListener;
import model.Game;
import model.GameGames1_5StatsListener;
import model.GameNewWonLostListener;
import model.GameObjListener;
import model.GameWonStats;
import model.ObjListener;

public class Controller {	
	
	private Game newGame;
	private ButtonFieldListener textGameMFieldRevealingListener;
	private GameNewWonLostListener intGameMWonLostListener;
	private ButtonFieldListener textGameMRemainingMines;
	private ButtonFieldListener textGameMTimerListener;
	private GameGames1_5StatsListener arrayGameMStats5GamesListener;
	private ButtonFieldListener textGameMStatsNewRecordListener;
	private GameNewWonLostListener intGameMOptionsStatsWindowListener;
	private GameNewWonLostListener intGameMOptionsWindowPosXListener;
	private GameNewWonLostListener intGameMOptionsWindowPosYListener;
	private GameObjListener objGameMStatisticsListener;
		
	public Controller(){
		super();    //calling superclass constructor
		
		newGame = new Game();
		
		
		
		// ***************************    LISTENERS    ***************************  
		// from Game to Mainframe and then to other objects
		
		
		newGame.setStringListenerFieldRevealing(new ButtonFieldListener(){   // listener of FieldRevealing

			@Override
			public void textEmitted(String text) {
				// TODO Auto-generated method stub
				
				//System.out.println("    Controller. Game text listener FieldRevealing");
				
				if(textGameMFieldRevealingListener != null ){
					//System.out.println("      Game. text field listener");
					textGameMFieldRevealingListener.textEmitted(text);
				}				
			}			
		});
		
		
		newGame.setIntListenerGameNewWonLost(new GameNewWonLostListener(){   // listener of GameNewWonLost

			@Override
			public void numberEmitted(int won) {
				// TODO Auto-generated method stub
				
				if(intGameMWonLostListener != null ){
					//System.out.println("      Game. text field listener");

					intGameMWonLostListener.numberEmitted(won);
				}				
			}
		});
		
		
		newGame.setStringListernerRemainingMines(new ButtonFieldListener(){   // listener of RemainingMines

			@Override
			public void textEmitted(String text) {
				// TODO Auto-generated method stub
				
				//System.out.println("    Controller. Game remaining mines listener: " + text);

				if(textGameMRemainingMines != null ){
					//System.out.println("      Game. text field listener");

					textGameMRemainingMines.textEmitted(text);
				}
			}
		});
		
		
	
		newGame.setStringListernerGameGUITimer(new ButtonFieldListener(){   // listener of GameTimer

			@Override
			public void textEmitted(String text) {
				// TODO Auto-generated method stub
				
				//System.out.println("    Controller. Game   game time: " + text);
				
				if(textGameMTimerListener != null ){
					//System.out.println("      Game. text field listener");
					textGameMTimerListener.textEmitted(text);
				}
			}
		});
		
				
		
		newGame.setArrayListenerGameStats5Games(new GameGames1_5StatsListener(){

			@Override
			public void setEmitted(ArrayList<GameWonStats> set) {
				// TODO Auto-generated method stub
				
				//System.out.println("    Controller. Game listener, send new set of 5 games to display in statistics");
				
				if(arrayGameMStats5GamesListener != null){					
					arrayGameMStats5GamesListener.setEmitted(set);
				}				
			}	
		});
		
		
		
		newGame.setStringListenerGameStatsNewRecord(new ButtonFieldListener(){

			@Override
			public void textEmitted(String text) {
				// TODO Auto-generated method stub
				
				//System.out.println("    Controller. Game new record listener. " + text);
				
				if(textGameMStatsNewRecordListener != null){					
					textGameMStatsNewRecordListener.textEmitted(text);
				}				
			}		
		});
		
		
		// OBJECT listener
		newGame.setObjListenerGameStatistics(new GameObjListener(){
			@Override
			public void objectEmitted(ObjListener object) {
				// TODO Auto-generated method stub
				//System.out.println("    Controller. Listener OBJECT. code: " + object.getCode());		
				
				if(objGameMStatisticsListener != null){
					objGameMStatisticsListener.objectEmitted(object);
				}				
			}			
		});
		
				
		
		newGame.setIntListernerGameOptionsWindowPosX(new GameNewWonLostListener(){

			@Override
			public void numberEmitted(int won) {
				// TODO Auto-generated method stub
				
				if(intGameMOptionsWindowPosXListener != null ){
					//System.out.println("    Controller. WindowPos X listener: " + won);

					intGameMOptionsWindowPosXListener.numberEmitted(won);
				}				
			}
		});
		
		
		
		newGame.setIntListernerGameOptionsWindowPosY(new GameNewWonLostListener(){

			@Override
			public void numberEmitted(int won) {
				// TODO Auto-generated method stub
				
				if(intGameMOptionsWindowPosYListener != null ){
					//System.out.println("    Controller. WindowPos Y listener: " + won);

					intGameMOptionsWindowPosYListener.numberEmitted(won);
				}				
			}
		});
		
		
		
		newGame.setIntListernerGameOptionsStatsWindow(new GameNewWonLostListener(){

			@Override
			public void numberEmitted(int won) {
				// TODO Auto-generated method stub
				
				if(intGameMOptionsStatsWindowListener != null ){
					//System.out.println("    Controller. Options Stats Window listener: " + won);					

					intGameMOptionsStatsWindowListener.numberEmitted(won);
				}				
			}
		});
		
		
		
		
		
		
	} // ends conructor
	
	
	public void createNewGame(){ // this is how mainFrame calls making new game
		newGame.newGame();		
	}
		
	
	public boolean returnIfGameIsNotRunning(){    // to allow action when clicking 'new game' buttons
		return newGame.returnIfGameIsNotRunning();
	}
	
	
	public void remoteFinishGame(){    // used when new game interrupts existing game
		newGame.remoteFinishGame();
	}
	
	
	public void fieldLeftClickedSendToGame(String text){
		// sends text about clicked field from Minefield to Game
		
		//System.out.println("    Controller. Clicked field: " + text);		
		newGame.fieldLeftClicked(text);
	}
	
	
	public void fieldRightClickedSendToGame(String text){
		// sends text about right clicked field from Minefield to Game
		
		newGame.fieldRightClicked(text);
	}
	
	
	public void changeGameStatisticsShowStatisticsSetting(boolean setting){
		newGame.changeShowStatisticsSetting(setting);
	}
	
	
	public void setGameWindowPostionVariables(Point point){
		newGame.setWindowPostionVariables(point);		
	}
	
	public void resetGamesStatistics(){
		newGame.resetGamesStats();		
	}
	
	public void writeGameSettingsToFile(){
		newGame.writeSettingsToFile();	
	}
	
	
	public void setStringListener(ButtonFieldListener listener){
		this.textGameMFieldRevealingListener = listener;		
	}
	
	
	public void setIntListener(GameNewWonLostListener listener){
		this.intGameMWonLostListener = listener;		
	}
	
	public void setStringListenerRemainingMines(ButtonFieldListener listener){
		this.textGameMRemainingMines = listener;		
	}	
		
	public void setStringListenerGameGUITimer(ButtonFieldListener listener){
		this.textGameMTimerListener = listener;		
	}
		
	public void setArrayListenerGameStats5Games(GameGames1_5StatsListener listener){
		this.arrayGameMStats5GamesListener = listener;
	}
	
	public void setStringListenerGameStatsNewRecord(ButtonFieldListener listener){
		this.textGameMStatsNewRecordListener = listener;
	}
	
	public void setIntListernerGameOptionsStatsWindow(GameNewWonLostListener listener){
		this.intGameMOptionsStatsWindowListener = listener;
	}
	
	public void setIntListernerGameOptionsWindowPosX(GameNewWonLostListener listener){
		this.intGameMOptionsWindowPosXListener = listener;
	}
	
	public void setIntListernerGameOptionsWindowPosY(GameNewWonLostListener listener){
		this.intGameMOptionsWindowPosYListener = listener;
	}
	
	public void setobjListenersetGameStatistic(GameObjListener listener){
		this.objGameMStatisticsListener = listener;
	}
	
	
	
	

}