using System;
using UIKit;
using ShowcaseiOS.SelectScreenlets;

namespace ShowcaseiOS.ViewController
{
    public partial class SelectScreenletViewController : UITableViewController
    {
        protected string[] screenlets = { "DDLFormScreenlet" };

        public SelectScreenletViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            TableView.Source = new ScreenletsTableSource(screenlets, this);
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }
    }
}

