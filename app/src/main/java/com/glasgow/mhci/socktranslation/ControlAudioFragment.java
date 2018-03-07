package com.glasgow.mhci.socktranslation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ControlAudioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ControlAudioFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ControlAudioFragment";

    private boolean recording = false;

    private OnFragmentInteractionListener mListener;

    public ControlAudioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_control_audio, container, false);

        // set click listener for button
        ImageButton button = v.findViewById(R.id.toggle_recording);
        button.setOnClickListener(this);

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

    public void onClick(View view) {
        Log.v(TAG, "OnClick toggle" + recording);

        // if recording == false, start the service
        // else cancel
        //TODO: add service call here based on recording value

        FloatingActionButton button = (FloatingActionButton) view;

        if(recording){
            button.setImageResource(R.drawable.ic_stop_white);
        } else {
            button.setImageResource(R.drawable.ic_mic_white);
        }

        // finally change recording value
        this.recording = !recording;
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
