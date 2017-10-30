using Foundation;
using LiferayScreens;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class AudioDisplayViewController : UIViewController, IFileDisplayScreenletDelegate
    {
        public AudioDisplayViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.audioDisplayScreenlet.ClassPK = LiferayServerContext.LongPropertyForKey("audioClassPK");
            this.audioDisplayScreenlet.ClassName = LiferayServerContext.StringPropertyForKey("fileClassName");

            this.audioDisplayScreenlet.Delegate = this;
        }

        /* IFileDisplayScreenletDelegate */

        [Export("screenlet:onFileAssetError:")]
        public virtual void OnFileAssetError(FileDisplayScreenlet screenlet, NSError error)
        {
            Console.WriteLine($"Audio display failed: {error.DebugDescription}");
        }

        [Export("screenlet:onFileAssetResponse:")]
        public virtual void OnFileAssetResponse(FileDisplayScreenlet screenlet, NSUrl url)
        {
            Console.WriteLine($"Audio display success: {url.DebugDescription}");
        }
    }
}

