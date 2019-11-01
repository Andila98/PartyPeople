package com.partypeople.user.fragments;


        import android.os.Bundle;

        import androidx.fragment.app.Fragment;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.partypeople.user.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PastEvents extends Fragment {


    public PastEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past_events, container, false);
    }

}
