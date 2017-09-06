using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface CommentAddScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface CommentAddScreenlet
    {
        // @property (copy, nonatomic) NSString * _Nonnull className;
        [Export("className")]
        string ClassName { get; set; }

        // @property (nonatomic) int64_t classPK;
        [Export("classPK")]
        long ClassPK { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        [Wrap("WeakCommentAddDelegate")]
        [NullAllowed]
        CommentAddScreenletDelegate CommentAddDelegate { get; }

        // @property (readonly, nonatomic, strong) id<CommentAddScreenletDelegate> _Nullable commentAddDelegate;
        [NullAllowed, Export("commentAddDelegate", ArgumentSemantic.Strong)]
        NSObject WeakCommentAddDelegate { get; }

        // @property (readonly, nonatomic, strong) id<CommentAddViewModel> _Nonnull viewModel;
        [Export("viewModel", ArgumentSemantic.Strong)]
        ICommentAddViewModel ViewModel { get; }

        // @property (nonatomic, strong) Comment * _Nullable comment;
        [NullAllowed, Export("comment", ArgumentSemantic.Strong)]
        Comment Comment { get; set; }

        // -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
        [Export("createInteractorWithName:sender:")]
        [return: NullAllowed]
        Interactor CreateInteractorWithName(string name, [NullAllowed] NSObject sender);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
