package com.safu.azan;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class run {
	  private static String readAll(Reader rd) throws IOException {
		 StringBuilder sb = new StringBuilder();
		 int cp;
		 while ((cp = rd.read()) != -1) {
		 	sb.append((char) cp);
		 }
		 return sb.toString();
	   }
	  
	public static String theMonth(int month){
	     String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	     return monthNames[month];
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		 InputStream is = new URL(url).openStream();
		 try {
		   BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		   String jsonText = readAll(rd);
		   JSONObject json = new JSONObject(jsonText);
		   return json;
		 } finally {
		   is.close();
		 }
	}

	public static void main(String[] args) throws IOException, JSONException {
		java.util.Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int monthNumber = cal.get(Calendar.MONTH)+1;
		int yearNumber = cal.get(Calendar.YEAR);
		JSONObject json = readJsonFromUrl("http://api.aladhan.com/v1/calendarByCity?city=Toronto&country=Canada&method=2&month=" + monthNumber + "&year=" + yearNumber);
		getPrayerTimes(json);
		//System.out.println(json.get("id"));
	}
		  
	public static void getPrayerTimes(JSONObject json) throws JSONException, IOException {
		java.util.Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dateNumber = cal.get(Calendar.DAY_OF_MONTH)-1;
		JSONArray data = (JSONArray) json.get("data");
		getAllPrayerTimes((JSONObject) data.get(dateNumber));
	}
		  
	public static void getAllPrayerTimes(JSONObject json) throws IOException {
		//30 18 * * * fsdfs >/dev/null 2>&1
		json = json.getJSONObject("timings");
		String fajr = json.getString("Fajr").substring(0, json.getString("Fajr").indexOf("(")-1);
		String dhuhr = json.getString("Dhuhr").substring(0, json.getString("Dhuhr").indexOf("(")-1);
		String asr = json.getString("Asr").substring(0, json.getString("Asr").indexOf("(")-1);
		String magrib = json.getString("Maghrib").substring(0, json.getString("Maghrib").indexOf("(")-1);
		String isha = json.getString("Isha").substring(0, json.getString("Isha").indexOf("(")-1);
		//System.out.println(fajr + " " + dhuhr + " " + asr + " " + magrib + " " + isha);
		modifyCron(fajr, dhuhr, asr, magrib, isha);
	}
		  
	public static void modifyCron(String fajr, String dhuhr, String asr, String magrib, String isha) throws IOException {
		String userHomeFolder = System.getProperty("/home/pi/Desktop/azan");
		File textFile = new File(userHomeFolder, "cronjob.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
		try {
			  out.write("02 06 * * * bash /home/pi/Desktop/azan/src/compile" + "\n" +
			  	    dhuhr.substring(3,5) + " " + dhuhr.substring(0,2) + " * * * play /home/pi/Desktop/azan/src/preZuhr.mp3 vol 15 dB; play /home/pi/Desktop/azan/src/azan.mp3 vol 15 dB; play /home/pi/Desktop/azan/src/afterAzan.mp3 vol 15 dB" + "\n" +
				    asr.substring(3,5) + " " + asr.substring(0,2) + " * * * play /home/pi/Desktop/azan/src/preAsr.mp3 vol 15 dB; play /home/pi/Desktop/azan/src/azan.mp3 vol 15 dB; play /home/pi/Desktop/azan/src/afterAzan.mp3 vol 15 dB" + "\n" +
				    magrib.substring(3,5) + " " + magrib.substring(0,2) + " * * * play /home/pi/Desktop/azan/src/preMagrib.mp3 vol 15 dB; play /home/pi/Desktop/azan/src/azan.mp3 vol 15 dB; play /home/pi/Desktop/azan/src/afterAzan.mp3 vol 15 dB" + "\n" +
				    isha.substring(3,5) + " " + isha.substring(0,2) + " * * * play /home/pi/Desktop/azan/src/preIsha.mp3 vol 15 dB; play /home/pi/Desktop/azan/src/azan.mp3 vol 15 dB; play /home/pi/Desktop/azan/src/afterAzan.mp3 vol 15 dB \n"
			  );
		} finally {
			  out.close();
			  Runtime.getRuntime().exec("crontab -r");
			  Runtime.getRuntime().exec("crontab /home/pi/Desktop/azan/src/cronjob.txt");
		}
	}
}
