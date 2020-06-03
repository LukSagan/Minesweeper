
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


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import controller.Controller;
import model.GameGames1_5StatsListener;
import model.GameNewWonLostListener;
import model.GameObjListener;
import model.GameWonStats;
import model.ObjListener;


public class MainFrame extends JFrame {
	//dont forget  'extends JFrame' 
	
	private JFrame mainFrame;
	private Toolbar toolbar;	
	private JTextArea textArea;
	private Minefield minefield;
	private StatsPanel statsPanel;
	
	private Controller controller;
	private int buttonHeightDifference = 10; // for fast adjusting whole window size, when finding good size of field
	private int dummyHeightChanger = 40; // for fast changing window size for testing purposes
	private final int WINDOW_HEIGHT_NO_STATS = 500 + 9*buttonHeightDifference;
	private final int WINDOW_HEIGHT_WITH_STATS = 700 + 9*buttonHeightDifference + 40;// 700
	private final int WINDOW_WITDTH = 470;
	
	//private TextPanel textPanel;     // temporary thing, fixing toolbars and jtextareas
	
	
	
	
	
	
	
		
	public MainFrame(){
		// whole is borderlayout with menu.  
		// In border layout    'Page start'    timer, counter, etc.
		// In border layout    'center'        pane with  minefield
		// 
		// pane with minefield  is GridBagLayout    (probably should be GridLayout = cells have same sizes, span only 1 row/column)
				
		super("Saper 001");    //calling superclass constructor
		//mainFrame = mainframeAdress;		
		
		
		setLayout(new BorderLayout());		
		
		toolbar = new Toolbar();
		textArea = new JTextArea();
		minefield = new Minefield();
		statsPanel = new StatsPanel();		
		controller = new Controller();
		
		listeners();
				
		setJMenuBar(createMenuBar());
		
		
		add(toolbar, BorderLayout.NORTH);
		
		
		
		//add(textArea, BorderLayout.CENTER);
		add(minefield, BorderLayout.CENTER);
		add(statsPanel, BorderLayout.PAGE_END);		
		//statsPanel.setPreferredSize(new Dimension(WINDOW_WITDTH, 50));  // too low height (10) messed up showing items in window
		statsPanel.setVisible(false);
		
		//add(textPanel, BorderLayout.CENTER);     // temporary thing, fixing toolbars and jtextareas

		//setSize(700, 450);
		//setSize(460, 500);
		setSize(WINDOW_WITDTH, WINDOW_HEIGHT_NO_STATS);		
		//setLocation(740, 240);
		setLocation(100, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setResizable(false);
		setVisible(true);	
		//this.defa
		
		
		
		addWindowListener(new WindowListener(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub				
				//System.out.println("Mainframe. closing program");
				controller.writeGsmeSettingsToFile();
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub		
			}			

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub				
			}			
		});
		
		
		
		URL url = MainFrame.class.getResource("/images/010_mine.png");
		ImageIcon icon = new ImageIcon(url);
		setIconImage( icon.getImage() );
		
		
		
		
		
		//java.net.URL url = ClassLoader.getSystemResource("src/images/040_info_jdialog_icon.png");  // ???? maybe one time it will be useful
		
		// changing program icon in system taskbar
		
		// works
		
		/*
		Toolkit kit = Toolkit.getDefaultToolkit();
		//Image img = kit.createImage("src/images/010_mine.png");
		Image img = kit.createImage("src/images/010_mine.png");
		setIconImage( img );
		*/
		
		
		
		/*
		Toolkit kit = Toolkit.getDefaultToolkit();
		//Image img = kit.createImage("src/images/010_mine.png");
		
		Image img = kit.createImage(    (ImageProducer)(new javax.swing.ImageIcon(getClass().getResource("src/images/010_mine.png")).getImage()  )  );
		setIconImage( img );
		*/
		
		
		
		
		/*
		
		//String logoOneUrl = "jar:file:src/images/010_mine.png";
		String logoOneUrl = "jar:file:D:/szkolne/JAVA/00 Udemy/eclipse/_workplace  moje/Miner v001/src/images/010_mine.png";
		URL FileSysUrl = null;
		
		try {
			FileSysUrl = new URL(logoOneUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon logoOne = new ImageIcon( FileSysUrl );
		*/
		
		
		
