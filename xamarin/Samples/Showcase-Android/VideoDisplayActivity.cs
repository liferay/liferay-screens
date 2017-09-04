using Android.App;
using Android.OS;
using Com.Liferay.Mobile.Screens.Asset;
using Com.Liferay.Mobile.Screens.Asset.Display;
using Com.Liferay.Mobile.Screens.Dlfile.Display.Video;

namespace ShowcaseAndroid
{
    [Activity]
    public class VideoDisplayActivity : Activity, IAssetDisplayListener
    {
        private VideoDisplayScreenlet videoDisplayScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.VideoDisplayView);

            videoDisplayScreenlet = (VideoDisplayScreenlet)FindViewById(Resource.Id.video_display_screenlet);
            videoDisplayScreenlet.Listener = this;
        }

		public void Error(Java.Lang.Exception p0, string p1)
		{
			System.Diagnostics.Debug.WriteLine($"Video display error: {p0}");
		}

		public void OnRetrieveAssetSuccess(AssetEntry p0)
		{
			System.Diagnostics.Debug.WriteLine($"Video display succes: {p0}");
		}

	}
}
