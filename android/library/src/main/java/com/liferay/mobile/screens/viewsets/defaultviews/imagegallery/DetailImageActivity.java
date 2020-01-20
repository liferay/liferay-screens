package com.liferay.mobile.screens.viewsets.defaultviews.imagegallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.context.PicassoScreens;
import com.liferay.mobile.screens.dlfile.display.pdf.OnSwipeTouchListener;
import com.liferay.mobile.screens.dlfile.display.pdf.SwipeListener;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;
import com.squareup.picasso.Picasso;

/**
 * @author Víctor Galán Grande
 */
public class DetailImageActivity extends AppCompatActivity implements SwipeListener {

    public static final String GALLERY_SCREENLET_IMAGE_DETAILED = "GALLERY_SCREENLET_IMAGE_DETAILED";

    public int imgSrcPosition;
    public String[] allImgSrc;
    public ImageView detailedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_detailed_default);

        Intent intent = getIntent();
        detailedImageView = findViewById(R.id.detailed_image);

        if (intent != null) {

            if (intent.hasExtra(GALLERY_SCREENLET_IMAGE_DETAILED)) {

                ImageEntry imageEntry = (ImageEntry) intent.getExtras().get(GALLERY_SCREENLET_IMAGE_DETAILED);
                PicassoScreens.load(imageEntry.getImageUrl()).into(detailedImageView);
            } else if (intent.hasExtra("imgSrcPosition")) {

                if (intent.hasExtra("allImgSrc")) {
                    allImgSrc = intent.getStringArrayExtra("allImgSrc");
                }

                imgSrcPosition = intent.getIntExtra("imgSrcPosition", -1);
                String imgSrc = allImgSrc[imgSrcPosition];
                Picasso.with(getApplicationContext()).load(imgSrc).into(detailedImageView);
                Toast.makeText(DetailImageActivity.this, imgSrc, Toast.LENGTH_SHORT).show();
            }

            imgSrcPosition = intent.getIntExtra("imgSrcPosition", -1);
            String imgSrc = allImgSrc[imgSrcPosition];
            Picasso.with(getApplicationContext()).load(imgSrc).into(detailedImageView);
        }

        detailedImageView.setOnTouchListener(new OnSwipeTouchListener(this, this));
    }

    @Override
    public void onSwipeLeft() {
        PicassoScreens.load(allImgSrc[imgSrcPosition + 1]).into(detailedImageView);
        imgSrcPosition++;
    }

    @Override
    public void onSwipeRight() {
        PicassoScreens.load(allImgSrc[imgSrcPosition - 1]).into(detailedImageView);
        imgSrcPosition--;
    }
}
