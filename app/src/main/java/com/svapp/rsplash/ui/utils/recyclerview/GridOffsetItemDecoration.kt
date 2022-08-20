package com.svapp.rsplash.ui.utils.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

// TODO: This ItemDecoration was made as a draft. A lot of corner cases not covered
class GridOffsetItemDecoration(
    @DimenRes private val offsetRes: Int,
    private val spanCount: Int
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val offset = view.context.resources.getDimensionPixelSize(offsetRes)
        val position = parent.getChildAdapterPosition(view)
        val firstRow = position > spanCount - 1
        val last = (parent.adapter?.itemCount ?: 0) - 1

        when {
            firstRow -> {
                outRect.set(offset, 0, offset, offset)
            }
            position == last -> {
                outRect.set(offset, offset, offset, 0)
            }
            else -> {
                outRect.set(offset, offset, offset, offset)
            }
        }
    }
}
