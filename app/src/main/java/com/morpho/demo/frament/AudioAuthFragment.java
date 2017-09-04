package com.morpho.demo.frament;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.morpho.demo.R;
import com.morpho.demo.audio.AudioRecoder;
import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.DialogUses;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.tools.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by alex on 30/06/2015.
 */
public class AudioAuthFragment extends ParentFragment implements View.OnClickListener{

    private AudioRecoder audioRecoder;
    private MediaPlayer mediaPlayer;

    private Button startRecord1;

    private Button play1;

    private String audio1Path;

    private boolean dondAudio1 = false;

    private Button confirmAudios;

    public AudioAuthFragment(){}

    public static AudioAuthFragment newInstance(){
        AudioAuthFragment sectionFragment = new AudioAuthFragment();
        return(sectionFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AudioManager amanager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = amanager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        amanager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.auth_audio_layout, container,
                false);

        startRecord1 = (Button) layoutView.findViewById(R.id.startRecord1);
        startRecord1.setOnClickListener(this);

        play1 = (Button) layoutView.findViewById(R.id.play1);
        play1.setOnClickListener(this);


        confirmAudios = (Button) layoutView.findViewById(R.id.confirmAudios);
        confirmAudios.setOnClickListener(this);

        return layoutView;
    }

    public void start() {

        audioRecoder = new AudioRecoder(Constants.outputFileAut);
        dondAudio1 = false;
        showAlert("Alerta", "Detener grabación del AUDIO.", "Detener", null, new DialogUses() {
            @Override
            public void acceptButtonAction() {
                stop();
            }

            @Override
            public void cancelButtonAction() {

            }
        });

        audioRecoder.startRecording();

        Toast.makeText(getActivity().getApplicationContext(), "Iniciando grabación...",
                Toast.LENGTH_SHORT).show();
    }

    private void checkAudios(){
        if(dondAudio1)
            confirmAudios.setVisibility(View.VISIBLE);
        else
            confirmAudios.setVisibility(View.GONE);
    }

    private void setAudios(){
        try {
            MorphoFormApp.getSingleton().getCustomer().audioAuth = IOUtil.readFile(audioRecoder.getFilename());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stop(){
        try {
            audioRecoder.stopRecording();
            audio1Path = audioRecoder.getFilename();
            play1.setVisibility(View.VISIBLE);
            dondAudio1 = true;

            checkAudios();

            Toast.makeText(getActivity().getApplicationContext(), "Grabación detenida...",
                    Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            //  it is called before start()
            e.printStackTrace();
            dondAudio1 = false;
        } catch (RuntimeException e) {
            // no valid audio/video data has been received
            e.printStackTrace();
            dondAudio1 = false;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == startRecord1.getId()){
            showAlert("Alerta", "Se iniciará la grabación del AUDIO. ¿Desea continuar?", "Aceptar", "Cancelar", new DialogUses() {
                @Override
                public void acceptButtonAction() {
                    start();
                }

                @Override
                public void cancelButtonAction() {

                }
            });
        }else if(v.getId() == play1.getId()){
            playAudio(audio1Path);
        }else if(confirmAudios.getId() == v.getId()) {
            showAlert("Alerta", "¿Desea confirmar el audio grabado?,\n si lo haces ya no se podrá cambiar?",
                    "Aceptar", "Cancelar", new DialogUses() {
                        @Override
                        public void acceptButtonAction() {
                            setAudios();
                            startRecord1.setEnabled(false);
                            confirmAudios.setEnabled(false);
                            confirmAudios.setVisibility(View.GONE);
                        }
                        @Override
                        public void cancelButtonAction() {

                        }
                    });
        }
    }

    private void playAudio(String pathFile){
        FileInputStream inputStream = null;
        try {
            mediaPlayer = new MediaPlayer();
            File file = new File(pathFile); // acquire the file from path string
            inputStream = new FileInputStream(file);
            mediaPlayer.setDataSource(inputStream.getFD());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.setLooping(false);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.reset();
                    mp.release();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }catch (IllegalStateException ie){
            ie.printStackTrace();
        }catch (IllegalArgumentException ia){
            ia.printStackTrace();
        }finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void resetAudio(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                audio1Path = null;
                dondAudio1 = false;
                startRecord1.setEnabled(true);
                confirmAudios.setEnabled(true);
                confirmAudios.setVisibility(View.VISIBLE);
                play1.setVisibility(View.GONE);
            }
        });
    }
}
