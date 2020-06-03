
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

package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import model.GameObjListener;
import model.GameWonStats;
import model.ObjListener;

public class StatsPanel extends JPanel implements ActionListener{
	
	private JFrame mainFrameReference;
	
	private JLabel lastGameLabel1;
	private JLabel lastGameLabel2;
	private JLabel infoRecordLabel;
	
	private JLabel titleBest5GamesStatsLabel;
	private static JLabel[][] labelsBest5Games;    // stores labels (time, clicks) of best 5 games
	private JLabel titleOverallStatsLabel;
	private JLabel gamesPlayedTLabel;
	private JLabel gamesPlayedLabel;
	private JLabel gamesWonTLabel;
	private JLabel gamesWonLabel;
	private JLabel percenWonTLabel;
	private JLabel percenWonLabel;
	private JLabel avgGameTimeTLabel;
	private JLabel avgGameTimeLabel;
	
	private JLabel dummy1Label;
	private JLabel dummy2Label;	
	private JButton resetStatsButton;
	
	private boolean shouldFill = true;
	private boolean shouldWeightX = true;
	private boolean RIGHT_TO_LEFT = false;
	
	protected GameObjListener objStatsPanelStatisticsListener;  // universal listener, sends ints, Strings, Arrays depending how its set up
	
	
	public StatsPanel(){
		// creates stats pane
		super();
		
		lastGameLabel1 = new JLabel("lastGameLabel1");
		lastGameLabel2 = new JLabel("lastGameLabel2");
		infoRecordLabel = new JLabel("infoRecordLabel");
		
		titleBest5GamesStatsLabel = new JLabel("Najlepsze 5 gier");
		labelsBest5Games = new JLabel[5][6];
		
		titleOverallStatsLabel = new JLabel("Ogólne statystyki");
		gamesPlayedTLabel = new JLabel("Rozegrane gry:");
		gamesPlayedLabel = new JLabel("gamesPlayedLabel");
		gamesWonTLabel = new JLabel("Wygrano:");
		gamesWonLabel = new JLabel("gamesWonLabel");
		percenWonTLabel = new JLabel("Procent wygranych:  ");
		percenWonLabel = new JLabel("percenWonLabel");
		avgGameTimeTLabel = new JLabel("Œredni czas gry:");
		avgGameTimeLabel = new JLabel("avgGameTimeLabel");
		dummy1Label = new JLabel("");
		dummy2Label = new JLabel("");
		
		resetStatsButton = new JButton("Zresetuj statystyki");
		
		/*
		Border innerBorder = BorderFactory.createTitledBorder("Add Person");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		*/
		//setPreferredSize(new Dimension(10, 170));
		//setPreferredSize(new Dimension(10, 200));
		
		Dimension dim = getPreferredSize();
		dim.height = 240;  // 250
		setPreferredSize(dim);
		
		
		
		
		//Border innerBorder = BorderFactory.createEtchedBorder();
		Border innerBorder = BorderFactory.createTitledBorder("Statystyki");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		//Border outerBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraOne = new GridBagConstraints();
		
		if (shouldFill) {
            //natural height, maximum width
			//constraOne.fill = GridBagConstraints.HORIZONTAL;
			//constraOne.fill = GridBagConstraints.CENTER;
			constraOne.fill = GridBagConstraints.BOTH;
			constraOne.anchor=GridBagConstraints.CENTER;
		}
		
		
		if (shouldWeightX) {
			constraOne.weightx = 0.5;
		}
		
		// ********* last game, infos *********
		
		constraOne.gridwidth = 8;
		addItemToGridBagLayout(constraOne, 0, 0, lastGameLabel1, null);
		addItemToGridBagLayout(constraOne, 0, 1, lastGameLabel2, null);
		
		//infoRecordLabel.setPreferredSize(new Dimension(1, 40));
		//constraOne.ipady = 20;      // this will make next items take more vertical space.    add constraOne.ipady = 0; later, so its reset
		constraOne.insets = new Insets(10,0,10,0);  // padding
		addItemToGridBagLayout(constraOne, 0, 2, infoRecordLabel, null);
		//constraOne.ipady = 0;
		constraOne.insets = new Insets(0,0,0,0);
		
		// ********* best 5 games *********
		
		int graphicalShiftBest5GamesGeneralStatsPositionY = 3;   // to easily move whole party row down, up when adding something between
		constraOne.gridwidth = 6;		
		int paddingTitlesBest5OverallStats = 5;
		constraOne.insets = new Insets(0,0,paddingTitlesBest5OverallStats,0);  // padding
		addItemToGridBagLayout(constraOne, 0, graphicalShiftBest5GamesGeneralStatsPositionY, titleBest5GamesStatsLabel, null);
		constraOne.gridwidth = 1;
		constraOne.insets = new Insets(0,0,0,0);
		for(int i=0; i<5; i++){  // labels with best 5 games stats
			JLabel label = new JLabel((i+1) + ". Czas: "); // game + time
			int width = 56;
			int height = 12;
			label.setMinimumSize(new Dimension(width, height));
			label.setMaximumSize(new Dimension(width, height));
			label.setPreferredSize(new Dimension(width, height));
			addItemToGridBagLayout(constraOne, 0, (i+1+graphicalShiftBest5GamesGeneralStatsPositionY), label, null);
			labelsBest5Games[i][0] = label;
			
			label = new JLabel("time" + (i+1));
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			width = 30;
			height = 12;
			label.setMinimumSize(new Dimension(width, height));
			label.setMaximumSize(new Dimension(width, height));
			label.setPreferredSize(new Dimension(width, height));
			addItemToGridBagLayout(constraOne, 1, (i+1+graphicalShiftBest5GamesGeneralStatsPositionY), label, null);
			labelsBest5Games[i][1] = label;
			
			label = new JLabel("klikniêæ: ");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			width = 60;
			height = 12;
			label.setMinimumSize(new Dimension(width, height));
			label.setMaximumSize(new Dimension(width, height));
			label.setPreferredSize(new Dimension(width, height));
			addItemToGridBagLayout(constraOne, 2, (i+1+graphicalShiftBest5GamesGeneralStatsPositionY), label, null);
			labelsBest5Games[i][2] = label;
			
			label = new JLabel("clicks" + (i+1));
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			width = 27;
			height = 12;
			label.setMinimumSize(new Dimension(width, height));
			label.setMaximumSize(new Dimension(width, height));
			label.setPreferredSize(new Dimension(width, height));			
			addItemToGridBagLayout(constraOne, 3, (i+1+graphicalShiftBest5GamesGeneralStatsPositionY), label, null);
			labelsBest5Games[i][3] = label;
			
			label = new JLabel("" );
			addItemToGridBagLayout(constraOne, 4, (i+1+graphicalShiftBest5GamesGeneralStatsPositionY), label, null);
			labelsBest5Games[i][4] = label;
			
			label = new JLabel("date" + (i+1));
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			width = 95;
			height = 12;
			label.setMinimumSize(new Dimension(width, height));
			label.setMaximumSize(new Dimension(width, height));
			label.setPreferredSize(new Dimension(width, height));	
			addItemToGridBagLayout(constraOne, 5, (i+1+graphicalShiftBest5GamesGeneralStatsPositionY), label, null);
			labelsBest5Games[i][5] = label;
		}				
		
		
		
		// ********* overall stats *********
		
		constraOne.gridwidth = 2;
		constraOne.insets = new Insets(0,0,paddingTitlesBest5OverallStats,0);  // padding
		addItemToGridBagLayout(constraOne, 6, graphicalShiftBest5GamesGeneralStatsPositionY, titleOverallStatsLabel, null);
		constraOne.gridwidth = 1;
		constraOne.insets = new Insets(0,0,0,0);	
		
		addItemToGridBagLayout(constraOne, 6, graphicalShiftBest5GamesGeneralStatsPositionY+1, gamesPlayedTLabel, null);
		addItemToGridBagLayout(constraOne, 7, graphicalShiftBest5GamesGeneralStatsPositionY+1, gamesPlayedLabel, null);
		addItemToGridBagLayout(constraOne, 6, graphicalShiftBest5GamesGeneralStatsPositionY+2, gamesWonTLabel, null);
		addItemToGridBagLayout(constraOne, 7, graphicalShiftBest5GamesGeneralStatsPositionY+2, gamesWonLabel, null);
		addItemToGridBagLayout(constraOne, 6, graphicalShiftBest5GamesGeneralStatsPositionY+3, percenWonTLabel, null);
		//constraOne.weightx = 1.0;  // useless
		percenWonLabel.setMinimumSize(new Dimension(42, 10));
		percenWonLabel.setMaximumSize(new Dimension(42, 10));
		percenWonLabel.setPreferredSize(new Dimension(42, 10));
		//percenWonLabel.setMaximumSize(new Dimension(10, 10));
		//percenWonLabel.setPreferredSize(new Dimension(10, 10));		
		//constraOne.weightx = 1.0;  // useless
		addItemToGridBagLayout(constraOne, 7, graphicalShiftBest5GamesGeneralStatsPositionY+3, percenWonLabel, null);
		//percenWonLabel.setPreferredSize(new Dimension(50, 400));
		//constraOne.weightx = 0.5;   // useless
		addItemToGridBagLayout(constraOne, 6, graphicalShiftBest5GamesGeneralStatsPositionY+4, avgGameTimeTLabel, null);
		addItemToGridBagLayout(constraOne, 7, graphicalShiftBest5GamesGeneralStatsPositionY+4, avgGameTimeLabel, null);
		
		addItemToGridBagLayout(constraOne, 6, graphicalShiftBest5GamesGeneralStatsPositionY+5, dummy1Label, null);
		addItemToGridBagLayout(constraOne, 6, graphicalShiftBest5GamesGeneralStatsPositionY+6, dummy2Label, null);
		
		
		
		// ********* reset button *********
		
		int graphicalShiftResetButtonY = graphicalShiftBest5GamesGeneralStatsPositionY+6;   // to easily move whole party row down, up when adding something between
		constraOne.gridwidth = 2;
		resetStatsButton.addActionListener(this);
		addItemToGridBagLayout(constraOne, 6, graphicalShiftResetButtonY, null, resetStatsButton);
		
		//setPreferredSize(new Dimension(10, 170));		// moving up to test border
		
		//labelsBest5Games[0][1].setText("10");   // ???? test
		
		
		
	}
	
