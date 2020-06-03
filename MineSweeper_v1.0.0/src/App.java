
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


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.SwingUtilities;

import gui.AboutDialog;
import gui.MainFrame;



public class App {
	
	public static void main(String[] args) {		
		
		// license    checking if file exist
		File f = new File("copying");
		//System.out.println("App. there is license file: " + f.toPath());
		
		//System.out.println("App. original stream: " + App.class.getResource("/images/040_info_jdialog_icon.png")   );
		//System.out.println("App. original stream: " + App.class.getResource("/copying.txt")    + "\n");
		//System.out.println("App. original stream: " + App.class.getResource("/copying")    + "\n");
		
		if(f.exists() && !f.isDirectory()) { 
			System.out.println("App. there is license file");
		}else{
			System.out.println("App. there is NO license file");
			
			Path copiedPath = Paths.get("copying");
			//Path copiedPath = f.toPath();
			Path originalPath = Paths.get("src/copying");
			//Path originalPath =  App.class.getResource("/src/copying").getPath()  ;
			
			InputStream stream = null;
			stream = App.class.getResourceAsStream("/copying");
			//System.out.println("App. original stream: " + stream    );
			
			//System.out.println("App. original stream: " + AboutDialog.class.getResource("/images/040_info_jdialog_icon.png")   );
			
			
			
			
			try {
				//Files.copy(originalPath, copiedPath, StandardCopyOption.REPLACE_EXISTING);
				//Files.copy(App.class.getResource("/src/copying"), copiedPath, StandardCopyOption.REPLACE_EXISTING);
				//Files.cop
				//Files.copy(stream, copiedPath, StandardCopyOption.REPLACE_EXISTING);
				Files.copy(stream, copiedPath);
				
 			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			assertThat(copiedPath).exists();
			assertThat(Files.readAllLines(originalPath).equals(Files.readAllLines(copiedPath)));
			*/
			
		}
		
		
		
		
		assertNoOtherInstanceRunning();  // to block more instances of program
				
		SwingUtilities.invokeLater(new Runnable() {				
			public void run() {
				
				
				/*
				URL url = App.class.getResource("/images/stackoverflow.png");
				ImageIcon icon = new ImageIcon(url);
				*/
				
				
				MainFrame mainFrame = new MainFrame();
				mainFrame.getFrameReference(mainFrame);					

			}			
		});
		
		
		
		
		
		
		
		
	}
	

	public static void assertNoOtherInstanceRunning() {  // to block more instances of program
		//System.out.println("App. assertNoOtherInstanceRunning running");
		new Thread(() -> {
			try {
				//System.out.println("App. assertNoOtherInstanceRunning    Starting 1st instance of program");
				new ServerSocket(9000).accept();				
			} catch (IOException e) {
				System.out.println("App. assertNoOtherInstanceRunning    Tried to start another instance of program");
				System.exit(0); // close the program
				throw new RuntimeException("the application is probably already started", e);				
			}
		}).start();       
	}
	
	
	
	
}


/*
 * 1
 * 
 *  
 * GitHUB  
 * 
 * link to githbub
 * 
 * 
 * license
 * 
 * 
 * 
 * 
 * 
 * 
 * disable fields which are revealed
 * 
 * when comparing game times and 2 have same time, older one should be taken as better
 * 
 * change some variables into upper case with "_"    **constants
 * SQL: same as text + other statistics of each game
 * 
 * STATISTICS: 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *****************    DONE
 * 
 * counter fields revealed
 * counter marked fields, blocking left clicking those fields, detecting right mouse button clicks
 * activating new game button               image game won/lost/new game
 * image marked field
 * new image for 'clicked mine'          NO and other image for 'remaining mines'      also image for 'mines with flags' ? (original doesnt have it)
 * when game is won, show mine icons on all unrevealed fields
 * image 'incorrectly flagged field'
 * leave for now, maybe delete it after small icon when game is won with record     record text, change after new game, info it didnt fit best 5
 * didnt get on list, better luck next time           good luck
 * reset statistics ( file/SQL )
 * statistics pane: except stats from file, also show last game stats (udpate it on game end)
 * 56/235 rounds sometimes wrong, gives  .000 001   from precision error        56/240   56/246
 * some padding between components in stats pane
 * average game time in stats
 * pop up window (0,5sec) when game is won/lost ( winner's cup/dead fish)
 * some sort of window, when new record is set       JOptionPane?
 * menu actions       fix new game   and  action when closing game with 'x'         nope. leaving 'x' as it is right now 
 * save in file   option show statistics, window position when game is closed
 * best 5:      time, clicks need preferred size
 * fix file write    only need last game stats, rest should be global
 * center toolbar elements
 * help menu
 * program icon
 * actions when there is no game file,
 * actions when some of games data is corrupted 
 * serialize data.txt
 * exporting program  reading resources (images) from jar file
 * date
 * 
 * 
 * STATISTICS: counter number of moves, flags, times, etc.
 * 
 * 
 * TEXT FILE: window position, show/hide statistic section with game start, games played, games won, games lost (success rate count on each game), average time of game
 * cont. best 5 times of won games + clicks + date, 
 * 
 * 
 * 
 */
