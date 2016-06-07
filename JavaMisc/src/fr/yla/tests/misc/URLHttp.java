package fr.yla.tests.misc;

import java.net.*;
import java.io.*;

class URLHttp {

	public static void main(String[] args) {
		if(args.length != 1){
			System.err.println("Usage : java tests.URLHttp <url>");
			System.exit(1);
		}
		try {
			URL url = new URL (args[0]);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("OPTIONS");
			connection.connect();
			System.out.println(connection.getResponseCode());
			System.out.println(connection.getResponseMessage());

			String key;
			String value;

			for(int i=1;(key = connection.getHeaderFieldKey(i)) != null;i++){
				value = connection.getHeaderField(i);
				System.out.println(key+" = "+value);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
