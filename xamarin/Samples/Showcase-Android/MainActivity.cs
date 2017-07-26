using Android.App;
using Android.Widget;
using Android.OS;
using Android.Support.V7.App;
using Com.Liferay.Mobile.Screens.Auth.Login;
using Com.Liferay.Mobile.Screens.Context;

namespace ShowcaseAndroid
{
    [Activity(Label = "AndroidSample.Android", Theme =
    "@style/default_theme", MainLauncher = true)]
    public class MainActivity : AppCompatActivity, ILoginListener
    {
        private LoginScreenlet loginScreenlet;

        public void OnLoginFailure(Java.Lang.Exception p0)
        {
            Toast.MakeText(this, "Login failed", ToastLength.Short);
        }

        public void OnLoginSuccess(User p0)
        {
            Toast.MakeText(this, "Login successful", ToastLength.Short);
        }

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            SetContentView(Resource.Layout.LoginView);

            loginScreenlet = (LoginScreenlet)FindViewById(Resource.Id.login_screenlet);
            loginScreenlet.SetListener(this);

            setDefaultValues();
        }

        private void setDefaultValues()
        {
            EditText login = (EditText)this.loginScreenlet.FindViewById(Resource.Id.liferay_login);
            EditText password = (EditText)this.loginScreenlet.FindViewById(Resource.Id.liferay_password);

            login.SetText("test@liferay.com", TextView.BufferType.Editable);
            password.SetText("test1", TextView.BufferType.Editable);
        }
    }
}