/**
 *  MyNeurioServiceMgr
 *
 *  Copyright 2015 Yves Racine
 *  linkedIn profile: ca.linkedin.com/pub/yves-racine-m-sc-a/0/406/4b/
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable lax or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
	name: "MyNeurioServiceMgr",
	namespace: "yracine",
	author: "Yves Racine",
	description: "This smartapp is the Service Manager for My Neurio Device: it instantiates the Neurio devices and polls them on a regular basis",
	category: "My Apps",
	iconUrl: "https://s3-us-west-2.amazonaws.com/neurio/community/NeurioAppLogo60x60.png",
	iconX2Url: "https://s3-us-west-2.amazonaws.com/neurio/community/NeurioAppLogo72x72.png",
	iconX3Url: "https://s3-us-west-2.amazonaws.com/neurio/community/NeurioAppLogo72x72.png"
)


preferences {
	page(name: "about", title: "About", nextPage: "auth")
	page(name: "auth", title: "Neurio", content:"authPage", nextPage:"deviceList")
	page(name: "deviceList", title: "Neurio", content:"NeurioDeviceList", install:true)
}

mappings {
	path("/swapToken") {
		action: [
			GET: "swapToken"
		]
	}
}

def about() {
 	dynamicPage(name: "about", install: false, uninstall: true) {
 		section("About") {	
			paragraph "MyNeurioServiceMgr, the smartapp that connects your Neurio Sensor(s) to SmartThings via cloud-to-cloud integration"
			paragraph "Version 0.1\n\n" +
			"If you like this app, please support the developer via PayPal:\n\nyracine@yahoo.com\n\n" +
			"Copyright©2015 Yves Racine"
			href url:"http://github.com/yracine/device-type.myneurio", style:"embedded", required:false, title:"More information...", 
			description: "http://github.com/yracine/device-type.myneurio"
		}
	}        
}

def authPage() {
	log.debug "authPage()"

	if(!atomicState.accessToken)
	{
		log.debug "about to create access token"
		createAccessToken()
		atomicState.accessToken = state.accessToken
	}


	def description = "Required"
	def uninstallAllowed = false
	def oauthTokenProvided = false

	if(atomicState.authToken)
	{
		log.debug "atomicState.authToken in authPage= ${atomicState.authToken}"

		// TODO: Check if it's valid
		if(true)
		{
			description = "You are connected."
			uninstallAllowed = true
			oauthTokenProvided = true
		}
		else
		{
			description = "Required" // Worth differentiating here vs. not having atomicState.authToken? 
			oauthTokenProvided = false
		}
	}

	def redirectUrl = oauthInitUrl()

	log.debug "RedirectUrl = ${redirectUrl}"

	// get rid of next button until the user is actually auth'd

	if (!oauthTokenProvided) {

		return dynamicPage(name: "auth", title: "Login", nextPage:null, uninstall:uninstallAllowed) {
			section(){
				paragraph "Tap below to log in to the Neurio portal and authorize SmartThings access. Be sure to scroll down on page 2 and press the 'Allow' button."
				href url:redirectUrl, style:"embedded", required:true, title:"My Neurio", description:description
			}
		}

	} else {

		return dynamicPage(name: "auth", title: "Log In", nextPage:"deviceList", uninstall:uninstallAllowed) {
			section(){
				paragraph "Tap Next to continue to setup your Neurio Sensors."
				href url:redirectUrl, style:"embedded", state:"complete", title:"My Neurio", description:description
			}
		}

	}


}

def NeurioDeviceList() {
	log.debug "NeurioDeviceList()"

	def neurioSensors = getNeurioSensors()

	log.debug "device list: $neurioSensors"

	def p = dynamicPage(name: "deviceList", title: "Select Your Sensor(s)", uninstall: true) {
		section(""){
			paragraph "Tap below to see the list of Neurio Sensors available in your Neurio account and select the ones you want to connect to SmartThings."
			input(name: "NeurioSensors", title:"", type: "enum", required:true, multiple:true, description: "Tap to choose", metadata:[values:neurioSensors])
		}
	}

	log.debug "list p: $p"
	return p
}

def getNeurioSensors() {
	def NEURIO_SUCCESS=200

	log.debug "getting Neurio devices list"
	def deviceListParams = [
		uri: "${get_URI_ROOT()}",
		path: "/users/current",
		headers: ["Authorization": "Bearer ${atomicState.authToken}"],
		Accept: "application/json"
	]

	log.debug "_______AUTH______ ${atomicState.authToken}"
	log.debug "device list params: $deviceListParams"

	def sensors = [:]
	try {
		httpGet(deviceListParams) { resp ->

			if (resp.status == NEURIO_SUCCESS) {
/*        
				int i=0    // Used to simulate many sensors
*/
				log.debug "getNeurioSensors>resp data = ${resp.data}" 
				def userid = resp.data.user.id
				def username = resp.data.user.name
				def email = resp.data.user.email
				def active = resp.data.user.status  
				if (status != 'active') {
					log.error "getNeurioSensors>userId=${userid},name=${username},email=${email} not active, exiting..."
					return
				}
                
				log.debug "getNeurioSensors>userId=${userId},name=${username},email=${email},active=${active}"
				resp.data.user.locations.each {
					def locationId = it.id
					def locationName = it.name
					def timezone = it.timezone
					log.debug "getNeurioSensors>found locationId=${locationId},name=${locationName},timezone=${timezone}"
					it.sensors.each {
						def sensorId = it.id
						def sensorType = it.sensorType
						def dni = [ app.id, locationName, sensorType, sensorId, ].join('.')
						sensors[dni] = sensorId
						log.debug "getNeurioSensors>sensorId=${sensorId},type=${sensorType}"
					} /* end each sensor */                        
				} /* end each location */                        
			} else {
				log.debug "http status: ${resp.status}"

				log.error "Authentication error, invalid authentication method, lack of credentials, etc."
			}
            
    		}        
	} catch (java.net.UnknownHostException e) {
		log.error "getNeurioSensors> Unknown host - check the URL " + deviceListParams.uri
	} catch (java.net.NoRouteToHostException t) {
		log.error "getNeurioSensors> No route to host - check the URL " + deviceListParams.uri
	} catch (java.io.IOException e) {
		log.error "getNeurioSensors> malformed request " +
			deviceListParams
    }

	log.debug "sensors: $sensors"

	return sensors
}

