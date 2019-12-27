package com.example.guardianpolitico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter {
    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link Info} object located at this position in the list
        News currentNewsAdapter = (News) getItem(position);

        // Find the TextView in the list_item.xml layout with title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        // Get the title from the current News object and
        // set this text on the title TextView
        titleTextView.setText(currentNewsAdapter.getTitle());

        // Find the TextView in the list_item.xml layout with the section
        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.section_text_view);
        // Get the section name from the current News object and
        // set this text on the number TextView
        sectionTextView.setText(currentNewsAdapter.getSection());

        // Find the TextView in the list_item.xml layout with the date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        // Get the article date from the current News object and
        // set this text on the number TextView
        dateTextView.setText(currentNewsAdapter.getDate());

        // Find the TextView in the list_item.xml layout with the author
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author_text_view);
        // Get the article date from the current News object and
        // set this text on the number TextView
        authorTextView.setText(currentNewsAdapter.getAuthor());


        return listItemView;
    }
}