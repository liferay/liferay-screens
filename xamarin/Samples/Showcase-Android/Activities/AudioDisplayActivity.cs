
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Asset;
using Com.Liferay.Mobile.Screens.Asset.Display;
using Com.Liferay.Mobile.Screens.Dlfile.Display.Audio;

namespace ShowcaseAndroid
{
    [Activity]
    public class AudioDisplayActivity : Activity, IAssetDisplayListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.AudioDisplayView);

            AudioDisplayScreenlet audioDisplayScreenlet =
                (AudioDisplayScreenlet) FindViewById(Resource.Id.audio_display_screenlet);
            audioDisplayScreenlet.Listener = this;
        }

        /* IAssetDisplayListener */

        public void Error(Java.Lang.Exception p0, string p1)
        {
            System.Diagnostics.Debug.WriteLine($"Audio display error: {p0.Message}");
        }

        public void OnRetrieveAssetSuccess(AssetEntry p0)
        {
            Toast.MakeText(this, "Audio display success: " + p0.EntryId, ToastLength.Short).Show();
        }
    }
}
