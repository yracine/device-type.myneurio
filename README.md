# device-type.myneurio

Author:             Yves Racine

linkedIn profile:   ca.linkedin.com/pub/yves-racine-m-sc-a/0/406/4b/

Date:               2015-03-15


**************************************************************************************************

My Neur.io device is now available at my store:

www.ecomatiqhomes.com/#!store/tc3yr


Setup time: about 5 minutes

=====================
PREREQUISITES
=====================

- Your Neurio device fully operational
- Your Neurio credentials (username/password)
- Developer access to SmartThings (http://graph.api.smartthings.com/)

=====================
INSTALLATION STEPS
=====================




/*******************************************************************************************************************/

<b>1) Create a new device handler (My Neurio Device) for your sensor(s)</b>
/*******************************************************************************************************************/



a) Go to https://graph.api.smartthings.com/ide/devices

b) Hit the "+New Device Handler" at the top right corner

c) Hit the "From Code" tab on the left corner

d) Copy and paste the code from neurio.devicetype under 
http://github.com/yracine/device-type.myneurio/blob/master/myNeurio.devicetype

e) Hit the create button at the bottom

f) Hit the "publish/for me" button at the top right corner (in the code window)

/*******************************************************************************************************************/

<b>2) Create a new device handler (My Neurio Appliance) for your appliances</b>
/*******************************************************************************************************************/



a) Go to https://graph.api.smartthings.com/ide/devices

b) Hit the "+New Device Handler" at the top right corner

c) Hit the "From Code" tab on the left corner

d) Copy and paste the code from neurio.devicetype under 

https://github.com/yracine/device-type.myneurio/blob/master/MyNeurioAppliance.devicetype

e) Hit the create button at the bottom

f) Hit the "publish/for me" button at the top right corner (in the code window)

/*******************************************************************************************************************/

<b>3) Create a new smartapp (MyNeurioServiceMgr)</b>
/*******************************************************************************************************************/


a) Go to https://graph.api.smartthings.com/ide/apps

b) Hit the "+New SmartApp" at the top right corner

c) Hit the "From Code" tab on the left corner

d) Copy and paste the code from MyNeurioServiceMgr
under https://github.com/yracine/device-type.myneurio/tree/master/smartapp

e) Hit the create button at the bottom

f) <b>Make sure that enable OAuth in Smartapp settings is active</b> 

* Goto app settings (top right corner), 
* Click on Oauth (middle of the page), and enable OAuth in Smart app
* Hit "Update" at the bottom

g) Go back to the code window, and hit the "publish/for me" button at the top right corner 

/*******************************************************************************************************************/

<b>4) Optionally, you can activate live logging for more tracing</b> 
/*******************************************************************************************************************/

This is useful for debugging in case of any error/exception.

Go to https://graph.api.smartthings.com/ide/logs


/*******************************************************************************************************************/

<b>5) Under the ST App, execute My NeurioServiceMgr</b>
/*******************************************************************************************************************/


<b>Under the ST app, click on the Smartapps link in the upper section of the following Marketspace screen (last icon at the bottom), and then MyApps (last item in the list).</b>


The smartapp will ask you to authenticate on the Neurio portal (by pressing on MyNeurio on the first page), and then
after the authentication, will show you the list of sensor(s) under your Neurio Account (by pressing 'Next' at the top right corner). 

On the next page, you can then select the sensor(s) to be exposed to SmartThings.

After pressing 'Next', on the following 2 pages, you may also select the appliance(s) to be exposed to SmartThings.
Please select not more than 3 appliances per page, to avoid any ST issues (i.e. execution time constraints, a ticket
has been submitted to ST support about it).

If you get a blank screen after pressing 'Next or you get the following error: " Error - bad state. Unable to complete page configuration", you'd need to enable oAuth as specified in step 2f) above.

**********************************************************************************************************
For new ST users, check if you're on a different shard then graph-na01.

Check this thread for more details:

https://community.smartthings.com/t/faq-how-to-find-out-what-shard-cloud-slice-ide-url-your-account-location-is-on/53923

If you are on the different shard, the redirect url in Neurio needs to be changed.

To do so, go to 

a) https://my.neur.io/#settings/applications

b) register a new app at my.neurio, and make sure the callback url is set to https://graph-na02-useast1.api.smartthings.com (or your actual shard if different)

c) Change the following in  MyNeurioServiceMgr at the end of the file:

- def getServerUrl() for "https://graph-na02-useast1.api.smartthings.com" (or your actual shard if different than na02)
- Change the public and private keys for the new ones from your Neur.io app.

**********************************************************************************************************

After pressing 'Done' on the last page, the smartapp will instantiate the MyNeurio device object under 

https://graph.api.smartthings.com/device/list

You can click on any new My Neurio object created and see its populated attributes.

/*******************************************************************************************************************/

<b>6) Under the ST app. go under MyHome/Smartapps and execute MyNeurioServiceMgr again to create the Neurio Appliances this time</b>
/*******************************************************************************************************************/

If you want to create also some Neurio Appliances under ST, execute MyNeurioServiceMgr again
under My Home/Smartapps

You then just need to press 'Next' till the last page where your appliances are selected.  Then,
you press 'Done' to confirm the creation.

After pressing 'Done' on the last page, the smartapp will instantiate the MyNeurioAppliance objects under 

https://graph.api.smartthings.com/device/list

You can click on any new My Appliance objects created and see their populated attributes.


/*******************************************************************************************************************/

7) Under the SmartThings app (on your tablet or smartphone), you should then
see the new Neurio Objects under the 'myHome/Things' shortcut on the dashboard

/*******************************************************************************************************************/



/*******************************************************************************************************************/

8) (optional) After instantiation of MyNeurio and My Appliance Objects, you can enable more tracing 
at the device level (for debugging only, can affect performance)

/*******************************************************************************************************************/


Edit the preferences of MyNeurio or My Appliance devices to enable more tracing

- Go to https://graph.api.smartthings.com/device/list
- Click on the My Neurio object in the list
- Edit the preferences by clicking on 'edit' (middle of the page) 
- Set the trace input parameter to true 
- Save the changes by clicking 'Save' at the bottom.



