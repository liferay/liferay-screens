using System.Collections;
using Android.App;
using Android.OS;
using Android.Views;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Base.List;
using Com.Liferay.Mobile.Screens.Ddl.List;

namespace ShowcaseAndroid
{
    [Activity]
    public class DDLListActivity : Activity, IBaseListListener
    {

        private DDLListScreenlet ddlListScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.DDLListView);

            ddlListScreenlet = (DDLListScreenlet)FindViewById(Resource.Id.ddl_list_screenlet);
            ddlListScreenlet.Listener = this;
	
        }

		public void Error(Java.Lang.Exception p0, string p1)
		{
            System.Diagnostics.Debug.WriteLine($"DDLList error: {p0.Message}");
		}

		public void OnListItemSelected(Java.Lang.Object p0, View p1)
		{
            Toast.MakeText(this, "Item selected: " + p0, ToastLength.Short).Show();
		}

		public void OnListPageFailed(int p0, Java.Lang.Exception p1)
		{
            System.Diagnostics.Debug.WriteLine($"DDLList page failed: {p1.Message}");
		}

		public void OnListPageReceived(int p0, int p1, IList p2, int p3)
		{
            Toast.MakeText(this, $"DDLList page received: {p3} entries", ToastLength.Short).Show();
		}
    }
}
