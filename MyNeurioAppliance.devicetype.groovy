/**
 *  My Neurio Appliance
 *
 *  Copyright 2015 Yves Racine
 *  linkedIn profile: ca.linkedin.com/pub/yves-racine-m-sc-a/0/406/4b/
 *  Refer to readme file for installation instructions.
 *
 *  Code: https://github.com/yracine/device-type.myneurio
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

import java.text.SimpleDateFormat

// for the UI
preferences {
	input("applianceId", "number", title: "applianceId (optional,format= XXXXXXXXXXXXXXXXXXXXXX)", description:
		"The appliance ID (no spaces and no ':')")
	input("trace", "text", title: "trace", description:
		"Set it to true to enable tracing (no spaces) or leave it empty (no tracing)"
	)
}
metadata {
	definition (name: "My Neurio Appliance", namespace: "yracine", author: "Yves Racine") {
		capability "Power Meter"
		capability "Refresh"
		capability "Polling"
		capability "Energy Meter"

		command "generateApplianceAllStats"
		command "generateApplianceStats"
		command "generateApplianceEvents"
        
		attribute "verboseTrace","string"
		attribute "applianceId","string"
		attribute "applianceName","string"
		attribute "applianceLabel","string"
		attribute "applianceTags","string"
		attribute "applianceCreatedAt","string"
		attribute "applianceUpdatedAt","string"
		attribute "applianceEventCount","string"
		attribute "applianceTimeOn","string"
		attribute "applianceUsagePct","string"

		attribute "eventStart","string"                            
		attribute "eventEnd","string"
		attribute "eventStatus","string"
		attribute "eventIsConfirmed","string"
		attribute "eventCycleCount","string"							                            
		attribute "eventAveragePower","string"
		attribute "eventEnergy","string"
        
		// Consumed Energy attributes

		attribute "consTotalInPeriod","string"
		attribute "consAvgInPeriod","string"
		attribute "consEnergyDay","string"
		attribute "consEnergyWeek","string"
		attribute "consEnergyMonth","string"
		attribute "consEnergy2DaysAgo","string"
		attribute "consEnergy2WeeksAgo","string"
		attribute "consAvgPowerDay","string"
		attribute "consAvgPowerWeek","string"
		attribute "consAvgPowerMonth","string"
		attribute "consAvgPower2DaysAgo","string"
		attribute "consAvgPower2WeeksAgo","string"

		attribute "applianceEventsData","string"
		attribute "applianceStatsData","string"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	// UI tile definitions
	tiles {
		valueTile(	"power","device.power",unit: 'Watts', width: 1, height: 1,canChangeIcon: false)
        	{
            		state(	"power", label:'AvgPower\n${currentValue}W',
					backgroundColor: "#ffffff",
					backgroundColors: [
						[value: 500, color: "green"],
						[value: 3000, color: "orange"],
						[value: 6000, color: "red"]
                  	]
				)
		}
		valueTile(	"energy", "device.energy", unit:'kWh',width: 1, height: 1,canChangeIcon: false
				) 
        	{
			state("energy",
					label:'AvgEnergy\n${currentValue}kWh',
					backgroundColor: "#ffffff",
                 		)
		}
		valueTile(	"name", "device.applianceLabel",width: 2, height: 1,canChangeIcon: false,
  					decoration: "flat") 
        	{
			state("default",
					icon: "st.quirky.spotter.quirky-spotter-plugged",           
					label:'${currentValue}',
					backgroundColor: "#ffffff",
                 		)
		}

		valueTile(	"usagePct", "device.applianceUsagePct", unit:'%',width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat"
				) 
        	{
			state("usagePct",
					label:'UsagePct\n${currentValue}%',
					backgroundColor: "#ffffff",
                 		)
		}
		valueTile(	"eventCount", "device.applianceEventCount",width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat"
				) 
        	{
			state("eventCount",
					label:'EventCount\n${currentValue}',
					backgroundColor: "#ffffff",
                 		)
		}
		 
		valueTile(	"consEnergyYesterday", "device.consEnergyDay", unit:'kWh',width: 1, height: 1,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'Yesterday\nEnergy\n${currentValue}kWh',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"consEnergy2DaysAgo", "device.consEnergy2DaysAgo", unit:'kWh',width: 1, height: 1,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'2DaysAgo\nEnergy\n${currentValue}kWh',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"consEnergyLastWeek", "device.consEnergyWeek", unit:'kWh',width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Week\nEnergy\n${currentValue}kWh',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"consEnergy2WeeksAgo", "device.consEnergy2WeeksAgo", unit:'kWh',width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'2WksAgo\nEnergy\n${currentValue}kWh',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"consEnergyLastMonth", "device.consEnergyMonth", unit:'kWh',width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Month\nEnergy\n${currentValue}kWh',
					backgroundColor: "#ffffff"
				)
		}
        
         
		valueTile(	"consAvgPowerYesterday", "device.consAvgPowerDay", unit:'Watts',width: 1, height: 1,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'Yesterday\nAvgPower\n${currentValue}W',
					backgroundColor: "#ffffff"
				)
		}

		valueTile(	"consAvgPower2DaysAgo", "device.consAvgPower2DaysAgo", unit: 'Watts',width: 1, height: 1,canChangeIcon: false,
 					decoration: "flat"
				) 
        	{
			state("default",
					label:'2DaysAgo\nAvgPower\n${currentValue}W',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"consAvgPowerLastWeek", "device.consAvgPowerWeek", unit:'Watts',width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Week\nAvgPower\n${currentValue}W',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"consAvgPower2WeeksAgo", "device.consAvgPower2WeeksAgo", unit: 'Watts',width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat",
				)
        	{
			state("default",
					label:'2WksAgo\nAvgPower\n${currentValue}W',
					backgroundColor: "#ffffff"
				)
		}
		valueTile(	"consAvgPowerLastMonth", "device.consAvgPowerMonth", unit: 'Watts',width: 1, height: 1,canChangeIcon: false,
  					decoration: "flat"
				)
        	{
			state("default",
					label:'Month\nAvgPower\n${currentValue}W',
					backgroundColor: "#ffffff"
				)
		}
        
		standardTile("refresh", "device.power") {
			state "default", action:"polling.poll", icon:"st.secondary.refresh"
		}

		main(["name", "power", "energy"])
		details(["power", "energy",  "name", "usagePct", "eventCount","refresh", "consEnergyYesterday",  "consAvgPowerYesterday", "consEnergy2DaysAgo", "consAvgPower2DaysAgo",
        		"consEnergyLastWeek", "consAvgPowerLastWeek", "consEnergy2WeeksAgo","consAvgPower2WeeksAgo","consEnergyLastMonth","consAvgPowerLastMonth" ])

	}
}

// handle commands

 

void poll() {
    
	
	def applianceId= determine_appliance_id("") 	    
    
	if (settings.trace) {
		log.debug "poll>applianceId = ${applianceId}, about to call getApplianceData()"
	}
	// Get Basic appliance Data
    
	getApplianceData(applianceId)

	String dateInLocalTime = new Date().format("yyyy-MM-dd", location.timeZone)
        
	// generate all stats only once every day
    
	if (state.lastGeneratedStatsDate != dateInLocalTime) {
		if (settings.trace) {
			log.debug "poll>about to generateApplianceAllStats,dateInLocalTime=${dateInLocalTime},state.lastGeneratedDate= $state.lastGeneratedDate"
		}
        
		// Once a day, generate all appliance stats (yesterday, 2 days ago, 1 week ago, 2 weeks ago, 1 month ago)
        
		generateApplianceAllStats("")
		state.lastGeneratedStatsDate= dateInLocalTime       
        
	}
    
 	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {  
	// check if there is any exception or an error reported in the verboseTrace associated to the device 
		log.error "poll>applianceId=$applianceId, $exceptionCheck" 
		return    
	}
    
	Long totalConsInPeriod =  device.currentValue("consEnergyMonth")?.toLong()
	Long consAvgPowerInPeriod =  device.currentValue("consAvgPowerMonth")?.toLong() 
	if (settings.trace) {
		log.debug "poll>applianceId = ${applianceId},totalConsInPeriod= $totalConsInPeriod,consAvgPowerInPeriod=$consAvgPowerInPeriod"
	}

	def dataEvents = [
		applianceId:data?.appliance?.id,
		applianceName:data?.appliance?.name,
		applianceLabel:data?.appliance?.label,
		applianceTags:data?.appliance?.tags.toString().minus('[').minus(']'),
		applianceCreatedAt:formatDateInLocalTime(data?.appliance?.createdAt),
		applianceUpdatedAt:formatDateInLocalTime(data?.appliance?.updatedAt),
		power:consAvgPowerInPeriod,
		energy:(totalConsInPeriod  * (60*60*1000)) // for formatting
		]

	try {
		if (data?.stats[0]?.eventCount) {
			dataEvents = dataEvents + [applianceEventCount: data.stats[0].eventCount.toString()]
		}		
	} catch(any) {
		log.debug ("poll>applianceId = ${applianceId}, missing eventCount stats value")    
	}    

	try {
		if (data?.stats[0]?.timeOn) {
			dataEvents = dataEvents + [applianceTimeOn: data.stats[0].timeOn]
		}		
	} catch(any) {
		log.debug ("poll>applianceId = ${applianceId}, missing timeOn stats value")    
	}    
	try {
		if (data?.stats[0]?.usagePourcentage) {
			dataEvents = dataEvents + [applianceUsagePct: data.stats[0].usagePercentage.toString()]
		}		
	} catch(any) {
		log.debug ("poll>applianceId = ${applianceId}, missing usagePourcentage stats value")    
	}    
    
	generateEvent(dataEvents)
    
	Date endDate = new Date()
	Date startDate 
	if (state?.lastEndDate) {
		startDate = new Date(state?.lastEndDate)    
	}    
	if ((!startDate) || (endDate - startDate>1)) {
		startDate= endDate -1        
	}    
	state.lastEndDate=endDate.getTime()   
	if (settings.trace) {
		log.debug "poll>applianceId = ${applianceId}, about to call generateApplianceEvents()"
	}
	// Get Appliance Events since last execution (max 1 day back)

	generateApplianceEvents("",startDate,endDate,"",'true')
		    
	sendEvent name: "verboseTrace", value:
			"poll>done for applianceId = ${applianceId}"


}


private void generateEvent(Map results) {
	if (settings.trace) {
		log.debug "generateEvent>parsing data $results"
	}
    
	if(results) {
		results.each { name, value ->
			def isDisplayed = true

// 			Energy variable names contain "energy"           

			if ((name.toUpperCase().contains("ENERGY"))) {  
				Double energyValue = getEnergy(value.toDouble()).round()
				String energyValueString = String.format("%5d", energyValue.intValue())                
				def isChange = isStateChange(device, name, energyValueString)
				isDisplayed = isChange
                
				sendEvent(name: name, value: energyValueString, unit: "kWh")                                     									 
			} else if (name.toUpperCase().contains("POWER")) { // power variable names contain 'power'

// 			Power variable names contain "power"

 				Long powerValue = value?.toLong()
				def isChange = isStateChange(device, name, powerValue.toString())
				isDisplayed = isChange
				sendEvent(name: name, value: powerValue?.toString(), unit: "Watts")                                     									 

			} else if (name.toUpperCase().contains("TIME")) { // power variable names contain 'power'

// 			Time variable names contain "time"

 				Double timeValue = getTimeInMinutes(value.toDouble()).round()
				String timeValueString = String.format("%5d", timeValue.intValue())                
				def isChange = isStateChange(device, name, timeValueString)
				isDisplayed = isChange
				sendEvent(name: name, value: timeValueString, unit: "minutes")                                     									 

 			} else {
				def isChange = isStateChange(device, name, value)
				isDisplayed = isChange

				sendEvent(name: name, value: value, isStateChange: isChange)       
			}
		}
	}
}




private def getEnergy(value) {

	if (value ==null) {
		return 0    
	}
	// conversion from watts-sec to KWh 
	return (value/ (60*60*1000))
}

private def getTimeInMinutes(value) {

	if (value ==null) {
		return 0    
	}
    
	// conversion from sec to minutes 
	return (value/ 60)
}


void refresh() {
	poll()
}
private def api(method, args, success={}) {
	String URI_ROOT = "${get_URI_ROOT()}"
	if (!isLoggedIn()) {
		login()
	}
	if (isTokenExpired()) {
		if (settings.trace) {
			log.debug "api> need to refresh tokens"
		}
		login()
	}
	def methods = [
		'applianceData': 
			[uri:"${URI_ROOT}/appliances/${args}", 
          		type: 'get'],
		'appliancesStats': 
			[uri:"${URI_ROOT}/appliances/stats?${args}", 
          		type: 'get'],
		'appliancesEvents': 
			[uri:"${URI_ROOT}/appliances/events?${args}", 
          		type: 'get']
		]
	def request = methods.getAt(method)
	if (settings.trace) {
		sendEvent name: "verboseTrace", value:
			"api> about to call doRequest with (unencoded) args = ${args}"
	}
	doRequest(request.uri, args, request.type, success)
}

// Need to be authenticated in before this is called. So don't call this. Call api.
private def doRequest(uri, args, type, success) {
	def params = [
		uri: uri,
		headers: [
			'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
			'Content-Type': "application/json",
			'charset': "UTF-8",
			'Accept': "application/json"
		],
		body: args
	]
	try {
		if (settings.trace) {
//			sendEvent name: "verboseTrace", value: "doRequest>token= ${data.auth.access_token}"
			sendEvent name: "verboseTrace", value:
				"doRequest>about to ${type} with uri ${params.uri}, args= ${args}"
				log.debug "doRequest> ${type}> uri ${params.uri}, args= ${args}"
		}
		if (type == 'post') {
			httpPostJson(params, success) 
		} else if (type == 'get') {
			params.body = null // parameters already in the URL request
			httpGet(params, success) 
		}
	} catch (java.net.UnknownHostException e) {
		log.error "doRequest> Unknown host - check the URL " + params.uri
		sendEvent name: "verboseTrace", value: "doRequest>exception $e, Unknown host ${params.uri}" 
		throw e        
	} catch (java.net.NoRouteToHostException e) {
		log.error "doRequest> No route to host - check the URL " + params.uri
		sendEvent name: "verboseTrace", value: "doRequest>exception $e, No route to host ${params.uri}"
		throw e        
	} catch (e) {
		log.error "doRequest> exception $e " + params.uri
		sendEvent name: "verboseTrace", value: "doRequest>exception $e at ${params.uri}" 
		throw e        
	}
}



void generateApplianceAllStats(applianceId) {

// generate stats for yesterday

	String dateInLocalTime = new Date().format("yyyy-MM-dd", location.timeZone) 
	dateInLocalTime = dateInLocalTime + " 00:00"
    
	if (settings.trace) {
		log.debug("generateApplianceAllStats>date in local date/time= ${dateInLocalTime}")
	}
	Date endDate = formatDate(dateInLocalTime)
	Date startDate = (endDate -1).clearTime()

	String nowInLocalTime = new Date().format("yyyy-MM-dd HH:mm", location.timeZone)
	if (settings.trace) {
		log.debug("generateApplianceAllStats>yesterday: local date/time= ${nowInLocalTime}, startDate in UTC = ${String.format('%tF %<tT',startDate)}," +
			"endDate in UTC= ${String.format('%tF %<tT', endDate)}")
	}
	generateApplianceStats("",startDate,endDate,"days",null)
	Long totalConsInPeriod =  device.currentValue("consTotalInPeriod")?.toLong()
	Long consAvgPowerInPeriod =  device.currentValue("consAvgInPeriod")?.toLong()
	def dataStats = ['consEnergyDay':totalConsInPeriod, 'consAvgPowerDay': consAvgPowerInPeriod]    

// generate stats for 2 days ago

	endDate=startDate
	startDate = (endDate -1)

	if (settings.trace) {
		log.debug("generateApplianceAllStats>2 days ago: startDate in UTC = ${String.format('%tF %<tT',startDate)}," +
			"endDate in UTC= ${String.format('%tF %<tT', endDate)}")
	}
	generateApplianceStats("",startDate,endDate,"days",null)
	totalConsInPeriod =  device.currentValue("consTotalInPeriod")?.toLong()
	consAvgPowerInPeriod =  device.currentValue("consAvgInPeriod")?.toLong()
	dataStats = dataStats + ['consEnergy2DaysAgo':totalConsInPeriod,'consAvgPower2DaysAgo':consAvgPowerInPeriod]    

// generate stats for the past week

	endDate = formatDate(dateInLocalTime)
	startDate = endDate -7

	if (settings.trace) {
		log.debug("generateApplianceAllStats>past week (last 7 days): startDate in UTC = ${String.format('%tF %<tT',startDate)}," +
			"endDate in UTC= ${String.format('%tF %<tT', endDate)}")
	}
	generateApplianceStats("",startDate,endDate,"weeks",null)
	totalConsInPeriod =  device.currentValue("consTotalInPeriod")?.toLong()
	consAvgPowerInPeriod =  device.currentValue("consAvgInPeriod")?.toLong()
	dataStats = dataStats + ['consEnergyWeek':totalConsInPeriod,'consAvgPowerWeek':consAvgPowerInPeriod]    


// generate stats for 2 weeks ago

	endDate = startDate
	startDate = endDate -7

	if (settings.trace) {
		log.debug("generateApplianceAllStats>2 weeks ago: startDate in UTC = ${String.format('%tF %<tT',startDate)}," +
			"date/time endDate in UTC= ${String.format('%tF %<tT', endDate)}")
	}
	generateApplianceStats("",startDate,endDate,"weeks",null)
	totalConsInPeriod =  device.currentValue("consTotalInPeriod")?.toLong()
	consAvgPowerInPeriod =  device.currentValue("consAvgInPeriod")?.toLong()
	dataStats = dataStats + ['consEnergy2WeeksAgo':totalConsInPeriod,'consAvgPower2WeeksAgo':consAvgPowerInPeriod]    

// generate stats for the past month

	endDate = formatDate(dateInLocalTime)
	Calendar oneMonthAgoCal = new GregorianCalendar()
	oneMonthAgoCal.add(Calendar.MONTH, -1)
	Date oneMonthAgo = oneMonthAgoCal.getTime().clearTime()
	
	if (settings.trace) {
		log.debug("generateApplianceAllStats>past month: startDate in UTC = ${String.format('%tF %<tT',oneMonthAgo)}," +
			"endDate in UTC= ${String.format('%tF %<tT', endDate)}")
	}
	generateApplianceStats("",startDate,endDate,"months",null)
	totalConsInPeriod =  device.currentValue("consTotalInPeriod")?.toLong()
	consAvgPowerInPeriod =  device.currentValue("consAvgInPeriod")?.toLong()
	dataStats = dataStats + ['consEnergyMonth':totalConsInPeriod,'consAvgPowerMonth':consAvgPowerInPeriod]    

	generateEvent(dataStats)


}

private def formatDate(dateString) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm")
	Date theDate = sdf.parse(dateString)
	return theDate
}

private def ISOdateFormat(date) {
 	SimpleDateFormat ISO8601format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	def ISOdate = ISO8601format.format(date)
    
	return ISOdate
}


//	applianceId - Id of the appliance 

void getApplianceData(applianceId) {
	def NEURIO_SUCCESS=200
    
	def statusCode=true
	int j=0        
	def bodyReq=applianceId 
    
	while ((statusCode!= NEURIO_SUCCESS) && (j++ <2)) { // retries once if api call fails

		api('applianceData', bodyReq) {resp->
		
			statusCode= resp.status
			if (statusCode == NEURIO_SUCCESS) {
				data?.appliance=resp.data
				if (settings.trace) {
					def applianceName=data.appliance?.name
					def applianceLabel=data.appliance?.label
					def applianceTags=data.appliance?.tags
					def applianceCreated=data.appliance?.createdAt
					def applianceUpdated=data.appliance?.updatedAt
					log.debug "getApplianceData>applianceId= ${applianceId}, applianceName=${applianceName}" +
						",applianceLabel=${applianceLabel},applianceTags=${applianceTags},created=${applianceCreated}, updated=${applianceUpdated}"
					                    
					sendEvent name: "verboseTrace", value:"getApplianceData>applianceId= ${applianceId}, applianceName=${applianceName}" +
						",applianceLabel=${applianceLabel},applianceTags=${applianceTags},created=${applianceCreated}, updated=${applianceUpdated}"
				}
				sendEvent name: "verboseTrace", value:"getApplianceData>done for applianceId=${applianceId}"
			} else {
				def message = resp.message
				def errors = resp.errors
				log.error "getApplianceData>status=${statusCode.toString()},message=${message},error=${errors} for applianceId=${applianceId}"
				sendEvent name: "verboseTrace", value:"getApplianceData>status=${statusCode.toString()},message=${message},error=${errors} for applianceId=${applianceId}"
			}                
		}  /* end api call */              
	} /* end while */

}


