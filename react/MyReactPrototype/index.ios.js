'use strict';

var React = require('react-native');
var {
	AppRegistry,
	StyleSheet,
	Text,
	View
} = React;

var LoginScreenlet = require('./LoginScreenlet');
var UserPortraitScreenlet = require('./UserPortraitScreenlet');

class MyReactPrototype extends React.Component {
	constructor() {
		super();

		this.state = {
	    	logged: false
		};

		this._onPortraitLoaded = this._onPortraitLoaded.bind(this);
		this._onPortraitError = this._onPortraitError.bind(this);

		this._loginFailed = this._loginFailed.bind(this);
		this._loginSucceeded = this._loginSucceeded.bind(this);
	}

	render() {
		if (this.state.logged) {
			return (
				<View style={styles.containerPortrait}>
					<UserPortraitScreenlet
						onPortraitError={this._onPortraitError}
						onPortraitLoaded={this._onPortraitLoaded}
						style={styles.userPortrait}
						themeName="flat7"
						userId={this.state.userId} />
				</View>
			);
		} else {
			return (
				<View style={styles.container}>
					<LoginScreenlet
						onLoginError={this._loginFailed}
						onLoginSuccess={this._loginSucceeded}
						style={styles.login}
						themeName="flat7" />
				</View>
			);
		}
	}

	_loginFailed(error) {
		console.log('Login failed!', error);
	}

	_loginSucceeded(attributes) {
		console.log('Login done!', attributes);

		this.setState({
			logged: true,
			userId: attributes.userId
		});
	}

	_onPortraitError(error) {
		console.log('Portrait loading failed!', error);
	}

	_onPortraitLoaded(image) {
		console.log('Portrait loading done!', image);
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
		height: 300,
		marginTop:50,
		width: 300
	},
	userPortrait: {
		flex: 1,
		height: 300,
		width: 300
	},
	containerPortrait: {
		borderWidth: 2,
		borderColor: 'red',
		flex: 1,
		height: 300,
		marginTop:50,
		width: 300,
		justifyContent: 'center',
		alignItems: 'center'
	}
});

AppRegistry.registerComponent('MyReactPrototype', () => MyReactPrototype);
