using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    interface IWebContentDisplayViewModel {}

    // @protocol WebContentDisplayViewModel
    [BaseType(typeof(NSObject))]
    [Protocol, Model]
    interface WebContentDisplayViewModel
    {
        // @required @property (copy, nonatomic) NSString * _Nullable htmlContent;
        [Abstract]
        [NullAllowed, Export("htmlContent")]
        string HtmlContent { get; set; }

        // @required @property (nonatomic, strong) DDLRecord * _Nullable recordContent;
        [Abstract]
        [NullAllowed, Export("recordContent", ArgumentSemantic.Strong)]
        DDLRecord RecordContent { get; set; }

        // @required @property (copy, nonatomic) NSString * _Nullable customCssFile;
        [Abstract]
        [NullAllowed, Export("customCssFile")]
        string CustomCssFile { get; set; }
    }
}