//	applianceId - Id of the appliance (not required)
//		By default, the first appliance set as Child Device
//	start - start date for the stats generation
//	end - end date for the stats generation
//	granularity: in days,weeks, months, or unknown for the stats generation
//	minPower: min. Power for the events data extraction
//	postData, default='false', indicates whether to post the raw data or not 

void generateApplianceStats(applianceId,start,end,granularity,minPower,postData='false') {
	def NEURIO_SUCCESS=200
	def applianceStatsData=[]	
	def bodyReq    
	Long totalConsumedEnergy=0
	Long totalAvgConsumedPower=0
	int nbAvgPowerRecords=0	 
	Long avgPower=0    
    
	applianceId=determine_appliance_id(applianceId)

	// make sure start and granularity are defined
	if ((!start) || (!end) || (!granularity)) {
		log.error "generateAppliancesStats>start, end and granularity are required parameters"
		sendEvent name: "verboseTrace", value:"generateAppliancesStats>start, end and granularity are required parameters"
		return 
	}
	
	granularity=granularity?.trim()
    
	// make sure granularity is one of the correct values
	if ((granularity != 'unknown') && (granularity != 'days') && (granularity != 'weeks') && (granularity != 'months')) {
		log.error "generateApplianceStats>only values of [days, weeks, months or unknown] are supported for granularity (${granularity})"
		sendEvent name: "verboseTrace", value: "only values of minutes, hours, days or months are supported for granularity (${granularity})"
		return 
	}
     
	bodyReq="applianceId=" + applianceId + "&start=" + ISOdateFormat(start) + "&end=" + ISOdateFormat(end)    	
    
	bodyReq = bodyReq+ "&granularity=" + granularity    	
	if ((minPower != null) && (minPower != "")) {
		bodyReq = bodyReq+ "&minPower=" + minPower    	
	}
	int j=0        
	def statusCode=true

	while ((statusCode!= NEURIO_SUCCESS) && (j++ <2)) { // retries once if api call fails

		api('appliancesStats', bodyReq) {resp->
			statusCode = resp.status        
			if (settings.trace) {
				log.debug "generateApplianceStats>applianceId=${applianceId},status=${resp.status},resp data = ${resp.data}" 
				sendEvent name: verboseTrace, value:"generateApplianceStats>applianceId=${applianceId},status=${resp.status},resp data = ${resp.data}"                
			}                
			if (statusCode == NEURIO_SUCCESS) {
				if (resp.data !=[]) {            	
	 				data.stats=resp.data
					data.stats.each {
						def applianceName= it?.appliance.name
						def applianceLabel= it?.appliance.label
						def applianceTags= it?.appliance.tags
						def applianceCreated= it?.appliance.createdAt
						def applianceUpdated= it?.appliance.updatedAt
						def statsAvgPower= it?.averagePower 
						def statsEventCount= it?.eventCount
						def statsUsagePct= it?.usagePourcentage
						def statsTimeOn= it?.timeOn
						def statsEnergy= it?.energy
						def statsStart= it?.start
						def statsEnd= it?.end
						if (postData=='true') {                    
							applianceStatsData << it
						}                            
						if (statsEnergy) {
							totalConsumedEnergy =totalConsumedEnergy + statsEnergy.toLong()
						}                        
						if (statsAvgPower) {
							totalAvgConsumedPower =totalAvgConsumedPower + statsAvgPower.toLong()
							nbAvgPowerRecords++                            
						}                        
						if (settings.trace) {
							log.debug "generateApplianceStats>locationId=${locationId},applianceId= ${applianceId}, applianceName=${applianceName}" +
								",applianceLabel=${applianceLabel},applianceTags=${applianceTags}.created=${applianceCreated},updated=${applianceUpdated}," +
								"statsAvgPower=${statsAvgPower},statsEventCount=${statsEventCount}," +
								"statsTimeOn=${statsTimeOn},statsUsagePourcentage=${statsUsagePct}," + 
								"statsEnergy=${statsEnergy},statsStart=${statsStart},statsEnd=${statsEnd}" 
							sendEvent name: "verboseTrace", value:"generateApplianceStats>locationId=${locationId},applianceId= ${applianceId}, applianceName=${applianceName}" +
								",applianceLabel=${applianceLabel},applianceTags=${applianceTags}.created=${applianceCreated},updated=${applianceUpdated}," +
								"statsAvgPower=${statsAvgPower},statsEventCount=${statsEventCount}," +
								"statsTimeOn=${statsTimeOn},statsUsagePourcentage=${statsUsagePct}," + 
								"statsEnergy=${statsEnergy},statsStart=${statsStart},statsEnd=${statsEnd}" 
						}
					} /* end each stats */
				} 
				sendEvent name: "verboseTrace", 
                	value:"generateApplianceStats>done for applianceId=${applianceId}"
			} else {
				def message = resp.message
				def errors = resp.errors
				log.error "generateApplianceStats>status=${statusCode.toString()},message=${message},error=${errors} for applianceId=${applianceId}"
				sendEvent name: "verboseTrace", 
					value:"generateApplianceStats>status=${statusCode.toString()},message=${message},error=${errors} for applianceId=${applianceId}"
			} /* end if statusCode */               
		}  /* end api call */              
	} /* end while */

	if (nbAvgPowerRecords >0) {
		avgPower = totalAvgConsumedPower / nbAvgPowerRecords
/*        
		if (settings.trace) {
			log.debug "generateApplianceStats>avgPower= ${avgPower},totalAvgConsumedPower=${totalAvgConsumedPower},nbAvgPowerRecords=${nbAvgPowerRecords}"
		}
*/        
	}                        

	def applianceStatsJson=""
 
	if (applianceStatsData != []) {
    
		applianceStatsJson = new groovy.json.JsonBuilder(applianceStatsData)
	}
/*	
	if (settings.trace) {
		log.debug "generateApplianceStats>applianceStatsJson=${applianceStatsJson}"
	}
*/    
	def applianceStatsEvents = [
		applianceStatsData: "${applianceStatsJson.toString()}",
		consAvgInPeriod:avgPower.toString(),       
		consTotalInPeriod:totalConsumedEnergy.toString()
	]
/*    
	if (settings.trace) {
		log.debug "generateApplianceStats>applianceStatsEvents to be sent= ${applianceStatsEvents}"
	}
*/    
	generateEvent(applianceStatsEvents)
}

