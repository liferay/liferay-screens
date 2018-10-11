package com.liferay.mobile.screens.testapp.postings.view

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.liferay.mobile.screens.testapp.R
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.model.Collection
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.adapter.ThingAdapter
import com.liferay.mobile.screens.thingscreenlet.screens.events.Event
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import com.liferay.apio.consumer.delegates.converter
import com.liferay.apio.consumer.model.Thing

open class CollectionDetailCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : BaseView,
    FrameLayout(context, attrs, defStyleAttr), ThingAdapter.Listener {

    override var screenlet: ThingScreenlet? = null

    val recyclerView by bindNonNull<RecyclerView>(R.id.collection_recycler_view)
    val addButton by bindNonNull<FloatingActionButton>(R.id.add_button)

    override var thing: Thing? by converter<Collection> {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ThingAdapter(it, this)

        if (thing!!.containsOperation("create")) {
            addButton.visibility = View.GONE
        }

        addButton.setOnClickListener {
            sendEvent(Event.CustomEvent("create", this, thing!!))
        }
    }

    override fun onClickedRow(view: View, thing: Thing): OnClickListener? =
        sendEvent(Event.Click(view, thing))

    override fun onLayoutRow(view: BaseView?, thing: Thing, scenario: Scenario): Int? {
        return sendEvent(Event.FetchLayout(view, thing, scenario))
    }
}
