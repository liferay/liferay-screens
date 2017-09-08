using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Asset;
using Com.Liferay.Mobile.Screens.Asset.Display;
using Com.Liferay.Mobile.Screens.Blogs;

namespace ShowcaseAndroid
{
    [Activity]
    public class BlogsEntryDisplayActivity : Activity, IAssetDisplayListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.BlogsEntryView);

            BlogsEntryDisplayScreenlet blogsEntryDisplayScreenlet =
                (BlogsEntryDisplayScreenlet) FindViewById(Resource.Id.blogs_entry_display_screenlet);
            blogsEntryDisplayScreenlet.Listener = this;
        }

        /* IAssetDisplayListener */

        public void OnRetrieveAssetSuccess(AssetEntry p0)
        {
            Toast.MakeText(this, "Blog entry display success: " + p0.EntryId, ToastLength.Short).Show();
        }

        public void Error(Java.Lang.Exception p0, string p1)
        {
            System.Diagnostics.Debug.WriteLine($"Blog entry display failed: {p0.Message}");
        }
    }
}