def setParentAuthTokens(auth_data) {
/*
	For Debugging purposes, due to the fact that logging is not working when called (separate thread)
	sendPush("setParentAuthTokens>begin auth_data: $auth_data")
*/

	atomicState.refreshToken = auth_data?.refresh_token
	atomicState.authToken = auth_data?.access_token
	atomicState.expiresIn=auth_data?.expires_in
	atomicState.tokenType = auth_data?.token_type
	atomicState.authexptime= auth_data?.authexptime
	refreshAllChildAuthTokens()
/*
	For Debugging purposes, due to the fact that logging is not working when called (separate thread)
	sendPush("setParentAuthTokens>New atomicState: $atomicState")
*/
}

def refreshAllChildAuthTokens() {
/*
	For Debugging purposes, due to the fact that logging is not working when called (separate thread)
	sendPush("refreshAllChildAuthTokens>begin updating children with $atomicState")
*/

	def children= getChildDevices()
/*
	For Debugging purposes, due to the fact that logging is not working when called (separate thread)
	sendPush("refreshAllChildAuthtokens> refreshing ${children.size()} thermostats")
*/

	children.each { 
/*
	For Debugging purposes, due to the fact that logging is not working when called (separate thread)
		sendPush("refreshAllChildAuthTokens>begin updating $it.deviceNetworkId with $atomicState")
*/
		it.refreshChildTokens(atomicState) 
	}
}

