package com.glasgow.mhci.socktranslation.video;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glasgow.mhci.socktranslation.R;
import com.glasgow.mhci.socktranslation.audio.ControlFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TranslateVideoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class TranslateVideoFragment extends Fragment implements CameraPreviewFragment.OnFragmentInteractionListener {

    private static final String TAG = "TranslateVideoFragment";

    private OnFragmentInteractionListener mListener;

    public TranslateVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "Attaching camera fragment");
        getChildFragmentManager().beginTransaction().replace(R.id.camera_preview, CameraPreviewFragment.newInstance()).commit();

        Log.v(TAG, "Attaching video control fragment");
        getChildFragmentManager().beginTransaction().replace(R.id.video_control_frame, ControlFragment.newInstance()).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_translate_video, container, false);

        TextView subtitlesView = v.findViewById(R.id.subtitles_view);
        subtitlesView.setText(R.string.subtitle_text);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onFragmentInteraction(Uri uri) {

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
        void onFragmentInteraction(Uri uri);
    }
}
