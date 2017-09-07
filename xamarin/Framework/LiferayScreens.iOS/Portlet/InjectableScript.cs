using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    interface IInjectableScript {}
    
    // @protocol LiferayScreens
    [Protocol, Model]
    interface InjectableScript
    {
        // @required @property (readonly, copy, nonatomic) NSString * _Nonnull name;
        [Abstract]
        [Export("name")]
        string Name { get; }

        // @required @property (readonly, copy, nonatomic) NSString * _Nonnull content;
        [Abstract]
        [Export("content")]
        string Content { get; }
    }
}