	private void addItemToGridBagLayout(GridBagConstraints constraOne, int x, int y, JLabel someLabel, JButton someButton){
		// makes adding items easier
		//System.out.println("StatsPanel. Adding item to grid bag layout");
		constraOne.fill = GridBagConstraints.HORIZONTAL;
		constraOne.weightx = 0.5;
		constraOne.gridx = x;		
		constraOne.gridy = y;
		if(someLabel != null) add(someLabel, constraOne);
		else if(someButton != null) add(someButton, constraOne);		
	}
	
	
	
	
	//public void changeLastGameLabel(String text){
	public void changeLastGameLabel(String[] stringArray){
		//won/lost     time      clicks     cliks: fast, slow, avg    flags  
		//when new game is loaded, just time, clicks, date		
		/*
		if(stringArray[3].compareTo("-1") != 0){
			lastGameLabel1.setText("Ostani¹ grê " + stringArray[0] + ". Czas: " + stringArray[1] + ". Klikniêæ: " + stringArray[2] + 
				". Œredni czas klikniêcia: " + stringArray[3] + ". Najszybszy: " + stringArray[4] + 
				". Najwolniejszy: " + stringArray[5] + "U¿yto " + stringArray[6] + " flag.");			
		}else{
			lastGameLabel1.setText("Czas ostatniej gry: " + stringArray[1] + ". Klikniêæ: " + stringArray[2] + ". Data: " + stringArray[0]);
		}
		*/
		
		
		
		if(stringArray[3].compareTo("-1") != 0){
			/*
			lastGameLabel1.setText("Ostani¹ grê " + stringArray[0] + ". Czas: " + stringArray[1] + ". Klikniêæ: " + stringArray[2] + 
				". Œredni czas klikniêcia: " + stringArray[3] + ". Najszybszy: " + stringArray[4] + 
				". Najwolniejszy: " + stringArray[5] + "U¿yto " + stringArray[6] + " flag.");
			lastGameLabel2.setText("stats");
			*/
			
			lastGameLabel1.setText("Ostani¹ grê " + stringArray[0] + ".     Czas:  " + stringArray[1] + " sek.     Klikniêæ:  " + stringArray[2] + ".     U¿yto flag:  " + stringArray[6] + ".");
					
			lastGameLabel2.setText("Œredni czas klikniêcia:  " + stringArray[3] + ".     Najszybsze:  " + stringArray[4] + 
				".     Najwolniejsze:  " + stringArray[5] + ".");
		
			
			
		}else{
			lastGameLabel1.setText("Czas ostatniej gry:  " + stringArray[1] + " sek.   Klikniêæ:  " + stringArray[2] + ".   Data:  " + stringArray[0]);
			lastGameLabel2.setText(" ");
		}
		
		
		
		
		
	}
		
