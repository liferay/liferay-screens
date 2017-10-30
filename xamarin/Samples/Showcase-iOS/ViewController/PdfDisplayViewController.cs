using Foundation;
using LiferayScreens;
using System;
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
            Console.WriteLine($"Pdf display failed: {error.DebugDescription}");
        }

        [Export("screenlet:onFileAssetResponse:")]
        public virtual void OnFileAssetResponse(FileDisplayScreenlet screenlet, NSUrl url)
        {
            Console.WriteLine($"Pdf display success: {url.DebugDescription}");
        }
    }
}
