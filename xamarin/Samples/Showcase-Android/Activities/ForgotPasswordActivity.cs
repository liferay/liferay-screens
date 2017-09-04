using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Auth.Forgotpassword;

namespace ShowcaseAndroid
{
    [Activity]
    public class ForgotPasswordActivity : Activity, IForgotPasswordListener
    {

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.ForgotPasswordView);

            ForgotPasswordScreenlet forgotPasswordScreenlet = 
                (ForgotPasswordScreenlet) FindViewById(Resource.Id.forgot_password_screenlet);
            forgotPasswordScreenlet.Listener = this;
        }

        /* IForgotPasswordListener */

        public void OnForgotPasswordRequestFailure(Java.Lang.Exception p0)
        {
            System.Diagnostics.Debug.WriteLine($"Forgot password failed: {p0.Message}");
        }

        public void OnForgotPasswordRequestSuccess(bool p0)
        {
            Toast.MakeText(this, "Forgot password successfully sent", ToastLength.Short).Show();
        }
    }
}
