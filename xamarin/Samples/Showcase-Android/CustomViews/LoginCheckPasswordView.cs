using System;
using Android.Content;
using Android.Util;
using Com.Liferay.Mobile.Screens.Viewsets.Defaultviews.Auth.Login;

namespace ShowcaseAndroid.CustomViews
{
    public class LoginCheckPasswordView : LoginView
    {
        //You can create the logic to consider if a password is strong or not 
        bool PasswordIsStrong = true;

        public LoginCheckPasswordView(Context context) : base(context) { }

        public LoginCheckPasswordView(Context context, IAttributeSet attributes) : base(context, attributes) { }

        public LoginCheckPasswordView(Context context, IAttributeSet attributes, int defaultStyle) : base(context, attributes, defaultStyle) { }

        public override void OnClick(Android.Views.View view)
        {
            //Compute password strength
            if (PasswordIsStrong)
            {
                base.OnClick(view);
            }
            else
            {
                Console.WriteLine("Login error: Password is not strong");
            }
        }
    }
}