package com.example.yap.customrecycler;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class HeaderDecoration extends RecyclerView.ItemDecoration {

    private final int headerOffset;
    private final boolean sticky;
    private final SectionCallback sectionCallback;
    private ArrayList<Integer> bottomPositions;

    private View headerView;
    private TextView header;

    public interface SectionCallback {

        boolean inSection(int position);
        String getSectionHeader(int position);
    }

    public HeaderDecoration(ArrayList<Integer> bottomPositions, int headerHeight, boolean sticky, @NonNull SectionCallback sectionCallback)
    {
        this.bottomPositions = bottomPositions;
        headerOffset = headerHeight;
        this.sticky = sticky;
        this.sectionCallback = sectionCallback;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (headerView == null)
        {
            headerView = inflateHeaderView(parent);
            header = headerView.findViewById(R.id.tv_header);
            fixLayoutSize(headerView, parent);
        }

        String previousHeader = "";

        for (int i=0; i<parent.getChildCount(); i++)
        {
            View child = parent.getChildAt(i);
            final int position = parent.getChildAdapterPosition(child);

            Timber.d("position =  " + position);

            String title = sectionCallback.getSectionHeader(position);
            header.setText(title);

            if (!previousHeader.equals(title) || sectionCallback.inSection(position))
            {
                drawHeader(c, child, headerView);
                previousHeader = title;
            }

        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildAdapterPosition(view);

        if (sectionCallback.inSection(pos))
            outRect.top = headerOffset;
    }

    private void drawHeader(Canvas c, View child, View headerView)
    {
        c.save();

        Timber.d("sticky = " + sticky);

        if (sticky)
        {
            c.translate(0, Math.max(0, child.getTop()));
        }
        else
        {
            c.translate(0, child.getTop()-headerView.getHeight());
        }

        headerView.draw(c);
        c.restore();
    }

    private View inflateHeaderView(RecyclerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
    }

    private void fixLayoutSize(View view, ViewGroup parent)
    {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), view.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() +  parent.getPaddingBottom(), view.getLayoutParams().height);

        view.measure(childWidth, childHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }
}
