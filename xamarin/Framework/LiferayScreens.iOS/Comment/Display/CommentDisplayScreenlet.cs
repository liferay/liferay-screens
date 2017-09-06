using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface CommentDisplayScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface CommentDisplayScreenlet
    {
        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull DeleteAction;
        [Static]
        [Export("DeleteAction")]
        string DeleteAction { get; }

        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull UpdateAction;
        [Static]
        [Export("UpdateAction")]
        string UpdateAction { get; }

        // @property (nonatomic) int64_t commentId;
        [Export("commentId")]
        long CommentId { get; set; }

        // @property (nonatomic) BOOL autoLoad;
        [Export("autoLoad")]
        bool AutoLoad { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        // @property (nonatomic) BOOL editable;
        [Export("editable")]
        bool Editable { get; set; }

        [Wrap("WeakCommentDisplayDelegate")]
        [NullAllowed]
        CommentDisplayScreenletDelegate CommentDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<CommentDisplayScreenletDelegate> _Nullable commentDisplayDelegate;
        [NullAllowed, Export("commentDisplayDelegate", ArgumentSemantic.Strong)]
        NSObject WeakCommentDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<CommentDisplayViewModel> _Nonnull viewModel;
        [Export("viewModel", ArgumentSemantic.Strong)]
        ICommentDisplayViewModel ViewModel { get; }

        // @property (nonatomic, strong) Comment * _Nullable comment;
        [NullAllowed, Export("comment", ArgumentSemantic.Strong)]
        Comment Comment { get; set; }

        // -(void)load;
        [Export("load")]
        void Load();

        // -(void)deleteComment;
        [Export("deleteComment")]
        void DeleteComment();

        // -(void)editComment;
        [Export("editComment")]
        void EditComment();

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

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
