(DEPRECATED)

# device-type.myneurio

*** The devices can be created using the Samsung connect app ****

Author:             Yves Racine

linkedIn profile:   ca.linkedin.com/pub/yves-racine-m-sc-a/0/406/4b/

Date:               2015-03-15


**************************************************************************************************

My Neur.io device is now available at my store:

www.ecomatiqhomes.com/#!store/tc3yr


Setup time: about 10-15 minutes depending on your ST skills.


PREREQUISITES
=====================

- (a) Your Neurio device fully operational
- (b) Your Neurio credentials (username/password)
- (c) Developer access to SmartThings (http://graph.api.smartthings.com/)

https://support.smartthings.com/hc/en-us/articles/205956850-How-to-edit-Location-settings

- (d) <b> Determine your shard, please consult this thread: </b>

https://community.smartthings.com/t/faq-how-to-find-out-what-shard-cloud-slice-ide-url-your-account-location-is-on/53923

Or the SmartThings documentation here for more details:


http://docs.smartthings.com/en/latest/publishing/index.html#ensure-proper-location


 <b>If you are on a different shard, you need to change the links below for your right shard. 
 Also, please take note of the extra tasks involved in step 6. </b>

As an example, in North America,

replace https://graph.api.smartthings.com/ide/devices by https://graph-na02-useast1.api.smartthings.com


Or use  https://consigliere-regional.api.smartthings.com/ to point to the right shard.

INSTALLATION STEPS
=====================



# 1) Create a new device handler (My Neurio Device) for your sensor(s)



a) Go to https://graph.api.smartthings.com/ide/devices    (or whatever your shard is and click on My Device Handlers in the IDE's top menu)

b) Hit the "+New Device Handler" at the top right corner

c) Hit the "From Code" tab on the left corner

d) Copy and paste the code from the corresponding txt file in the zip 

e) Hit the create button at the bottom

f) Hit the "publish/for me" button at the top right corner (in the code window)


# 2) Create a new device handler (My Neurio Appliance) for your appliances

a) Go to https://graph.api.smartthings.com/ide/devices   (or whatever your shard is and click on My Device Handlers in the IDE's top menu)

b) Hit the "+New Device Handler" at the top right corner

c) Hit the "From Code" tab on the left corner

d) Copy and paste the code from the corresponding txt file in the zip

e) Hit the create button at the bottom

f) Hit the "publish/for me" button at the top right corner (in the code window)


# 3) Create a new smartapp (MyNeurioServiceMgr)


a) Go to https://graph.api.smartthings.com/ide/apps    (or whatever your shard is and click on My Smartapps in the IDE's top menu)

b) Hit the "+New SmartApp" at the top right corner

c) Hit the "From Code" tab on the left corner

d) Copy and paste the code from MyNeurioServiceMgr from the txt file in the zip 

e) Hit the create button at the bottom

f) <b>Make sure that enable OAuth in Smartapp settings is active</b> 

* Goto app settings (top right corner, click on it),
* Click on Oauth (middle of the page), and enable OAuth in Smart app
* Hit "Update" at the bottom

g) Go back to the code window, and hit the "publish/for me" button at the top right corner 

If the instructions above are not clear enough, you can refer to the troubleshooting section below with some pictures:

http://thingsthataresmart.wiki/index.php?title=MyNeurioServiceMgr#Issue_.231:_I_don.27t_know_how_to_create_a_custom_smartapp


# 4) (Optional) Create a new smartapp (MyNeurioAppliancesMgr)

This step is not required if you do not want to expose your appliances to ST.

a) Go to https://graph.api.smartthings.com/ide/apps    (or whatever your shard is and click on My Smartapps in the IDE's top menu)

b) Hit the "+New SmartApp" at the top right corner

c) Hit the "From Code" tab on the left corner

d) Copy and paste the code from MyNeurioApplianceMgr from the text file in the zip that was sent to you.

e) Hit the create button at the bottom

f) <b>Make sure that enable OAuth in Smartapp settings is active</b> 

* Goto app settings (top right corner), 
* Click on Oauth (middle of the page), and enable OAuth in Smart app
* Hit "Update" at the bottom

g) Go back to the code window, and hit the "publish/for me" button at the top right corner 


If the instructions above are not clear enough, you can refer to the troubleshooting section below with some pictures:

http://thingsthataresmart.wiki/index.php?title=MyNeurioServiceMgr#Issue_.231:_I_don.27t_know_how_to_create_a_custom_smartapp


# 5) Optionally, you can activate live logging for more tracing</b> 

This is useful for debugging in case of any error/exception.
  
