package org.lulz.jrat.view.impl;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import org.lulz.jrat.R;

public class RatSightingHolder extends RecyclerView.ViewHolder {
    public View view;
    public TextView dateView;
    public TextView addressView;

    public RatSightingHolder(View itemView) {
        super(itemView);
        view = itemView;
        dateView = itemView.findViewById(R.id.id);
        addressView = itemView.findViewById(R.id.content);
    }
}
