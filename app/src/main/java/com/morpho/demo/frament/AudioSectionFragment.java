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
public class AudioSectionFragment extends ParentFragment implements View.OnClickListener{

    private AudioRecoder audioRecoder;
    private MediaPlayer mediaPlayer;

    private static int STATE = 0;
    private static final int AUDIO_1 = 10;
    private static final int AUDIO_2 = 20;
    private static final int AUDIO_3 = 30;
    //private static final int AUDIO_4 = 40;

    private Button startRecord1;
    private Button startRecord2;
    private Button startRecord3;
    //private Button startRecord4;

    private Button play1;
    private Button play2;
    private Button play3;
    //private Button play4;

    private String audio1Path;
    private String audio2Path;
    private String audio3Path;
    //private String audio4Path;

    private boolean dondAudio1 = false;
    private boolean dondAudio2 = false;
    private boolean dondAudio3 = false;
    //private boolean dondAudio4 = false;

    private Button confirmAudios;

    public AudioSectionFragment(){}

    public static AudioSectionFragment newInstance(){
        AudioSectionFragment sectionFragment = new AudioSectionFragment();
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
        View layoutView = inflater.inflate(R.layout.card_framgment_layout, container,
                false);

        startRecord1 = (Button) layoutView.findViewById(R.id.startRecord1);
        startRecord2 = (Button) layoutView.findViewById(R.id.startRecord2);
        startRecord3 = (Button) layoutView.findViewById(R.id.startRecord3);
        //startRecord4 = (Button) layoutView.findViewById(R.id.startRecord4);

        startRecord1.setOnClickListener(this);
        startRecord2.setOnClickListener(this);
        startRecord3.setOnClickListener(this);
        //startRecord4.setOnClickListener(this);

        play1 = (Button) layoutView.findViewById(R.id.play1);
        play2 = (Button) layoutView.findViewById(R.id.play2);
        play3 = (Button) layoutView.findViewById(R.id.play3);
        //play4 = (Button) layoutView.findViewById(R.id.play4);

        play1.setOnClickListener(this);
        play2.setOnClickListener(this);
        play3.setOnClickListener(this);
        //play4.setOnClickListener(this);

        confirmAudios = (Button) layoutView.findViewById(R.id.confirmAudios);
        confirmAudios.setOnClickListener(this);

        return layoutView;
    }

    public void start() {

        switch (STATE){
            case AUDIO_1:
                audioRecoder = new AudioRecoder(Constants.outputFile1);
                dondAudio1 = false;
                showAlert("Alerta", "Detener grabación del AUDIO 1.","Detener", null, new DialogUses() {
                    @Override
                    public void acceptButtonAction() {
                        stop();
                    }

                    @Override
                    public void cancelButtonAction() {

                    }
                });
                break;
            case AUDIO_2:
                audioRecoder = new AudioRecoder(Constants.outputFile2);
                dondAudio2 = false;
                showAlert("Alerta", "Detener grabación del AUDIO 2.","Detener", null, new DialogUses() {
                    @Override
                    public void acceptButtonAction() {
                        stop();
                    }

                    @Override
                    public void cancelButtonAction() {

                    }
                });
                break;
            case AUDIO_3:
                audioRecoder = new AudioRecoder(Constants.outputFile3);
                dondAudio3 = false;
                showAlert("Alerta", "Detener grabación del AUDIO 3.","Detener", null, new DialogUses() {
                    @Override
                    public void acceptButtonAction() {
                        stop();
                    }

                    @Override
                    public void cancelButtonAction() {

                    }
                });
                break;
            /*
            case AUDIO_4:
                audioRecoder = new AudioRecoder(Constants.outputFile4);
                dondAudio4 = false;
                showAlert("Alerta", "Detener grabación del AUDIO 4.","Detener", null, new DialogUses() {
                    @Override
                    public void acceptButtonAction() {
                        stop();
                    }

                    @Override
                    public void cancelButtonAction() {

                    }
                });
                break;*/
        }

        audioRecoder.startRecording();

        Toast.makeText(getActivity().getApplicationContext(), "Iniciando grabación...",
                Toast.LENGTH_SHORT).show();
    }

