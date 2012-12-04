package flickr.timemeasure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLogger {
	private File file;

	public FileLogger(String file) {
		this.file = new File(file);
	}

	public void log(String line) {
		FileWriter fstream = null;
		try {
			fstream = new FileWriter(file, true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(line + "\n");
			out.close();
		} catch (IOException ex) {
			Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				fstream.close();
			} catch (IOException ex) {
				Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}
