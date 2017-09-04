using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Asset;
using Com.Liferay.Mobile.Screens.Asset.Display;
using Com.Liferay.Mobile.Screens.Dlfile.Display.Pdf;

namespace ShowcaseAndroid
{
    [Activity]
    public class PdfDisplayActivity : Activity, IAssetDisplayListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.PdfDisplayView);

            PdfDisplayScreenlet pdfDisplayScreenlet = 
                (PdfDisplayScreenlet)FindViewById(Resource.Id.pdf_display_screenlet);
            pdfDisplayScreenlet.Listener = this;
        }

        /* IAssetDisplayListener */

        public void OnRetrieveAssetSuccess(AssetEntry p0)
        {
            Toast.MakeText(this, "Pdf display success: " + p0.EntryId, ToastLength.Short).Show();
        }

        public void Error(Java.Lang.Exception p0, string p1)
		{
            System.Diagnostics.Debug.WriteLine($"Pdf display failed: {p0.Message}");
		}
    }
}
