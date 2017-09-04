using System;
using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Asset;
using Com.Liferay.Mobile.Screens.Asset.Display;
using Com.Liferay.Mobile.Screens.Dlfile.Display.Video;
using Java.Lang;

namespace ShowcaseAndroid
{
    [Activity]
    public class VideoDisplayActivity : Activity, IAssetDisplayListener, IVideoDisplayScreenletListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.VideoDisplayView);

            VideoDisplayScreenlet videoDisplayScreenlet = 
                (VideoDisplayScreenlet)FindViewById(Resource.Id.video_display_screenlet);
            videoDisplayScreenlet.Listener = this;
        }

        /* IAssetDisplayListener */

        public void OnRetrieveAssetSuccess(AssetEntry p0)
        {
            Toast.MakeText(this, "Video display success: " + p0.EntryId, ToastLength.Short).Show();
        }

        public void Error(Java.Lang.Exception p0, string p1)
        {
            System.Diagnostics.Debug.WriteLine($"Video display error: {p0.Message}");
        }

        /* IVideoDisplayScreenletListener */

        public void OnVideoCompleted()
        {
            System.Diagnostics.Debug.WriteLine("Video completed");
        }

        public void OnVideoError(Java.Lang.Exception p0)
        {
            System.Diagnostics.Debug.WriteLine($"Video error: {p0.Message}");
        }

        public void OnVideoPrepared()
        {
            System.Diagnostics.Debug.WriteLine("Video prepared");
        }
    }
}
