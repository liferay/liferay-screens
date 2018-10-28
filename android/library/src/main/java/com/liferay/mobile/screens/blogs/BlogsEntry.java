package com.liferay.mobile.screens.blogs;

import com.liferay.mobile.screens.asset.AssetEntry;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

/**
 * @author Sarai Díaz García
 */

public class BlogsEntry extends AssetEntry {

    public BlogsEntry() {
        super();
    }

    public BlogsEntry(Map<String, Object> map) {
        super(map);
    }

    public Map<String, Object> getBlogsEntry() {
        return (Map<String, Object>) getObject().get("blogsEntry");
    }

    public String getUserName() {
        return (String) getBlogsEntry().get("userName");
    }

    public String getDate() {
        long displayDate = Long.parseLong(getBlogsEntry().get("displayDate").toString());
        return dateToString(displayDate);
    }

    public long getCoverImage() {
        if (getBlogsEntry() != null && getBlogsEntry().get("coverImageFileEntryId") != null) {
            String cover = getBlogsEntry().get("coverImageFileEntryId").toString();
            return Long.parseLong(cover);
        }
        return 0;
    }

    public long getUserId() {
        return Long.parseLong(getBlogsEntry().get("userId").toString());
    }

    public String getSubtitle() {
        return (String) getBlogsEntry().get("subtitle");
    }

    public String getContent() {
        return (String) getBlogsEntry().get("content");
    }

    private String dateToString(long displayDate) {
        SimpleDateFormat simpleDateFormat =
            (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        return simpleDateFormat.format(new Date(displayDate));
    }
}
