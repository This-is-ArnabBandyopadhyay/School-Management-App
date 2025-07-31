// package com.example.stuadminlogin.adapters;

// import android.content.Context;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.ArrayAdapter;
// import android.widget.TextView;
// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.models.ParentModel; // Corrected: Using ParentModel
// import java.util.List;

// public class ParentAdapter extends ArrayAdapter<ParentModel> {

//     public ParentAdapter(Context context, List<ParentModel> parents) {
//         super(context, 0, parents);
//     }

//     @Override
//     public View getView(int position, View convertView, ViewGroup parent) {
//         // Get the data item for this position
//         ParentModel currentParent = getItem(position);

//         // Check if an existing view is being reused, otherwise inflate the view
//         if (convertView == null) {
//             // Using a standard Android layout that supports two lines of text.
//             // android.R.layout.simple_list_item_2 provides two TextViews (text1 and text2).
//             convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
//         }

//         // Lookup view for data population
//         TextView text1 = convertView.findViewById(android.R.id.text1);
//         TextView text2 = convertView.findViewById(android.R.id.text2);

//         // Populate the data into the template view using the data object
//         if (currentParent != null) {
//             // Set the parent's name on the first line (text1)
//             text1.setText(currentParent.getName());

//             // Set the email and phone number on the second line (text2)
//             // Using a newline character "\n" to separate them
//             String details = "\nEmail: " + currentParent.getEmail() + "\nPhone No: " + currentParent.getPhoneNo();
//             text2.setText(details);
//         }

//         // Return the completed view to render on screen
//         return convertView;
//     }
// }


package com.example.stuadminlogin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.stuadminlogin.R; // Make sure this R is correctly pointing to your project's R file
import com.example.stuadminlogin.models.ParentModel;
import java.util.List;

public class ParentAdapter extends ArrayAdapter<ParentModel> {

    public ParentAdapter(Context context, List<ParentModel> parents) {
        super(context, 0, parents);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ParentModel currentParent = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            // Inflate your custom layout: item_parent.xml
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_parent, parent, false);
        }

        // Lookup views from your custom layout for data population
        TextView tvParentName = convertView.findViewById(R.id.tv_parent_name);
        TextView tvParentDetails = convertView.findViewById(R.id.tv_parent_details);

        // Populate the data into the template view using the data object
        if (currentParent != null) {
            // Set the parent's name
            tvParentName.setText(currentParent.getName());
            // The color for tvParentName is set in item_parent.xml to black.

            // Set the email and phone number on separate lines for the details TextView
            String details = "Email: " + currentParent.getEmail() + "\nPhone No: " + currentParent.getPhoneNo();
            tvParentDetails.setText(details);
            // The color for tvParentDetails is set in item_parent.xml to darker_gray.
        }

        // Return the completed view to render on screen
        return convertView;
    }
}