package com.safu.azan;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.safu.date.date;

public class prayerTimer {
	public HashMap<String, String> getPrayerTimes(JSONObject json) throws JSONException, IOException {
		JSONArray data = (JSONArray) json.get("data");
		return getAllPrayerTimes((JSONObject) data.get(date.getDayDate()));
	}
		  
	private HashMap<String, String> getAllPrayerTimes(JSONObject json) throws IOException {
		//30 18 * * * fsdfs >/dev/null 2>&1
		HashMap<String, String> returnAzanTimes = new HashMap<String, String>();
		json = json.getJSONObject("timings");
		returnAzanTimes.put("fajr", json.getString("Fajr").substring(0, json.getString("Fajr").indexOf("(")-1));
		returnAzanTimes.put("dhuhr", json.getString("Dhuhr").substring(0, json.getString("Dhuhr").indexOf("(")-1));
		returnAzanTimes.put("asr", json.getString("Asr").substring(0, json.getString("Asr").indexOf("(")-1));
		returnAzanTimes.put("magrib", json.getString("Maghrib").substring(0, json.getString("Maghrib").indexOf("(")-1));
		returnAzanTimes.put("isha", json.getString("Isha").substring(0, json.getString("Isha").indexOf("(")-1));
		return returnAzanTimes;
		//System.out.println(fajr + " " + dhuhr + " " + asr + " " + magrib + " " + isha);
		//modifyCron(fajr, dhuhr, asr, magrib, isha);
	}
}
