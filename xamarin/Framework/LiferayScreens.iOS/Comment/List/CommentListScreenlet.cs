using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface CommentListScreenlet : BaseListScreenlet <CommentDisplayScreenletDelegate, BaseScreenletDelegate>
    [BaseType(typeof(BaseListScreenlet))]
    interface CommentListScreenlet : CommentDisplayScreenletDelegate, BaseScreenletDelegate
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

        // @property (nonatomic) BOOL editable;
        [Export("editable")]
        bool Editable { get; set; }

        // @property (readonly, nonatomic, strong) id<CommentListViewModel> _Nullable viewModel;
        [NullAllowed, Export("viewModel", ArgumentSemantic.Strong)]
        ICommentListViewModel ViewModel { get; }

        [Wrap("WeakCommentListDelegate")]
        [NullAllowed]
        CommentListScreenletDelegate CommentListDelegate { get; }

        // @property (readonly, nonatomic, strong) id<CommentListScreenletDelegate> _Nullable commentListDelegate;
        [NullAllowed, Export("commentListDelegate", ArgumentSemantic.Strong)]
        NSObject WeakCommentListDelegate { get; }

        // -(void)addComment:(Comment * _Nonnull)comment;
        [Export("addComment:")]
        void AddComment(Comment comment);

        // -(void)deleteComment:(Comment * _Nonnull)comment;
        [Export("deleteComment:")]
        void DeleteComment(Comment comment);

        // -(void)updateComment:(Comment * _Nonnull)comment;
        [Export("updateComment:")]
        void UpdateComment(Comment comment);

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(BaseListPageLoadInteractor * _Nonnull)createPageLoadInteractorWithPage:(NSInteger)page computeRowCount:(BOOL)computeRowCount __attribute__((warn_unused_result));
        //[Export("createPageLoadInteractorWithPage:computeRowCount:")]
        //BaseListPageLoadInteractor CreatePageLoadInteractorWithPage(nint page, bool computeRowCount);

        // -(void)onLoadPageErrorWithPage:(NSInteger)page error:(NSError * _Nonnull)error;
        [Export("onLoadPageErrorWithPage:error:")]
        void OnLoadPageErrorWithPage(nint page, NSError error);

        // -(void)onLoadPageResultWithPage:(NSInteger)page rows:(NSArray * _Nonnull)rows rowCount:(NSInteger)rowCount;
        [Export("onLoadPageResultWithPage:rows:rowCount:")]
        //[Verify(StronglyTypedNSArray)]
        void OnLoadPageResultWithPage(nint page, NSObject[] rows, nint rowCount);

        // -(void)onSelectedRow:(id _Nonnull)row;
        [Export("onSelectedRow:")]
        void OnSelectedRow(NSObject row);

        // -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onCommentDeleted:(Comment * _Nonnull)comment;
        [Export("screenlet:onCommentDeleted:")]
        void Screenlet(CommentDisplayScreenlet screenlet, Comment comment);

        // -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onDeleteComment:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error;
        [Export("screenlet:onDeleteComment:onError:")]
        void Screenlet(CommentDisplayScreenlet screenlet, Comment comment, NSError error);

        // -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onCommentUpdated:(Comment * _Nonnull)comment;
        [Export("screenlet:onCommentUpdated:")]
        void ScreenletCommentUpdate(CommentDisplayScreenlet screenlet, Comment comment);

        // -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onUpdateComment:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error;
        [Export("screenlet:onUpdateComment:onError:")]
        void ScreenletCommentUpdateError(CommentDisplayScreenlet screenlet, Comment comment, NSError error);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
