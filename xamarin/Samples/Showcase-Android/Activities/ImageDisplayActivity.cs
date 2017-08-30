using Android.App;
using Android.OS;
using Com.Liferay.Mobile.Screens.Dlfile.Display.Image;

namespace ShowcaseAndroid
{
    [Activity]
    public class ImageDisplayActivity : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.ImageDisplayView);

            ImageDisplayScreenlet imageDisplayScreenlet =
                (ImageDisplayScreenlet)FindViewById(Resource.Id.image_display_screenlet);
            //FIX-ME: Need AssetDisplayListener
            //imageDisplayScreenlet.Listener = this;

            imageDisplayScreenlet.Load();
        }
    }
}
