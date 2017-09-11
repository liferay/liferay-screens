using System;
using UIKit;
using Foundation;
using LiferayScreens;
using System.Diagnostics;

namespace ShowcaseiOS.ViewController
{
    public partial class DDLListViewController : UIViewController, IDDLListScreenletDelegate
    {
        public DDLListViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.ddlListScreenlet.RecordSetId = 33280;
            this.ddlListScreenlet.LabelFields = "description";

            this.ddlListScreenlet.Delegate = this;
        }

        /* IDDLListScreenletDelegate */

        [Export("screenlet:onDDLListError:")]
        public virtual void OnDDLListError(DDLListScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"DDLList error: {error.DebugDescription}");
        }

        [Export("screenlet:onDDLListResponseRecords:")]
        public virtual void OnDDLListResponseRecords(DDLListScreenlet screenlet, DDLRecord[] records)
        {
            Debug.WriteLine($"DDLList response: {records.Length} records");
        }

        [Export("screenlet:onDDLSelectedRecord:")]
        public virtual void OnDDLSelectedRecord(DDLListScreenlet screenlet, DDLRecord record)
        {
            Debug.WriteLine($"DDLList selected record: {record.Attributes}");
        }
    }
}