		/*
		//java.net.URL logoOneUrl = ClassLoader.getSystemResource("src/images/040_info_jdialog_icon.png");  // ???? maybe one time it will be useful
		java.net.URL logoOneUrl = ClassLoader.getSystemResource("D:/szkolne/JAVA/00 Udemy/eclipse/_workplace  moje/Miner v001/src/images/040_info_jdialog_icon.png"); 
		//java.net.URL logoOneUrl = getClass().getResource("src/images/010_mine.png");
		System.out.println("MainFrame.  logoOneUrl: " + logoOneUrl);
		//Icon logoOne = new ImageIcon(logoOneUrl );
		
		ImageIcon logoOne = new ImageIcon(logoOneUrl );
		setIconImage( logoOne.getImage() );
		*/
		
		
		
		/*
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("src/images/010_mine.png");
		setIconImage( stream );
		*/
		
		
		
		
		/*
		Image img;
		try {
			//ImageIO.read(getClass().getResource("src/images/010_mine.png"));
			img = kit.createImage(  ImageIO.read(getClass().getResource("src/images/010_mine.png"))  );
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Image img = kit.createImage(    (ImageProducer)(new javax.swing.ImageIcon(getClass().getResource("src/images/010_mine.png")).getImage()  )  );
		setIconImage( img );
		*/
		
		
		/*
		// ????  delete later after making writing to file
		Point point = new Point();
		point = getWindowPostion();
		System.out.println("Mainframe. Window position read after conversion: " + point.x + "." + point.y);
		*/
	}


	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		// main menu bar consists of gameMenu, helpMenu
		
		
		JMenu gameMenu = new JMenu ("Gra");
		JMenuItem newGameItem = new JMenuItem("Nowa gra");
		JMenuItem statisticsItem = new JMenuItem("Statystyki");
		JMenuItem exitItem = new JMenuItem("Zakoñcz");
		
		gameMenu.add(newGameItem);
		gameMenu.addSeparator();
		gameMenu.add(statisticsItem);
		gameMenu.addSeparator();
		gameMenu.add(exitItem);		
		
		
		
		JMenu helpMenu = new JMenu ("Pomoc");
		JMenuItem helpItem = new JMenuItem("Wyœwietl pomoc");
		JMenuItem aboutItem = new JMenuItem("O programie");
		
		helpMenu.add(helpItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutItem);
		
		
		
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		
		
		newGameItem.addActionListener(new ActionListener(){  // actions after clicking "new Game Item" in menu			
			@Override
			public void actionPerformed(ActionEvent ev) {			
				//System.out.println("Mainframe. Clicked New game button from menu");
				
				
				
				
				
				if (controller.returnIfGameIsNotRunning()) {
					System.out.println("Mainframe. Menu new game trying to start new game   Game allowed to start new game");
					minefield.resetFieldsLooks();	
					statsPanel.changeInfoRecordLabel("Powodzenia!");
					controller.createNewGame();
				}else{
					//UIManager.put("JOptionPane.okButtonText", "yup");
					JOptionPane pane = new JOptionPane();
					
					//pane.set.Xxxx(...); // Configure
					//int reply = pane.showConfirmDialog((JFrame)null, "Czy na pewno chcesz przerwaæ aktualn¹ grê?", "Rozpoczynanie nowej gry", JOptionPane.YES_NO_OPTION);
					Object[] options = {"Tak", "Nie"};
					@SuppressWarnings("static-access")
					//int reply = pane.showOptionDialog((JFrame)null,
					int reply = pane.showOptionDialog(mainFrame,
							"Czy na pewno chcesz przerwaæ aktualn¹ grê?",
							"Rozpoczynanie nowej gry",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null, options, options[0]);				
					
					switch(reply){
						case JOptionPane.YES_OPTION: //System.out.println("Mainframe. NewGame menu item. Selected: YES. " + reply);
							System.out.println("Mainframe. Menu new game trying to start new game   Game allowed to start new game");
							controller.remoteFinishGame();
							minefield.resetFieldsLooks();	
							statsPanel.changeInfoRecordLabel("Powodzenia!");
							controller.createNewGame();
							break;
						case JOptionPane.NO_OPTION: //System.out.println("Mainframe. NewGame menu item. Selected: NO. " + reply);
							break;
						case JOptionPane.CLOSED_OPTION: //System.out.println("Mainframe. NewGame menu item. Selected: Nothing was selected. " + reply);
							break;
					}					
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
			}
		});
		
		
		statisticsItem.addActionListener(new ActionListener(){  // actions after clicking "statistics Item" in menu
			@Override
			public void actionPerformed(ActionEvent ev) {				
				Dimension windowSize = getSize();
				//System.out.println("Mainframe. Clicked statistics button from menu. Window Height:" + windowSize.getHeight());
				if(windowSize.getHeight() == WINDOW_HEIGHT_NO_STATS){
					// show stats window
					setSize(WINDOW_WITDTH, WINDOW_HEIGHT_WITH_STATS);
					statsPanel.setVisible(true);
					controller.changeGameStatisticsShowStatisticsSetting(true);					
				}else if(windowSize.getHeight() == WINDOW_HEIGHT_WITH_STATS){
					// hide stats window					
					setSize(WINDOW_WITDTH, WINDOW_HEIGHT_NO_STATS);	
					statsPanel.setVisible(false);
					controller.changeGameStatisticsShowStatisticsSetting(false);
				}				
			}			
		});
		
		
		
		exitItem.addActionListener(new ActionListener(){  // actions after clicking "exit Item" in menu
			@Override
			public void actionPerformed(ActionEvent ev) {
				//System.out.println("Mainframe. Clicked Exit button from menu");

				if (controller.returnIfGameIsNotRunning()) {  // confirmation in only when game was started (done 1st click)
				//  exit program when game is not running
					System.exit(0); // close the program
					dispose();										
				}else{
					Object[] options = {"Tak", "Nie"};
					//int reply = JOptionPane.showOptionDialog((JFrame)null,
					int reply = JOptionPane.showOptionDialog(mainFrame,
							"Czy na pewno chcesz przerwaæ aktualn¹ grê?",
							"Koñczenie gry",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null, options, options[0]);
					switch(reply){
						case JOptionPane.YES_OPTION: //System.out.println("Mainframe. NewGame Exit item. Selected: YES. " + reply);
							//  exit program when game is running
							
							System.exit(0); // close the program
							dispose();							
							break;
						case JOptionPane.NO_OPTION: //System.out.println("Mainframe. NewGame Exit item. Selected: NO. " + reply);
							break;
						case JOptionPane.CLOSED_OPTION: //System.out.println("Mainframe. NewGame Exit item. Selected: Nothing was selected. " + reply);
							break;
					}
				}
			}
			
		});
		
		
		
		
		
		helpItem.addActionListener(new ActionListener(){  // actions after clicking "about Item" in menu
			@Override
			public void actionPerformed(ActionEvent ev) {				
				//System.out.println("Mainframe. Clicked Display help button from menu");
				
				JOptionPane pane = new JOptionPane();
				
				String displayText = "Zasady gry\n"
						+ "Pole gry sk³ada siê z pól z minami, pól z liczbami i pustych pól.\n"
						+ "Aby wygraæ nale¿y odkryæ wszyskie pola z liczbami i puste pola.\n"
						+ "Pole z liczb¹ oznacza, ¿e w s¹siedztwie danego pola\n"
						+ "jest dana liczba min. Je¿eli gracz ma pewnoœæ,\n"
						+ "¿e w pewnym polu jest mina, to prawym przyciskiem myszy\n"
						+ "mo¿na oflagowaæ dane pole, aby przez przypadek na nie\n"
						+ "nie klikn¹æ oraz odliczaæ liczbê pozosta³ych min.\n"
						+ "\n"
						+ "W pewnych przypadkach nie da siê obliczyæ pozycji\n"
						+ "wszystkich min i wygrana/przegrana zale¿y od szczêœcia.\n"
						+ "\n"
						+ "Porada:\n"
						+ "Now¹ grê mo¿na równie¿ zacz¹æ klikaj¹c na ikonê z buŸk¹.\n"
						+ "\n"
						+ "Powodzenia";
				
				//pane.showMessageDialog(mainFrame, "Informacje o autorze\n, licencji, itp", "O programie", JOptionPane.INFORMATION_MESSAGE);
				pane.showMessageDialog(mainFrame, displayText, "Pomoc", JOptionPane.INFORMATION_MESSAGE);				
			}
			
		});
		
		
		
		
		
		aboutItem.addActionListener(new ActionListener(){  // actions after clicking "about Item" in menu
			@Override
			public void actionPerformed(ActionEvent ev) {				
				//System.out.println("Mainframe. Clicked About button from menu");
				
				AboutDialog d = new AboutDialog(mainFrame);
				
				/*
				
				JOptionPane pane = new JOptionPane();
				//pane.set.Xxxx(...); // Configure
				//pane.setLocation(10, 10);
				//pane.setBounds(10, 10, 200, 200);
				//pane.showMessageDialog(minefield, "Informacje o autorze, licencji, itp", "O programie", JOptionPane.INFORMATION_MESSAGE);
				
				String displayText = "Wersja programu: "
						+ "\n"
						+ "Autor: £ukasz S.\n"
						+ "\n"
						//+ "Strona internetorwa: <html><a href=\"http://google.com/\">a link</a></html>\n"
						+ "Strona internetorwa: http://google.com\n"
						+ "\n"
						+ "Licencja: \n"
						+ "\n"
						+ "Ten program jest dystrybuowany bez ¿adnej gwarancji";
				
				//pane.showMessageDialog(mainFrame, "Informacje o autorze, licencji, itp", "O programie", JOptionPane.INFORMATION_MESSAGE);
				pane.showMessageDialog(mainFrame, displayText, "O programie", JOptionPane.INFORMATION_MESSAGE);
				//pane.setLocation(10, 10);
				//pane.setBounds(10, 10, 200, 200);
				
				//controller.writeGsmeSettingsToFile();    // ???? testing purposes only				
				
				//JOptionPane.showMessageDialog(null, "Informacje o autorze, licencji, itp", "O programie", JOptionPane.INFORMATION_MESSAGE);
				
				
				*/
				
				
				
				
				
				
				
				
				/*
				final JDialog d = new JDialog();
				d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				String displayText = "Licencja: \n" // text to display in licence section
						+ "\n"
						+ "Ten program jest dystrybuowany bez ¿adnej gwarancji";
				
				
				
				
				JLabel imageInfoLabel = new JLabel("image");
				JLabel programVersionTLabel = new JLabel("Werjsa programu");
				JLabel programVersionLabel = new JLabel();
				JLabel dummyWidthLabel = new JLabel("width");
				JLabel authorTlabel = new JLabel("Autor:");
				JLabel authorlabel = new JLabel();
				JLabel webPageTLabel = new JLabel("Strona internetowa");
				JLabel webPageLabel = new JLabel();
				JLabel licenseLabel = new JLabel(displayText);
				JButton okButton = new JButton("OK");
				
				
				d.setTitle("O programie");
				
				d.setLayout(new GridBagLayout());
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
				
				
				addItemToGridBagLayout(d, constraOne, 0, 0, imageInfoLabel, null, null);
				addItemToGridBagLayout(d, constraOne, 1, 0, programVersionTLabel, null, null);
				addItemToGridBagLayout(d, constraOne, 2, 0, programVersionLabel, null, null);
				addItemToGridBagLayout(d, constraOne, 3, 0, dummyWidthLabel, null, null);
				
				addItemToGridBagLayout(d, constraOne, 1, 1, authorTlabel, null, null);
				addItemToGridBagLayout(d, constraOne, 2, 1, authorlabel, null, null);
				
				addItemToGridBagLayout(d, constraOne, 1, 2, webPageTLabel, null, null);
				addItemToGridBagLayout(d, constraOne, 2, 2, webPageLabel, null, null);
				
				addItemToGridBagLayout(d, constraOne, 1, 3, licenseLabel, null, null);
				
				addItemToGridBagLayout(d, constraOne, 1, 4, null, okButton, null);
								
				okButton.addActionListener(this);
				
				
				//d.add( programVersionTLabel );
				//d.add( authorTlabel );
				
				
				d.pack();				
				//d.setSize(dialogSizeX, dialogSizeY);
				d.setModal(true);

				
				//d.setResizable(false);	
				d.setLocationRelativeTo(mainFrame);
				d.setVisible(true);
				
				*/
				
				
				
				
				
				
				
				
				
				
				
				/*
				final JOptionPane pane = new JOptionPane();
				//final JDialog d = pane.createDialog((JFrame)null, "Title");				
				final JDialog d = pane.createDialog(mainFrame, "Title");
				//d.setLocation(10,10)f3;
				//d.setLocationRelativeTo(mainFrame);				
				
				d.setVisible(true);
				*/
				
				/*
				// Set a 2 second timer
				new Thread(new Runnable() {
				    @Override
				    public void run() {
				        try {
				            Thread.sleep(2000);
				        } catch (Exception e) {
				        }
				        d.dispose();
				    }
				
				}).start();
				*/
				
				
				
				
				
				
				/*
				JOptionPane pane = new JOptionPane();
				//pane.set.Xxxx(...); // Configure
				//pane.setLocation(10, 10);
				//pane.setBounds(10, 10, 200, 200);
				pane.showMessageDialog((JFrame)null, "Informacje o autorze, licencji, itp", "O programie", JOptionPane.INFORMATION_MESSAGE);
				pane.setLocation(10, 10);
				//pane.setBounds(10, 10, 200, 200);
				*/				

				
			}
			
		});
		
		
		
		
		return menuBar;
	}
	
	
	public void showWonLostMessage(int messageWonOrLost){
		// messageWonOrLost  tells  to show message won or message lost  or message record

		//System.out.println("Mainframe. showWonLostMessage   Game lost/won/record");
		
		//final JOptionPane pane = new JOptionPane();			
		//final JDialog d = pane.createDialog(mainFrame, "Koniec gry");
		final JDialog d = new JDialog();
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JLabel imageLabel = new JLabel();
		int imageSizeToResize = 200;
		int dialogSizeX = 206;
		int dialogSizeY = 228;
		
		if( messageWonOrLost == ObjListener.messageGameLost ){
			//System.out.println("Mainframe. show message   Game lost  " + messageWonOrLost);			
			//d = pane.createDialog(mainFrame, "Przegrana");
			d.setTitle("Przegrana");
			
			//scaleImageIco("src/images/031_loser_dead_fish.png", 100, 100)
			//d.add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(IMAGE_URL)))));
			//d.add(new JLabel(new ImageIcon( toolbar.scaleImageIco("src/images/031_loser_dead_fish.png", 100, 100)  )    ));
			imageLabel.setIcon(toolbar.scaleImageIco("/images/031_loser_dead_fish.png", imageSizeToResize, imageSizeToResize));	
		}else if ( messageWonOrLost == ObjListener.messageGameWon ){
			//System.out.println("Mainframe. show message   Game won  " + messageWonOrLost);
			//d = pane.createDialog(mainFrame, "Wygrana");
			d.setTitle("Wygrana");
			//imageLabel.setIcon(toolbar.scaleImageIco("src/images/030_winner_cup.png", imageSizeToResize, imageSizeToResize));
			imageLabel.setIcon(toolbar.scaleImageIco("/images/030_winner_cup.png", imageSizeToResize, imageSizeToResize));
		}else{ // game won and record
			//System.out.println("Mainframe. show message   Game won  + new record  " + messageWonOrLost);
			//d = pane.createDialog(mainFrame, "Rekord!");
			d.setTitle("Rekord!");
			//imageLabel.setIcon(toolbar.scaleImageIco("src/images/030_winner_cup.png", imageSizeToResize, imageSizeToResize));			
			imageLabel.setIcon(toolbar.scaleImageIco("/images/030_winner_cup.png", imageSizeToResize, imageSizeToResize));
		}
		
		
		d.add( imageLabel );
		
		
		
		// Set a 2 second timer
		new Thread(new Runnable() {    // autoclosing pop up
			@Override
			public void run() {
				try {
					Thread.sleep( 700 );
				} catch (Exception e) {
				}
				d.dispose();
				//d.setAlwaysOnTop(false);
				/*
				mainFrame.setFocusable(true);
				//mainFrame.setEnabled(true);
				*/
			}
		
		}).start();
		
		
		d.pack();		
		d.setSize(dialogSizeX, dialogSizeY);
		//percenWonLabel.setMinimumSize(new Dimension(40, 10));
		//d.setBounds(100, 100, 100, 100);
		d.setModal(true);
		//d.setAlwaysOnTop(true);		
		/*
		mainFrame.setFocusable(false);
		//mainFrame.setEnabled(false);
		//this.setEnabled(false);   // works, but lets keep it other way for more visualibity
		*/
		
		d.setResizable(false);
		//d.setLocationRelativeTo(mainFrame);		
		d.setLocationRelativeTo(minefield);
		d.setVisible(true);
		
		
		
		
		/*
		final JOptionPane pane = new JOptionPane();			
		//final JDialog d = pane.createDialog(mainFrame, "Koniec gry");
		final JDialog d;
		
		if( messageWonOrLost == ObjListener.messageGameLost ){			
			//System.out.println("Mainframe. show message   Game lost  " + messageWonOrLost);
			
			d = pane.createDialog(mainFrame, "Przegrana");
		}else{
			
			
			if( messageWonOrLost == ObjListener.messageGameWon ){
				//System.out.println("Mainframe. show message   Game won  " + messageWonOrLost);
				d = pane.createDialog(mainFrame, "Wygrana");
				
			}else{
				//System.out.println("Mainframe. show message   Game won  + new record  " + messageWonOrLost);
				d = pane.createDialog(mainFrame, "Rekord!");
			}
				
		}
		
		
		// Set a 2 second timer
		new Thread(new Runnable() {
		    @Override
		    public void run() {
		        try {
		            Thread.sleep(700);
		        } catch (Exception e) {
		        }
		        d.dispose();
		    }
		
		}).start();
		
		d.setVisible(true);
		*/
		
	}
	
	
	
	

	
	public Point getWindowPostion(){ // so it can be used outside Mainframe
		/*
		Point pointPom = new Point();
		pointPom = getLocation();
		System.out.println("Mainframe. Window position read: " + pointPom.x + "." + pointPom.y);
		 */
		return getLocation();
	}
	
	
	public void getFrameReference(JFrame frameReference){ // 
		mainFrame = frameReference;	
		statsPanel.getFrameReference(mainFrame);
		
	}
	
	public void addItemToGridBagLayout(JDialog dialog, GridBagConstraints constraOne, int x, int y, JLabel someLabel, JButton someButton, JEditorPane ep){
		// makes adding items easier
		System.out.println("StatsPanel. Adding item to grid bag layout");
		constraOne.fill = GridBagConstraints.HORIZONTAL;
		constraOne.weightx = 0.5;
		constraOne.gridx = x;		
		constraOne.gridy = y;
		if(someLabel != null) dialog.add(someLabel, constraOne);
		else if(someButton != null) dialog.add(someButton, constraOne);
		else if(ep != null) dialog.add(someButton, constraOne);	
	}
	
	
	
	
	
	private void listeners(){
		// ***************************    LISTENERS    ***************************    
		
				//MainFrame.setStringListener(new StringFieldListener() {
				
				
				minefield.setStringListenerLeftMouseClick(new ButtonFieldListener() {
					// listener to any minefield button click. should call game class methods

					@Override
					public void textEmitted(String text) {
						// TODO Auto-generated method stub
						
						//System.out.println("Mainframe. This button was clicked: " + text);
						controller.fieldLeftClickedSendToGame(text);
					}
					
					
				});
				
				
				minefield.setStringListenerRightMouseClick(new ButtonFieldListener() {
					// listener to any minefield button click. should call game class methods

					@Override
					public void textEmitted(String text) {
						// TODO Auto-generated method stub
						
						//System.out.println("Mainframe. This button was clicked: " + text);
						controller.fieldRightClickedSendToGame(text);
					}
					
					
				});
				
				
				
				statsPanel.setObjListenerStatsPanel(new GameObjListener(){
					// listener to StatsPanel reset statistics button. game resets stats and saves to file
					@Override
					public void objectEmitted(ObjListener object) {
						// TODO Auto-generated method stub						
						System.out.println("Mainframe. StatsPanel listener of OBJECT");		
						
						switch(object.getCode()){
						case ObjListener.checkIfGameIsOngoing: statsPanel.tryToResetGamesStatsMessages(controller.returnIfGameIsNotRunning());
							/*
						if (controller.returnIfGameIsStillOngoing()) {
								statsPanel.showMessageRequestResetStats();
							}else{
								statsPanel.showMessageCannotResetStats();					
							}				*/			
							break;
						case ObjListener.sendRequestResetStatistics: controller.resetGamesStatistics();							
							break;
						
						
						}// end switch for obj listeners
						
						
					}
				});
				
				
				
				
				
				toolbar.setStringListenerToolbarNewgame(new ButtonFieldListener() {
					// listener to toolbar new game button. new game resets minefield look and game fields 

					@Override
					public void textEmitted(String text) {
						// TODO Auto-generated method stub
						
						//System.out.println("Mainframe. Toolbar listener trying to start new game");
						if (controller.returnIfGameIsNotRunning()) {
							//System.out.println("Mainframe. Toolbar listener trying to start new game   Game allowed to start new game");
							minefield.resetFieldsLooks();	
							statsPanel.changeInfoRecordLabel("Powodzenia!");
							controller.createNewGame(); //
							//controller = new Controller();    // this doesnt change controller reference in minefield listener
						}
					}
				});
				
				
				
				
				
				
				
				
				
				
				
				controller.setStringListener(new ButtonFieldListener() {
					// listener to any controller action. should call minefield class methods

					@Override
					public void textEmitted(String text) {
						// TODO Auto-generated method stub
						
						//System.out.println("Mainframe. Controller listener " + text);
						minefield.changeMinefieldFieldLook(text);
					}			
				});
				
				controller.setIntListener(new GameNewWonLostListener(){

					@Override
					public void numberEmitted(int won) {
						// TODO Auto-generated method stub				
						toolbar.changeFaceIco(won);
					}
				});
				
				
				toolbar.changeMinesGUICounter(" 010");
				controller.setStringListenerRemainingMines(new ButtonFieldListener() {
					// listener to any controller action. should call minefield class methods

					@Override
					public void textEmitted(String text) {
						// TODO Auto-generated method stub
						
						//System.out.println("Mainframe. Controller listener " + text);
						toolbar.changeMinesGUICounter(text);
					}			
				});
				
				
				toolbar.changeMinesGUITimer("000");
				
				controller.setStringListenerGameGUITimer(new ButtonFieldListener() {
					// listener to any controller action. should call minefield class methods

					@Override
					public void textEmitted(String text) {
						// TODO Auto-generated method stub
						
						//System.out.println("Mainframe. Controller listener " + text);
						toolbar.changeMinesGUITimer(text);
					}			
				});
				
				
				
				controller.setArrayListenerGameStats5Games(new GameGames1_5StatsListener(){
					@Override
					public void setEmitted(ArrayList<GameWonStats> set) {
						// TODO Auto-generated method stub
						
						//System.out.println("Mainframe. Game listener, send new set of 5 games to display in statistics");
						statsPanel.changeGames1_5Label(set);
					}	
				});
				
				controller.setStringListenerGameStatsNewRecord(new ButtonFieldListener(){
					@Override
					public void textEmitted(String text) {
						// TODO Auto-generated method stub
						
						//System.out.println("Mainframe. Game new record listener. " + text);
						statsPanel.changeInfoRecordLabel(text);
						
					}		
				});
				
				
				
				
				controller.setIntListernerGameOptionsWindowPosX(new GameNewWonLostListener(){
					@Override
					public void numberEmitted(int number) {
						// TODO Auto-generated method stub				
						//toolbar.changeFaceIco(number);
						//System.out.println("Mainframe. Controller listener WindowPosX " + number);
					}
				});		
				
				controller.setIntListernerGameOptionsWindowPosY(new GameNewWonLostListener(){
					@Override
					public void numberEmitted(int number) {
						// TODO Auto-generated method stub				
						//toolbar.changeFaceIco(number);
						//System.out.println("Mainframe. Controller listener WindowPosY " + number);
					}
				});		
				
				controller.setIntListernerGameOptionsStatsWindow(new GameNewWonLostListener(){
					@Override
					public void numberEmitted(int number) {
						// TODO Auto-generated method stub				
						//toolbar.changeFaceIco(number);
						System.out.println("Mainframe. Controller listener OptStatsWindow " + number);
						if(number == 1){
							setSize(WINDOW_WITDTH, WINDOW_HEIGHT_WITH_STATS);
							statsPanel.setVisible(true);
						}else {
							setSize(WINDOW_WITDTH, WINDOW_HEIGHT_NO_STATS);
							statsPanel.setVisible(false);	
						}
					}
				});
				
				
				controller.setobjListenersetGameStatistic(new GameObjListener(){
					@Override
					public void objectEmitted(ObjListener object) {
						// TODO Auto-generated method stub
						
						//System.out.println("Mainframe. Controller listener of OBJECT");
						
						switch(object.getCode()){
						case ObjListener.lastGameStats: statsPanel.changeLastGameLabel(object.getArrayStrings());
							//System.out.println("Mainframe. Controller listener of OBJECT LastGameLabel");
							break;
						case ObjListener.overallStatsStats: statsPanel.changeoOverallStatsLabels(object.getArrayStrings());
							//System.out.println("Mainframe. Controller listener of OBJECT OverallStatsLabels");
							break;
						case ObjListener.getWindowPosition: //System.out.println("Mainframe. got window pos: " + getLocation().x + "." + getLocation().y);
							controller.setGameWindowPostionVariables(getLocation());
							//System.out.println("Mainframe. Controller listener of OBJECT OverallStatsLabels");
							break;
						case ObjListener.setWindowPosition: setLocation(object.getArrayInts()[0], object.getArrayInts()[1]);;
						//System.out.println("Mainframe. Controller listener of OBJECT OverallStatsLabels");
							break;
						case ObjListener.showGameWonLostPopUp: showWonLostMessage(object.getNumber());
							break;
						
						}// end switch for obj listeners
						
					}			
				});
				
				
				
				
				
				
		
		
		
	}
	
	
}