	public void changeInfoRecordLabel(String text){  // DONE         ????
		infoRecordLabel.setText(text);
		
	}
	
	public void changeGames1_5Label(ArrayList<GameWonStats> set){  // DONE         ????
		for(int i=0; i<5; i++){
			labelsBest5Games[i][1].setText(Double.toString(set.get(i).readPastGameStatsTime()));
			labelsBest5Games[i][3].setText(Integer.toString(set.get(i).readPastGameStatsClicks()));			
			//labelsBest5Games[i][5].setText(set.get(i).readPastGameStatsDate());
			labelsBest5Games[i][5].setText(set.get(i).readPastGameStatsDate() + "       ");  // giving some space between 'best 5 games' and 'overall stats'
		}
	}
	
	public void changeoOverallStatsLabels(String[] stringArray){
		gamesPlayedLabel.setText(stringArray[0]);
		gamesWonLabel.setText(stringArray[1]);
		percenWonLabel.setText(stringArray[2] + "%");
		avgGameTimeLabel.setText(stringArray[3]);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// actions when clicking button Reset statistics   (check if game is ongoing then listener in mainframe will do actions)
		
		//System.out.println("StatsPanel. Reset stats button was clicked. Source: " + clicked.getText());		
		if(objStatsPanelStatisticsListener != null ){
			//System.out.println("StatsPanel. Reset stats listener worked");			
			objStatsPanelStatisticsListener.objectEmitted(new ObjListener(ObjListener.checkIfGameIsOngoing));
		}		
	}
	
