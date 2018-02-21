using AndorraTelecomiOS.Util;
using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Web;
using static Com.Liferay.Mobile.Screens.Web.WebScreenletConfiguration;

namespace AndorraTelecomAndroid.Activities
{
    [Activity(Label = "ForfetActivity")]
    public class ForfetActivity : Activity, IWebListener
    {
        public LanguageHelper.Pages ForfetUrl;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.Forfet);

            var ForfetIndex = Intent.GetStringExtra("body");
            ForfetUrl = GetUrlFromForfetIndex(ForfetIndex);

            SetCustomActionBar();

            LoadWebScreenlet();
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

            var Url = LanguageHelper.Url(ForfetUrl);

            WebScreenletConfiguration webScreenletConfiguration = new WebScreenletConfiguration
                .Builder(Url)
                .SetWebType(WebType.Other)
                .AddRawJs(Resource.Raw.forfet_js, "forfet_js.js")
                .AddRawCss(Resource.Raw.forfet_css, "forfet_css.css")
                .Load();

            WebScreenlet.SetWebScreenletConfiguration(webScreenletConfiguration);
            WebScreenlet.Listener = this;
            WebScreenlet.Load();
        }

        LanguageHelper.Pages GetUrlFromForfetIndex(string ForfetIndex)
        {
            switch (ForfetIndex)
            {
                case "0":
                    return LanguageHelper.Pages.Mobile;
                case "1":
                    return LanguageHelper.Pages.Roaming;
                case "2":
                    return LanguageHelper.Pages.Paquete69;
                case "3":
                    return LanguageHelper.Pages.Optima;
                default:
                    Android.Util.Log.Debug("WebScreenlet forfet", "Invalid page, redirect to index");
                    return LanguageHelper.Pages.Index;
                    
            }
        }

        /* IWebListener */

        public void Error(Java.Lang.Exception ex, string userAction)
        {
            Android.Util.Log.Debug("WebScreenlet", $"Web Screenlet error: {ex.Message}");
        }

        public void OnPageLoaded(string url)
        {
            Android.Util.Log.Debug("WebScreenlet", $"Page loaded: {url}");
        }

        public void OnScriptMessageHandler(string namespace_, string body)
        {
            Android.Util.Log.Debug("WebScreenlet", $"JS Message center | namespace: {namespace_} - message: {body}");
        }
    }
}
