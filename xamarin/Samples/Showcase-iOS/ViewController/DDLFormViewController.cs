using System;
using UIKit;
using LiferayScreens;

namespace ShowcaseiOS.ViewController
{
    public partial class DDLFormViewController : UIViewController, IDDLFormScreenletDelegate
    {
        public DDLFormViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.ddlFormScreenlet.StructureId = 54371;
            this.ddlFormScreenlet.RecordSetId = 54375;
            this.ddlFormScreenlet.RecordId = 54385;
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }

        public virtual void OnFormLoaded(DDLFormScreenlet screenlet, DDLRecord record)
        {
            System.Diagnostics.Debug.WriteLine($"DDLForm loaded: {record}");
		}

        public virtual void OnRecordLoaded(DDLFormScreenlet screenlet, DDLRecord record)
        {
			System.Diagnostics.Debug.WriteLine($"DDLForm record loaded: {record}");
		}

	}
}

