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
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.safu.date.date;
import com.safu.jsonManagement.read;

public class run {

	public static void main(String[] args) throws IOException, JSONException {
		// Initialize REST request.
		read restCaller = new read();
		prayerTimer prayerTimeGetter = new prayerTimer();
		JSONObject json = restCaller.readJsonFromUrl("http://api.aladhan.com/v1/calendarByCity?city=Toronto&country=Canada&method=2&month=" + date.getMonth() + "&year=" + date.getYear());
		HashMap<String, String> prayerTimes = prayerTimeGetter.getPrayerTimes(json);
		cronManagement.modifyCron(prayerTimes.get("fajr"), prayerTimes.get("dhuhr"), prayerTimes.get("asr"), prayerTimes.get("magrib"), prayerTimes.get("isha"));
		
	}
}