	public void tryToResetGamesStatsMessages(boolean gameOngoing){
		if(gameOngoing) showMessageRequestResetStats();
		else showMessageCannotResetStats();
		
	}
	
	private void showMessageRequestResetStats(){
		// used when trying to resets games stats and game is not ongoing
		
		JOptionPane pane = new JOptionPane();
		Object[] options = {"Tak", "Nie"};
		@SuppressWarnings("static-access")
		//int reply = pane.showOptionDialog((JFrame)null,
		int reply = pane.showOptionDialog(mainFrameReference,
				"Czy na pewno chcesz zresetowaæ statystyki?",
				"Resetowanie statystyk",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);				
		switch(reply){
		case JOptionPane.YES_OPTION: System.out.println("StatsPanel. Reset stats button. Selected: YES. " + reply);
			//
			if(objStatsPanelStatisticsListener != null ){
				//System.out.println("StatsPanel. Reset stats listener worked");			
				objStatsPanelStatisticsListener.objectEmitted(new ObjListener(ObjListener.sendRequestResetStatistics));
			}
			break;
		case JOptionPane.NO_OPTION: //System.out.println("StatsPanel. Reset stats button. Selected: NO. " + reply);
			break;
		case JOptionPane.CLOSED_OPTION: //System.out.println("StatsPanel. Reset stats button. Selected: Nothing was selected. " + reply);
			break;
		}	
	}
	
	private void showMessageCannotResetStats(){
		// used when trying to resets games stats and game is ongoing
		
		JOptionPane pane = new JOptionPane();
		//pane.showMessageDialog((JFrame)null, "Aby zresetowaæ statystyki nale¿y zakoñczyæ aktualn¹ grê", "Próba resetowania statystyk", JOptionPane.INFORMATION_MESSAGE);
		pane.showMessageDialog(mainFrameReference, "Aby zresetowaæ statystyki nale¿y zakoñczyæ aktualn¹ grê", "Próba resetowania statystyk", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	
	public void getFrameReference(JFrame frameReference){ // 
		mainFrameReference = frameReference;
	}
	
	
	
	
	public void setObjListenerStatsPanel(GameObjListener listener){
		this.objStatsPanelStatisticsListener = listener;
	}
	
	
	
	
}
