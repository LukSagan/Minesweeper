
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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Toolbar extends JPanel implements ActionListener {
	
	/*
	private JButton menuButton;
	private JButton helpButton;
	private JButton statisticsButton;
	*/
	
	private JLabel minesGUIIco;	
	private JLabel minesGUICounter;
	private JButton minesGUIFaceIco;	
	private JLabel minesGUITimerIco;
	private JLabel minesGUITimer;
	
	final int minesGUIFaceIcoSize = 100;
	
	private ButtonFieldListener textToolbarNewgameListener;
	
	
	public Toolbar(){
		// creates flag counter, face ico button, game timer
		super();
		
		minesGUIIco = new JLabel();		
		minesGUICounter = new JLabel("minesCounter");
		minesGUIFaceIco = new JButton();
		minesGUITimerIco = new JLabel();
		minesGUITimer = new JLabel("minesTimer");
		
				
		final int minesGUIIcoSize = 50;
		//minesGUIIco.setIcon(scaleImageIco("src/images/010_mine.png", minesGUIIcoSize, minesGUIIcoSize));
		minesGUIIco.setIcon(scaleImageIco("/images/010_mine.png", minesGUIIcoSize, minesGUIIcoSize));
		
		minesGUICounter.setPreferredSize(new Dimension(70, 100));
		//minesGUICounter.setFont(new Font("Dialog", Font.BOLD, 30));
		minesGUICounter.setFont(new Font("Arial", Font.BOLD, 30));
		minesGUICounter.setHorizontalAlignment(JLabel.RIGHT);
		
		//minesFaceIco.setIcon(new ImageIcon("src/images/001_face_happy.png"));
		//minesGUIFaceIco.setIcon(scaleImageIco("src/images/001_face_happy.png", minesGUIFaceIcoSize, minesGUIFaceIcoSize));
		minesGUIFaceIco.setIcon(scaleImageIco("/images/001_face_happy.png", minesGUIFaceIcoSize, minesGUIFaceIcoSize));
		minesGUIFaceIco.setToolTipText("Nowa gra");
		
		minesGUIFaceIco.setOpaque(false);
		minesGUIFaceIco.setContentAreaFilled(false);
		minesGUIFaceIco.setBorderPainted(false);
		//minesGUITimerIco.setIcon(scaleImageIco("src/images/015_clock.png", minesGUIIcoSize, minesGUIIcoSize));
		minesGUITimerIco.setIcon(scaleImageIco("/images/015_clock.png", minesGUIIcoSize, minesGUIIcoSize));
		
		int topPad = 0;
		int leftPad = 0;
		int bottomPad = 0;
		int rightPad = 10;		
		//minesGUITimer.setPreferredSize(new Dimension(60, 100));
		minesGUITimer.setPreferredSize(new Dimension(60 + leftPad + rightPad, 100 + topPad + bottomPad));
		minesGUITimer.setBorder(new EmptyBorder(0,leftPad,0,rightPad));//top,left,bottom,right		
		minesGUITimer.setFont(new Font("Arial", Font.BOLD, 30));
		minesGUITimer.setHorizontalAlignment(JLabel.RIGHT);
		
		setLayout(new FlowLayout(FlowLayout.CENTER));    // passed variable to constructor to make things aligned from center
		
		minesGUIFaceIco.addActionListener(this);
		
		add(minesGUIIco);
		add(minesGUICounter);
		add(minesGUIFaceIco);
		add(minesGUITimerIco);
		add(minesGUITimer);
		
		
		validate();
	}
	
	
	
	public void changeFaceIco(int state){
		// changes FaceIco icon to "won" or "lost"

		if (state == 1){
			//minesGUIFaceIco.setIcon(scaleImageIco("src/images/001_face_happy.png", minesGUIFaceIcoSize, minesGUIFaceIcoSize));
			minesGUIFaceIco.setIcon(scaleImageIco("/images/001_face_happy.png", minesGUIFaceIcoSize, minesGUIFaceIcoSize));
		}else if (state == 2){
			//minesGUIFaceIco.setIcon(scaleImageIco("src/images/004_face_winner.png", minesGUIFaceIcoSize, minesGUIFaceIcoSize));
			minesGUIFaceIco.setIcon(scaleImageIco("/images/004_face_winner.png", minesGUIFaceIcoSize, minesGUIFaceIcoSize));
		}else if (state == 3){
			//minesGUIFaceIco.setIcon(scaleImageIco("src/images/003_face_defeat.png", minesGUIFaceIcoSize, minesGUIFaceIcoSize));
			minesGUIFaceIco.setIcon(scaleImageIco("/images/003_face_defeat.png", minesGUIFaceIcoSize, minesGUIFaceIcoSize));
		}
	}
	
	
	public void changeMinesGUICounter(String counter){
		minesGUICounter.setText(counter);
		
	}
	
	public void changeMinesGUITimer(String timer){
		minesGUITimer.setText(timer);
		
	}
	
	
	
	
	public ImageIcon scaleImageIco(String imgIcoLocation, int resizedW, int resizedH){
		// method for resizing images (image icons). Used for graphic icons. Reworked method from internet
		
		
		//imageInfoLabel.setIcon(  new ImageIcon(MainFrame.class.getResource("/images/040_info_jdialog_icon.png"))  );
		//MainFrame.class.getResource("/images/040_info_jdialog_icon.png")
		
		//ImageIcon imageIcon = new ImageIcon(imgIcoLocation); // load the image to a imageIcon
		//ImageIcon imageIcon = new ImageIcon(   MainFrame.class.getResource(  imgIcoLocation)  ); // load the image to a imageIcon
		ImageIcon imageIcon = new ImageIcon(   Toolbar.class.getResource(  imgIcoLocation)  ); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(resizedW, resizedH,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		//imageIcon = new ImageIcon(newimg);  // transform it back
		
	    return new ImageIcon(newimg); // returns after transforming it back
	}

	public void setStringListenerToolbarNewgame(ButtonFieldListener listener){
		this.textToolbarNewgameListener = listener;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton clicked = (JButton)e.getSource();  // (JButton)  casts event 
		//System.out.println("Toolbar. Button Face NewGame was clicked");
		
		if(textToolbarNewgameListener != null ){
			//System.out.println("Toolbar. Hello button was clicked");

			textToolbarNewgameListener.textEmitted("");
		}
		
		
		/*
		
		if(textFieldListener != null ){
			System.out.println("      Hello button was clicked");

			//textFieldListener.textEmitted("Hello\n");
			
			textFieldListener.textEmitted(clicked.getText());
		}
		
		*/
		
		
	}

	
	
	
	
}
