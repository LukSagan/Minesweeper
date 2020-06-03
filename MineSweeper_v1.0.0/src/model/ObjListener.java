
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

public class ObjListener {
	
	private int code;
	private String text;
	private int number;
	private double dNumber;
	private String[] arrayStrings;// = new String[4];
	private int[] arrayInts;
	
	// constants for use as code
	public static final int lastGameStats = 0;
	public static final int overallStatsStats = 1;
	public static final int getWindowPosition = 2;
	public static final int setWindowPosition = 3;
	public static final int checkIfGameIsOngoing = 4;
	public static final int sendRequestResetStatistics = 5;
	public static final int showGameWonLostPopUp = 6;
	public static final int messageGameLost = 0;	
	public static final int messageGameWon = 1;
	public static final int messageGameWonRecord = 2;
	
	public ObjListener(int codeM, String textM, int numberM, double dNumberM, String[] array4StringsM) {
		super();
		
		code = codeM;
		text = textM;
		number = numberM;
		dNumber = dNumberM;
		arrayStrings = array4StringsM;
	}
	
	private void resetVariables(){
		code = -1;
		text = "";
		number = 0;
		dNumber = 0.0F;
		arrayStrings = null;
		arrayInts = null;
	}
	
	
	public ObjListener(int codeM){
		super();
		resetVariables();
		code = codeM;
	}
	
	public ObjListener(int codeM, int[] arrayIntsM){
		super();
		resetVariables();
		code = codeM;
		arrayInts = arrayIntsM;
	}
	
	public ObjListener(int codeM, int numberSent){
		super();
		resetVariables();
		code = codeM;
		number = numberSent;
	}
	
	
	
	
	public int getCode(){		
		return code;
	}
	
	public String getText(){		
		return text;
	}
	
	public int getNumber(){		
		return number;
	}
	
	public double getDNumber(){		
		return dNumber;
	}
	
	public String[] getArrayStrings(){		
		return arrayStrings;
	}
	
		
	public int[] getArrayInts(){		
		return arrayInts;
	}
	
	
}
