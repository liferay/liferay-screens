using CoreGraphics;
using ObjCRuntime;
using Foundation;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface UserPortraitScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface UserPortraitScreenlet
    {
        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadPortrait;
        [Static]
        [Export("LoadPortrait")]
        string LoadPortrait { get; }

        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull UploadPortrait;
        [Static]
        [Export("UploadPortrait")]
        string UploadPortrait { get; }

        // @property (nonatomic) CGFloat borderWidth;
        [Export("borderWidth")]
        nfloat BorderWidth { get; set; }

        // @property (nonatomic, strong) UIColor * _Nullable borderColor;
        [NullAllowed, Export("borderColor", ArgumentSemantic.Strong)]
        UIColor BorderColor { get; set; }

        // @property (nonatomic) BOOL editable;
        [Export("editable")]
        bool Editable { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        // @property (nonatomic) BOOL autoLoad;
        [Export("autoLoad")]
        bool AutoLoad { get; set; }

        [Wrap("WeakUserPortraitDelegate")]
        [NullAllowed]
        UserPortraitScreenletDelegate UserPortraitDelegate { get; }

        // @property (readonly, nonatomic, strong) id<UserPortraitScreenletDelegate> _Nullable userPortraitDelegate;
        [NullAllowed, Export("userPortraitDelegate", ArgumentSemantic.Strong)]
        NSObject WeakUserPortraitDelegate { get; }

        // @property (readonly, nonatomic, strong) id<UserPortraitViewModel> _Nonnull viewModel;
        [Export("viewModel", ArgumentSemantic.Strong)]
        IUserPortraitViewModel ViewModel { get; }

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
        [Export("createInteractorWithName:sender:")]
        [return: NullAllowed]
        Interactor CreateInteractorWithName(string name, [NullAllowed] NSObject sender);

        // -(BOOL)loadLoggedUserPortrait;
        [Export("loadLoggedUserPortrait")]
        bool LoadLoggedUserPortrait();

        // -(BOOL)loadWithPortraitId:(int64_t)portraitId uuid:(NSString * _Nonnull)uuid male:(BOOL)male;
        [Export("loadWithPortraitId:uuid:male:")]
        bool LoadWithPortraitId(long portraitId, string uuid, bool male);

        // -(BOOL)loadWithUserId:(int64_t)userId;
        [Export("loadWithUserId:")]
        bool LoadWithUserId(long userId);

        // -(BOOL)loadWithCompanyId:(int64_t)companyId emailAddress:(NSString * _Nonnull)emailAddress;
        [Export("loadWithCompanyId:emailAddress:")]
        bool LoadWithCompanyIdEmailAddress(long companyId, string emailAddress);

        // -(BOOL)loadWithCompanyId:(int64_t)companyId screenName:(NSString * _Nonnull)screenName;
        [Export("loadWithCompanyId:screenName:")]
        bool LoadWithCompanyIdScreenName(long companyId, string screenName);

        // -(void)loadPlaceholder;
        [Export("loadPlaceholder")]
        void LoadPlaceholder();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }

}
