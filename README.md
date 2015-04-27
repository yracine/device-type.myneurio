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

**************************************************************************************************

=====================
INSTALLATION STEPS
=====================

*************************************************
1) Create a new device type (My Neurio Device)
*************************************************


a) Go to https://graph.api.smartthings.com/ide/devices

b) Hit the "+New SmartDevice" at the top right corner

c) Hit the "From Code" tab on the left corner

d) Copy and paste the code from neurio.devicetype
under https://github.com/yracine/device-type.myneurio/blob/master/MyNeurio.devicetype.groovy

e) Hit the create button at the bottom

f) Hit the "publish/for me" button at the top right corner (in the code window)

*************************************************
2) Create a new smartapp (MyNeurioInit)
*************************************************

a) Go to https://graph.api.smartthings.com/ide/apps

b) Hit the "+New SmartApp" at the top right corner

c) Hit the "From Code" tab on the left corner

d) Copy and paste the code from MyNeurioInit
under http://github.com/yracine/device-type.myneurio/tree/master/smartapp

e) Hit the create button at the bottom

f) Make sure that enable OAuth in Smartapp is active 

* Goto app settings (top right corner), 
* Click on Oauth (middle of the page), and enable OAuth in Smart app
* Hit "Update" at the bottom

g) Go back to the code window, and hit the "publish/for me" button at the top right corner 

*************************************************
3) Create a new device (https://graph.api.smartthings.com/device/list)
*************************************************

 * Name: Your Choice
 * Device Network Id: Your Choice
 * Type: My Neurio Device (should be the last option)
 * Location: Choose the correct location

  Hub/Group: (optional) leave blank or set it up to your liking
  
  
*************************************************
4) Update device's preferences
*************************************************

    (a) <appKey> public key provided by Neurio (mandatory, no spaces)
    (b) <privateKey> provided by Neurio (mandatory, no spaces)
    (c) <sensorId> (MAC Address of your sensor, optional,default=1st sensor found,no spaces, no ':')
    (d) <trace> when needed, set to true to get more tracing (no spaces)


*************************************************
5) Use SmartSetup and execute My Neurio Init
*************************************************


From your phone or tablet, within the smarttings app and on the main screen, click on '+' at the bottom, scroll right to to My Apps, execute MyNeurioInit

*************************************************
6) The device should now be populated with your latest Neurio data
*************************************************

