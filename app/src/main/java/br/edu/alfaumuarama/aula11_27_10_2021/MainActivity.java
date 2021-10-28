package br.edu.alfaumuarama.aula11_27_10_2021;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    VideoView videoPlay;
    Button btnPlay;
    RatingBar barVotos;
    TextView txtValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //http://marcosdiasvendramini.com.br/aula/meu_video.mp4

        videoPlay = findViewById(R.id.videoPlay);
        btnPlay = findViewById(R.id.btnPlay);
        barVotos = findViewById(R.id.barVotos);
        txtValor = findViewById(R.id.txtValor);

        //criando a classe do controle de midia do android
        MediaController mediaController = new MediaController(this);

        //adicionando o controlador de midia no componente de video
        videoPlay.setMediaController(mediaController);

        //criando o caminho do video (local)
        Uri linkVideo = Uri.parse("android.resource://" +
                getPackageName() + "/" + R.raw.meu_video);

        //criando o caminho do video da web (link)
        //Uri linkVideo =
        //    Uri.parse("http://marcosdiasvendramini.com.br/aula/meu_video.mp4");

        //carregando o video no player
        videoPlay.setVideoURI(linkVideo);

        //iniciando o video ao criar a tela (PLAY)
        //videoPlay.start();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifica se o video esta tocando (PLAY)
                if (videoPlay.isPlaying()) {
                    //pausa o video (PAUSE)
                    videoPlay.pause();
                    btnPlay.setText("Play");
                }
                else {
                    //inicia o video (PLAY)
                    videoPlay.start();
                    btnPlay.setText("Pause");
                }
            }
        });

        barVotos.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //mostrando no txtValor o numero de estrelas selecionado pelo usuario
                txtValor.setText(String.valueOf(rating));

                //criando a classe da thread de envio de dados
                EnviarDadosWeb enviar = new EnviarDadosWeb();

                try {
                    //executando a thread e capturando o retorno que veio da API
                    String retorno = enviar.execute(String.valueOf(rating)).get();

                    //mostrando na tela via TOAST o retorno da API
                    Toast.makeText(MainActivity.this,
                            retorno, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}