private String formatDateInLocalTime(dateInString) {
	if ((dateInString==null) || (dateInString.trim()=="")) {
		return ""    
	}    
	SimpleDateFormat ISODateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	Date ISODate = ISODateFormat.parse(dateInString)
	String dateInLocalTime = ISODate.format("yyyy-MM-dd HH:mm", location.timeZone)
	return dateInLocalTime
}

//	applianceId - Id of the appliance (not required)
//		By default, the first appliance set as Child Device
//	start - start date for the stats generation
//	end - end date for the stats generation
//	minPower: min. Power for the events data extraction
//	postData, default='false', indicates whether to post the raw data or not 

void generateApplianceEvents(applianceId,start,end,minPower,postData='false') {
	def NEURIO_SUCCESS=200
	def applianceEventsData=[]
	def bodyReq
	def applianceEvents = []
    
	applianceId=determine_appliance_id(applianceId)

	// make sure start and end are defined
	if ((!start) || (!end)) {
		log.error "generateApplianceEvents>start and end are required parameters"
		sendEvent name: "verboseTrace", value:"generateApplianceStats>start and end are required parameters"
		return 
	}
	
	bodyReq="applianceId=" + applianceId + "&start=" + ISOdateFormat(start) + "&end=" + ISOdateFormat(end)    	
	if ((minPower != null) && (minPower != "")) {
		bodyReq = bodyReq+ "&minPower=" + minPower    	
	}
	int j=0        
	def statusCode=true
    
	while ((statusCode!= NEURIO_SUCCESS) && (j++ <2)) { // retries once if api call fails

		api('appliancesEvents', bodyReq) {resp->
			statusCode = resp.status        
			if (settings.trace) {
				log.debug "generateApplianceEvents>applianceId=${applianceId},status=${resp.status},resp data = ${resp.data}" 
				sendEvent name: verboseTrace, value:"generateApplianceEvents>applianceId=${applianceId},status=${resp.status},resp data = ${resp.data}"                
			}                
			if (statusCode == NEURIO_SUCCESS) {
				if (resp.data !=[]) {            	
 					data.events=resp.data
					data.events.each {	
						def applianceName= it.appliance?.name
						def applianceLabel= it.appliance?.label
						def applianceTags= it.appliance?.tags
						def eventGuesses= it?.guesses
						def eventEnergy= it?.energy
						def eventAvgPower= it?.averagePower 
						def eventStart= formatDateInLocalTime(it?.start)
						def eventEnd= formatDateInLocalTime(it?.end)
						def eventStatus= it?.status
						def eventIsConfirmed= it?.isConfirmed
						def eventCycleCount= it?.cycleCount
                        
						applianceEvents = [
							eventStart: eventStart,                            
							eventEnd: eventEnd,
							eventStatus:eventStatus,
							eventIsConfirmed:eventIsConfirmed.toString(),
							eventCycleCount:eventCycleCount.toString(),							                            
							eventAvgPower: eventAvgPower,
							eventEnergy: eventEnergy
						]
						if (settings.trace) {
							log.debug "generateApplianceEvents>applianceId= ${applianceId},applianceName=${applianceName}" +
								",applianceLabel=${applianceLabel},applianceTags=${applianceTags}," +
								"eventAvgPower=${eventAvgPower},eventGuesses=${eventGuesses}," +
								"eventEnergy=${eventEnergy},eventStart=${eventStart},eventEnd=${eventEnd}" +
								"eventStatus=${eventStatus},eventIsConfirmed=${eventIsConfirmed}" 
							sendEvent name: "verboseTrace", value:"generateApplianceEvents>applianceId= ${applianceId},applianceName=${applianceName}" +
								",applianceLabel=${applianceLabel},applianceTags=${applianceTags}," +
								"eventAvgPower=${eventAvgPower},eventGuesses=${eventGuesses}," +
								"eventEnergy=${eventEnergy},eventStart=${eventStart},eventEnd=${eventEnd}" +
								"eventStatus=${eventStatus},eventIsConfirmed=${eventIsConfirmed}" 
							log.debug "generateApplianceEvents>applianceEvents to be sent= ${applianceEvents}"
						}
						generateEvent(applianceEvents)
						if (postData=='true') {                    
							applianceEventsData << it
						}                            
    
					} /* end each event */
				} 
				sendEvent name: "verboseTrace", value:"generateApplianceEvents>done for applianceId=${applianceId}"
			} else {
				def message = resp.message
				def errors = resp.errors
				log.error "generateApplianceEvents>status=${statusCode.toString()},message=${message},error=${errors}"
				sendEvent name: "verboseTrace", value:"generateApplianceEvents>status=${statusCode.toString()},message=${message},error=${errors} for applianceId=${applianceId}"
			} /* end if statusCode */               
		}  /* end api call */              
	} /* end while */

	def applianceEventsJson=""
 
	if (applianceEventsData != []) {
    
		applianceEventsJson = new groovy.json.JsonBuilder(applianceEventsData)
	}
/*	
	if (settings.trace) {
		log.debug "generateApplianceEvents>applianceEventsJson=${applianceEventsJson}"
	}
*/    
	applianceEvents =[applianceEventsData: "${applianceEventsJson.toString()}"]
/*    
	if (settings.trace) {
		log.debug "generateApplianceEvents>applianceEvents to be sent= ${applianceEvents}"
	}
*/    
	generateEvent(applianceEvents)
}


