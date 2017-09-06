using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface RatingScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface RatingScreenlet
    {
        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull DeleteRatingAction;
        [Static]
        [Export("DeleteRatingAction")]
        string DeleteRatingAction { get; }

        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull UpdateRatingAction;
        [Static]
        [Export("UpdateRatingAction")]
        string UpdateRatingAction { get; }

        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadRatingsAction;
        [Static]
        [Export("LoadRatingsAction")]
        string LoadRatingsAction { get; }

        // @property (nonatomic) int64_t entryId;
        [Export("entryId")]
        long EntryId { get; set; }

        // @property (copy, nonatomic) NSString * _Nonnull className;
        [Export("className")]
        string ClassName { get; set; }

        // @property (nonatomic) int64_t classPK;
        [Export("classPK")]
        long ClassPK { get; set; }

        // @property (nonatomic) int32_t ratingsGroupCount;
        [Export("ratingsGroupCount")]
        int RatingsGroupCount { get; set; }

        // @property (nonatomic) BOOL autoLoad;
        [Export("autoLoad")]
        bool AutoLoad { get; set; }

        // @property (nonatomic) BOOL editable;
        [Export("editable")]
        bool Editable { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        [Wrap("WeakRatingDisplayDelegate")]
        [NullAllowed]
        RatingScreenletDelegate RatingDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<RatingScreenletDelegate> _Nullable ratingDisplayDelegate;
        [NullAllowed, Export("ratingDisplayDelegate", ArgumentSemantic.Strong)]
        NSObject WeakRatingDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<RatingViewModel> _Nullable viewModel;
        [NullAllowed, Export("viewModel", ArgumentSemantic.Strong)]
        IRatingViewModel ViewModel { get; }

        // -(void)prepareForInterfaceBuilder;
        [Export("prepareForInterfaceBuilder")]
        void PrepareForInterfaceBuilder();

        // -(void)onPreCreate;
        [Export("onPreCreate")]
        void OnPreCreate();

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
        [Export("createInteractorWithName:sender:")]
        [return: NullAllowed]
        Interactor CreateInteractorWithName(string name, [NullAllowed] NSObject sender);

        // -(BOOL)performDefaultAction __attribute__((warn_unused_result));
        [Export("performDefaultAction")]
        bool PerformDefaultAction();

        // -(BOOL)loadRatings;
        [Export("loadRatings")]
        bool LoadRatings();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
