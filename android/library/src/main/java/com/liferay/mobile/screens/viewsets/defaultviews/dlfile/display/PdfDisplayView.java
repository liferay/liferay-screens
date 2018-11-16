package com.liferay.mobile.screens.viewsets.defaultviews.dlfile.display;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.dlfile.display.pdf.OnSwipeTouchListener;
import com.liferay.mobile.screens.dlfile.display.pdf.SwipeListener;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.io.File;
import java.io.IOException;

/**
 * @author Sarai Díaz García
 */
public class PdfDisplayView extends BaseFileDisplayView implements View.OnClickListener {

    private int currentPage;
    private Button nextPage;
    private Button previousPage;
    private Button goToPageButton;
    private EditText goToPage;
    private LinearLayout linearLayoutButtons;
    private ImageView imagePdf;
    private PdfRenderer renderer;
    private ProgressBar progressBarHorizontal;
    private TextView progressText;
    private TextView title;
    private Matrix matrix;

    public PdfDisplayView(Context context) {
        super(context);
    }

    public PdfDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PdfDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PdfDisplayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void loadFileEntry(String url) {
        render(url);
    }

    @Override
    public void renderDownloadProgress(int progress) {
        progressText.setText(String.valueOf(progress).concat("%"));
        progressBarHorizontal.setProgress(progress);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.liferay_previous_page) {
            changeCurrentPage(-1);
        } else if (v.getId() == R.id.liferay_next_page) {
            changeCurrentPage(+1);
        } else if (v.getId() == R.id.liferay_go_to_page_submit) {
            String number = goToPage.getText().toString();
            if (!number.isEmpty()) {
                changeCurrentPage(Integer.parseInt(number) - 1 - currentPage);
                closeKeyboard(v);
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        imagePdf = findViewById(R.id.liferay_pdf_renderer);

        progressText = findViewById(R.id.liferay_asset_progress_number);
        progressBarHorizontal = findViewById(R.id.liferay_asset_progress_horizontal);

        goToPage = findViewById(R.id.liferay_go_to_page);
        goToPageButton = findViewById(R.id.liferay_go_to_page_submit);

        previousPage = findViewById(R.id.liferay_previous_page);
        nextPage = findViewById(R.id.liferay_next_page);

        linearLayoutButtons = findViewById(R.id.liferay_linear_buttons);

        title = findViewById(R.id.liferay_asset_title);
    }

    protected void onPageRendered() {
        hideProgressBar();
    }

    //TODO this should go in the screenlet class
    private void render(String url) {
        if (Build.VERSION.SDK_INT >= 21) {
            renderInLollipop(url);
        } else {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void changeCurrentPage(int i) {
        currentPage += i;

        if (currentPage < 0) {
            currentPage = 0;
        } else if (currentPage > renderer.getPageCount() - 1) {
            currentPage = renderer.getPageCount() - 1;
        }

        renderPdfPage(currentPage);
    }

    private void renderInLollipop(String url) {
        previousPage.setOnClickListener(this);
        nextPage.setOnClickListener(this);
        goToPageButton.setOnClickListener(this);

        renderPdfInImageView(url);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void renderPdfPage(int page) {
        PdfRenderer.Page renderedPage = renderer.openPage(page);
        Bitmap bitmap = Bitmap.createBitmap(renderedPage.getWidth(), renderedPage.getHeight(), Bitmap.Config.ARGB_8888);
        Rect rect = new Rect(0, 0, renderedPage.getWidth(), renderedPage.getHeight());
        renderedPage.render(bitmap, rect, matrix, PdfRenderer.Page.RENDER_MODE_FOR_PRINT);
        imagePdf.setImageMatrix(matrix);
        imagePdf.setImageBitmap(bitmap);
        imagePdf.setOnTouchListener(new OnSwipeTouchListener(getContext(), new SwipeListener() {
            @Override
            public void onSwipeLeft() {
                changeCurrentPage(+1);
            }

            @Override
            public void onSwipeRight() {
                changeCurrentPage(-1);
            }
        }));
        renderedPage.close();

        onPageRendered();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void renderPdfInImageView(String url) {
        progressBar.setVisibility(VISIBLE);
        try {
            renderer = new PdfRenderer(ParcelFileDescriptor.open(new File(url), ParcelFileDescriptor.MODE_READ_ONLY));
            matrix = imagePdf.getImageMatrix();
            renderPdfPage(0);
            title.setText(fileEntry.getTitle());
        } catch (IOException e) {
            LiferayLogger.e("Error rendering PDF", e);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideProgressBar() {
        linearLayoutButtons.setVisibility(VISIBLE);
        nextPage.setEnabled(currentPage != renderer.getPageCount() - 1);
        previousPage.setEnabled(currentPage != 0);

        progressBarHorizontal.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        progressText.setVisibility(GONE);

        title.setVisibility(VISIBLE);
    }

    private void closeKeyboard(View view) {
        InputMethodManager inputManager =
            (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
