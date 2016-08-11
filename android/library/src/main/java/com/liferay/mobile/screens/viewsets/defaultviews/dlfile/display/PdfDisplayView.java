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
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.dlfile.display.BaseFileDisplayViewModel;
import com.liferay.mobile.screens.dlfile.display.DownloadService;
import com.liferay.mobile.screens.dlfile.display.FileEntry;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.io.File;
import java.io.IOException;

/**
 * @author Sarai Díaz García
 */
public class PdfDisplayView extends LinearLayout implements BaseFileDisplayViewModel, View.OnClickListener {

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
	protected void onFinishInflate() {
		super.onFinishInflate();

		imagePdf = (ImageView) findViewById(R.id.liferay_pdf_renderer);

		progressBar = (ProgressBar) findViewById(R.id.liferay_asset_progress_bar);
		progressText = (TextView) findViewById(R.id.liferay_asset_progress_number);

		goToPage = (EditText) findViewById(R.id.liferay_go_to_page);
		goToPageButton = (Button) findViewById(R.id.liferay_go_to_page_submit);

		previousPage = (Button) findViewById(R.id.liferay_previous_page);
		nextPage = (Button) findViewById(R.id.liferay_next_page);

		title = (TextView) findViewById(R.id.liferay_asset_title);
	}

	@Override
	public void showStartOperation(String actionName) {
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		LiferayLogger.e("Could not load file asset: " + e.getMessage());
	}

	@Override
	public void showFinishOperation(FileEntry fileEntry) {
		this.fileEntry = fileEntry;

		render();
	}

	@Override
	public void showFinishOperation(String actionName) {
		throw new UnsupportedOperationException(
			"showFinishOperation(String) is not supported." + " Use showFinishOperation(FileEntry) instead.");
	}

	//TODO this should go in the screenlet class
	private void render() {
		if (Build.VERSION.SDK_INT >= 21) {
			renderInLollipop();
		} else {
			String server = getResources().getString(R.string.liferay_server);
			getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(server + fileEntry.getUrl())));
		}
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
			}
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

	private void renderInLollipop() {
		previousPage.setOnClickListener(this);
		nextPage.setOnClickListener(this);
		goToPageButton.setOnClickListener(this);

		progressBar.setVisibility(VISIBLE);

		String filePath = getResources().getString(R.string.liferay_server) + fileEntry.getUrl();
		file = new File(getContext().getExternalCacheDir().getPath() + "/" + fileEntry.getTitle());
		if (!file.exists()) {
			Intent intent = new Intent(getContext(), DownloadService.class);
			intent.putExtra(DownloadService.REMOTE_PATH, filePath);
			intent.putExtra(DownloadService.LOCAL_PATH, file.getAbsolutePath());
			intent.putExtra(DownloadService.RESULT_RECEIVER, new DownloadReceiver(new Handler()));
			getContext().startService(intent);
		} else {
			renderPdfInImageView(file);
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void renderPdfPage(int page) {
		Matrix m = imagePdf.getImageMatrix();
		PdfRenderer.Page renderedPage = renderer.openPage(page);
		Bitmap bitmap = Bitmap.createBitmap(renderedPage.getWidth(), renderedPage.getHeight(), Bitmap.Config.ARGB_8888);
		Rect rect = new Rect(0, 0, renderedPage.getWidth(), renderedPage.getHeight());
		renderedPage.render(bitmap, rect, m, PdfRenderer.Page.RENDER_MODE_FOR_PRINT);
		imagePdf.setImageMatrix(m);
		imagePdf.setImageBitmap(bitmap);
		renderedPage.close();

		hideProgressBar();
	}

	private class DownloadReceiver extends ResultReceiver {

		public DownloadReceiver(Handler handler) {
			super(handler);
		}

		protected void onReceiveResult(int resultCode, Bundle resultData) {
			super.onReceiveResult(resultCode, resultData);

			if (resultCode == DownloadService.UPDATE_PROGRESS) {

				int progress = resultData.getInt(DownloadService.FILE_DOWNLOAD_PROGRESS);
				progressText.setText(String.valueOf(progress).concat("%"));
				progressBar.setVisibility(VISIBLE);
				progressText.setVisibility(VISIBLE);
			} else if (resultCode == DownloadService.FINISHED_DOWNLOAD) {
				renderPdfInImageView(file);
			} else {
				//TODO launch error
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void renderPdfInImageView(File file) {
		try {
			renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));
			renderPdfPage(0);
			title.setText(fileEntry.getTitle());
		} catch (IOException e) {
			LiferayLogger.e("Error rendering PDF", e);
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void hideProgressBar() {
		nextPage.setVisibility(VISIBLE);
		nextPage.setEnabled(currentPage != renderer.getPageCount() - 1);
		previousPage.setVisibility(VISIBLE);
		previousPage.setEnabled(currentPage != 0);
		goToPage.setVisibility(VISIBLE);
		goToPageButton.setVisibility(VISIBLE);

		progressBar.setVisibility(GONE);
		progressText.setVisibility(GONE);

		title.setVisibility(VISIBLE);
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}

	private int currentPage;
	private BaseScreenlet screenlet;
	private Button nextPage;
	private Button previousPage;
	private Button goToPageButton;
	private EditText goToPage;
	private File file;
	private FileEntry fileEntry;
	private ImageView imagePdf;
	private PdfRenderer renderer;
	private ProgressBar progressBar;
	private TextView progressText;
	private TextView title;
}
