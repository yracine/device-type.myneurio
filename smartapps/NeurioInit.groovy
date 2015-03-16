/**
 *  NeurioInit
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
definition(
    name: "NeurioInit",
    namespace: "yracine",
    author: "Yves Racine",
    description: "Neurio device Initialization",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("About") {
		paragraph "NeurioInit, the smartapp that initializes your Neurio device and polls it on a regular basis"
		paragraph "Version 1.0\n\n" +
			"If you like this app, please support the developer via PayPal:\n\nyracine@yahoo.com\n\n" +
			"Copyright©2014 Yves Racine"
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

	pollHandler()
    	// set up internal poll timer
	def pollTimer = 20

	log.trace "setting poll to ${pollTimer}"
	schedule("0 0/${pollTimer.toInteger()} * * * ?", pollHandler)
}

def pollHandler() {
	log.trace "pollHandler>begin"
	neurio.poll()
	log.trace "pollHandler>end"
}

