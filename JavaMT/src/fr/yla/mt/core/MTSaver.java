package fr.yla.mt.core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class MTSaver<T> {

	private final static String file = "MultiplicationTables.ser";
	private final Function<OutputStream,T> factory;
	private final BiConsumer<T, MultiplicationTable> writer;
	private final Consumer<T> closer;

	private MTSaver(Function<OutputStream, T> factory, BiConsumer<T, MultiplicationTable> writer, Consumer<T> closer) {
		this.factory = Objects.requireNonNull(factory);
		this.writer = Objects.requireNonNull(writer);
		this.closer = Objects.requireNonNull(closer);
	}

	public static <T> MTSaver<T> createMTSaver(Function<OutputStream, T> factory, BiConsumer<T, MultiplicationTable> writer, Consumer<T> closer){
		return new MTSaver<T>(factory,writer,closer);
	}

	public void save(MultiplicationTable mt){
		System.out.println("### Save the different matrix ###");
		try (OutputStream os = Files.newOutputStream(FileSystems.getDefault().getPath(file))){
			T oos = factory.apply(os);

			if(mt != null){
				writer.accept(oos,mt);
				System.out.println("["+mt.getClass().getSimpleName()+"] saved");
			}
			
			closer.accept(oos);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			System.out.println("== Save is over ==");
		}
	}
}
