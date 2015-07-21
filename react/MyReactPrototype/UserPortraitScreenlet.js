'use strict';

var React = require('react-native');
var {requireNativeComponent} = React;

var NativeUserPortraitScreenlet = requireNativeComponent('NativeUserPortraitScreenlet', UserPortraitScreenlet);

class UserPortraitScreenlet extends React.Component {
	constructor() {
		super();
	}

	render() {
		return (
			<NativeUserPortraitScreenlet {...this.props} />
		);
	}

	_onPortraitError(event: Event) {
		if (!this.props.onPortraitError) {
			return;
		}

		this.props.onPortraitError(event.nativeEvent.error_msg);
	}

	_oPortraitSuccess(event: Event) {
		if (!this.props.onPortraitSuccess) {
			return;
		}

		this.props.onPortraitSuccess(event.nativeEvent.image);
	}
}

UserPortraitScreenlet.propTypes = {
	onPortraitError: React.PropTypes.func,
	onPortraitSuccess: React.PropTypes.func,
	themeName: React.PropTypes.string
};

module.exports = UserPortraitScreenlet;