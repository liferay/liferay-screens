using System;
using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Asset;
using Com.Liferay.Mobile.Screens.Asset.Display;
using Com.Liferay.Mobile.Screens.Dlfile.Display.Image;
using Java.Lang;

namespace ShowcaseAndroid
{
    [Activity]
    public class ImageDisplayActivity : Activity, IAssetDisplayListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.ImageDisplayView);

            ImageDisplayScreenlet imageDisplayScreenlet =
                (ImageDisplayScreenlet)FindViewById(Resource.Id.image_display_screenlet);
            imageDisplayScreenlet.Listener = this;

            imageDisplayScreenlet.Load();
        }

        /* IAssetDisplayListener */

        public void Error(Java.Lang.Exception p0, string p1)
        {
            Toast.MakeText(this, "Asset display failed: " + p0.Message, ToastLength.Short).Show();
        }

        public void OnRetrieveAssetSuccess(AssetEntry p0)
        {
            Toast.MakeText(this, "Asset display successful: " + p0.EntryId, ToastLength.Short).Show();
        }
    }
}
