using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    interface IFileDisplayScreenletDelegate { }

    // @protocol FileDisplayScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface FileDisplayScreenletDelegate
    {
        // @optional -(void)screenlet:(FileDisplayScreenlet * _Nonnull)screenlet onFileAssetResponse:(NSURL * _Nonnull)url;
        [Export("screenlet:onFileAssetResponse:")]
        void OnFileAssetResponse(FileDisplayScreenlet screenlet, NSUrl url);

        // @optional -(void)screenlet:(FileDisplayScreenlet * _Nonnull)screenlet onFileAssetError:(NSError * _Nonnull)error;
        [Export("screenlet:onFileAssetError:")]
        void OnFileAssetError(FileDisplayScreenlet screenlet, NSError error);
    }
}
