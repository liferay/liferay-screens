package com.liferay.mobile.screens.base.list.interactor;

import android.support.annotation.NonNull;
import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.liferay.mobile.screens.cache.CachePolicy.REMOTE_ONLY;

/**
 * @author Javier Gamarra
 */
public abstract class BaseListInteractor<L extends BaseListInteractorListener, E extends ListEvent>
    extends BaseCacheReadInteractor<L, BaseListEvent<E>> {

    protected Query query;

    public BaseListEvent<E> execute(Query query, Object... args) throws Exception {
        int startRow = query.getStartRow();
        int endRow = query.getEndRow();

        validate(startRow, endRow, locale);

        if (notRequestingRightNow(query) || retrying(args)) {

            JSONArray jsonArray = getPageRowsRequest(query, args);
            int rowCount = getPageRowCountRequest(args);

            List<E> entries = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                entries.add(createEntity(JSONUtil.toMap(jsonObject)));
            }

            return new BaseListEvent<>(query, entries, rowCount);
        }

        return null;
    }

    @Override
    public void onSuccess(BaseListEvent event) {

        List<E> entries = event.getEntries();
        int rowCount = event.getRowCount();

        List list = new ArrayList();

        for (E element : entries) {
            list.add(element.getModel());
        }

        cleanRequestState(event.getQuery());

        getListener().onListRowsReceived(event.getStartRow(), event.getEndRow(), list, rowCount);
    }

    @Override
    public BaseListEvent<E> execute(Object... args) throws Exception {
        throw new AssertionError("Should not be called!");
    }

    @Override
    public void onFailure(BaseListEvent<E> event) {

        RequestState.getInstance().clear(getTargetScreenletId());

        getListener().onListRowsFailure(0, 0, event.getException());
    }

    protected void cleanRequestState(Query query) {
        synchronized (this) {
            RequestState.getInstance().remove(getTargetScreenletId(), query.getRowRange());
        }
    }

    protected boolean notRequestingRightNow(Query query) {
        synchronized (this) {
            if (!RequestState.getInstance().contains(getTargetScreenletId(), query.getRowRange())) {
                RequestState.getInstance().put(getTargetScreenletId(), query.getRowRange());
                return true;
            }
            return false;
        }
    }

    protected boolean retrying(Object... args) {
        Object last = args[args.length - 1];

        if (last instanceof Boolean) {
            return (boolean) last;
        }

        return false;
    }

    protected void validate(int startRow, int endRow, Locale locale) {
        if (startRow < 0) {
            throw new IllegalArgumentException("Start row cannot be negative");
        } else if (endRow < 0) {
            throw new IllegalArgumentException("End row cannot be negative");
        } else if (startRow >= endRow) {
            throw new IllegalArgumentException("Start row cannot be greater or equals than end row");
        } else if (locale == null) {
            throw new IllegalArgumentException("Locale cannot be empty");
        }
    }

    protected boolean cached(Object... args) throws Exception {

        String cacheKey = getListId(query, args);
        Class aClass = BaseListEvent.class;

        BaseListEvent event = (BaseListEvent) Cache.getObject(aClass, groupId, userId, locale, cacheKey);

        if (event != null) {

            decorateBaseEvent(event);
            event.setCached(true);

            EventBusUtil.post(event);
            loadingFromCache(true);
            return true;
        }
        loadingFromCache(false);
        return false;
    }

    @NonNull
    private String getListId(Query query, Object... args) {
        return getIdFromArgs(args)
            + Cache.SEPARATOR
            + query.getStartRowFormatted()
            + Cache.SEPARATOR
            + query.getEndRowFormatted();
    }

    protected void storeToCache(BaseListEvent event) throws Exception {

        storingToCache(event);

        List<E> entries = event.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            Cache.storeObject(entries.get(i));
        }

        Cache.storeObject(event);
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @Override
    protected void online(boolean triedOffline, Exception e, Object[] args) throws Exception {

        if (triedOffline) {
            LiferayLogger.i("Retrieve from cache first failed, trying online");
        }

        retrievingOnline(triedOffline, e);

        BaseListEvent<E> newEvent = execute(query, args);
        if (newEvent != null) {
            decorateEvent(newEvent, false);

            for (E event : newEvent.getEntries()) {
                decorateEvent(event, false);
                event.setCacheKey(event.getListKey());
            }

            newEvent.setCacheKey(getListId(query, args));

            if (!newEvent.isFailed() && !REMOTE_ONLY.equals(getCachePolicy())) {
                storeToCache(newEvent);
            }

            EventBusUtil.post(newEvent);
        }
    }

    protected Class getEventClass() {
        return BaseListEvent.class;
    }

    protected abstract JSONArray getPageRowsRequest(Query query, Object... args) throws Exception;

    protected abstract Integer getPageRowCountRequest(Object... args) throws Exception;

    protected abstract E createEntity(Map<String, Object> stringObjectMap);
}