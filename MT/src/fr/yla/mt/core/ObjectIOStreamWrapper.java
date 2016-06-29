package fr.yla.mt.core;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

class ObjectIOStreamWrapper {

	static class Out{
		/**
		 * 
		 * @param os
		 * @return could be null if IOException is thrown.
		 */
		final static ObjectOutputStream ObjectOutputStreamFactory(OutputStream os){
			ObjectOutputStream oos = null;
			try {
				oos = new ObjectOutputStream(os);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return oos;
		}

		final static void writeMultiplicationTableInObjectOutputStream(ObjectOutputStream oos, MultiplicationTable mt){
			try{
				oos.writeObject(mt);
			}catch(IOException ioex){
				ioex.printStackTrace();
			}
		}

		final static void closeObjectOutputStream(ObjectOutputStream oos){
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static class In{
		/**
		 * 
		 * @param os
		 * @return could be null if IOException is thrown.
		 */
		final static ObjectInputStream ObjectInputStreamFactory(InputStream os){
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(os);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ois;
		}

		/**
		 * 
		 * @param os
		 * @return could be null if IOException or ClassNotFoundException is thrown.
		 */
		final static MultiplicationTable readMultiplicationTableFromObjectInputStream(ObjectInputStream ois){
			MultiplicationTable mt = null;
			try{
				mt = (MultiplicationTable) ois.readObject();
			}catch(EOFException e){
				//DO NOTHING
			}catch(IOException | ClassNotFoundException ioex){
				ioex.printStackTrace();
			}
			return mt;
		}

		final static void closeObjectInputStream(ObjectInputStream ois){
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



}
