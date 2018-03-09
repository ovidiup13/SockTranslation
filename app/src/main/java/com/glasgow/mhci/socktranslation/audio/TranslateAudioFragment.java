package com.glasgow.mhci.socktranslation.audio;

import android.annotation.SuppressLint;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TranslateAudioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TranslateAudioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TranslateAudioFragment extends Fragment implements ControlFragment.OnFragmentInteractionListener,
        LanguageControlFragment.OnFragmentInteractionListener {

    private static final String TAG = "TranslateAudioFragment";

    private OnFragmentInteractionListener mListener;

    public TranslateAudioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TranslateAudioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TranslateAudioFragment newInstance() {
        return new TranslateAudioFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // add language fragment
        Log.v(TAG, "Create language selection fragment");
        LanguageControlFragment languageControlFragment = new LanguageControlFragment();
        getChildFragmentManager().beginTransaction().add(R.id.audio_language_frame, languageControlFragment).commit();

        // add control fragment
        Log.v(TAG, "Create control fragment");
        ControlFragment controlFragment = new ControlFragment();
        getChildFragmentManager().beginTransaction().add(R.id.audio_control_frame, controlFragment).commit();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_translate_audio, container, false);

        TextView textView = v.findViewById(R.id.text_translated_view);
        textView.setText("Press the play button to start translation!");

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
     * Handle interaction with child fragments.
     *
     * @param uri
     */
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
