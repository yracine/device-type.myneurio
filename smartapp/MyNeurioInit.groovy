/**
 *  MyNeurioInit
 *
 *  Copyright 2014 Yves Racine
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

import groovy.json.JsonSlurper

definition(
    name: "MyNeurioInit",
    namespace: "yracine",
    author: "Yves Racine",
    description: "My Neurio device Initialization",
    category: "My Apps",
    iconUrl: "https://s3-us-west-2.amazonaws.com/neurio/community/NeurioAppLogo60x60.png",
    iconX2Url: "https://s3-us-west-2.amazonaws.com/neurio/community/NeurioAppLogo72x72.png",
    iconX3Url: "https://s3-us-west-2.amazonaws.com/neurio/community/NeurioAppLogo72x72.png"
)

preferences {
	section("About") {
		paragraph "MyNeurioInit, the smartapp that initializes your Neurio device and polls it on a regular basis"
		paragraph "Version 1.0\n\n" +
			"If you like this app, please support the developer via PayPal:\n\nyracine@yahoo.com\n\n" +
			"Copyright©2015 Yves Racine"
		href url: "http://github.com/yracine", style: "embedded", required: false, title: "More information...",
		description: "http://github.com/yracine/device-type.myneurio.groovy/blob/master/README.md"
	}

	section("Initialize and regularly polls  this Neurio device") {
		input "neurio", "capability.powerMeter", title: "Which Neurio"
	}
}

def installed() {

	log.debug "installed> calling initialize... "
	initialize()
}


def updated() {	
	log.debug "updated> calling initialize... "
    
	initialize()
}


def initialize() {
	neurio.setAuthTokens()
	neurio.getCurrentUserInfo()

	takeAction()
    	// set up internal poll timer
	def pollTimer = 20

	log.trace "setting poll to ${pollTimer}"
	schedule("0 0/${pollTimer.toInteger()} * * * ?",takeAction)

}

def takeAction() {
	log.trace "takeAction>begin"
	neurio.poll()	
/*	
	get_neurio_appliances_data()
*/    
	log.trace "takeAction>end"
}


private def get_neurio_appliances_data() {
	Boolean foundAppliance=false		
	def applianceList
	def applianceFields

	try {
		neurio.getApplianceList("")
		applianceList = neurio.currentAppliancesList.toString().minus('[').minus(']').tokenize(',')
		foundAppliance=true        
	} catch (any) {
		log.debug("Not able to get the list of appliances from Neurio")    	
	}    
	if (foundAppliance) {

		Date endDate = new Date().clearTime()
		Date startDate = (endDate -1).clearTime()

		String nowInLocalTime = new Date().format("yyyy-MM-dd HH:mm", location.timeZone)
		if (settings.trace) {
			log.debug("get_neurio_appliances_data>yesterday: local date/time= ${nowInLocalTime}, startDate in UTC = ${String.format('%tF %<tT',startDate)}," +
				"endDate in UTC= ${String.format('%tF %<tT', endDate)}")
		}
		log.debug("list of appliances = $applianceList")    	
    
		for (applianceId in applianceList) {
			log.debug("applianceId=${applianceId}")    	
			neurio.getApplianceData(applianceId)
			String applianceData=neurio.currentApplianceData.toString()
			if (applianceData) {    
				applianceFields = new JsonSlurper().parseText(applianceData)
			} else {
				log.error("get_neurio_appliances_data>applianceData is empty, exiting")
				return        
			}    
			log.debug "get_neurio_appliances_data>applianceFields = $applianceFields"

			if (applianceFields?.size() > 0) {
				def applianceName=applianceFields?.name
				def applianceLabel=applianceFields?.label
				def applianceTags=applianceFields?.tags
				def applianceCreated=applianceFields?.createdAt
				def applianceUpdated=applianceFields?.updatedAt
				log.debug "get_neurio_appliances_data>applianceId= ${applianceId}, applianceName=${applianceName}" +
					",applianceLabel=${applianceLabel},applianceTags=${applianceTags},created=${applianceCreated}, updated=${applianceUpdated}"
				            
    			}
			// generate Appliance stats & events for yesterday
		
			neurio.generateAppliancesStats("",applianceId,startDate,endDate,"days")
			neurio.generateAppliancesEvents("",applianceId,startDate,endDate)
		} /* end for */            
        
		// generate Location stats & events for yesterday
		neurio.generateAppliancesStats("","",startDate,endDate,"days")
		neurio.generateAppliancesEvents("","",startDate,endDate)

	}    
}
