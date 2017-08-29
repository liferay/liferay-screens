using Android.App;
using Android.Graphics;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Base.Interactor.Listener;
using Com.Liferay.Mobile.Screens.Userportrait;

namespace ShowcaseAndroid
{
    [Activity]
    public class UserPortraitActivity : Activity, IUserPortraitListener, ICacheListener
    {
        UserPortraitScreenlet userPortraitScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.UserPortraitView);

            userPortraitScreenlet = (UserPortraitScreenlet)FindViewById(Resource.Id.user_portrait_screenlet);
            userPortraitScreenlet.Listener = this;
            userPortraitScreenlet.CacheListener = this;
        }

        protected override void OnResume()
        {
            base.OnResume();

            userPortraitScreenlet.LoadLoggedUserPortrait();
        }

        /* IUserPortraitListener */

        public void Error(Java.Lang.Exception p0, string p1)
		{
            Toast.MakeText(this, $"User portrait failed: {p0} - {p1}", ToastLength.Short).Show();
		}

		public Bitmap OnUserPortraitLoadReceived(Bitmap p0)
		{
            Toast.MakeText(this, "User portrait loaded successfully", ToastLength.Short).Show();
            return p0;
		}

		public void OnUserPortraitUploaded()
		{
            Toast.MakeText(this, "User portrait uploaded successfully", ToastLength.Short).Show();
		}

        /* ICacheListener */

        public void LoadingFromCache(bool p0)
        {
            System.Diagnostics.Debug.WriteLine("Loading user portrait from cache");
        }

        public void RetrievingOnline(bool p0, Java.Lang.Exception p1)
        {
            System.Diagnostics.Debug.WriteLine($"Retrieving user portrait online: {p0} - {p1}");
        }

        public void StoringToCache(Java.Lang.Object p0)
        {
            System.Diagnostics.Debug.WriteLine($"Storing user portrait to cache: {p0}");
        }
    }
}
