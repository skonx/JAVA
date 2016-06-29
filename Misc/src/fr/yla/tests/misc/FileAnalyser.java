package fr.yla.tests.misc;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileAnalyser {

	private final FileSystem fs;
	private final Path path;
	private final String file = ".DS_Store";
	private static final Logger log = Logger.getAnonymousLogger();

	public FileAnalyser(Handler handler){
		fs = FileSystems.getDefault();
		path = fs.getPath(System.getProperty("user.home"));
		handler.setFormatter(new SimpleFormatter());
		log.addHandler(handler);
		log.info("Start Detection");
	}

	private List<Path> analyse(Path path) throws IOException{

		List<Path> searchedFiles = new ArrayList<>();
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(path)){
			for (Path p : stream) {
				if(Files.isDirectory(p))
					searchedFiles.addAll(analyse(p));
				else
					if(p.getFileName().endsWith(file)){
						searchedFiles.add(p);
						log.warning(p.toString()+" = "+Files.size(p)+" Bytes");
					}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return searchedFiles;
	}

	public static void main(String[] args){
		try {
			FileAnalyser fa = new FileAnalyser(new FileHandler("%h/jsie_DS_Store_Analysis-%u.log",false));
			List<Path> searchedFiles = fa.analyse(fa.path);
			long total = searchedFiles.stream().mapToLong(p->{
				long size = 0L;
				try {
					size = Files.size(p);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return size;
			}).sum();
			log.info("TOTAL = "+total+" Bytes");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			log.info("Detection is over");
			for(Handler h:log.getHandlers())
				h.close();

		}
	}
}