void refreshChildTokens(auth) {
	if (settings.trace) {
		log.debug "refreshChildTokens>begin token auth= $auth"
	}
	data.auth.access_token = auth.authToken
	data.auth.refresh_token = auth.refreshToken
	data.auth.expires_in = auth.expiresIn
	data.auth.token_type = auth.tokenType
	data.auth.scope = auth.scope
	data.auth.authexptime = auth.authexptime
	if (settings.trace) {
		log.debug "refreshChildTokens>end data.auth=$data.auth"
	}
}

private void refreshParentTokens() {
	if (settings.trace) {
		log.debug "refreshParentTokens>begin data.auth = ${data.auth}"
	}
	if (settings.trace) {
		log.debug "refreshParentTokens>auth=$auth, about to call parent.setParentAuthTokens"
	}         
	parent.setParentAuthTokens(data.auth)
	if (settings.trace) {
		log.debug "refreshParentTokens>end"
	}         
}

private void login() {
	if (settings.trace) {
		log.debug "login> about to call setAuthTokens"
	}
	if (data?.auth?.applianceId) {
    	// Created by initalSetup
		if (settings.trace) {
			log.debug "login> about to call refreshThisChildAuthTokens"
		}
		parent.refreshThisChildAuthTokens(this)
	} else { 
		log.error("login>error, not created by initialSetup")
	}    
	if (!isLoggedIn()) {
		if (settings.trace) {
			log.debug "login> no auth_token..., failed"
		}
		return
	}
}



