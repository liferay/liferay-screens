package com.liferay.mobile.screens.viewsets.defaultviews.dlfile.display;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.dlfile.display.video.VideoDisplayScreenlet;

/**
 * @author Sarai Díaz García
 */
public class VideoDisplayView extends BaseFileDisplayView {

    private VideoView videoView;
    private TextView message;

    public VideoDisplayView(Context context) {
        super(context);
    }

    public VideoDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VideoDisplayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {
        super.showFailedOperation(actionName, e);
        message.setText(R.string.file_error);
    }

    @Override
    public void loadFileEntry(String url) {
        loadPrepareListener();
        loadErrorListener();
        loadCompletionListener();
        loadVideo(url);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        videoView = findViewById(R.id.liferay_video_asset);
        message = findViewById(R.id.liferay_video_message);
    }

    private void loadVideo(String url) {
        progressBar.setVisibility(GONE);
        videoView.setVisibility(VISIBLE);
        videoView.setVideoPath(url);
        videoView.setMediaController(new MediaController(getContext()));
        videoView.requestFocus();
        videoView.start();
    }

    private void loadPrepareListener() {
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(GONE);
                getScreenlet().onVideoPrepared();
            }
        });
    }

    private void loadErrorListener() {
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                progressBar.setVisibility(GONE);
                message.setText(R.string.file_error);
                getScreenlet().onVideoError(new MediaPlayerException(what, extra));
                return false;
            }
        });
    }

    private void loadCompletionListener() {
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                getScreenlet().onVideoCompleted();
            }
        });
    }

    public VideoDisplayScreenlet getScreenlet() {
        return (VideoDisplayScreenlet) super.getScreenlet();
    }
}
