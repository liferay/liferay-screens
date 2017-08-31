using System.Collections;
using Android.App;
using Android.OS;
using Android.Views;
using Com.Liferay.Mobile.Screens.Asset.List;
using Com.Liferay.Mobile.Screens.Base.List;

namespace ShowcaseAndroid
{
    [Activity]
    public class AssetListActivity : Activity, IBaseListListener
    {
        private AssetListScreenlet assetListScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.AssetListView);

            assetListScreenlet = (AssetListScreenlet)FindViewById(Resource.Id.asset_list_screenlet);
            assetListScreenlet.Listener = this;
        }

        protected override void OnResume()
        {
            base.OnResume();
            assetListScreenlet.LoadPage(0);
        }

        /* IBaseListListener */

        public void Error(Java.Lang.Exception p0, string p1)
        {
        	System.Diagnostics.Debug.WriteLine($"Asset List error: {p0}");
        }

        public void OnListItemSelected(Java.Lang.Object p0, View p1)
        {
        	System.Diagnostics.Debug.WriteLine($"Item selected: {p0}");
        }

        public void OnListPageFailed(int p0, Java.Lang.Exception p1)
        {
        	System.Diagnostics.Debug.WriteLine($"List page failed: {p1}");
        }

        public void OnListPageReceived(int p0, int p1, IList p2, int p3)
        {
            System.Diagnostics.Debug.WriteLine($"List page received");
        }
    }
}
