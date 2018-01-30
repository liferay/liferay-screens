using System;
using AndorraTelecomiOS.Util;
using CoreGraphics;
using Foundation;
using LiferayScreens;
using ObjCRuntime;
using UIKit;

namespace AndorraTelecomiOS
{
    public partial class LegalConditionsView : UIView
    {
        public LegalConditionsView(IntPtr handle) : base(handle) { }

        public static LegalConditionsView Create()
        {
            var LegalConditionsViewArr = NSBundle.MainBundle.LoadNib("LegalConditionsView", null, null);
            var View = Runtime.GetNSObject<LegalConditionsView>(LegalConditionsViewArr.ValueAt(0));

            LoadWebScreenlet(View);

            return View;
        }

        /* Private methods */

        static void LoadWebScreenlet(LegalConditionsView View)
        {
            var Url = LanguageHelper.Url(LanguageHelper.Pages.Legal);

            var Configuration = new WebScreenletConfigurationBuilder(Url)
                .SetWithWebType(WebType.Other)
                .AddJsWithLocalFile("js/legal_js")
                .AddCssWithLocalFile("css/legal_css")
                .Load();

            View.WebScreenlet.ThemeName = "andorra";
            View.WebScreenlet.Configuration = Configuration;
            View.WebScreenlet.Load();
        }
    }
}
