using Foundation;
using LiferayScreens;
using System;
using System.Diagnostics;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class PdfDisplayViewController : UIViewController, IFileDisplayScreenletDelegate
    {
        public PdfDisplayViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.pdfDisplayScreenlet.ClassPK = 38930;
			this.pdfDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";

            this.pdfDisplayScreenlet.Delegate = this;
		}

        /* IFileDisplayScreenletDelegate */

        [Export("screenlet:onFileAssetError:")]
        public virtual void OnFileAssetError(FileDisplayScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"Pdf display failed: {error.DebugDescription}");
        }

        [Export("screenlet:onFileAssetResponse:")]
        public virtual void OnFileAssetResponse(FileDisplayScreenlet screenlet, NSUrl url)
        {
            Debug.WriteLine($"Pdf display success: {url.DebugDescription}");
        }
    }
}

