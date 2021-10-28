package br.edu.alfaumuarama.aula11_27_10_2021;

//http://marcosdiasvendramini.com.br/Set/Aula.aspx?texto=teste

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class EnviarDadosWeb extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        try {
            //montando o link da api com o parametro recebido pela thread
            String link = "http://marcosdiasvendramini.com.br/Set/Aula.aspx?texto=" +
                    codificaTexto(strings[0]);

            //criando a URL apartir do link gerado
            URL url = new URL(link);

            //criando a conexao para abrir a URL
            URLConnection connection = url.openConnection();

            //criando o objeto que ira receber o conteudo retornado do servidor (API)
            //InputStreamReader input = new InputStreamReader(connection.getInputStream());

            //pegando o retorno do servidor e transformando em algo legivel para o APP
            //BufferedReader reader = new BufferedReader(input);

            //pega o retorno do servidor em Data e ja transforma em algo legivel
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );

            String linha;

            //linha recebe o valor retornado pela API
            //  enquanto for diferente de nula, continua a leitura
            while ((linha = reader.readLine()) != null) {
                //criando um array de JSON com a linha retornada da API
                JSONArray ja = new JSONArray(linha);

                //rodar todos os registros retornado pela API
                for (int i = 0; i < ja.length(); i++) {
                    //pegando o registro (i) JSON e carregando dentro do Object
                    //JSONObject jo = (JSONObject)ja.get(i);
                    JSONObject jo = ja.getJSONObject(i);

                    //retornando o valor da KEY "texto" da API
                    return jo.getString("texto");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private String codificaTexto(String texto) {
        try {
            //codificando o texto recebido para o formato UTF-8
            return URLEncoder.encode(texto, "utf-8");
        }
        catch (Exception e) {
            return "";
        }
    }
}
