var React = require('react-native');
var { requireNativeComponent } = React;

var NativeLoginScreenlet = requireNativeComponent('NativeLoginScreenlet', LoginScreenlet);


class LoginScreenlet extends React.Component {
  constructor() {
  	super();
    this._onSubmitEditing = this._onSubmitEditing.bind(this);
  }
  _onSubmitEditing(event: Event) {
    if (!this.props.onLoginSuccess) {
      return;
    }
    this.props.onLoginSuccess(event.nativeEvent.attributes);
  }
  render() {
    return <NativeLoginScreenlet {...this.props} onSubmitEditing={this._onSubmitEditing} />;
  }
}

LoginScreenlet.propTypes = {
//  themeName: React.PropTypes.string,
  
  onLoginSuccess: React.PropTypes.func,  
};


module.exports = LoginScreenlet;