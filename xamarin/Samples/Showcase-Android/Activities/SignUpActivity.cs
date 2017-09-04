using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Auth.Signup;
using Com.Liferay.Mobile.Screens.Context;

namespace ShowcaseAndroid
{
    [Activity]
    public class SignUpActivity : Activity, ISignUpListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.SignUpView);

            SignUpScreenlet signUpScreenlet =
                (SignUpScreenlet) FindViewById(Resource.Id.sign_up_screenlet);
            signUpScreenlet.Listener = this;
        }

        /* ISignUpListener */

        public void OnSignUpFailure(Java.Lang.Exception p0)
        {
            System.Diagnostics.Debug.WriteLine($"Sign up failed: {p0.Message}");
        }

        public void OnSignUpSuccess(User p0)
        {
            Toast.MakeText(this, "Sign up success: " + p0.EntryId, ToastLength.Short).Show();
        }
    }
}
