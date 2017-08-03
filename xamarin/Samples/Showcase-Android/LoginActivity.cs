using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Auth.Login;
using Com.Liferay.Mobile.Screens.Context;

namespace ShowcaseAndroid
{
    [Activity(Label = "LoginActivity")]
    public class LoginActivity : Activity, ILoginListener
    {
        private LoginScreenlet loginScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.LoginView);

            loginScreenlet = (LoginScreenlet)FindViewById(Resource.Id.login_screenlet);
            loginScreenlet.Listener = this;

            setDefaultValues();
        }

        /* LoginListener */

        public void OnLoginFailure(Java.Lang.Exception p0)
        {
            Toast.MakeText(this, "Login failed", ToastLength.Short).Show();
        }

        public void OnLoginSuccess(User p0)
        {
            Toast.MakeText(this, "Login successful", ToastLength.Short).Show();
        }

        /* Private methods */

        void setDefaultValues()
        {
            EditText login = (EditText)loginScreenlet.FindViewById(Resource.Id.liferay_login);
            EditText password = (EditText)loginScreenlet.FindViewById(Resource.Id.liferay_password);

            login.SetText("test@liferay.com", TextView.BufferType.Editable);
            password.SetText("test1", TextView.BufferType.Editable);
        }
    }
}
