# Hangman
Hangman Game For Android 
[Download Debug APK](https://github.com/jason1114/Hangman/blob/master/)
## Tools Used

+ Volley: Handle all HTTP traffic
+ EventBus: Post all async results as events
+ Gson: JSON deserializer
+ ButterKnife: DI solution for UI compments

## Package Structure

+ activity: all activities are here
+ bean: request & response JSON models. 
 + request: Used to serialize to JSON and send to server side
 + response: They are deserialized from JSON data returned by server
+ event: Contains server response and corresponding request, and they are post by Eventbus
+ network: Rest client to send HTTP requests and delivery responses to UI
+ utils: Constants, ToastUtils, and custom application object

## How to play

+ Login in first:
![](https://github.com/jason1114/Hangman/blob/master/imgs/device-2015-10-29-192959.png)
+ Main User Interface:
![](https://github.com/jason1114/Hangman/blob/master/imgs/device-2015-10-29-193602.png)
 + REFRESH: Refresh the current score
 + SUBMIT: Save current score to server
 + RESTART: Restart the game, if you haven't do SUBMIT, then your score will be lost
 + NEW WORD: Get a new word to guess
 + KEYBOARD: Select a character to guess

