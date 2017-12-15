using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Auth.Login;
using Com.Liferay.Mobile.Screens.Context;

namespace ShowcaseAndroid
{
    [Activity]
    public class LoginActivity : Activity, ILoginListener
    {
        LoginScreenlet loginScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.LoginView);

            loginScreenlet = (LoginScreenlet) FindViewById(Resource.Id.login_screenlet);
            loginScreenlet.Listener = this;

            //Comment this method if you want to test LoginCheckPasswordView
            setDefaultValues();
        }

        /* ILoginListener */

        public void OnLoginSuccess(User p0)
        {
            Toast.MakeText(this, "Login success: " + p0.Id, ToastLength.Short).Show();
        }

        public void OnLoginFailure(Java.Lang.Exception p0)
        {
            Android.Util.Log.Debug("LoginScreenlet", $"Login failed: {p0.Message}");
        }

        /* Private methods */

        void setDefaultValues()
        {
            EditText login = (EditText) loginScreenlet.FindViewById(Resource.Id.liferay_login);
            EditText password = (EditText) loginScreenlet.FindViewById(Resource.Id.liferay_password);

            login.SetText(GetString(Resource.String.liferay_default_user_name), TextView.BufferType.Editable);
            password.SetText(GetString(Resource.String.liferay_default_password), TextView.BufferType.Editable);
        }
    }
}
