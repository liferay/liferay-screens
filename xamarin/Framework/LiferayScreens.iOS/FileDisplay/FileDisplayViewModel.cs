using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    interface IFileDisplayViewModel { }

    // @protocol FileDisplayViewModel
    [BaseType(typeof(NSObject))]
    [Protocol, Model]
    interface FileDisplayViewModel
    {
        // @required @property (copy, nonatomic) NSURL * _Nullable url;
        [Abstract]
        [NullAllowed, Export("url", ArgumentSemantic.Copy)]
        NSUrl Url { get; set; }

        // @required @property (copy, nonatomic) NSString * _Nullable title;
        [Abstract]
        [NullAllowed, Export("title")]
        string Title { get; set; }
    }
}
