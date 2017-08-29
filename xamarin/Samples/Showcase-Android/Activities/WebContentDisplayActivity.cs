using Android.App;
using Android.OS;
using Android.Views;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Webcontent;
using Com.Liferay.Mobile.Screens.Webcontent.Display;

namespace ShowcaseAndroid
{
    [Activity]
    public class WebContentDisplayActivity : Activity, IWebContentDisplayListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.WebContentDisplayView);

            WebContentDisplayScreenlet webContentDisplay = 
                (WebContentDisplayScreenlet)FindViewById(Resource.Id.web_content_display_screenlet);
            webContentDisplay.Listener = this;
        }

        /* IWebContentDisplayListener */

        public void Error(Java.Lang.Exception p0, string p1)
        {
            Toast.MakeText(this, $"WebContent display failed: {p0} - {p1}", ToastLength.Short).Show();
        }

        public bool OnUrlClicked(string p0)
        {
            Toast.MakeText(this, $"WebContent url clicked: {p0}", ToastLength.Short).Show();
            return false;
        }

        public WebContent OnWebContentReceived(WebContent p0)
        {
            Toast.MakeText(this, $"WebContent received successfully: {p0}", ToastLength.Short).Show();
            return p0;
        }

        public bool OnWebContentTouched(View p0, MotionEvent p1)
        {
            Toast.MakeText(this, $"WebContent touched: {p0}", ToastLength.Short).Show();
            return false;
        }
    }
}
