'use strict';

var React = require('react-native');
var {requireNativeComponent} = React;

var NativeLoginScreenlet = requireNativeComponent('NativeLoginScreenlet', LoginScreenlet);

class LoginScreenlet extends React.Component {
	constructor() {
		super();
	}

	render() {
		return (
			<NativeLoginScreenlet {...this.props} onLoginError={this._onLoginError.bind(this)} onLoginSuccess={this._onLoginSuccess.bind(this)} />
		);
	}

	_onLoginError(event: Event) {
		console.log('INSIDE ON LOGIN ERROR');
		if (!this.props.onLoginError) {
			return;
		}

		this.props.onLoginError(event.nativeEvent.error_msg);
	}

	_onLoginSuccess(event: Event) {
		if (!this.props.onLoginSuccess) {
			return;
		}

		this.props.onLoginSuccess(event.nativeEvent.attributes);
	}
}

LoginScreenlet.propTypes = {
	onLoginError: React.PropTypes.func,
	onLoginSuccess: React.PropTypes.func,
	themeName: React.PropTypes.string
};

module.exports = LoginScreenlet;