
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


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

//sysout spaces  

public class Minefield extends JPanel implements ActionListener, MouseListener {
	
	private ButtonFieldListener textMinefieldFieldListenerLeftMouseClick;
	private ButtonFieldListener textMinefieldFieldListenerRightMouseClick;
	private static JButton[][] buttonsReferences;    // variable to store references of dynamically created buttons/fields
	
	
	private final static int MINEFIELD_SIZE = 9;  // square
	
	private boolean shouldFill = true;
	private boolean shouldWeightX = true;
	private boolean RIGHT_TO_LEFT = false;
	
	public int testInt;    // added it to check if MainFrame can see it  ????
	public int intToolbarTest;
	private int[] defaultMineBackground = {200, 200, 200};      // RGB codes 
	
	private JButton menuButton;
	
	
	public Minefield(){
		// creates minefield fields
		
		super();
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraOne = new GridBagConstraints();
		
		if (shouldFill) {
            //natural height, maximum width
			//constraOne.fill = GridBagConstraints.HORIZONTAL;
			//constraOne.fill = GridBagConstraints.CENTER;
			constraOne.fill = GridBagConstraints.BOTH;
			constraOne.anchor=GridBagConstraints.CENTER;
		}
		
		
		buttonsReferences = new JButton[MINEFIELD_SIZE][MINEFIELD_SIZE];    // creating variable to store buttons/fields references
		for (int i=0; i<MINEFIELD_SIZE; i++){
			for (int j=0; j<MINEFIELD_SIZE; j++){
				
				//JLabel lbl = new JLabel( i + "." + j);
				//JButton lbl = new JButton( i + "." + j);        // just a button name
				JButton lbl = new JButton("");
				//JButton lbl = new JButton( "101" );        // just a button name ????
				
				if ( (i == 0) && (j == 0) ){
					
					if (shouldWeightX) {
						constraOne.weightx = 0.5;
					}
				}
				
				lbl.setHorizontalAlignment(SwingConstants.CENTER);
				constraOne.fill = GridBagConstraints.HORIZONTAL;
				constraOne.weightx = 0.5;
				constraOne.gridx = i;
				constraOne.gridy = j;
				int fieldSideSize = 45;   // 35 are the biggest.  height changes, but width doesnt
				lbl.setPreferredSize(new Dimension(fieldSideSize, fieldSideSize));
				//lbl.setBackground(Color.WHITE);
				lbl.setBackground(new Color(defaultMineBackground[0],defaultMineBackground[0],defaultMineBackground[0]));
				
				add(lbl, constraOne);
				
				lbl.addActionListener(this);
				lbl.addMouseListener(this);
				
				
				
				
				buttonsReferences[i][j] = lbl;
				
				
				//System.out.println("i: " + i + "  j: " + j);
				
			}			
		}
		
		
		// test to check if I really store references to dynamically created buttons/fields
		/*
		JButton test1 = buttonsReferences[2][2];
		System.out.println("Button was clicked: " + test1.getText());
		*/
		
		
		
		
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void testMethod(){
		
		System.out.println("  Minefield. testMethod  wiadomosc testowa");
	}
	
	
	
	@Override	
	public void actionPerformed(ActionEvent e) {
		// method to perform action after clicking any minefield button 
		
		JButton clicked = (JButton)e.getSource();  // (JButton)  casts event 
		//System.out.println("Minefield. Button was clicked: " + clicked.getText());
		
		
		String clickedField = "empty";
		
		//clicked.setEnabled(false);    // ????
		for (int i=0; i<MINEFIELD_SIZE; i++){     
			// looking for button coordinates
			for (int j=0; j<MINEFIELD_SIZE; j++){
				if (clicked == buttonsReferences[i][j]){
					//System.out.println("  Minefield. Found button in references. Coordinates: " + i + "."  + j);
					clickedField = i + "."  + j;
					// ????
					//buttonsReferences[i][j].setEnabled(false);    // this 'disables' all buttons
					
					//buttonsReferences[i][j].setFocusable(false);
					//buttonsReferences[i][j].setSelected(true);
					//buttonsReferences[i][j].setBorderPainted(false);
					//buttonsReferences[i][j].setOpaque(false);
					
					
					//buttonsReferences[8][8].setEnabled(false);    // this works on single button
					break;
				}	
			}			
		}
		
		
		
		if(textMinefieldFieldListenerLeftMouseClick != null ){
			//System.out.println("      Minefield. Hello button was clicked");
			
			textMinefieldFieldListenerLeftMouseClick.textEmitted(clickedField);
		}			
	}
	
	
	public void changeMinefieldFieldLook(String code){
		// changes field's look
		// example code: "8.4.0"  for normal fields,      "8.4.01" for  flagged fields
		//System.out.println("  Minefield. Text from game to minefield: " + code + " Char at 0: " + Integer.parseInt(String.valueOf(code.charAt(0))));
		
		
		// cooridinates of button to edit +  type of button (blank, number, mine)
		int i = Integer.parseInt(String.valueOf(code.charAt(0)));
		int j = Integer.parseInt(String.valueOf(code.charAt(2)));
		//int type = Integer.parseInt(String.valueOf(code.charAt(4)));
		String type = String.valueOf(code.charAt(4));
		

		if (code.length() == 5){   // blanks, mines, numbers
			if (type.charAt(0) == "0".charAt(0)){	// blanks
				// this is embedded in IF's for now. later on probably just 3 lines of code
				
				buttonsReferences[i][j].setBackground(new Color(255,255,255));
				
			}else if (type.charAt(0) == "9".charAt(0)){   // mines
				//System.out.println("  Minefield. Changing button name to type of field: " + type);
				//buttonsReferences[i][j].setIcon(scaleImageIco("src/images/020_minefield_mine.png", 20, 20));    //49x35
				buttonsReferences[i][j].setIcon(scaleImageIco("/images/020_minefield_mine.png", 20, 20));    //49x35
			}else if (type.charAt(0) == "r".charAt(0)){   // clicked mine, game end
				//System.out.println("  Minefield. Changing button name to type of field: " + type);

				//buttonsReferences[i][j].setIcon(scaleImageIco("src/images/022_minefield_mine_clicked.png", 20, 20));    //49x35
				buttonsReferences[i][j].setIcon(scaleImageIco("/images/022_minefield_mine_clicked.png", 20, 20));    //49x35
			}else if (type.charAt(0) == "g".charAt(0)){   // clicked mine, game end
				//System.out.println("  Minefield. Changing button name to type of field: " + type);

				//buttonsReferences[i][j].setIcon(scaleImageIco("src/images/024_minefield_mine_not_clicked.png", 20, 20));    //49x35
				buttonsReferences[i][j].setIcon(scaleImageIco("/images/024_minefield_mine_not_clicked.png", 20, 20));    //49x35	
			} else{    // numbers
				
				
				//System.out.println("  Minefield. Changing button name to type of field: " + type);

				switch(  Integer.parseInt(  String.valueOf(type.charAt(0))  )  ){
					case 1:
						buttonsReferences[i][j].setForeground(Color.GREEN);
						//buttonsReferences[i][j].setText("<html><font color = red>3</font></html>");
						//buttonsReferences[i][j].setText("<html><p style='color:red'>This is a paragraph.</p></html>");
						break;
					case 2:
						buttonsReferences[i][j].setForeground(Color.BLUE);
						break;
					case 3:
						buttonsReferences[i][j].setForeground(Color.RED);
						break;
					case 4:
						buttonsReferences[i][j].setForeground(Color.MAGENTA);
						break;
					case 5:
						buttonsReferences[i][j].setForeground(Color.DARK_GRAY);
						break;
					case 6:
						buttonsReferences[i][j].setForeground(Color.ORANGE);
						break;
					case 7:
						buttonsReferences[i][j].setForeground(Color.PINK);
						break;
					case 8:
						buttonsReferences[i][j].setForeground(Color.YELLOW);
						break;				
				}
				
				
				buttonsReferences[i][j].setBackground(new Color(255,255,255));
				buttonsReferences[i][j].setFont(new Font("Dialog", Font.BOLD, 20));
				//button.setFont(new Font("Arial", Font.BOLD, 20));
				buttonsReferences[i][j].setText(type);
				
			}
		}else if (code.length() == 6){
			// flagging/unflagging fields
			
			if (code.charAt(5) == "1".charAt(0)){
				// flag field
				
				//System.out.println("  Minefield. Flagging field");
				//buttonsReferences[i][j].setIcon(scaleImageIco("src/images/021_minefield_flag.png", 20, 20));    //49x35
				buttonsReferences[i][j].setIcon(scaleImageIco("/images/021_minefield_flag.png", 20, 20));    //49x35
			} else if (code.charAt(5) == "0".charAt(0)){
				// unflag field
				
				//System.out.println("  Minefield. UnFlagging field");
				buttonsReferences[i][j].setIcon(null);
			} else if (code.charAt(5) == "r".charAt(0)){
				// incorrectly flagged field
				
				//buttonsReferences[i][j].setIcon(scaleImageIco("src/images/023_minefield_flag_incorrect.png", 20, 20));    //49x35
				buttonsReferences[i][j].setIcon(scaleImageIco("/images/023_minefield_flag_incorrect.png", 20, 20));    //49x35
			}
			
			
			
			
		}
		
		
		
		
	}
	
	
	public void resetFieldsLooks(){
		// resets fields look to look as unclicked. method run when doing new game
		
		for (int i=0; i<MINEFIELD_SIZE; i++){
			for (int j=0; j<MINEFIELD_SIZE; j++){				
				buttonsReferences[i][j].setText(null);
				buttonsReferences[i][j].setIcon(null);
				//buttonsReferences[i][j].setEnabled(true);  // ????
				buttonsReferences[i][j].setBackground(new Color(defaultMineBackground[0],defaultMineBackground[0],defaultMineBackground[0]));
				
			}			
		}		
	}
	
	
		
	private ImageIcon scaleImageIco(String imgIcoLocation, int resizedW, int resizedH){
		// method for resizing images (image icons). Used for graphic icons. Reworked method from internet
		
		//ImageIcon imageIcon = new ImageIcon(imgIcoLocation); // load the image to a imageIcon
		//ImageIcon imageIcon = new ImageIcon(  MainFrame.class.getResource(  imgIcoLocation)  ); // load the image to a imageIcon
		ImageIcon imageIcon = new ImageIcon(  Minefield.class.getResource(  imgIcoLocation)  ); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(resizedW, resizedH,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		//imageIcon = new ImageIcon(newimg);  // transform it back
		
	    return new ImageIcon(newimg); // returns after transforming it back
	}




	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("  Minefield. clicked");
		
		if (SwingUtilities.isRightMouseButton(e)){
			//System.out.println("  Minefield. clicked with right mouse button");
			
			JButton clicked = (JButton)e.getSource();  // (JButton)  casts event 
			
			String clickedField = "empty";
			for (int i=0; i<MINEFIELD_SIZE; i++){     // looking for button coordinates
				for (int j=0; j<MINEFIELD_SIZE; j++){
					if (clicked == buttonsReferences[i][j]){
						//System.out.println("  Minefield. Found RIGHT clicked button in references. Coordinates: " + i + "."  + j);
						clickedField = i + "."  + j;

						break;
					}	
				}			
			}
			
			if(textMinefieldFieldListenerRightMouseClick != null ){
				//System.out.println("      Minefield. Hello button was clicked");

				//textFieldListener.textEmitted("Hello\n");
				
				//textFieldListener.textEmitted(clicked.getText());
				textMinefieldFieldListenerRightMouseClick.textEmitted(clickedField);
			}
		}
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("  Minefield. entered");
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("  Minefield. exited");
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("  Minefield. pressed");
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("  Minefield. released");
	}
	
	
	public void setStringListenerLeftMouseClick(ButtonFieldListener listener){
		this.textMinefieldFieldListenerLeftMouseClick = listener;
		
	}
	
	
	public void setStringListenerRightMouseClick(ButtonFieldListener listener){
		this.textMinefieldFieldListenerRightMouseClick = listener;
		
	}
	
	
	
	
	
	
	
	

}






/*
 * 
 * 
 	// this could have been better than JButton
 *  toggleButton.setBorderPainted(false)
 *  toggleButton.setBorder(null);
 *  toggleButton.setFocusable(false);
 * 	toggleButton.setMargin(new Insets(0, 0, 0, 0));
        toggleButton.setContentAreaFilled(false);
        toggleButton.setIcon((errorIcon));
        toggleButton.setSelectedIcon(infoIcon);
        
        toggleButton.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
                    if (toggleButton.isEnabled()) {
                        toggleButton.setEnabled(false);
                    } else {
                        toggleButton.setEnabled(true);
                    }
                }
            }
 * 
 * 
 * 
 */









