using System.Collections;
using Android.App;
using Android.OS;
using Android.Views;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Base.List;
using Com.Liferay.Mobile.Screens.Webcontent.List;

namespace ShowcaseAndroid
{
    [Activity]
    public class WebContentListActivity : Activity, IBaseListListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.WebContentListView);

            WebContentListScreenlet webContentListScreelet = 
                (WebContentListScreenlet) FindViewById(Resource.Id.web_content_list_screenlet);
            webContentListScreelet.Listener = this;
        }

        /* IBaseListListener */

        public void Error(Java.Lang.Exception p0, string p1)
		{
			System.Diagnostics.Debug.WriteLine($"WebContent list error: {p0}");
		}

		public void OnListItemSelected(Java.Lang.Object p0, View p1)
		{
			Toast.MakeText(this, $"Item selected {p0}", ToastLength.Short).Show();
		}

		public void OnListPageFailed(int p0, Java.Lang.Exception p1)
		{
			System.Diagnostics.Debug.WriteLine($"WebContent list page failed: {p0}");
		}

		public void OnListPageReceived(int p0, int p1, IList p2, int p3)
		{
            Toast.MakeText(this, $"WebContent list page recived: {p3} entries", ToastLength.Short).Show();
		}

	}
}
