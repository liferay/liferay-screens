using Android.App;
using Android.OS;
using Com.Liferay.Mobile.Screens.Webcontent.Display;

namespace ShowcaseAndroid
{
    [Activity]
    public class WebContentDisplayActivity : Activity
    {
        private WebContentDisplayScreenlet webContentDisplay;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.WebContentDisplayView);

            webContentDisplay = (WebContentDisplayScreenlet)FindViewById(Resource.Id.web_content_display_screenlet);

        }
    }
}
