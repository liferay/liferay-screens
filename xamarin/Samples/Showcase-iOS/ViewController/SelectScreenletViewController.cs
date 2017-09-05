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
            "AssetDisplayScreenlet",
            "ImageDisplayScreenlet",
            "PdfDisplayScreenlet",
            "AudioDisplayScreenlet",
            "VideoDisplayScreenlet",
            "ImageGalleryScreenlet"
        };

        protected string[] viewControllers = 
        { 
            "DDLFormViewController",
            "UserPortraitViewController",
            "WebContentViewController",
            "AssetListViewController",
            "AssetDisplayViewController",
            "ImageDisplayViewController",
            "PdfDisplayViewController",
            "AudioDisplayViewController",
            "VideoDisplayViewController",
            "ImageGalleryViewController"
        };

        public SelectScreenletViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            TableView.Source = new ScreenletsTableSource(screenlets, viewControllers, this);
        }
    }
}

