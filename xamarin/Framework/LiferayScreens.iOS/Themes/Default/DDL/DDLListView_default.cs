using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface DDLListView_default : BaseListTableView <DDLListViewModel>
    [BaseType(typeof(BaseListTableView))]
    interface DDLListView_default : IDDLListViewModel
    {
        // @property (copy, nonatomic) NSArray<NSString *> * _Nonnull labelFields;
        [Export("labelFields", ArgumentSemantic.Copy)]
        string[] LabelFields { get; set; }

        // -(NSString * _Nonnull)composeLabel:(DDLRecord * _Nonnull)record __attribute__((warn_unused_result));
        [Export("composeLabel:")]
        string ComposeLabel(DDLRecord record);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
