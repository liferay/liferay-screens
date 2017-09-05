using System.Collections;
using Android.App;
using Android.OS;
using Android.Views;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Imagegallery;
using Com.Liferay.Mobile.Screens.Imagegallery.Model;

namespace ShowcaseAndroid
{
    [Activity]
    public class ImageGalleryActivity : Activity/*, IImageGalleryListener*/
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.ImageGalleryView);

            ImageGalleryScreenlet imageGalleryScreenlet = 
                (ImageGalleryScreenlet) FindViewById(Resource.Id.image_gallery_screenlet);
            //imageGalleryScreenlet.Listener = this;
        }

        /* IImageGalleryListener */

        //FIXME: Listener doesn't compile
        public void Error(Java.Lang.Exception p0, string p1)
        {
            System.Diagnostics.Debug.WriteLine($"Image gallery failed: {p0.Message}");
        }

        public void OnImageEntryDeleted(long p0)
        {
            Toast.MakeText(this, "Image gallery entry deleted: " + p0, ToastLength.Short).Show();
        }

        public void OnImageUploadEnd(ImageEntry p0)
        {
            Toast.MakeText(this, "Image gallery entry upload end: " + p0.EntryId, ToastLength.Short).Show();
        }

        public void OnImageUploadProgress(int p0, int p1)
        {
            Toast.MakeText(this, "Image gallery entry upload progress: " + p1 + " of " + p0, ToastLength.Short).Show();
        }

        public void OnImageUploadStarted(Android.Net.Uri p0, string p1, string p2, string p3)
        {
            Toast.MakeText(this, "Image gallery entry upload started: " + p1, ToastLength.Short).Show();
        }

        public void OnListItemSelected(Java.Lang.Object p0, View p1)
        {
            Toast.MakeText(this, "Image gallery entry selected: " + p0, ToastLength.Short).Show();
        }

        public void OnListPageFailed(int p0, Java.Lang.Exception p1)
        {
            System.Diagnostics.Debug.WriteLine($"Image gallery page failed: {p1.Message}");
        }

        public void OnListPageReceived(int p0, int p1, IList p2, int p3)
        {
            Toast.MakeText(this, "Image gallery page received: " + p3 + " rows", ToastLength.Short).Show();
        }

        public int ProvideImageUploadDetailView()
        {
            return 0;
        }

        public bool ShowUploadImageView(string p0, Android.Net.Uri p1, int p2)
        {
            return false;
        }
    }
}
