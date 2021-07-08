# Kotlin Service for Android Studio

In this project, there are 3 services, 2 of them still work one of them deprecated even before project is made.

1. JobIntentService -> Deprecated
2. Service
Service done in this project is a very basic one. The service only be able to run without doing anything otherthan printing some log messages. The service can run with 
or without input. The input will be printed as log messages as well. Inside the service there is an infinite loop separated in other thread so make sure to stop the
service if you want this infinite loop to stop. But don't worry because this infinite loop does nothing as well. The purpose of this infinite loop is to show how to make
non UI interfering process inside the service.

3. Foreground service
This service is supposed to be a service that gives notification to the phone. The service has a countdown timer and will print the remaining time per some seconds.
I have been trying to update the UI by this remaining time on timer but to this point I haven't succeeded yet.
I tried to 2 methods to do it:
    1. BroadcastReceiver (Not working, somehow the entity doesn't receive the sent messsages.)
    2. ServiceConnection (It is able to connect the data to UI but not able to trigger update on UI, so the UI needs to request for updating the text based on the remaining time)
