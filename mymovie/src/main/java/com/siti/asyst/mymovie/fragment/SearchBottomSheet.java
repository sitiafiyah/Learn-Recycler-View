package com.siti.asyst.mymovie.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.siti.asyst.mymovie.R;
import com.siti.asyst.mymovie.utility.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    EditText searchET;
    Button searchButton;
    String search = "";
    OnSubmitButtonListener listener;

    public SearchBottomSheet() {
        // Required empty public constructor
    }

    public static SearchBottomSheet newInstance(String search) {
        SearchBottomSheet fragment = new SearchBottomSheet();

        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_SEARCH, search);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_bottom_sheet, container, false);
        searchET = v.findViewById(R.id.search_edittext);
        searchButton = v.findViewById(R.id.search_button);

        searchButton.setOnClickListener(this);

        if (getArguments() != null) {
            search = getArguments().getString(Constant.KEY_SEARCH, "");
            searchET.setText(search);
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_button:
                if (TextUtils.isEmpty(search)) {
                    Toast.makeText(getActivity(), "Harap Isi Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    listener.onSubmitButton(searchET.getText().toString());
                    dismiss();
                }

                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SearchBottomSheet.OnSubmitButtonListener) {
            listener = (SearchBottomSheet.OnSubmitButtonListener) context;
        } else {
            throw new RuntimeException(context.toString() + "Activity harus implement OnSubmitButtonListener");
        }

    }

    public interface OnSubmitButtonListener {
        void onSubmitButton(String search);
    }

}
