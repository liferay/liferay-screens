using Foundation;
using LiferayScreens;
using System;
using System.Diagnostics;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class VideoDisplayViewController : UIViewController, IFileDisplayScreenletDelegate
    {
        public VideoDisplayViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.videoDisplayScreenlet.ClassPK = 38943;
            this.videoDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";

            this.videoDisplayScreenlet.Delegate = this;
        }

        /* IFileDisplayScreenletDelegate */

        [Export("screenlet:onFileAssetError:")]
        public virtual void OnFileAssetError(FileDisplayScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"Video display failed: {error.DebugDescription}");
        }

        [Export("screenlet:onFileAssetResponse:")]
        public virtual void OnFileAssetResponse(FileDisplayScreenlet screenlet, NSUrl url)
        {
            Debug.WriteLine($"Video display success: {url.DebugDescription}");
        }
    }
}

