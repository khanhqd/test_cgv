/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  TouchableOpacity
} from 'react-native';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
  android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});
import Interact from './custom_modules/react-native-interact';

export default class App extends Component<{}> {

  onPressSend() {
    Interact.sendToCGV('hello')
    .then((data) => {
        alert(data)
    })
    .catch((e) => { })
  }

  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity 
        onPress={()=>this.onPressSend()}
        style={styles.btnContainer}>
          <Text>SEND
          </Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  btnContainer: {
    padding: 30,
    borderColor: 'grey',
    borderWidth: 1,
    borderRadius: 5
  }
});
