'use strict';

import { NativeModules, Platform } from 'react-native';
const { Interact } = NativeModules;

var Interaction = {
	openApp: Platform.OS == 'android' ? Interact.openApp : '',
	checkCHVer: Platform.OS == 'android' ? Interact.checkCHVer : '',
}

export { Interaction };
export default Interaction;
