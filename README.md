DeleteIntentDemo
================

Demo project to illustrate problem with DeleteIntent discussed at http://stackoverflow.com/questions/24769228/notification-deleteintent-broken-on-later-versions-of-android

Steps to reproduce issue:

1. Start the app
2. Tap the "Show Notification - Receiver" button to send the deleteIntent to the BroadcastReceiver, or "Show Notification - Service" to send the deleteIntent to the Service.  A notification saying "Route 5 has departed" should be shown.
3. Dismiss the notification.

If the DeleteIntent is received by the AlarmReceiver or AlarmService, you'll see a debug log printout and a Toast will also be shown.

From current tests, this works correctly on:

* Android 2.3.3
* Android 2.3.6
* Android 4.1.2

...but does not work on:

* Android 4.4.3
* Android 4.4.4

I've filed an issue for this at the AOSP issue tracker here:

https://code.google.com/p/android/issues/detail?id=73720

...and submitted a fix for this issue here:

https://android-review.googlesource.com/#/c/102060/