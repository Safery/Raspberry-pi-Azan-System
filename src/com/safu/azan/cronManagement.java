package com.safu.azan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class cronManagement {
	
	public static String getAzan() {
		HashMap<Integer, String> azanPath= new HashMap<Integer, String>();
		azanPath.put(0, "play /home/pi/Desktop/azan/src/sounds/azans/azan.mp3 vol 25 dB; play /home/pi/Desktop/azan/src/sounds/afterAzan.mp3 vol 25 dB");
		azanPath.put(1, "play /home/pi/Desktop/azan/src/sounds/azans/azan1.mp3 vol 25 dB;");
		azanPath.put(2, "play /home/pi/Desktop/azan/src/sounds/azans/azan2.mp3 vol 25 dB; play /home/pi/Desktop/azan/src/sounds/afterAzan.mp3 vol 25 dB");
		azanPath.put(3, "play /home/pi/Desktop/azan/src/sounds/azans/azan3.mp3 vol 25 dB; play /home/pi/Desktop/azan/src/sounds/afterAzan.mp3 vol 25 dB");
		azanPath.put(4, "play /home/pi/Desktop/azan/src/sounds/azans/azan4.mp3 vol 25 dB;");

		
		int generateRandomIndex = (int) (Math.random() * (4 - 0)) + 0;

		return azanPath.get(generateRandomIndex);
	}
	
	public static void modifyCron(String fajr, String dhuhr, String asr, String magrib, String isha) throws IOException {
		String userHomeFolder = System.getProperty("/home/pi/Desktop/azan");
		File textFile = new File(userHomeFolder, "cronjob.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
		try {
			  out.write("17 17 * * * bash /home/pi/Desktop/azan/src/compile" + "\n" +
			  	    dhuhr.substring(3,5) + " " + dhuhr.substring(0,2) + " * * * play /home/pi/Desktop/azan/src/sounds/preAzan-Bengali/preZuhr.mp3 vol 15 dB;" + getAzan() + "\n" +
				    asr.substring(3,5) + " " + asr.substring(0,2) + " * * * play /home/pi/Desktop/azan/src/sounds/preAzan-Bengali/preAsr.mp3 vol 15 dB;" + getAzan() + "\n" +
				    magrib.substring(3,5) + " " + magrib.substring(0,2) + " * * * play /home/pi/Desktop/azan/src/sounds/preAzan-Bengali/preMagrib.mp3 vol 15 dB;" + getAzan() + "\n" +
				    isha.substring(3,5) + " " + isha.substring(0,2) + " * * * play /home/pi/Desktop/azan/src/sounds/preAzan-Bengali/preIsha.mp3 vol 15 dB;" + getAzan() + "\n"
			  );
		} finally {
			  out.close();
			  Runtime.getRuntime().exec("crontab -r");
			  Runtime.getRuntime().exec("crontab /home/pi/Desktop/azan/src/cronjob.txt");
		}
	}
}
