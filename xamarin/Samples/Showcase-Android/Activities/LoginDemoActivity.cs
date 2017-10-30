using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Auth.Login;
using Com.Liferay.Mobile.Screens.Context;

namespace ShowcaseAndroid
{
    [Activity]
    public class LoginDemoActivity : Activity, ILoginListener
    {
        LoginScreenlet loginScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.LoginDemoView);

            loginScreenlet = (LoginScreenlet)FindViewById(Resource.Id.login_screenlet_demo);
            loginScreenlet.Listener = this;
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
    }
}