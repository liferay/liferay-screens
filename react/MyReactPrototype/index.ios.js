'use strict';

var React = require('react-native');
var {
  AppRegistry,
  StyleSheet,
  Text,
  View,
} = React;

var LoginScreenlet = require('./LoginScreenlet');


var MyReactPrototype = React.createClass({
  loginSucceeded(attributes) {
    console.log("Login done! " + attributes);
  },

  render: function() {
    return (
      <View style={styles.container}>
      	<LoginScreenlet style={styles.login} themeName="flat7" onLoginSuccess={this.loginSucceeded} />
      </View>
    );
  }
});

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  login: {
    flex: 1,
    width: 300,
    height: 300,
    marginTop:50,
  },
});

AppRegistry.registerComponent('MyReactPrototype', () => MyReactPrototype);
