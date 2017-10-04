package org.lulz.jrat.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import org.lulz.jrat.R;
import org.lulz.jrat.model.impl.RatSighting;

public class RatSightingHolder extends RecyclerView.ViewHolder {
    private View view;
    private TextView nameView;
    private TextView descView;

    public RatSightingHolder(View itemView) {
        super(itemView);
        view = itemView;
        nameView = itemView.findViewById(R.id.name);
        descView = itemView.findViewById(R.id.desc);
    }

    public void bind(final RatSighting model) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ItemDetailActivity.class);
                intent.putExtra("data", model);
                view.getContext().startActivity(intent);
            }
        });
        nameView.setText(model.getAddr());
        descView.setText(model.getLocType());

    }
}
