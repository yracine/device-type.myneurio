# device-type.myneurio
My Neurio Device:  Custom Neurio device to enable more neurio capabilities within Smartthings 

Author:             Yves Racine

linkedIn profile:   ca.linkedin.com/pub/yves-racine-m-sc-a/0/406/4b/

Date:               2015-03-15

Code: http://github.com/yracine/device-type.myneurio

**************************************************************************************************
If you like My Neurio Device and related smartapps, please support the developer:


<br/> [![PayPal](https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif)](
https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=yracine%40yahoo%2ecom&lc=US&item_name=Maisons%20ecomatiq&no_note=0&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHostedGuest)


=====================
INSTALLATION STEPS
=====================



/******************************************************************************************/

1) Create the authorization code API keys at Neur.io portal
/******************************************************************************************/


a) Go to https://my.neur.io/

b) Log in using your Neur.io credentials (username/password)

c) Go to Settings (click on the menu at the upper right corner)

d) Click on Apps (left menu, second option)

e) Click on register new app (on the right inside)

f) a screen will appear with the following fields:

- Enter an application name (your choice)

    ex. SmartThings
- Enter a description (your choice)

- Enter a homepage URL (your choice)
 

    ex. http://testapp.com
-  Enter a callback URL: need to be the following (no spaces):

    https://graph.api.smartthings.com/api/token/

g) click on the 'Register new App' button at the bottom of the screen

h) Take note of the client id and the secret exactly as they appear on screen

/******************************************************************************************/

2) Create a new device type (My Neurio Device) for your sensor(s)
/******************************************************************************************/



a) Go to https://graph.api.smartthings.com/ide/devices

b) Hit the "+New SmartDevice" at the top right corner

c) Hit the "From Code" tab on the left corner

d) Copy and paste the code from neurio.devicetype
under http://github.com/yracine/device-type.myneurio/blob/master/MyNeurio.devicetype.groovy

e) Hit the create button at the bottom

f) Hit the "publish/for me" button at the top right corner (in the code window)

/******************************************************************************************/

3) Create a new device type (My Neurio Appliance) for your appliances
/******************************************************************************************/



a) Go to https://graph.api.smartthings.com/ide/devices

b) Hit the "+New SmartDevice" at the top right corner

c) Hit the "From Code" tab on the left corner

d) Copy and paste the code from neurio.devicetype
under https://github.com/yracine/device-type.myneurio/blob/master/MyNeurioAppliance.devicetype.groovy

e) Hit the create button at the bottom

f) Hit the "publish/for me" button at the top right corner (in the code window)

/******************************************************************************************/

4) Create a new smartapp (MyNeurioServiceMgr)
/******************************************************************************************/


a) Go to https://graph.api.smartthings.com/ide/apps

b) Hit the "+New SmartApp" at the top right corner

c) Hit the "From Code" tab on the left corner

d) Copy and paste the code from MyNeurioServiceMgr
under https://github.com/yracine/device-type.myneurio/blob/beta/smartapp/

e) Update the client id and client secret at the end of the file.

- def getSmartThingsClientId() { "kjPlS3AAQtaUGlmB30IU9g" } - substitute your client id here!

- def getSmartThingsPrivateKey() { "Insert your private API Key here!" } - substitute your client secret here!

f) Hit the create button at the bottom

g) Make sure that enable OAuth in Smartapp is active 

* Goto app settings (top right corner), 
* Click on Oauth (middle of the page), and enable OAuth in Smart app
* Hit "Update" at the bottom

h) Go back to the code window, and hit the "publish/for me" button at the top right corner 

/******************************************************************************************/

5) Optionally, you can activate live logging for more tracing
/******************************************************************************************/


Go to https://graph.api.smartthings.com/ide/logs


/******************************************************************************************/

6) Use SmartSetup and execute My NeurioServiceMgr
/******************************************************************************************/


From your phone or tablet, within the smarttings app and on the main screen, click on '+' at the bottom, scroll right to to My Apps, and execute MyNeurioServiceMgr.

The smartapp will ask you to authenticate on the Neurio portal (by pressing on MyNeurio on the first page), and then
after the authentication, will show you the list of sensor(s) under your Neurio Account (by pressing 'Next' at the top right corner). 

On the next page, you can then select the sensor(s) to be exposed to SmartThings.

After pressing 'Next', on the following 2 pages, you can also select the appliance(s) to be exposed to SmartThings.

After pressing 'Done' on the last page, il will instantiate the MyNeurio object under 

https://graph.api.smartthings.com/device/list

You can click on any new My Neurio object created and see its populated attributes.

/******************************************************************************************/

7) Use SmartSetup and execute My NeurioServiceMgr again (to create the Neurio Appliances only)
/******************************************************************************************/


You then just need to press Next till the last page where your appliances are selected.  Then,
you press 'Done' to confirm the creation.

You can click on any new My Appliance objects created and see their populated attributes.

/******************************************************************************************/

8) Under the SmartThings app (on your tablet or smartphone), you should then
see the new Object under the 'Things' shortcut on the dashboard
/******************************************************************************************/


/******************************************************************************************/

9) (optional) After instantiation of MyNeurio and My Appliance Objects, you can enable more tracing 
at the device level (if needed)
/******************************************************************************************/


Edit the preferences of MyNeurio or My Appliance devices to enable more tracing

- Go to https://graph.api.smartthings.com/device/list
- Click on the My Neurio object in the list
- Edit the preferences by clicking on 'edit' (middle of the page) 
- Set the trace input parameter to true 
- Save the changes by clicking 'Save' at the bottom.



