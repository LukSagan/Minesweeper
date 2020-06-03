
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

package gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class AboutDialog extends JDialog implements ActionListener {
	
	private JDialog thisDialog;
	
	
	public AboutDialog(JFrame mainFrame) {
		// TODO Auto-generated constructor stub
		super();
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
		String displayText = "Licencja: \n" // text to display in license section
				+ ""
				+ "Licencja GNU General Public License.\n"
				+ "Ten program jest dystrybuowany bez �adnej gwarancji.";
		
		
		
		
		JLabel imageInfoLabel = new JLabel();
		JLabel programVersionTLabel = new JLabel("Wersja programu");
		JLabel programVersionLabel = new JLabel("1.0.0");
		JLabel dummyWidthLabel = new JLabel("");
		JLabel authorTlabel = new JLabel("Autor:");
		JLabel authorlabel = new JLabel("�ukasz S.");
		JLabel webPageTLabel = new JLabel("Strona internetowa");
		//JLabel webPageLabel = new JLabel();
		
		/*
		JEditorPane webPagePane = new JEditorPane("text/html", "<html><body style=\"\">" //
	            + "<a href=\"https://github.com/LukSagan/Minesweeper\">GitHub LukSagan</a>" //
	            + "</body></html>");
		*/
		
		JEditorPane webPagePane = new JEditorPane("text/html", "<html><body style=\"\">" //
	            + "<a href=\"\">GitHub LukSagan</a>" //
	            + "</body></html>");
		
		//JLabel licenseLabel = new JLabel(displayText);
		JTextArea licenseArea = new JTextArea(displayText);
		JButton okButton = new JButton("OK");
		
		
		setTitle("O programie");
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraOne = new GridBagConstraints();
		
		boolean shouldFill = true;
		boolean shouldWeightX = true;
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
		
		constraOne.gridheight = 4;
		constraOne.anchor=GridBagConstraints.PAGE_START;
		int width = 32;
		int height = 32;
		imageInfoLabel.setMinimumSize(new Dimension(width, height));
		imageInfoLabel.setMaximumSize(new Dimension(width, height));
		imageInfoLabel.setPreferredSize(new Dimension(width, height));
		
		//URL url = MainFrame.class.getResource("/images/010_mine.png");
		//ImageIcon icon = new ImageIcon(url);		
		//ImageIcon icon = new ImageIcon(MainFrame.class.getResource("/images/040_info_jdialog_icon.png"));		
		
		//imageInfoLabel.setIcon(  new ImageIcon("src/images/040_info_jdialog_icon.png")  );
		//imageInfoLabel.setIcon(  new ImageIcon(MainFrame.class.getResource("/images/040_info_jdialog_icon.png"))  );
		imageInfoLabel.setIcon(  new ImageIcon(AboutDialog.class.getResource("/images/040_info_jdialog_icon.png"))  );
		constraOne.insets = new Insets(0,10,0,0);  // padding    top, left, bottom, right
		addItemToGridBagLayout(constraOne, 0, 0, imageInfoLabel, null, null, null);
		constraOne.gridheight = 1;
		constraOne.anchor=GridBagConstraints.CENTER;
		constraOne.insets = new Insets(0,0,0,0);  // padding    top, left, bottom, right
		
		addItemToGridBagLayout(constraOne, 1, 0, programVersionTLabel, null, null, null);
		addItemToGridBagLayout(constraOne, 2, 0, programVersionLabel, null, null, null);
		//constraOne.weightx = 1.0;
		addItemToGridBagLayout(constraOne, 3, 0, dummyWidthLabel, null, null, null);
		//constraOne.weightx = 1.0;
		
		addItemToGridBagLayout(constraOne, 1, 1, authorTlabel, null, null, null);
		addItemToGridBagLayout(constraOne, 2, 1, authorlabel, null, null, null);
		
		addItemToGridBagLayout(constraOne, 1, 2, webPageTLabel, null, null, null);
		//addItemToGridBagLayout(constraOne, 2, 2, webPageLabel, null, null, null);
		webPagePane.setEditable(false);
		//webPagePane.setBackground(label.getBackground());
		webPagePane.setBackground(this.getBackground());
		addItemToGridBagLayout(constraOne, 2, 2, null, null, webPagePane, null);
		
		
		constraOne.gridwidth = 2;
		width = 250;
		height = 60;
		licenseArea.setMinimumSize(new Dimension(width, height));
		licenseArea.setMaximumSize(new Dimension(width, height));
		licenseArea.setPreferredSize(new Dimension(width, height));
		licenseArea.setEditable(false);
		licenseArea.setBackground(this.getBackground());
		constraOne.insets = new Insets(5,0,0,0);  // padding    top, left, bottom, right
		addItemToGridBagLayout(constraOne, 1, 3, null, null, null, licenseArea);
		constraOne.insets = new Insets(0,0,0,0);  // padding    top, left, bottom, right
		
		
		width = 26;
		height = 26;
		okButton.setMinimumSize(new Dimension(width, height));
		okButton.setMaximumSize(new Dimension(width, height));
		okButton.setPreferredSize(new Dimension(width, height));	
		//constraOne.weighty = 1.0;   //request any extra vertical space
		//constraOne.anchor = GridBagConstraints.PAGE_END; //bottom of space
		int vertPadding = 120;
		constraOne.insets = new Insets(0,vertPadding,0,vertPadding);  // padding    top, left, bottom, right
		addItemToGridBagLayout(constraOne, 1, 4, null, okButton, null, null);
		//okButton.focus
		
		
		
		
		webPagePane.addHyperlinkListener(new HyperlinkListener(){
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e)
			{
				if (   e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)    ){
					
					System.out.println("test");
					//ProcessHandler.launchUrl(e.getURL().toString()); // roll your own link launcher or use Desktop if J6+
					
					if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
						try {
							Desktop.getDesktop().browse(new URI("https://github.com/LukSagan/Minesweeper"));
						} catch (IOException | URISyntaxException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					
					
				}
			}
	    });
		
		
		
		okButton.addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorRemoved(AncestorEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println("test1");
			}
			
			@Override
			public void ancestorMoved(AncestorEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println("test2");
			}
			
			@Override
			public void ancestorAdded(AncestorEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println("test3");
				getRootPane().setDefaultButton(okButton);
				okButton.requestFocus();
				okButton.removeAncestorListener( this );
			}
		});
		
		
		okButton.addActionListener(this);
		
		
		
		pack();				
		setSize(400, 210); // width, height
		setModal(true);

		
		setResizable(false);	
		setLocationRelativeTo(mainFrame);
		setVisible(true);
		
		/*  doesnt work
		getRootPane().setDefaultButton(okButton);
		okButton.requestFocus();
		*/

		
	}
	
	
	
	
	public void addItemToGridBagLayout(GridBagConstraints constraOne, int x, int y, JLabel someLabel, JButton someButton, JEditorPane ep, JTextArea ta){
		// makes adding items easier
		//System.out.println("AboutDialog. Adding item to grid bag layout");
		constraOne.fill = GridBagConstraints.HORIZONTAL;
		constraOne.weightx = 0.5;
		constraOne.gridx = x;		
		constraOne.gridy = y;
		if(someLabel != null) add(someLabel, constraOne);
		else if(someButton != null) add(someButton, constraOne);
		else if(ep != null) add(ep, constraOne);
		else if(ta != null) add(ta, constraOne);
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub		
		//System.out.println("AboutDialog. Clicked on OK button in about dialog");
		dispose();
		
	}
}
