using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface AudioDisplayScreenlet : FileDisplayScreenlet
    [BaseType(typeof(FileDisplayScreenlet))]
    interface AudioDisplayScreenlet
    {
        // @property (copy, nonatomic) NSString * _Nonnull mimeTypes;
        [Export("mimeTypes")]
        string MimeTypes { get; set; }

        // @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull supportedMimeTypes;
        [Export("supportedMimeTypes", ArgumentSemantic.Copy)]
        string[] SupportedMimeTypes { get; }

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