private def isLoggedIn() {
	if (data.auth == null) {
		if (settings.trace) {
			log.debug "isLoggedIn> no data auth"
		}
		return false
	} else {
		if (data.auth.access_token == null) {
			if (settings.trace) {
				log.debug "isLoggedIn> no access token"
				return false
			}
		}
	}
	return true
}
private def isTokenExpired() {
	def buffer_time_expiration=5   // set a 5 min. buffer time before token expiration to avoid auth_err 
	def time_check_for_exp = now() + (buffer_time_expiration * 60 * 1000);
	if (settings.trace) {
		log.debug "isTokenExpired> check expires_in: ${data.auth.authexptime} > time check for exp: ${time_check_for_exp}"
	}
	if (data.auth.authexptime > time_check_for_exp) {
		if (settings.trace) {
			log.debug "isTokenExpired> not expired"
		}
		return false
	}
	if (settings.trace) {
		log.debug "isTokenExpired> expired"
	}
	return true
}

// Determine id from argument or default value (applianceId in data)

private def determine_appliance_id(appliance_id) {
	def applianceId
    
	if (settings.trace) {
		log.debug "determine_appliance_id>begin appliance Id= ${appliance_id}"
	}
	if ((appliance_id != null) && (appliance_id != "")) {
		applianceId = appliance_id.trim()
	} else {
		applianceId=data?.auth?.applianceId
		if (settings.trace) {
			log.debug "determine_appliance_id> applianceId from data.auth.appliance.id"
		}
	}
	if (settings.trace) {
		log.debug "determine_appliance_id> end applianceId= ${applianceId}"
	}
	return applianceId
}



// Called by My Neurio Init for initial creation of a child Device
void initialSetup(auth_data, device_neurio_id) {
/*
	settings.trace='true'
*/
	if (settings.trace) {
		log.debug "initialSetup>begin"
		log.debug "initialSetup> device_client_id = ${device_neurio_id}"
	}	
	settings.applianceId = device_neurio_id

	data?.auth = settings    
	data.auth.access_token = auth_data.authToken
	data.auth.expires_in = auth_data.expiresIn
	data.auth.token_type = auth_data.tokenType

	state.lastEndDate= (new Date() -1).getTime()     
	if (settings.trace) {
		log.debug "initialSetup> settings = $settings"
		log.debug "initialSetup> data_auth = $data.auth"
		log.debug "initialSetup> about to call getApplianceData()"

	}
/*    
	getApplianceData(settings.applianceId)
*/
	poll()
	if (settings.trace) {
		log.debug "initialSetup>end"
	}
}

def toQueryString(Map m) {
	return m.collect { k, v -> "${k}=${URLEncoder.encode(v.toString())}" }.sort().join("&")
}

private def get_URI_ROOT() {
	return "https://api.neur.io/v1"
}
