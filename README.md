#ATAS - Automated Azan System
## About
ATAS is automated azan system that takes prayer time in respected area via an API and performs azan (muslim call to prayer) during respected time. It uses Internet (obviously to grab the data) but the files for the azan are locally saved.

The reason I created this is due to my OCD toward the azan clock that are made in China. I am not against them but they get their data via algorithms and the timing are always off since the algorithms does not do your location but rather its a pre-defined 'timezone' which is a real problem living in Canada. We observe 'day light' saving time so many times of the year our prayer time changes heavily. Also our climate is considered very dynamic to our winter which requries many manual changes to the clock. 

To get rid of this problem, I wnated to automate the azan system, thus I quickly turned on Hasan Minhaj latest show on Netflix and made ATAS.

###Features

- Automatically grab prayer time everyday
- No need for manual adjustment. It keeps track of your time regardless of daylight saving or sunset time being change.
- No need to check which prayer time it is. ATAS will mention what azan it will poof out before starting.

###Instruction
1. Start your pi and external speaker
2. Plug your external speaker into your pi
3. Log into your pi and install the following plugin (This will be your audio player)

```bash
 sudo apt install sox
 sudo apt install libsox-fmt-mp3
```
4. Make a folder call azan in the desktop.
5. Clone the repo somewhere in the desktop.
6. Copy all the contents from inside the repo folder into azan so doing ls on azan folder should show something like this:
```bash
 $ ls azan/
 README.md 		src
```

7. Go inside src folder and run the following command ./compile
8. Verify that the compilation of the program ran correctly by doing crontab -l. If everything worked you should see stuff in the cronjob. If not, your paths are wrong in the source code.

You are all set! Just leave your raspberry pi ON and speaker to good 50% volume, and you are good to go!.

If you have any problem then don't email me, but rather open a issue ticket.

#### How do I change the location for prayer time?
Inside /src/com/safu/azan/run.java modify line #29 which is the following:

		JSONObject json = restCaller.readJsonFromUrl("http://api.aladhan.com/v1/calendarByCity?city=Toronto&country=Canada&method=2&month=" + date.getMonth() + "&year=" + date.getYear());

to your location:

		JSONObject json = restCaller.readJsonFromUrl("http://api.aladhan.com/v1/calendarByCity?city=CITY&country=COUNTRY&method=2&month=" + date.getMonth() + "&year=" + date.getYear());

CITY - City
COUNTRY - Country

#### The path to my desktop is different than what the program is being defined with. How do I fix some of the path like getting the azan location?

Inside /src/com/safu/azan/cronManagement.java is where all the PATH are defined for all mp3. You can fix it there.

#### I am not Bengali, I don't want to Bangla audio.

Inside /src/com/safu/azan/cronManagement.java is where all the PATH are defined, you can remove the preAzan code from the cronjob String.

#### Where is the pre azan English audio?
I don't have any, you can record your own voice or download some pre-made from Internet, save it somewhere in your PI or local repo and then define the path in

/src/com/safu/azan/cronManagement.java

#### It is giving AZAN during wrong time.
This is not the faulty of the program. The local setting on your PI is different from your location. Please change the local setting on your PI to your location.
