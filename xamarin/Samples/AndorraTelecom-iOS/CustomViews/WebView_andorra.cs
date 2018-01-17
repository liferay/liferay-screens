using LiferayScreens;
using System;
using UIKit;
using AndorraTelecomiOS.CustomViews;

namespace AndorraTelecomiOS
{
    public partial class WebView_andorra : WebView_default
    {
        public WebView_andorra (IntPtr handle) : base (handle) { }

        public override IProgressPresenter CreateProgressPresenter()
        {
            return new AndorraTelecomProgressPresenter();
        }
    }
}