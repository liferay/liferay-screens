using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Asset;
using Com.Liferay.Mobile.Screens.Asset.Display;

namespace ShowcaseAndroid
{
    [Activity]
    public class AssetDisplayActivity : Activity, IAssetDisplayListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.AssetDisplayView);

            AssetDisplayScreenlet assetDisplayScreenlet = 
                (AssetDisplayScreenlet) FindViewById(Resource.Id.asset_display_screenlet);
            assetDisplayScreenlet.Listener = this;
        }

        /* IAssetDisplayListener */

        public void OnRetrieveAssetSuccess(AssetEntry p0)
        {
            Toast.MakeText(this, "Asset display success: " + p0.EntryId, ToastLength.Short).Show();
        }

        public void Error(Java.Lang.Exception p0, string p1)
		{
			System.Diagnostics.Debug.WriteLine($"Asset display failed: {p0}");
		}
    }
}