Go to https://graph.api.smartthings.com/ide/logs    (or whatever your shard is and click on Live logging in the IDE's top menu)


# 6) Under the new Samsung connect app, execute MyNeurioServiceMgr (under + in the upper right corner/Smartapp)



<b>Click on the Smartapp name (MyNeurioServiceMgr) in the list of smartapps under the Custom section</b>



The smartapp will ask you to authenticate on the Neurio portal (by pressing on MyNeurio on the first page), and then
after the authentication (you need to press 'done') , the next page will show you the list of sensor(s) under your Neurio Account (by pressing 'Next' at the top right corner). 

On the next page, you can then select the sensor(s) to be exposed to SmartThings.

*************************************************************************************************************************************
N.B. If you have any errors:

If you get a blank screen after pressing 'Next or you get the following error: "Error - bad state' or 'Java.lang.NullPointerException: Cannot get property 'accessToken' on null object" in the IDE', you'd need to enable oAuth as specified in step 2f) above.
 

If you have the message "{"error": true, "type": "AccessDenied","message:"This request is not authorized by the specified access token"}", this message can appear for new ST users as you're on a different shard then graph-na01.



Check this thread for more details:

https://community.smartthings.com/t/faq-how-to-find-out-what-shard-cloud-slice-ide-url-your-account-location-is-on/53923

<b>If you are on the different shard, the redirect url in Neurio needs to be changed.</b>

To do so, go to 

a) https://my.neur.io/#settings/applications

b) register a new app at my.neurio, and make sure the callback url is set to https://graph-na02-useast1.api.smartthings.com (or your actual shard if different)

c) Change the following in  MyNeurioServiceMgr at the end of the source file:

- def getServerUrl() for "https://graph-na02-useast1.api.smartthings.com" (or your actual shard if different than na02)
- Change the public and private keys for the new ones from your newly created Neur.io app.  Make sure to paste the keys without any extra characters or spaces.


*************************************************************************************************************************************
N.B.

<b> At the end of the authorization flow,  if you have the following error message: "Unexpected error" even if you press several times, this probably means that you have not "saved & published" one of the Device Handler Types (MyNeurioDevice,MyNeurioAppliance) under the right shard.  Refer to the prerequisites & steps 1 & 2 for more details.
 
Also, depending on the ST platform status, you may have to press "Save" several times if you have the following error message: "Error processing your request - please try again".  This is due to some ST platform timeouts due to rate limiting.</b> 
 
*************************************************************************************************************************************

The smartapp will instantiate the MyNeurio device object(s) under 

https://graph.api.smartthings.com/device/list    (or whatever your shard is and click on My Devices in the IDE's top menu)


You can click on any new My Neurio object created and see its populated attributes.

# 7) (Optional)Under the new Samsung connect app, execute  MyNeurioAppliancesMgr  (under + in the upper right corner/Smartapp) to create the Neurio Appliances 


You just need to authenticate to Neurio Portal,  press 'Next' till the last page where your appliances are selected.  Then,
you press 'Done' to confirm the creation.  In some instances, due to ST timeouts, you may want to press 'done' multiple
times to instantiate all your Neurio appliance objects (especially if you have a large number of Neurio appliances).
If you have recurrent ST timeout issues in live logging, then you should execute the smartapp several times and just expose 2-3 appliances more each time (this is not due to the code itself, but the ST rate limiting).

After pressing 'Done' on the last page, the smartapp will instantiate the MyNeurioAppliance objects under 

https://graph.api.smartthings.com/device/list (or whatever your shard is and click on My Devices in the IDE's top menu)

You can click on any new My Appliance objects created and see their populated attributes.



# 8) View your Neurio Objects Under the SmartThings app



On your tablet or smartphone, you should then see the all the new Neurio Objects under the 'myHome/Things' shortcut on the dashboard

<b> N.B. For all appliances, press the "refresh" tile in order to get the fields populated in the UI.</b>


# 9) (Optional) Set Device's Preferences

After instantiation of MyNeurio and My Appliance Objects, you can enable more tracing 
at the device level (for debugging only, can affect performance)

Edit the preferences of MyNeurio or My Appliance devices to enable more tracing

- Go to https://graph.api.smartthings.com/device/list (or whatever your shard is and click on My Devices in the IDE's top menu)
- Click on the My Neurio object in the list
- Edit the preferences by clicking on 'edit' (middle of the page) 
- Set the trace input parameter to true 
- Save the changes by clicking 'Save' at the bottom.


You only need to edit the above parameters

    P.S. Don't enter any values the neurioId or for the appKey as the values are only
    used for the PIN authentication method.  If you do it, you may
    experience authentication issues when used with MyNeurioServiceMgr smartapp.

