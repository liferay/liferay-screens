using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    interface IDDLListViewModel { }

    // @protocol DDLListViewModel
    [Protocol, Model]
    interface DDLListViewModel
    {
        // @required @property (copy, nonatomic) NSArray<NSString *> * _Nonnull labelFields;
        [Abstract]
        [Export("labelFields", ArgumentSemantic.Copy)]
        string[] LabelFields { get; set; }
    }
}
