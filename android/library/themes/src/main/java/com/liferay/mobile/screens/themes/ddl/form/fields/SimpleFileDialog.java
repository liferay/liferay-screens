package com.liferay.mobile.screens.themes.ddl.form.fields;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Environment;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimpleFileDialog {

	private final Context context;
	private final SimpleFileDialogListener listener;

	private String currentFile = "";
	private String currentDir = "";

	private static final String SD_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath();

	public interface SimpleFileDialogListener {
		public void onFileChosen(String path);
	}

	public SimpleFileDialog(Context context, SimpleFileDialogListener listener) {
		this.context = context;
		this.listener = listener;
	}

	public void chooseFile() {

		final String sdPath = checkDir(SD_DIRECTORY);
		final String defaultPath = checkDir("");

		if (sdPath != null) {
			currentDir = sdPath;
		} else if (defaultPath != null) {
			currentDir = defaultPath;
		} else {
			return;
		}

		final List<String> subdirs = getDirectories(currentDir, sdPath);

		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item, android.R.id.text1, subdirs);


		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		TextView title = new TextView(context);
		title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		title.setText("Open:");
		title.setGravity(Gravity.CENTER_VERTICAL);
		title.setTextColor(context.getResources().getColor(android.R.color.white));
		LinearLayout titleLayout1 = new LinearLayout(context);
		titleLayout1.setOrientation(LinearLayout.VERTICAL);
		titleLayout1.addView(title);
		LinearLayout titleLayout = new LinearLayout(context);
		titleLayout.setOrientation(LinearLayout.VERTICAL);
		final EditText inputText = new EditText(context);
		inputText.setText("");
		titleLayout.addView(inputText);
		dialogBuilder.setView(titleLayout);
		//dialogBuilder.setCustomTitle(R.layout.)
		dialogBuilder.setCustomTitle(titleLayout1);
		dialogBuilder.setCancelable(false);
		dialogBuilder.setPositiveButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				currentFile = inputText.getText() + "";
				listener.onFileChosen(currentDir + "/" + currentFile);
			}
		}).setNegativeButton("Cancel", null);

		class SimpleFileDialogOnClickListener implements DialogInterface.OnClickListener {
			public void onClick(DialogInterface dialog, int item) {
				String m_dir_old = currentDir;
				String sel = "" + ((AlertDialog) dialog).getListView().getAdapter().getItem(item);
				if (sel.charAt(sel.length() - 1) == '/') sel = sel.substring(0, sel.length() - 1);

				// Navigate into the sub-directory
				if (sel.equals("..")) {
					currentDir = currentDir.substring(0, currentDir.lastIndexOf("/"));
				} else {
					currentDir += "/" + sel;
				}
				currentFile = "";

				if ((new File(currentDir).isFile())) // If the selection is a regular currentFile
				{
					currentDir = m_dir_old;
					currentFile = sel;
				}

				subdirs.clear();
				subdirs.addAll(getDirectories(currentDir, sdPath));
				adapter.notifyDataSetChanged();
				inputText.setText(currentFile);
			}
		}

		dialogBuilder.setSingleChoiceItems(adapter, -1, new SimpleFileDialogOnClickListener());

		// Show directory chooser dialog
		dialogBuilder.create().show();
	}

	public String checkDir(String directory) {
		try {
			File file = new File(directory);
			return file.exists() && file.isDirectory() ? file.getCanonicalPath() : null;
		} catch (IOException e) {
			return null;
		}
	}

	private List<String> getDirectories(String dir, String sdPath) {
		List<String> dirs = new ArrayList<String>();
		try {
			File dirFile = new File(dir);

			// if directory is not the base sd card directory add ".." for going up one directory
			if (!currentDir.equals(sdPath)) dirs.add("..");

			if (!dirFile.exists() || !dirFile.isDirectory()) {
				return dirs;
			}

			for (File file : dirFile.listFiles()) {
				if (file.isDirectory()) {
					// Add "/" to directory names to identify them in the list
					dirs.add(file.getName() + "/");
				} else {
					// Add currentFile names to the list if we are doing a currentFile save or currentFile open operation
					dirs.add(file.getName());
				}
			}
		} catch (Exception e) {
		}

		Collections.sort(dirs, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		return dirs;
	}
}