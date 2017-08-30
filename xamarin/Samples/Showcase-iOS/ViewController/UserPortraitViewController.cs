using Foundation;
using LiferayScreens;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class UserPortraitViewController : UIViewController, IUserPortraitScreenletDelegate
    {
        public UserPortraitViewController(IntPtr handle) : base(handle) { }

		public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.userPortraitScreenlet.OfflinePolicy = "remote-first";
            this.userPortraitScreenlet.Editable = true;
            this.userPortraitScreenlet.AutoLoad = false;

            this.userPortraitScreenlet.Delegate = this;
        }

        public override void ViewWillAppear(bool animated)
        {
            base.ViewWillAppear(animated);

            this.userPortraitScreenlet.LoadLoggedUserPortrait();
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }

        /* IUserPortraitScreenletDelegate */

        [Export("screenlet:onUserPortraitError:")]
        public virtual void Screenlet(UserPortraitScreenlet screenlet, NSError error)
        {
            System.Diagnostics.Debug.WriteLine($"User portrait failed: {error.DebugDescription}");
        }

        [Export("screenlet:onUserPortraitResponseImage:")]
        public virtual UIImage Screenlet(UserPortraitScreenlet screenlet, UIImage image)
        {
            System.Diagnostics.Debug.WriteLine($"User portrait succesful: {image.DebugDescription}");
            return image;
        }

        [Export("screenlet:onUserPortraitUploaded:")]
        public virtual void Screenlet(UserPortraitScreenlet screenlet, NSDictionary<NSString, NSObject> attributes)
        {
            System.Diagnostics.Debug.WriteLine($"User portrait uploaded successfully: {attributes.DebugDescription}");
        }

        [Export("screenlet:onUserPortraitUploadError:")]
        public virtual void ScreenletUploadError(UserPortraitScreenlet screenlet, NSError error)
        {
            System.Diagnostics.Debug.WriteLine($"User portrait uploaded failed: {error.DebugDescription}");
        }
    }
}
