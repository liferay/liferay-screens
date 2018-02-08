using System;
using AndorraTelecomAndroid.Activities;
using AndorraTelecomiOS.Util;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Web;
using static Com.Liferay.Mobile.Screens.Web.WebScreenletConfiguration;

namespace AndorraTelecomAndroid
{
    [Activity(MainLauncher = true, Icon = "@mipmap/icon")]
    public class MainActivity : Activity, IWebListener
    {
        public RelativeLayout CallMeBackView;
        public TextView CallMeBackText;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.Main);

            CallMeBackView =
                (RelativeLayout)FindViewById(Resource.Id.call_me_back_view);
            CallMeBackText =
                (TextView)FindViewById(Resource.Id.call_me_back_text);

            SetCustomActionBar();

            LoadWebScreenlet();
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

        void LoadWebScreenlet()
        {
            WebScreenlet WebScreenlet =
                (WebScreenlet)FindViewById(Resource.Id.web_screenlet);

            var Url = LanguageHelper.Url(LanguageHelper.Pages.Index);

            WebScreenletConfiguration webScreenletConfiguration = new WebScreenletConfiguration
                .Builder(Url)
                .SetWebType(WebType.Other)
                .AddRawJs(Resource.Raw.menu_js, "menu_js.js")
                .AddRawCss(Resource.Raw.menu_css, "menu_css.css")
                .Load();

            WebScreenlet.SetWebScreenletConfiguration(webScreenletConfiguration);
            WebScreenlet.ScrollEnabled = false;
            WebScreenlet.Listener = this;
            WebScreenlet.Load();
        }

        void GoToNextForfet(string body)
        {
            var ForfetActivity = new Intent(this, typeof(ForfetActivity));
            ForfetActivity.PutExtra("body", body);
            StartActivity(ForfetActivity);
        }

        void GoToMap()
        {
            StartActivity(typeof(MapActivity));
        }

        void SetCallMeBackText(string body)
        {
            CallMeBackText.Text = body;
            CallMeBackView.Visibility = Android.Views.ViewStates.Visible;
        }

        /* IWebListener */

        public void Error(Java.Lang.Exception ex, string userAction)
        {
            Android.Util.Log.Debug("WebScreenlet", $"Web Screenlet error: {ex}");
        }

        public void OnPageLoaded(string url)
        {
            Android.Util.Log.Debug("WebScreenlet", $"Page loaded: {url}");
        }

        public void OnScriptMessageHandler(string namespace_, string body)
        {
            Android.Util.Log.Debug("WebScreenlet", $"JS Message center | namespace: {namespace_} - message: {body}");

            switch (namespace_)
            {
                case "call-me-back":
                    Android.Util.Log.Debug("WebScreenlet", "Call me back popover");
                    RunOnUiThread(() => SetCallMeBackText(body));
                    break;
                case "click-button":
                    Android.Util.Log.Debug("WebScreenlet", "Go to next forfet");
                    GoToNextForfet(body);
                    break;
                case "map":
                    Android.Util.Log.Debug("WebScreenlet", "Go to map");
                    GoToMap();
                    break;
                default:
                    Android.Util.Log.Debug("WebScreenlet", "Invalid event");
                    break;
            }
        }
    }
}
