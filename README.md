# weatherapplication

This application allows a user to enter in a zip code and see the weather at that location.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

Download Android Studio from https://developer.android.com/studio/
install Android Studio on your machine.
Download the project from Github.

In Android Studio click the File tab and select open.
Navigate to the project location and select it then click open.

### Prerequisites


```
Android Studio : https://developer.android.com/studio/
```

### Installing



```
Run the Android Studio Installer. 
Open the project from the Android Studio "open" option under the "file" menu.
Create an android emulator from the AVD "Android Virtual Device Manager" by clicking the purple phone icon in the IDE menu.
Start the emulator from the emulator menu.
Run the app from android studio and select the running android emulator.
```


## Deployment

To deploy to the Google Play store you first need to select the "Generate signed APK" from the Build menu.
If there is no keystore create one must be created now.
The created keystore needs to have a expiration date, password and at lest one identifier filled in.
Once a keystore is available the signed apk process can continue by entering the password.
The generated apk can be found inside the release folder inside the project folder.
The apk can be either uploaded to the google play store or installed onto an Android device manually.


## Instructions

A valid five digit zip code needs to be entered into the zip code field. The "Get weather" button is then pressed to load up the weather for the entered zip code. The menu button in the top bar can be pressed to show a drop down menu with six options that have no purpose.


## Built With

Android Studio
Kotlin

## Authors

* **Rob Latta** 


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

