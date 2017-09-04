using Foundation;
using LiferayScreens;
using System;
using UIKit;
using System.Diagnostics;

namespace ShowcaseiOS.ViewController
{
    public partial class WebContentViewController : UIViewController, IWebContentDisplayScreenletDelegate
    {
		public WebContentViewController(IntPtr handle) : base(handle) { }

		public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.webContentDisplayScreenlet.ArticleId = "57343";
            this.webContentDisplayScreenlet.GroupId = 20143;
            this.webContentDisplayScreenlet.TemplateId = 29644;

            this.webContentDisplayScreenlet.Delegate = this;
        }

        /* IWebContentDisplayScreenletDelegate */

        [Export("screenlet:onRecordContentResponse:")]
        public void Screenlet(WebContentDisplayScreenlet screenlet, DDLRecord record)
        {
            Debug.WriteLine($"WebContent display record successfully: {record.DebugDescription}");
        }

        [Export("screenlet:onWebContentResponse:")]
        public string Screenlet(WebContentDisplayScreenlet screenlet, string html)
        {
            Debug.WriteLine("WebContent display successfully");
            return html;
        }

        [Export("screenlet:onWebContentError:")]
        public void Screenlet(WebContentDisplayScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"WebContent failed: {error.DebugDescription}");
        }
    }
}