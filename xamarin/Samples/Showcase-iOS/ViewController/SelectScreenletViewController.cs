using System;
using UIKit;
using ShowcaseiOS.SelectScreenlets;

namespace ShowcaseiOS.ViewController
{
    public partial class SelectScreenletViewController : UITableViewController
    {
        protected string[] screenlets = 
        { 
            "DDLFormScreenlet", 
            "UserPortraitScreenlet",
            "WebContentScreenlet",
            "AssetListScreenlet",
            "ImageDisplayScreenlet"
        };

        protected string[] viewControllers = 
        { 
            "DDLFormViewController",
            "UserPortraitViewController",
            "WebContentViewController",
            "AssetListViewController",
            "ImageDisplayViewController"
        };

        public SelectScreenletViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            TableView.Source = new ScreenletsTableSource(screenlets, viewControllers, this);
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }
    }
}

