using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Asset;
using Com.Liferay.Mobile.Screens.Dlfile.Display.Video;

namespace ShowcaseAndroid
{
    [Activity]
    public class VideoDisplayActivity : Activity, IVideoDisplayScreenletListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.VideoDisplayView);

            VideoDisplayScreenlet videoDisplayScreenlet =
                (VideoDisplayScreenlet) FindViewById(Resource.Id.video_display_screenlet);
            videoDisplayScreenlet.Listener = this;
        }

        /* IVideoDisplayScreenletListener */

        public void OnVideoCompleted()
        {
            Android.Util.Log.Debug("VideoDisplayScreenlet", "Video completed");
        }

        public void OnVideoError(Java.Lang.Exception p0)
        {
            Android.Util.Log.Debug("VideoDisplayScreenlet", $"Video error: {p0.Message}");
        }

        public void OnVideoPrepared()
        {
            Android.Util.Log.Debug("VideoDisplayScreenlet", "Video prepared");
        }

        /* IAssetDisplayListener */

        public void OnRetrieveAssetSuccess(AssetEntry p0)
        {
            Toast.MakeText(this, "Video display success: " + p0.EntryId, ToastLength.Short).Show();
        }

        public void Error(Java.Lang.Exception p0, string p1)
        {
            Android.Util.Log.Debug("VideoDisplayScreenlet", $"Video display error: {p0.Message}");
        }
    }
}
