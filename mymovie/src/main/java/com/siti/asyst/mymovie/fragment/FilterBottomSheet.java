package com.siti.asyst.mymovie.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.siti.asyst.mymovie.R;
import com.siti.asyst.mymovie.utility.Constant;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    String yearSelected = "";
    String sortBySelected = "";
    Spinner yearSpinner, sortBySpinner;
    Button filterButton;
    OnSubmitButtonListener listener;
    ArrayList<String> listYear = new ArrayList<>();
    ArrayList<String> listSortBy = new ArrayList<>();

    public FilterBottomSheet() {
        // Required empty public constructor
    }

    public static FilterBottomSheet newInstance(String year, String sortBy) {
        FilterBottomSheet filter = new FilterBottomSheet();

        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_YEAR, year);
        bundle.putString(Constant.KEY_SORT_BY, sortBy);

        filter.setArguments(bundle);
        return filter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_filter_bottom_sheet, container, false);

        yearSpinner = v.findViewById(R.id.year_spinner);
        sortBySpinner = v.findViewById(R.id.sort_by_spinner);

        sortBySpinner.setOnItemSelectedListener(this);
        yearSpinner.setOnItemSelectedListener(this);

        filterButton = v.findViewById(R.id.filter_button);

        listSortBy.add("popularity.desc");
        listSortBy.add("popularity.asc");
        listSortBy.add("release_date.desc");
        listSortBy.add("release_date.asc");

        listYear.add("none");
        for (int i = 2020; i >= 1900; i--) {
            listYear.add(Integer.toString(i));
        }

        ArrayAdapter<String> sortByAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, listSortBy);
        sortBySpinner.setAdapter(sortByAdapter);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, listYear);
        yearSpinner.setAdapter(yearAdapter);

        int yearIndex = getSpinner(yearSpinner, yearSelected);
        yearSpinner.setSelection(yearIndex);

        int sortByIndex = getSpinner(sortBySpinner, sortBySelected);
        yearSpinner.setSelection(sortByIndex);

        filterButton.setOnClickListener(this);

        if (getArguments() != null) {
            yearSelected = getArguments().getString(Constant.KEY_YEAR, "");
            sortBySelected = getArguments().getString(Constant.KEY_SORT_BY, "");

            int positionYear = yearAdapter.getPosition(yearSelected);
            yearSpinner.setSelection(positionYear);

            int positionSortBy = sortByAdapter.getPosition(sortBySelected);
            sortBySpinner.setSelection(positionSortBy);

        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_button:

                String yearSelected = yearSpinner.getSelectedItem().toString();
                String sortBySelected = sortBySpinner.getSelectedItem().toString();

                if (yearSelected == "none") {
                    listener.onSubmitButton("", sortBySelected);
                } else {
                    listener.onSubmitButton(yearSelected, sortBySelected);
                }

                dismiss();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FilterBottomSheet.OnSubmitButtonListener) {
            listener = (FilterBottomSheet.OnSubmitButtonListener) context;
        } else {
            throw new RuntimeException(context.toString() + "Activity harus implement OnSubmitButtonListener");
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public int getSpinner(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0;
    }

    public interface OnSubmitButtonListener {
        void onSubmitButton(String year, String sortBy);
    }

}
