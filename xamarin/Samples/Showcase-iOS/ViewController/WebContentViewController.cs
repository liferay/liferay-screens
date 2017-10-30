using Foundation;
using LiferayScreens;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class WebContentViewController : UIViewController, IWebContentDisplayScreenletDelegate
    {
        public WebContentViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.webContentDisplayScreenlet.ArticleId = LiferayServerContext.StringPropertyForKey("webContentDisplayArticleId");
            this.webContentDisplayScreenlet.GroupId = LiferayServerContext.LongPropertyForKey("webContentDisplayGroupId");
            this.webContentDisplayScreenlet.TemplateId = LiferayServerContext.LongPropertyForKey("webContentDisplayTemplateId");

            this.webContentDisplayScreenlet.Delegate = this;
        }

        /* IWebContentDisplayScreenletDelegate */

        [Export("screenlet:onRecordContentResponse:")]
        public void Screenlet(WebContentDisplayScreenlet screenlet, DDLRecord record)
        {
            Console.WriteLine($"WebContent display record successfully: {record.DebugDescription}");
        }

        [Export("screenlet:onWebContentResponse:")]
        public string Screenlet(WebContentDisplayScreenlet screenlet, string html)
        {
            Console.WriteLine("WebContent display successfully");
            return html;
        }

        [Export("screenlet:onWebContentError:")]
        public void Screenlet(WebContentDisplayScreenlet screenlet, NSError error)
        {
            Console.WriteLine($"WebContent failed: {error.DebugDescription}");
        }
    }
}
