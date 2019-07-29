package com.bgrummitt.notes.controller.callback;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.bgrummitt.notes.R;
import com.bgrummitt.notes.controller.adapters.ListAdapter;
import com.bgrummitt.notes.controller.adapters.TODOAdapter;

public class SwipeToDeleteOrCompleteCallback extends ItemTouchHelper.SimpleCallback {

    private TODOAdapter mListAdapter;
    private Drawable iconDelete;
    private Drawable iconComplete;
    private final ColorDrawable background;

    public SwipeToDeleteOrCompleteCallback(TODOAdapter adapter){
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

        mListAdapter = adapter;

        background = new ColorDrawable(Color.RED);
        iconDelete = ContextCompat.getDrawable(mListAdapter.getContext(), R.drawable.baseline_delete_white_36);
        iconComplete = ContextCompat.getDrawable(mListAdapter.getContext(), R.drawable.baseline_done_outline_white_36);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mListAdapter.deleteItem(position);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        int iconMargin = (itemView.getHeight() - iconDelete.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - iconDelete.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + iconDelete.getIntrinsicHeight();

        if(dX > 0){
            // Being swiped to right
            int iconLeft = itemView.getLeft() + iconMargin + iconComplete.getIntrinsicWidth();
            int iconRight = itemView.getLeft() + iconMargin;
            iconComplete.setBounds(iconRight, iconTop, iconLeft, iconBottom);

            background.setColor(Color.BLUE);
            background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
        }else if(dX < 0){
            // Being swiped to left
            int iconLeft = itemView.getRight() - iconMargin - iconDelete.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            iconDelete.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setColor(Color.RED);
            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        }else{
            // Reset icons / background when not being swiped
            background.setBounds(0, 0, 0, 0);
            iconComplete.setBounds(0, 0, 0, 0);
            iconDelete.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        iconComplete.draw(c);
        iconDelete.draw(c);

    }
}