def refreshThisChildAuthTokens(child) {
/*
	For Debugging purposes, due to the fact that logging is not working when called (separate thread)
	sendPush("refreshThisChildAuthTokens>begin child id: ${child.device.deviceNetworkId}, updating it with ${atomicState}")
*/
	child.refreshChildTokens(atomicState)

/*
	For Debugging purposes, due to the fact that logging is not working when called (separate thread)
	sendPush("refreshThisChildAuthTokens>end")
*/
}



def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}


private def delete_child_devices() {
	def delete
	// Delete any that are no longer in settings
	if(!NeurioSensors)
	{
		log.debug "deleting all Neurio Sensors"
		delete = getAllChildDevices()
	}
	else
	{
		delete = getChildDevices().findAll { !NeurioSensors.contains(it.deviceNetworkId) }
	}

	log.debug "deleting ${delete.size()} Neurio Sensors"
	delete.each { deleteChildDevice(it.deviceNetworkId) }

}

private def create_child_devices() {

   	int i =0
	def devices = NeurioSensors.collect { dni ->

		def d = getChildDevice(dni)
		log.debug "Looping thru Neurio Sensors, found id $dni"

		if(!d)
		{
			log.debug "atomicState (i=$i) $atomicState "
			def neurio_info  = dni.tokenize('.')
			def sensorId = neurio_info.last()
 			def name = neurio_info[1]
 			def type = neurio_info[2]
/*            
			i++		// Used to simulate many Neurio Devices
			def labelName = 'My Neurio ' + "${name}:${sensorId}_${i}"
*/                    
			def labelName = 'My Neurio ' + "${name}:${sensorId}"
			log.debug "About to create child device with id $dni, sensorId = $sensorId, name=  ${name}"
			d = addChildDevice(getChildNamespace(), getChildName(), dni, null,
				[label: "${labelName}"]) 
			d.initialSetup( getSmartThingsClientId(), atomicState, sensorId ) 	// initial setup of the Child Device
			log.debug "created ${d.displayName} with id $dni"
		}
		else
		{
			log.debug "found ${d.displayName} with id $dni already exists"
		}

	}

	log.debug "created ${devices.size()} Neurio sensors"



}

def initialize() {
    
	log.debug "initialize"

	delete_child_devices()	
	create_child_devices()
    
	takeAction()
	// set up internal poll timer
	def pollTimer = 20

	log.trace "setting poll to ${pollTimer}"
	schedule("0 0/${pollTimer.toInteger()} * * * ?", takeAction)
}

def takeAction() {
	log.trace "takeAction>begin"
	def devices = NeurioSensors.collect { dni ->
		def d = getChildDevice(dni)
		log.debug "takeAction>Looping thru Neurio Sensors, found id $dni, about to poll"
		d.poll()
    
	}
	log.trace "takeAction>end"
}


def oauthInitUrl() {
	log.debug "oauthInitUrl"
	def stcid = getSmartThingsClientId();

	atomicState.oauthInitState = UUID.randomUUID().toString()

	def oauthParams = [
		response_type: "code",
		client_id: stcid,
		state: atomicState.oauthInitState,
		redirect_uri: buildRedirectUrl()
	]

	return "${get_URI_ROOT()}/oauth2/authorize?" + toQueryString(oauthParams)
}

def buildRedirectUrl() {
	log.debug "buildRedirectUrl, atomicState.accessToken=${atomicState.accessToken}"
	return serverUrl + "/api/token/${atomicState.accessToken}/smartapps/installations/${app.id}/swapToken"
}

