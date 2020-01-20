/*
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.liferay.mobile.screens.dlfile.display.pdf.OnSwipeTouchListener;
import com.liferay.mobile.screens.dlfile.display.pdf.SwipeListener;
import com.squareup.picasso.Picasso;

/**
 * @author Sarai Díaz García
 */
public class DetailMediaGalleryActivity extends AppCompatActivity implements SwipeListener {

    public int imgSrcPosition;
    public String[] allImgSrc;
    public ImageView detailedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_detailed_default);

        detailedImageView = findViewById(R.id.detailed_image);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("imgSrcPosition") && intent.hasExtra("allImgSrc")) {
            allImgSrc = intent.getStringArrayExtra("allImgSrc");
            imgSrcPosition = intent.getIntExtra("imgSrcPosition", -1);
            String imgSrc = allImgSrc[imgSrcPosition];

            loadImage(imgSrc);
        }

        detailedImageView.setOnTouchListener(new OnSwipeTouchListener(this, this));
    }

    @Override
    public void onSwipeLeft() {
        loadImage(allImgSrc[imgSrcPosition + 1]);
        imgSrcPosition++;
    }

    @Override
    public void onSwipeRight() {
        loadImage(allImgSrc[imgSrcPosition - 1]);
        imgSrcPosition--;
    }

    private void loadImage(String url) {
        Picasso.with(getApplicationContext()).load(url).into(detailedImageView);
    }
}
