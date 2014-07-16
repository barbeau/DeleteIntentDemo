DeleteIntentDemo
================

Demo project to illustrate problem with DeleteIntent discussed at http://stackoverflow.com/questions/24769228/notification-deleteintent-broken-on-later-versions-of-android

Steps to reproduce issue:

1. Start the app
2. Tap the "Show Notification" button.  A notification saying "Route 5 has departed" should be shown.
3. Dismiss the notification.

If the DeleteIntent is received by the AlarmReceiver, you'll see a debug log printout "In onReceive with intent action com.joulespersecond.seattlebusbot.action.CANCEL" and a Toast will also be shown that says "AlarmReceiver got CANCEL Intent".

From current tests, this works correctly on:

* Android 2.3.3
* Android 2.3.6
* Android 4.1.2

...but does not work on:

* Android 4.4.3
* Android 4.4.4
