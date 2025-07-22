package com.example.stuadminlogin.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.stuadminlogin.R;

public class FilterDialogFragment extends DialogFragment {

    public interface FilterDialogListener {
        void onFilterSelected(String column, String value);
    }

    private FilterDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FilterDialogListener) {
            listener = (FilterDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement FilterDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        final android.view.View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_filter, null);

        final Spinner spinner = view.findViewById(R.id.spinnerColumns);
        final EditText etValue = view.findViewById(R.id.etColumnValue);

        // Columns of the admins table
        String[] columns = {"username", "full_name", "created_at"};
        spinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, columns));

        builder.setView(view)
                .setTitle("Filter Admins")
                .setPositiveButton("Search", (dialog, which) -> {
                    String selectedColumn = spinner.getSelectedItem().toString();
                    String value = etValue.getText().toString();
                    listener.onFilterSelected(selectedColumn, value);
                })
                .setNegativeButton("Cancel", null);

        return builder.create();
    }
}
