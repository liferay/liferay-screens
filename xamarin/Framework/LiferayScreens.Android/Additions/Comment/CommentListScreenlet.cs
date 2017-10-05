using Android.Runtime;
using System;

namespace Com.Liferay.Mobile.Screens.Comment.List
{
    public partial class CommentListScreenlet
    {
        protected override void OnUserAction(string p0, Java.Lang.Object p1, params Java.Lang.Object[] p2)
        {
            throw new NotImplementedException();
        }

        protected override Java.Lang.Object CreateInteractor(string p0)
        {
            throw new NotImplementedException();
        }

        public virtual ICommentListListener Listener
        {
            [Register("getListener", "()Lcom/liferay/mobile/screens/comment/list/CommentListScreenlet;", "GetGetListenerHandler")]
            get;
            [Register("setListener", "(Lcom/liferay/mobile/screens/comment/list/CommentListScreenlet;)V", "GetSetListener_Lcom_liferay_mobile_screens_comment_list_CommentListListener_Handler")]
            set;
        }
    }
}
