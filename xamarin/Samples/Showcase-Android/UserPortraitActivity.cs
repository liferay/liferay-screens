using System;
using Android.App;
using Android.Graphics;
using Android.OS;
using Com.Liferay.Mobile.Screens.Userportrait;
using Java.Lang;

namespace ShowcaseAndroid
{
    [Activity]
    public class UserPortraitActivity : Activity, IUserPortraitListener
    {
        private UserPortraitScreenlet userPortraitScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            SetContentView(Resource.Layout.UserPortraitView);

            userPortraitScreenlet = (UserPortraitScreenlet)FindViewById(Resource.Id.user_portrait_screenlet);
            userPortraitScreenlet.Listener = this;
        }

		public void Error(Java.Lang.Exception p0, string p1)
		{
            System.Diagnostics.Debug.WriteLine($"User portrait error: {p0} - {p1}");
		}

		public Bitmap OnUserPortraitLoadReceived(Bitmap p0)
		{
			System.Diagnostics.Debug.WriteLine($"User portrait loaded");
            return p0;
		}

		public void OnUserPortraitUploaded()
		{
			System.Diagnostics.Debug.WriteLine($"User portrait uploaded");
		}

	}
}
