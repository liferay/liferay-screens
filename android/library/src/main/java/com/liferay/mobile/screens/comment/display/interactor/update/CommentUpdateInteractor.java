package com.liferay.mobile.screens.comment.display.interactor.update;

import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.service.v70.ScreenscommentService;
import com.liferay.mobile.screens.util.JSONUtil;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentUpdateInteractor
  extends BaseCacheWriteInteractor<CommentDisplayInteractorListener, CommentEvent> {

  @Override
  public CommentEvent execute(CommentEvent event) throws Exception {
    String className = event.getClassName();
    long classPK = event.getClassPK();
    long commentId = event.getCommentId();
    String newBody = event.getBody();

    validate(event.getGroupId(), className, classPK, commentId, newBody);

    ScreenscommentService service = new ScreenscommentService(getSession());

    JSONObject jsonObject = service.updateComment(commentId, newBody);

    CommentEntry commentEntry = new CommentEntry(JSONUtil.toMap(jsonObject));
    return new CommentEvent(commentId, className, classPK, newBody, commentEntry);
  }

  @Override
  public void onSuccess(CommentEvent event) throws Exception {
    getListener().onUpdateCommentSuccess(event.getCommentEntry());
  }

  @Override
  protected void onFailure(CommentEvent event) {
    getListener().error(event.getException(), CommentDisplayScreenlet.UPDATE_COMMENT_ACTION);
  }

  protected void validate(long groupId, String className, long classPK, long commentId,
    String newBody) {

    if (groupId <= 0) {
      throw new IllegalArgumentException("groupId must be greater than 0");
    } else if (className.isEmpty()) {
      throw new IllegalArgumentException("className cannot be empty");
    } else if (classPK <= 0) {
      throw new IllegalArgumentException("classPK must be greater than 0");
    } else if (commentId <= 0) {
      throw new IllegalArgumentException("commentId cannot be 0 or negative");
    } else if (newBody.isEmpty()) {
      throw new IllegalArgumentException("new body for comment cannot be empty");
    }
  }
}
