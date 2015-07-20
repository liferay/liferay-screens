var React = require('react-native');
var { requireNativeComponent } = React;

var NativeLoginScreenlet = requireNativeComponent('NativeLoginScreenlet', LoginScreenlet);


class LoginScreenlet extends React.Component {
  constructor() {
  	super();
    this._onLoginSuccess = this._onLoginSuccess.bind(this);
  }

  _onLoginSuccess(event: Event) {
    if (!this.props.onLoginSuccess) {
      return;
    }
    this.props.onLoginSuccess(event.nativeEvent.attributes);
  }

  render() {
    return <NativeLoginScreenlet {...this.props} onLoginSuccess={this._onLoginSuccess} />;
  }
}

LoginScreenlet.propTypes = {
  themeName: React.PropTypes.string,
};

module.exports = LoginScreenlet;