    public void reStartAudios(){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                audio1Path = null;
                audio2Path = null;
                audio3Path = null;
                dondAudio1 = false;
                dondAudio2 = false;
                dondAudio3 = false;
                startRecord1.setEnabled(true);
                startRecord2.setEnabled(true);
                startRecord3.setEnabled(true);
                confirmAudios.setEnabled(true);
                confirmAudios.setVisibility(View.VISIBLE);
                play1.setVisibility(View.GONE);
                play2.setVisibility(View.GONE);
                play3.setVisibility(View.GONE);
            }
        });

    }

    private void checkAudios(){
        if(dondAudio1 && dondAudio2 && dondAudio3 /*&& dondAudio4*/  )
            confirmAudios.setVisibility(View.VISIBLE);
        else
            confirmAudios.setVisibility(View.GONE);
    }

    private void setAudios(){
        try {
            MorphoFormApp.getSingleton().getCustomer().audio1 = IOUtil.readFile(audio1Path);
            MorphoFormApp.getSingleton().getCustomer().audio2 = IOUtil.readFile(audio2Path);
            MorphoFormApp.getSingleton().getCustomer().audio3 = IOUtil.readFile(audio3Path);
            //MorphoFormApp.getSingleton().getCustomer().audio4 = IOUtil.readFile(audio4Path);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        try {
            audioRecoder.stopRecording();

            switch (STATE){
                case AUDIO_1:
                    audio1Path = audioRecoder.getFilename();
                    play1.setVisibility(View.VISIBLE);
                    dondAudio1 = true;
                    break;
                case AUDIO_2:
                    audio2Path = audioRecoder.getFilename();
                    play2.setVisibility(View.VISIBLE);
                    dondAudio2 = true;
                    break;
                case AUDIO_3:
                    audio3Path = audioRecoder.getFilename();
                    play3.setVisibility(View.VISIBLE);
                    dondAudio3 = true;
                    break;
                /*
                case AUDIO_4:
                    audio4Path = audioRecoder.getFilename();
                    play4.setVisibility(View.VISIBLE);
                    dondAudio4 = true;
                    break;*/
            }

            checkAudios();

            Toast.makeText(getActivity().getApplicationContext(), "Grabación detenida...",
                    Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            //  it is called before start()
            e.printStackTrace();
            switch (STATE) {
                case AUDIO_1:
                    dondAudio1 = false;
                    break;
                case AUDIO_2:
                    dondAudio2 = false;
                    break;
                case AUDIO_3:
                    dondAudio3 = false;
                    break;
                /*
                case AUDIO_4:
                    dondAudio4 = false;
                    break;*/
            }
        } catch (RuntimeException e) {
            // no valid audio/video data has been received
            e.printStackTrace();
            switch (STATE) {
                case AUDIO_1:
                    dondAudio1 = false;
                    break;
                case AUDIO_2:
                    dondAudio2 = false;
                    break;
                case AUDIO_3:
                    dondAudio3 = false;
                    break;
                /*
                case AUDIO_4:
                    dondAudio4 = false;
                    break;*/
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == startRecord1.getId()){
            STATE = AUDIO_1;
            showAlert("Alerta", "Se iniciará la grabación del AUDIO 1. ¿Desea continuar?", "Aceptar", "Cancelar", new DialogUses() {
                @Override
                public void acceptButtonAction() {
                    start();
                }

                @Override
                public void cancelButtonAction() {

                }
            });
        }else if(v.getId() == startRecord2.getId()){
            STATE = AUDIO_2;
            showAlert("Alerta", "Se iniciará la grabación del AUDIO 2. ¿Desea continuar?", "Aceptar", "Cancelar", new DialogUses() {
                @Override
                public void acceptButtonAction() {
                    start();
                }

                @Override
                public void cancelButtonAction() {

                }
            });
        }else if(v.getId() == startRecord3.getId()){
            STATE = AUDIO_3;
            showAlert("Alerta", "Se iniciará la grabación del AUDIO 3. ¿Desea continuar?", "Aceptar", "Cancelar", new DialogUses() {
                @Override
                public void acceptButtonAction() {
                    start();
                }

                @Override
                public void cancelButtonAction() {

                }
            });
        /*
        }else if(v.getId() == startRecord4.getId()){
            STATE = AUDIO_4;
            showAlert("Alerta", "Se iniciará la grabación del AUDIO 4. ¿Desea continuar?", "Aceptar", "Cancelar", new DialogUses() {
                @Override
                public void acceptButtonAction() {
                    start();
                }

                @Override
                public void cancelButtonAction() {

                }
            });*/
        }else if(v.getId() == play1.getId()){
            playAudio(audio1Path);
        }else if(v.getId() == play2.getId()){
            playAudio(audio2Path);
        }else if(v.getId() == play3.getId()){
            playAudio(audio3Path);
        /*}else if(v.getId() == play4.getId()){
            playAudio(audio4Path);*/
        }else if(confirmAudios.getId() == v.getId()) {
            showAlert("Alerta", "¿Desea confirmar los audios grabados?,\n si lo haces ya no podrás cambiarlos?",
                    "Aceptar", "Cancelar", new DialogUses() {
                        @Override
                        public void acceptButtonAction() {
                            setAudios();
                            startRecord1.setEnabled(false);
                            startRecord2.setEnabled(false);
                            startRecord3.setEnabled(false);
                           // startRecord4.setEnabled(false);
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
}
