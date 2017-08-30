using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface FileDisplayScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface FileDisplayScreenlet
    {
        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadFileAction;
        [Static]
        [Export("LoadFileAction")]
        string LoadFileAction { get; }

        // @property (nonatomic) int64_t assetEntryId;
        [Export("assetEntryId")]
        long AssetEntryId { get; set; }

        // @property (copy, nonatomic) NSString * _Nonnull className;
        [Export("className")]
        string ClassName { get; set; }

        // @property (nonatomic) int64_t classPK;
        [Export("classPK")]
        long ClassPK { get; set; }

        // @property (nonatomic) BOOL autoLoad;
        [Export("autoLoad")]
        bool AutoLoad { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        [Wrap("WeakFileDisplayDelegate")]
        [NullAllowed]
        FileDisplayScreenletDelegate FileDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<FileDisplayScreenletDelegate> _Nullable fileDisplayDelegate;
        [NullAllowed, Export("fileDisplayDelegate", ArgumentSemantic.Strong)]
        NSObject WeakFileDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<FileDisplayViewModel> _Nullable fileDisplayViewModel;
        [NullAllowed, Export("fileDisplayViewModel", ArgumentSemantic.Strong)]
        FileDisplayViewModel FileDisplayViewModel { get; }

        // @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull supportedMimeTypes;
        [Export("supportedMimeTypes", ArgumentSemantic.Copy)]
        string[] SupportedMimeTypes { get; }

        // @property (nonatomic, strong) FileEntry * _Nullable fileEntry;
        [NullAllowed, Export("fileEntry", ArgumentSemantic.Strong)]
        FileEntry FileEntry { get; set; }

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
        [Export("createInteractorWithName:sender:")]
        [return: NullAllowed]
        Interactor CreateInteractorWithName(string name, [NullAllowed] NSObject sender);

        // -(BOOL)load;
        [Export("load")]
        bool Load();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
