package com.liferay.mobile.screens.viewsets.defaultviews.assetdisplay;

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
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.assetdisplay.model.FileEntry;
import com.liferay.mobile.screens.assetdisplay.service.DownloadService;
import com.liferay.mobile.screens.assetdisplay.view.PdfDisplayViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.io.File;
import java.io.IOException;

/**
 * @author Sarai Díaz García
 */
public class PdfDisplayView extends LinearLayout implements PdfDisplayViewModel {

	public PdfDisplayView(Context context) {
		super(context);
	}

	public PdfDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PdfDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void showStartOperation(String actionName) {
	}

	@Override
	public void showFinishOperation(String actionName) {
		throw new UnsupportedOperationException("showFinishOperation(String) is not supported."
			+ " Use showFinishOperation(FileEntry) instead.");
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		LiferayLogger.e("Could not load file asset: " + e.getMessage());
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return _screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this._screenlet = screenlet;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_btnOpenPdf = (Button) findViewById(R.id.liferay_pdf);
		_imagePdf = (ImageView) findViewById(R.id.liferay_pdf_renderer);
		_progressBar = (ProgressBar) findViewById(R.id.liferay_asset_progress_bar);
		_editPage = (EditText) findViewById(R.id.liferay_edit_pdf_page);
		_btnGo = (Button) findViewById(R.id.liferay_btn_go_page);
	}

	@Override
	public void showFinishOperation(FileEntry fileEntry) {
		_fileEntry = fileEntry;
		_btnPrevious = (Button) findViewById(R.id.liferay_btn_previous);
		_btnNext = (Button) findViewById(R.id.liferay_btn_next);

		_btnOpenPdf.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				render();
			}
		});

		_btnPrevious.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				_currentPage--;
				changePdfPage();
			}
		});

		_btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				_currentPage++;
				changePdfPage();
			}
		});

		_btnGo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!_editPage.getText().toString().isEmpty()) {
					setCurrentPage(Integer.parseInt(_editPage.getText().toString()));
					_currentPage--;
					changePdfPage();
				}
			}
		});
	}

	public void setCurrentPage(int currentPage) {
		this._currentPage = currentPage;
	}

	private class DownloadReceiver extends ResultReceiver {
		public DownloadReceiver(Handler handler) {
			super(handler);
		}

		protected void onReceiveResult(int resultCode, Bundle resultData) {
			super.onReceiveResult(resultCode, resultData);
			if (resultCode == DownloadService.UPDATE_PROGRESS) {
				_progress = resultData.getInt("progress");
				_progressBar.setVisibility(VISIBLE);
				if (_progress == 100) {
					renderPdfInImageView(_file);
					updateView();
				}
			}
		}
	}

	private void changePdfPage() {
		if (_currentPage < 0) {
			_currentPage = 0;
		} else if (_currentPage > _renderer.getPageCount() - 1) {
			_currentPage = _renderer.getPageCount() - 1;
		}

		Matrix m = _imagePdf.getImageMatrix();
		PdfRenderer.Page page = _renderer.openPage(_currentPage);
		Bitmap _bitmap = Bitmap.createBitmap(_imagePdf.getMeasuredWidth(), _imagePdf.getMeasuredHeight(),
			Bitmap.Config.ARGB_8888);
		Rect rect = new Rect(0, 0, page.getWidth(), page.getHeight());
		page.render(_bitmap, rect, m, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
		_imagePdf.setImageMatrix(m);
		_imagePdf.setImageBitmap(_bitmap);
		page.close();
		updateView();
	}

	private void render() {
		if (Build.VERSION.SDK_INT >= 21) {
			_progressBar.setVisibility(VISIBLE);
			String filePath = getResources().getString(R.string.liferay_server) + _fileEntry.getUrl();
			_file = new File(getContext().getExternalCacheDir().getPath() + "/" + _fileEntry.getTitle());
			if (!_file.exists()) {
				Intent intent = new Intent(getContext(), DownloadService.class);
				intent.putExtra("url", filePath);
				intent.putExtra("cacheDir", getContext().getExternalCacheDir().getPath());
				intent.putExtra("filename", _fileEntry.getTitle());
				intent.putExtra("receiver", new DownloadReceiver(new Handler()));
				getContext().startService(intent);
			} else {
				renderPdfInImageView(_file);
			}
		} else {
			_intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse(getResources().getString(R.string.liferay_server) + _fileEntry.getUrl()));
			getContext().startActivity(_intent);
		}
	}

	private void renderPdfInImageView(File file) {
		try {
			_renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));
		} catch (IOException e) {
			e.getMessage();
		}

		changePdfPage();
	}

	private void updateView() {
		_btnNext.setVisibility(VISIBLE);
		_btnPrevious.setVisibility(VISIBLE);
		_editPage.setVisibility(VISIBLE);
		_btnGo.setVisibility(VISIBLE);
		_btnOpenPdf.setVisibility(GONE);
		_progressBar.setVisibility(GONE);
	}

	private int _currentPage = 0;
	private int _progress;
	private BaseScreenlet _screenlet;
	private Button _btnOpenPdf;
	private Button _btnNext;
	private Button _btnPrevious;
	private Button _btnGo;
	private EditText _editPage;
	private File _file;
	private FileEntry _fileEntry;
	private ImageView _imagePdf;
	private Intent _intent;
	private PdfRenderer _renderer;
	private ProgressBar _progressBar;
}
