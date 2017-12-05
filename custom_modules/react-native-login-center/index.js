'use strict';

import { NativeModules, Platform } from 'react-native';
const { LoginCenter } = NativeModules;

var LoginCenters = {
	passDataResult: LoginCenter.passDataResult,
	getDataThirdparty: LoginCenter.getDataThirdparty,
	getStartIntent: LoginCenter.getStartIntent
}

export { LoginCenters };
export default LoginCenters;
