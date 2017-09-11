using System;
using UIKit;
using Foundation;
using LiferayScreens;
using System.Diagnostics;

namespace ShowcaseiOS.ViewController
{
    public partial class WebContentListViewController : UIViewController, IWebContentListScreenletDelegate
    {
        public WebContentListViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.webContentListScreenlet.FolderId = 0;

            this.webContentListScreenlet.Delegate = this;
        }

        /* IWebContentListScreenletDelegate */

        [Export("screenlet:onWebContentListError:")]
        public virtual void OnWebContentListError(WebContentListScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"WebContent error: {error.DebugDescription}");
        }

        [Export("screenlet:onWebContentListResponse:")]
        public virtual void OnWebContentListResponse(WebContentListScreenlet screenlet, WebContent[] contents)
        {
            Debug.WriteLine($"WebContent response: {contents.Length} entries");
        }

        [Export("screenlet:onWebContentSelected:")]
        public virtual void OnWebContentSelected(WebContentListScreenlet screenlet, WebContent content)
        {
            Debug.WriteLine($"WebContent selected: {content.Attributes}");
        }
    }
}

