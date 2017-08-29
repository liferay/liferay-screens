using Foundation;
using ObjCRuntime;
using UIKit;

namespace LiferayScreens
{
    // @protocol UserPortraitScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface UserPortraitScreenletDelegate
    {
        // @optional -(UIImage * _Nonnull)screenlet:(UserPortraitScreenlet * _Nonnull)screenlet onUserPortraitResponseImage:(UIImage * _Nonnull)image __attribute__((warn_unused_result));
        [Export("screenlet:onUserPortraitResponseImage:")]
        UIImage Screenlet(UserPortraitScreenlet screenlet, UIImage image);

        // @optional -(void)screenlet:(UserPortraitScreenlet * _Nonnull)screenlet onUserPortraitError:(NSError * _Nonnull)error;
        [Export("screenlet:onUserPortraitError:")]
        void Screenlet(UserPortraitScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(UserPortraitScreenlet * _Nonnull)screenlet onUserPortraitUploaded:(NSDictionary<NSString *,id> * _Nonnull)attributes;
        [Export("screenlet:onUserPortraitUploaded:")]
        void Screenlet(UserPortraitScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);

        // @optional -(void)screenlet:(UserPortraitScreenlet * _Nonnull)screenlet onUserPortraitUploadError:(NSError * _Nonnull)error;
        [Export("screenlet:onUserPortraitUploadError:")]
        void ScreenletUploadError(UserPortraitScreenlet screenlet, NSError error);
    }
}
