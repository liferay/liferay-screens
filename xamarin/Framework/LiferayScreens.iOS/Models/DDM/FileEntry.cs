using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface FileEntry : Asset
    [BaseType(typeof(Asset))]
    interface FileEntry
    {
        // @property (readonly, copy, nonatomic) NSString * _Nonnull url;
        [Export("url")]
        string Url { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nullable fileExtension;
        [NullAllowed, Export("fileExtension")]
        string FileExtension { get; }

        // @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull fileEntry;
        [Export("fileEntry", ArgumentSemantic.Copy)]
        NSDictionary<NSString, NSObject> FileEntryDict { get; }

        // @property (readonly, nonatomic) int64_t fileEntryId;
        [Export("fileEntryId")]
        long FileEntryId { get; }

        // -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
        [Export("initWithAttributes:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSDictionary<NSString, NSObject> attributes);
    }
}
