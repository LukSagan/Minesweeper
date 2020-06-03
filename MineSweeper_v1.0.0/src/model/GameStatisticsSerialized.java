
/*
 * 

copyright 2020 �ukasz Sagan

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

Niniejszy program jest wolnym oprogramowaniem; mo�esz go
rozprowadza� dalej i/lub modyfikowa� na warunkach Powszechnej
Licencji Publicznej GNU, wydanej przez Fundacj� Wolnego
Oprogramowania - wed�ug wersji 3 tej Licencji lub (wed�ug twojego
wyboru) kt�rej� z p�niejszych wersji.

Niniejszy program rozpowszechniany jest z nadziej�, i� b�dzie on
u�yteczny - jednak BEZ JAKIEJKOLWIEK GWARANCJI, nawet domy�lnej
gwarancji PRZYDATNO�CI HANDLOWEJ albo PRZYDATNO�CI DO OKRE�LONYCH
ZASTOSOWA�. W celu uzyskania bli�szych informacji si�gnij do     Powszechnej Licencji Publicznej GNU.

Z pewno�ci� wraz z niniejszym programem otrzyma�e� te� egzemplarz
Powszechnej Licencji Publicznej GNU (GNU General Public License);
je�li nie - zobacz <http://www.gnu.org/licenses/>.
 * 
 */


package model;

import java.io.Serializable;
import java.util.ArrayList;

public class GameStatisticsSerialized implements Serializable{
	
	private static final long serialVersionUID = -1735808850910072335L;
	
	private int windowPositionXSetting = 0; //800
	private int windowPositionYSetting = 0; //500
	private boolean showStatisticsSetting = false;
	
	private int gamesPlayedNumberHistory = 0;
	private int gamesWonNumberHistory = 0;
	private double averageTimeOfGameHistory = 0;
	
	protected ArrayList<GameWonStats> gamesStats = new ArrayList<GameWonStats>();
	protected GameWonStats gameLastStats;
	
	public GameStatisticsSerialized(int windPosXSetting, int windPosYSetting, boolean showStatsSetting, 
			int gamesPlayedNumberHist, int gamesWonNumberHist, double averageTimeOfGameHist, 
			ArrayList<GameWonStats> gamesStatsWr, GameWonStats gameLastStatsWr){
		
		windowPositionXSetting = windPosXSetting;
		windowPositionYSetting = windPosYSetting;
		showStatisticsSetting = showStatsSetting;
		
		gamesPlayedNumberHistory = gamesPlayedNumberHist;
		gamesWonNumberHistory = gamesWonNumberHist;
		averageTimeOfGameHistory = averageTimeOfGameHist;
		
				
		for(GameWonStats gameStats: gamesStatsWr){  // cloning list of objects
			gamesStats.add(gameStats);			
		}
				
		gameLastStats = new GameWonStats(gameLastStatsWr);  // cloning object		
	}

	@Override
	public String toString() {
		return "GameStatisticsSerialized [windowPositionXSetting=" + windowPositionXSetting
				+ ", windowPositionYSetting=" + windowPositionYSetting + ", showStatisticsSetting="
				+ showStatisticsSetting + ", gamesPlayedNumberHistory=" + gamesPlayedNumberHistory
				+ ", gamesWonNumberHistory=" + gamesWonNumberHistory + ", averageTimeOfGameHistory="
				+ averageTimeOfGameHistory + ", gamesStats=" + gamesStats + ", gameLastStats=" + gameLastStats + "]";
	}
	
	
	
	
	
	
	
	
}
