using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface RatingView_default_emojis : BaseScreenletView <RatingViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface RatingView_default_emojis : IRatingViewModel
    {
        // @property (readonly, nonatomic) int32_t defaultRatingsGroupCount;
        [Export("defaultRatingsGroupCount")]
        int DefaultRatingsGroupCount { get; }

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
        [Export("createProgressPresenter")]
        ProgressPresenter CreateProgressPresenter();

        // @property (nonatomic, strong) RatingEntry * _Nullable ratingEntry;
        [NullAllowed, Export("ratingEntry", ArgumentSemantic.Strong)]
        RatingEntry RatingEntry { get; set; }

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
