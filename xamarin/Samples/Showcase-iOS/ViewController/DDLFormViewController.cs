using Foundation;
using LiferayScreens;
using System;
using UIKit;
using System.Diagnostics;

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
            this.ddlFormScreenlet.AutoLoad = false;

            this.ddlFormScreenlet.Delegate = this;

            this.ddlFormScreenlet.LoadForm();
        }

        /* IDDLFormScreenletDelegate */

        [Export("screenlet:onFormLoaded:")]
        public virtual void OnFormLoaded(DDLFormScreenlet screenlet, DDLRecord record)
        {
            Debug.WriteLine("DDLForm loaded successfully");
        }

        [Export("screenlet:onFormLoadError:")]
        public virtual void OnFormLoadError(DDLFormScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"DDLForm loaded failed: {error.DebugDescription}");
        }

        [Export("screenlet:onFormSubmitError:")]
        public virtual void OnFormSubmitError(DDLFormScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"DDLForm submit failed: {error.DebugDescription}");
        }

        [Export("screenlet:onFormSubmitted:")]
        public virtual void OnFormSubmitted(DDLFormScreenlet screenlet, DDLRecord record)
        {
            Debug.WriteLine($"DDLForm submitted successfully: {record.Attributes}");
        }

        [Export("screenlet:onRecordLoaded:")]
        public virtual void OnRecordLoaded(DDLFormScreenlet screenlet, DDLRecord record)
        {
            Debug.WriteLine($"DDLForm record loaded successfully: {record.Attributes}");
        }

        [Export("screenlet:onRecordLoadError:")]
        public virtual void OnRecordLoadError(DDLFormScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"DDLForm record loaded failed: {error.DebugDescription}");
        }
	}
}