def swapToken() {
	log.debug "swapping token: $params"
	debugEvent ("swapping token: $params", true)

	def code = params.code
	def oauthState = params.state

	def stcid = getSmartThingsClientId()

	def tokenParams = [
		code: params.code,
		client_id: stcid,
		redirect_uri: buildRedirectUrl()
	]

	def tokenUrl = "${get_URI_ROOT()}/oauth2/token?" + toQueryString(tokenParams)

	log.debug "Swapping token $params"

	def jsonMap
	try {	
		httpPost(uri:tokenUrl) { resp ->
			jsonMap = resp.data
		}
	} catch (any) {
		
		log.error ("error swapping token: $resp.status")		
	}
	log.debug "Swapped token for $jsonMap"
	debugEvent ("swapped token for $jsonMap", true)

	atomicState.refreshToken = jsonMap.refresh_token
	atomicState.authToken = jsonMap.access_token
	atomicState.expiresIn=jsonMap.expires_in
	atomicState.tokenType = jsonMap.token_type
	def authexptime = new Date((now() + (atomicState.expiresIn * 60 * 1000))).getTime()
	atomicState.authexptime = authexptime


	def html = """
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=640">
<title>Withings Connection</title>
<style type="text/css">
	@font-face {
		font-family: 'Swiss 721 W01 Thin';
		src: url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-thin-webfont.eot');
		src: url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-thin-webfont.eot?#iefix') format('embedded-opentype'),
			 url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-thin-webfont.woff') format('woff'),
			 url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-thin-webfont.ttf') format('truetype'),
			 url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-thin-webfont.svg#swis721_th_btthin') format('svg');
		font-weight: normal;
		font-style: normal;
	}
	@font-face {
		font-family: 'Swiss 721 W01 Light';
		src: url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-light-webfont.eot');
		src: url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-light-webfont.eot?#iefix') format('embedded-opentype'),
			 url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-light-webfont.woff') format('woff'),
			 url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-light-webfont.ttf') format('truetype'),
			 url('https://s3.amazonaws.com/smartapp-icons/Partner/fonts/swiss-721-light-webfont.svg#swis721_lt_btlight') format('svg');
		font-weight: normal;
		font-style: normal;
	}
	.container {
		width: 560px;
		padding: 40px;
		/*background: #eee;*/
		text-align: center;
	}
	img {
		vertical-align: middle;
	}
	img:nth-child(2) {
		margin: 0 30px;
	}
	p {
		font-size: 2.2em;
		font-family: 'Swiss 721 W01 Thin';
		text-align: center;
		color: #666666;
		padding: 0 40px;
		margin-bottom: 0;
	}
/*
	p:last-child {
		margin-top: 0px;
	}
*/
	span {
		font-family: 'Swiss 721 W01 Light';
	}
</style>
</head>
<body>
	<div class="container">
		<img src="https://s3-us-west-2.amazonaws.com/neurio/community/NeurioAppLogo72x72.png" alt="neurio icon" />
		<img src="https://s3.amazonaws.com/smartapp-icons/Partner/support/connected-device-icn%402x.png" alt="connected device icon" />
		<img src="https://s3.amazonaws.com/smartapp-icons/Partner/support/st-logo%402x.png" alt="SmartThings logo" />
		<p>Your Neurio Account is now connected to SmartThings!</p>
		<p>Click 'Done' to finish setup.</p>
	</div>
</body>
</html>
"""

	render contentType: 'text/html', data: html
}

def getChildDeviceIdsString() {
	return NeurioSensors.collect { it.split(/\./).last() }.join(',')
}

def toJson(Map m) {
	return new org.codehaus.groovy.grails.web.json.JSONObject(m).toString()
}

def toQueryString(Map m) {
	return m.collect { k, v -> "${k}=${URLEncoder.encode(v.toString())}" }.sort().join("&")
}

def getChildNamespace() { "yracine" }
def getChildName() { "My Neurio Device" }

def getServerUrl() { return "https://graph.api.smartthings.com" }

def getSmartThingsClientId() { "kjPlS3AAQtaUGlmB30IU9g" }


def debugEvent(message, displayEvent) {

	def results = [
		name: "appdebug",
		descriptionText: message,
		displayed: displayEvent
	]
	log.debug "Generating AppDebug Event: ${results}"
	sendEvent (results)
}

private def get_URI_ROOT() {
	return "https://api.neur.io/v1"
}
    
