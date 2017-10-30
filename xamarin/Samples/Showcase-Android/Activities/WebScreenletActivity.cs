using Android.App;
using Android.OS;
using Com.Liferay.Mobile.Screens.Web;

namespace ShowcaseAndroid
{
    [Activity]
    public class WebScreenletActivity : Activity, IWebListener
    {
        WebScreenlet webScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.WebScreenletView);

            webScreenlet = (WebScreenlet)FindViewById(Resource.Id.web_screenlet_screenlet);

        	loadWebScreenlet();
        }

        private void loadWebScreenlet()
        {
            WebScreenletConfiguration webScreenletConfiguration = new WebScreenletConfiguration
        		.Builder("/web/westeros-hybrid/documents")
                .AddRawJs(Resource.Raw.js_master, "js_master.js")
                .AddRawCss(Resource.Raw.css_master, "css_master.css")
        		.Load();

            webScreenlet.SetWebScreenletConfiguration(webScreenletConfiguration);
            webScreenlet.Listener = this;

            webScreenlet.Load();
        }

        /* IWebListener */

        public void Error(Java.Lang.Exception p0, string p1)
        {
            Android.Util.Log.Debug("WebScreenlet", $"Web Screenlet error: {p0}");
        }

        public void OnPageLoaded(string p0)
        {
            Android.Util.Log.Debug("WebScreenlet", $"Page loaded: {p0}");
        }

        public void OnScriptMessageHandler(string p0, string p1)
        {
            Android.Util.Log.Debug("WebScreenlet", $"JS Message center | namespace: {p0} - message: {p1}");
        }
    }
}
