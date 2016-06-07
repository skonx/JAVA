package fr.yla.mt.core;

import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class MTLoader<T> {

	private final static String file = "MultiplicationTables.ser";

	private final Function<InputStream,T> factory;
	private final Function<T,MultiplicationTable> reader;
	private final Consumer<T> closer;

	private MTLoader(Function<InputStream, T> factory, Function<T, MultiplicationTable> reader, Consumer<T> closer) {
		this.factory = Objects.requireNonNull(factory);
		this.reader = Objects.requireNonNull(reader);
		this.closer = Objects.requireNonNull(closer);
	}

	public static <T> MTLoader<T> createMTLoader(Function<InputStream, T> factory, Function<T, MultiplicationTable> reader, Consumer<T> closer){
		return new MTLoader<T>(factory,reader,closer);
	}

	public MTComposite loadIntoComposite(){
		MTComposite mtc = new MTComposite();

		System.out.println("### Load the backups ###");

		try(InputStream is = Files.newInputStream(FileSystems.getDefault().getPath(file))){
			T ois = factory.apply(is);
			MultiplicationTable mt;
			while((mt = (MultiplicationTable) reader.apply(ois)) != null){
				mtc.add(mt);
				System.out.println("["+mt.getClass().getSimpleName()+"] loaded");
			}
			closer.accept(ois);	
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			System.out.println("~~ Load is over ~~");
		}
		return mtc;
	}
}
