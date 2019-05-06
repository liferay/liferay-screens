package com.liferay.mobile.screens.comment.display.interactor;

import com.liferay.mobile.screens.base.list.interactor.ListEvent;
import com.liferay.mobile.screens.comment.CommentEntry;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentEvent extends ListEvent<CommentEntry> {

    private String className;
    private long classPK;
    private long commentId;
    private String body;
    private CommentEntry commentEntry;

    public CommentEvent() {
        super();
    }

    public CommentEvent(long commentId, String className, long classPK, String body) {
        this(commentId, body);
        this.className = className;
        this.classPK = classPK;
    }

    public CommentEvent(long commentId, String body, CommentEntry commentEntry) {
        this(commentId, body);
        this.commentEntry = commentEntry;
    }

    public CommentEvent(long commentId, String body) {
        this.commentId = commentId;
        this.body = body;
    }

    public CommentEvent(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public String getListKey() {
        return String.valueOf(commentEntry.getCommentId());
    }

    @Override
    public CommentEntry getModel() {
        return commentEntry;
    }

    public CommentEntry getCommentEntry() {
        return commentEntry;
    }

    public void setCommentEntry(CommentEntry commentEntry) {
        this.commentEntry = commentEntry;
    }

    public String getClassName() {
        return className;
    }

    public long getClassPK() {
        return classPK;
    }

    public long getCommentId() {
        return commentId;
    }

    public String getBody() {
        return body;
    }
}
