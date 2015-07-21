'use strict';

var React = require('react-native');
var {
	AppRegistry,
	StyleSheet,
	Text,
	View
} = React;

var LoginScreenlet = require('./LoginScreenlet');

class MyReactPrototype extends React.Component {
	constructor() {
		super();

		this.state = {
	    	logged: false
		};

		this._loginFailed = this._loginFailed.bind(this);
		this._loginSucceeded = this._loginSucceeded.bind(this);
	}

	render() {
		if (this.state.logged) {
			return (
				<View style={styles.container}>
					<Text>Logged successfully!</Text>
				</View>
			);
		} else {
			return (
				<View style={styles.container}>
					<LoginScreenlet onLoginError={this._loginFailed} onLoginSuccess={this._loginSucceeded} style={styles.login} themeName="flat7" />
				</View>
			);
		}
	}

	_loginFailed(jose) {
		console.log('Login failed!', jose);
	}

	_loginSucceeded(attributes) {
		console.log('Login done!', attributes);

		this.setState({
			logged: true
		});
	}
}

var styles = StyleSheet.create({
	container: {
		alignItems: 'center',
		backgroundColor: '#F5FCFF',
		flex: 1,
		justifyContent: 'center'
	},
	login: {
		flex: 1,
		height: 350,
		marginTop:50,
		width: 300
	}
});

AppRegistry.registerComponent('MyReactPrototype', () => MyReactPrototype);
