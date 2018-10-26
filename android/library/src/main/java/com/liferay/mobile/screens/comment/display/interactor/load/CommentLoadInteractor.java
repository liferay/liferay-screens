package com.liferay.mobile.screens.comment.display.interactor.load;

import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.connector.ScreensCommentConnector;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentLoadInteractor extends BaseCacheReadInteractor<CommentDisplayInteractorListener, CommentEvent> {

    @Override
    public CommentEvent execute(Object... args) throws Exception {

        long commentId = (long) args[0];

        validate(commentId);

        ScreensCommentConnector connector = ServiceProvider.getInstance().getScreensCommentConnector(getSession());
        JSONObject jsonObject = connector.getComment(commentId);
        return new CommentEvent(jsonObject);
    }

    @Override
    public void onSuccess(CommentEvent event) {
        Map<String, Object> map;

        try {
            map = JSONUtil.toMap(event.getJSONObject());
        } catch (JSONException e) {
            event.setException(e);
            onFailure(event);
            return;
        }

        CommentEntry commentEntry = new CommentEntry(map);
        getListener().onLoadCommentSuccess(commentEntry);
    }

    @Override
    public void onFailure(CommentEvent event) {
        getListener().error(event.getException(), CommentDisplayScreenlet.LOAD_COMMENT_ACTION);
    }

    @Override
    protected String getIdFromArgs(Object... args) {
        long commentId = (long) args[0];
        return String.valueOf(commentId);
    }

    private void validate(long commentId) {
        if (commentId <= 0) {
            throw new IllegalArgumentException("commentId cannot be 0 or negative");
        }
    }
}
