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

/**
 * @author Sarai Díaz García
 */
public class AudioDisplayView extends BaseFileDisplayView {

    private VideoView audioView;
    private TextView title;
    private TextView message;

    public AudioDisplayView(Context context) {
        super(context);
    }

    public AudioDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AudioDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AudioDisplayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {
        super.showFailedOperation(actionName, e);
        message.setText(R.string.file_error);
    }

    @Override
    public void loadFileEntry(String url) {
        loadAudio(url);
        loadPrepareListener();
        loadErrorListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        audioView = findViewById(R.id.liferay_audio_asset);
        title = findViewById(R.id.liferay_audio_title);
        message = findViewById(R.id.liferay_audio_message);
    }

    private void loadAudio(String url) {
        progressBar.setVisibility(VISIBLE);
        title.setText(fileEntry.getTitle());
        audioView.setVideoPath(url);
        audioView.setMediaController(new MediaController(getContext()));
        audioView.setZOrderOnTop(true);
        audioView.requestFocus();
        audioView.start();
    }

    private void loadPrepareListener() {
        audioView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(INVISIBLE);
            }
        });
    }

    private void loadErrorListener() {
        audioView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                progressBar.setVisibility(GONE);
                message.setText(R.string.file_error);
                return false;
            }
        });
    }
}
