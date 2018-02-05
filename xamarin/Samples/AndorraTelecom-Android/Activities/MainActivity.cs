using AndorraTelecomiOS.Util;
using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Web;
using static Com.Liferay.Mobile.Screens.Web.WebScreenletConfiguration;

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
            WebScreenlet.Load();
        }
    }
}
