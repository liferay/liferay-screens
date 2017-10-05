using Android.Runtime;
using Com.Liferay.Mobile.Screens.Base.Interactor.Listener;
using Com.Liferay.Mobile.Screens.Base.List;
using System;

namespace Com.Liferay.Mobile.Screens.Comment.List
{
    [Register("com/liferay/mobile/screens/comment/list/CommentListListener", "", "Com.Liferay.Mobile.Screens.Comment.List.ICommentListListenerInvoker")]
    public interface ICommentListListener : IBaseListListener, IBaseCacheListener, IJavaObject, IDisposable
    {
        //
        // Methods
        //
        [Register("onDeleteCommentSuccess", "(Lcom/liferay/mobile/screens/comment/CommentEntry;)V", "GetOnDeleteCommentSuccess_Lcom_liferay_mobile_screens_comment_CommentEntry_Handler:Com.Liferay.Mobile.Screens.Comment.List.ICommentListListenerInvoker, LiferayScreens_Android")]
        void OnDeleteCommentSuccess(CommentEntry p0);

        [Register("onUpdateCommentSuccess", "(Lcom/liferay/mobile/screens/comment/CommentEntry;)V", "GetOnUpdateCommentSuccess_Lcom_liferay_mobile_screens_comment_CommentEntry_Handler:Com.Liferay.Mobile.Screens.Comment.List.ICommentListListenerInvoker, LiferayScreens_Android")]
        void OnUpdateCommentSuccess(CommentEntry p0);
    }
}


namespace Com.Liferay.Mobile.Screens.Comment
{
    public partial class CommentEntry { }
}