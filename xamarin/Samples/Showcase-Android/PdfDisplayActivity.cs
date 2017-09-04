using System;
using Android.App;
using Android.OS;
using Com.Liferay.Mobile.Screens.Asset;
using Com.Liferay.Mobile.Screens.Asset.Display;
using Com.Liferay.Mobile.Screens.Dlfile.Display.Pdf;
using Java.Lang;

namespace ShowcaseAndroid
{
    [Activity]
    public class PdfDisplayActivity : Activity, IAssetDisplayListener
    {

        private PdfDisplayScreenlet pdfDisplayScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.PdfDisplayView);

            pdfDisplayScreenlet = (PdfDisplayScreenlet)FindViewById(Resource.Id.pdf_display_screenlet);
            pdfDisplayScreenlet.Listener = this;
        }

		public void Error(Java.Lang.Exception p0, string p1)
		{
			System.Diagnostics.Debug.WriteLine($"Pdf display error: {p0}");
		}

		public void OnRetrieveAssetSuccess(AssetEntry p0)
		{
			System.Diagnostics.Debug.WriteLine($"Pdf display succes: {p0}");
		}
    }
}
