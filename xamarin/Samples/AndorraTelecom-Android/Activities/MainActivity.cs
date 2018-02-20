using System;
using AndorraTelecomAndroid.Activities;
using AndorraTelecomiOS.Util;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Views;
using Android.Views.Animations;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Web;
using static Android.Views.View;
using static Com.Liferay.Mobile.Screens.Web.WebScreenletConfiguration;

namespace AndorraTelecomAndroid
{
    [Activity(MainLauncher = true, Icon = "@mipmap/icon")]
    public class MainActivity : Activity, IWebListener
    {
        public RelativeLayout CallMeBackPopOver;
        public LinearLayout CallMeBackViewHeader;
        public LinearLayout CallMeBackViewBody;
        public TextView CallMeBackText;
        public Button ICallButton;
        public Button CallMeNowButton;
        private bool isOpen = false;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.Main);

            FindViews();

            SetCustomActionBar();

            LoadWebScreenlet();

            CallMeBackPopOver.Click += OpenOrClosePopover;
            CallMeNowButton.Click += CallMeNowAction;
            ICallButton.Click += ICAllAction;
        }

        public override bool OnCreateOptionsMenu(IMenu menu)
        {
            MenuInflater.Inflate(Resource.Menu.top_menu, menu);
            return base.OnCreateOptionsMenu(menu);
        }

        public override bool OnOptionsItemSelected(IMenuItem item)
        {
            AlertDialog.Builder LanguageDialog = new AlertDialog.Builder(this)
                .SetTitle(Resource.String.language_dialog_title)
                .SetSingleChoiceItems(LanguageHelper.ListLanguages, 
                                      LanguageHelper.CurrentSelectedLanguageInDialog, 
                                      (sender, e) => { 
                var position = e.Which; 
                LanguageHelper.SetCurrentLanguage(position); 
                this.Recreate();
            });

            Dialog Dialog = LanguageDialog.Create();
            Dialog.Show();

            return base.OnOptionsItemSelected(item);
        }

        /* Private methods */

        void FindViews()
        {
            CallMeBackPopOver =
                (RelativeLayout)FindViewById(Resource.Id.call_me_back_popover);
            CallMeBackViewHeader =
                (LinearLayout)FindViewById(Resource.Id.call_me_back_header);
            CallMeBackViewBody =
                (LinearLayout)FindViewById(Resource.Id.call_me_back_body);
            CallMeBackText =
                (TextView)FindViewById(Resource.Id.call_me_back_text);

            CallMeNowButton =
                (Button)FindViewById(Resource.Id.button_call_me_now);
            ICallButton =
                (Button)FindViewById(Resource.Id.button_i_call);
        }

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
        }

        void ClosePopOverOnInit()
        {
            CallMeBackViewHeader.Visibility = ViewStates.Visible;
            CallMeBackViewBody.Visibility = ViewStates.Visible;

            Animation close = AnimationUtils.LoadAnimation(this, Resource.Animation.close);
            CallMeBackPopOver.StartAnimation(close);
        }

        void OpenOrClosePopover(object sender, EventArgs e)
        {
            if(!isOpen)
            {
                isOpen = true;
                Animation open = AnimationUtils.LoadAnimation(this, Resource.Animation.open);
                CallMeBackPopOver.StartAnimation(open);
            }
            else
            {
                isOpen = false;
                Animation close = AnimationUtils.LoadAnimation(this, Resource.Animation.close);
                CallMeBackPopOver.StartAnimation(close);
            }
        }

        /* Button actions */

        void CallMeNowAction(object sender, EventArgs e)
        {
            Toast.MakeText(this, Resource.String.call_me_now_message, ToastLength.Short).Show();
        }

        void ICAllAction(object sender, EventArgs e)
        {
            Toast.MakeText(this, Resource.String.i_call_message, ToastLength.Short).Show();
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
                    RunOnUiThread(() =>
                    {
                        SetCallMeBackText(body);
                        ClosePopOverOnInit();
                    });
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
