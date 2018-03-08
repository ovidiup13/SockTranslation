package com.glasgow.mhci.socktranslation;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.api.gax.rpc.ApiStreamObserver;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeRequest;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.StreamingRecognitionConfig;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import com.google.common.util.concurrent.SettableFuture;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.grpc.stub.StreamObserver;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SpeechService extends Service {

    public interface Listener {

        /**
         * Called when a new piece of text was recognized by the Speech API.
         *
         * @param text    The text.
         * @param isFinal {@code true} when the API finished processing audio.
         */
        void onSpeechRecognized(String text, boolean isFinal);

    }
    private static final String TAG = "SpeechService";

    private final SpeechBinder mBinder = new SpeechBinder();
    private final ArrayList<Listener> mListeners = new ArrayList<>();

    private SpeechClient speech;
    private ApiStreamObserver<StreamingRecognizeRequest> requestObserver;

    private final StreamObserver<StreamingRecognizeResponse> responseObserver
            = new StreamObserver<StreamingRecognizeResponse>() {

        @Override
        public void onNext(StreamingRecognizeResponse response) {
            String text = null;
            boolean isFinal = false;
            if (response.getResultsCount() > 0) {
                final StreamingRecognitionResult result = response.getResults(0);
                isFinal = result.getIsFinal();
                if (result.getAlternativesCount() > 0) {
                    final SpeechRecognitionAlternative alternative = result.getAlternatives(0);
                    text = alternative.getTranscript();
                }
            }
            if (text != null) {
                for (Listener listener : mListeners) {
                    listener.onSpeechRecognized(text, isFinal);
                }
            }
        }

        @Override
        public void onError(Throwable t) {
            Log.e(TAG, "Error calling the API.", t);
        }

        @Override
        public void onCompleted() {
            Log.i(TAG, "API completed.");
        }

    };
    /**
     * Starts recognizing speech audio.
     *
     * @param sampleRate The sample rate of the audio.
     */
    public void startRecognizing(int sampleRate) throws IOException {

        if (speech == null) {
            Log.w(TAG, "API not ready. Ignoring the request.");
            return;
        }
        // Configure the API
        // Instantiates a client with GOOGLE_APPLICATION_CREDENTIALS
        speech = SpeechClient.create();

        // Configure request with local raw PCM audio
        RecognitionConfig recConfig = RecognitionConfig.newBuilder()
                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                .setSampleRateHertz(sampleRate)
                .build();
        StreamingRecognitionConfig config = StreamingRecognitionConfig.newBuilder()
                .setConfig(recConfig)
                .setInterimResults(true)
                .setSingleUtterance(true)
                .build();

        class ResponseApiStreamingObserver<T> implements ApiStreamObserver<T> {
            private final SettableFuture<List<T>> future = SettableFuture.create();
            private final List<T> messages = new java.util.ArrayList<T>();

            @Override
            public void onNext(T message) {
                messages.add(message);
            }
            @Override
            public void onError(Throwable t) {
                future.setException(t);
            }

            @Override
            public void onCompleted() {
                future.set(messages);
            }

            // Returns the SettableFuture object to get received messages / exceptions.
            public SettableFuture<List<T>> future() {
                return future;
            }
        }

        ResponseApiStreamingObserver<StreamingRecognizeResponse> responseObserver =
                new ResponseApiStreamingObserver<StreamingRecognizeResponse>();

        BidiStreamingCallable<StreamingRecognizeRequest,StreamingRecognizeResponse> callable =
                speech.streamingRecognizeCallable();

        ApiStreamObserver<StreamingRecognizeRequest> requestObserver =
                callable.bidiStreamingCall(responseObserver);

        // The first request must **only** contain the audio configuration:
        requestObserver.onNext(StreamingRecognizeRequest.newBuilder()
                .setStreamingConfig(config)
                .build());

        // Subsequent requests must **only** contain the audio data.
        requestObserver.onNext(StreamingRecognizeRequest.newBuilder()
                .build());
        // Mark transmission as completed after sending the data.
        //requestObserver.onCompleted();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Recognizes the speech audio. This method should be called every time a chunk of byte buffer
     * is ready.
     *
     * @param data The audio data.
     * @param size The number of elements that are actually relevant in the {@code data}.
     */
    public void recognize(byte[] data, int size) {
        if (requestObserver == null) {
            return;
        }
        // Call the streaming recognition API
        requestObserver.onNext(StreamingRecognizeRequest.newBuilder()
                .setAudioContent(ByteString.copyFrom(data, 0, size))
                .build());
    }

    /**
     * Finishes recognizing speech audio.
     */
    public void finishRecognizing() {
        if (requestObserver == null) {
            return;
        }
        requestObserver.onCompleted();
        requestObserver = null;
    }

    public void addListener(@NonNull Listener listener) {
        mListeners.add(listener);
    }

    public void removeListener(@NonNull Listener listener) {
        mListeners.remove(listener);
    }

    public static SpeechService from(IBinder binder) {
        return ((SpeechBinder) binder).getService();
    }

    private class SpeechBinder extends Binder {

        SpeechService getService() {
            return SpeechService.this;
        }

    }

}
