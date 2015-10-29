# Hangman
Hangman Game For Android 
[Download Debug APK](https://github.com/jason1114/Hangman/blob/master/app-debug.apk)
## Tools Used

+ Volley: Handle all HTTP traffic
+ EventBus: Post all async results as events
+ Gson: JSON deserializer
+ ButterKnife: DI solution for UI components

## Package Structure

+ activity: all activities are here
+ bean: request & response JSON models. 
 + request: Used to serialize to JSON and send to server side
 + response: They are de-serialized from JSON data that returned by server
+ event: Contains server responses and corresponding requests, and they are post by EventBus
+ network: REST client to send HTTP requests and deliver responses to UI
+ utils: Constants, ToastUtils, and custom application object

## How to play

+ Login in first:

<img src="https://github.com/jason1114/Hangman/blob/master/imgs/device-2015-10-29-192959.png" width="300px">

+ Main User Interface:

<img src="https://github.com/jason1114/Hangman/blob/master/imgs/device-2015-10-29-193602.png" width="300px">

 + REFRESH: Refresh the current score
 + SUBMIT: Save current score to server
 + RESTART: Restart the game, if you haven't SUBMIT, then your score will be lost
 + NEW WORD: Get a new word to guess
 + KEYBOARD: Select a letter to guess

