package com.glasgow.mhci.socktranslation.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glasgow.mhci.socktranslation.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordingView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecordingView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordingView extends Fragment {

    private static final String RECORDING_PARAM = "recording";
    private Recording recording;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recording Parameter 1.
     * @return A new instance of fragment RecordingView.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordingView newInstance(Recording recording) {
        RecordingView fragment = new RecordingView();
        Bundle args = new Bundle();
        args.putSerializable(RECORDING_PARAM, recording);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recording = (Recording) getArguments().getSerializable(RECORDING_PARAM);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recording_view, container, false);

        // name
        TextView nameView = v.findViewById(R.id.recording_name);
        nameView.setText(recording.getName());

        // text
        TextView textView = v.findViewById(R.id.recording_text_partial);
        textView.setText(recording.getText().substring(0, 15) + "...");

        // languages
        TextView fromLanguage = v.findViewById(R.id.from_language);
        fromLanguage.setText(recording.getFromLanguage());

        TextView toLanguage = v.findViewById(R.id.to_language);
        toLanguage.setText(recording.getToLanguage());

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
