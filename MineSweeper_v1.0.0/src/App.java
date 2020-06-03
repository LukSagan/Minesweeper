
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

 
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.SwingUtilities;

import gui.MainFrame;

// resize items in minefield?

public class App {
	
	public static void main(String[] args) {		
		
		// license    checking if file exist
		File f = new File("copying");
		//System.out.println("App. there is license file: " + f.toPath());
		
		if(f.exists() && !f.isDirectory()) { 
			//System.out.println("App. there is license file");
		}else{
			//System.out.println("App. there is NO license file");
			
			Path copiedToPath = Paths.get("copying");			
			InputStream stream = null;
			stream = App.class.getResourceAsStream("/copying");
			try {
				Files.copy(stream, copiedToPath);
				
 			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		assertNoOtherInstanceRunning();  // to block more instances of program
				
		
		SwingUtilities.invokeLater(new Runnable() {	// create gui window	
			public void run() {				
				MainFrame mainFrame = new MainFrame();
				mainFrame.getFrameReference(mainFrame);					

			}			
		});
		
		
	}
	

	@SuppressWarnings("resource")
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
