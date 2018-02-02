using System;
using Android.App;
using Android.OS;
using Android.Widget;

namespace AndorraTelecomAndroid
{
    [Activity(MainLauncher = true, Icon = "@mipmap/icon")]
    public class MainActivity : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.Main);

            SetCustomActionBar();
        }

        public override bool OnCreateOptionsMenu(Android.Views.IMenu menu)
        {
            MenuInflater.Inflate(Resource.Menu.top_menu, menu);
            return base.OnCreateOptionsMenu(menu);
        }

        /* Private methods */

        void SetCustomActionBar()
        {
            Toolbar Toolbar = (Toolbar)FindViewById(Resource.Id.toolbar);
            SetActionBar(Toolbar);
            ActionBar.SetDisplayShowTitleEnabled(false);
            ActionBar.SetIcon(Resource.Drawable.logo);
        }
    }
}
