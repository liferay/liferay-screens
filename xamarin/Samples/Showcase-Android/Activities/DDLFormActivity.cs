using System.Collections.Generic;
using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Ddl.Form;
using Com.Liferay.Mobile.Screens.Ddl.Model;
using Org.Json;

namespace ShowcaseAndroid
{
    [Activity]
    public class DDLFormActivity : Activity, IDDLFormListener
    {

        private DDLFormScreenlet ddlFormScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.DDLFormView);

            ddlFormScreenlet = (DDLFormScreenlet)FindViewById(Resource.Id.ddl_form_screenlet);
            ddlFormScreenlet.Listener = this;
        }

        protected override void OnResume()
        {
            base.OnResume();
            ddlFormScreenlet.Load();
        }

        public void Error(Java.Lang.Exception p0, string p1)
        {
            System.Diagnostics.Debug.WriteLine($"DDLForm error: {p0}");
		}

        public void OnDDLFormDocumentUploaded(DocumentField p0, JSONObject p1)
        {
			System.Diagnostics.Debug.WriteLine($"DDLForm document uploaded: {p0}");
		}

        public void OnDDLFormDocumentUploadFailed(DocumentField p0, Java.Lang.Exception p1)
        {
			System.Diagnostics.Debug.WriteLine($"DDLForm document uploaded fail: {p0}");
		}

        public void OnDDLFormLoaded(Record p0)
        {
            Toast.MakeText(this, "Form loaded", ToastLength.Short).Show();
        }

        public void OnDDLFormRecordAdded(Record p0)
        {
            Toast.MakeText(this, "Record added", ToastLength.Short).Show();
        }

        public void OnDDLFormRecordLoaded(Record p0, IDictionary<string, Java.Lang.Object> p1)
        {
            Toast.MakeText(this, "Record Loaded", ToastLength.Short).Show();
        }

        public void OnDDLFormRecordUpdated(Record p0)
        {
            System.Diagnostics.Debug.WriteLine($"DDLForm record updated: {p0}");
        }
    }
}
