/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import static com.google.gson.JsonParser.parseReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.System.Logger.Level;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.SarcasmDetection;

public class Sarcasmo {

    private static String api_key = "hoCrbKUXXN3XidRS87uV2oQoA7Yi8GaDT2po92TPW0E";
    private static String host = "https://apis.paralleldots.com/v4/";

    public static String sarcasm(String text) throws Exception {
        if (api_key != null) {
            String url = host + "sarcasm";
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("api_key", api_key)
                    .addFormDataPart("text", text)
                    .addFormDataPart("lang_code", "en")
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .addHeader("cache-control", "no-cache")
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } else {
            return "{ \"Error\": \"API key does not exist\" }";
        }
    }

    public static void metodo(SarcasmDetection modelo) {
        try {
            String texto = modelo.getTexto();

            if (api_key != null) {
                String url = host + "sarcasm";
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("api_key", api_key)
                        .addFormDataPart("text", texto)
                        .addFormDataPart("lang_code", "en")
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .addHeader("cache-control", "no-cache")
                        .build();
                Response response = client.newCall(request).execute();
                JsonObject jsonObject = JsonParser.parseString​(response.body().string()).getAsJsonObject();
                if (jsonObject.isJsonObject()) {
                    JsonObject rootobj = jsonObject.getAsJsonObject();
                    String nosarcastico = rootobj.get("Non-Sarcastic").getAsString();
                    String sisarcastico = rootobj.get("Sarcastic").getAsString();
                    float nosar = Float.parseFloat(nosarcastico);
                    float sisar = Float.parseFloat(sisarcastico);
                    modelo.setNosarcasmo(nosar);
                    modelo.setSisarcasmo(sisar);
                    System.out.println("Resultado" + nosar + "\n");
                    System.out.println("Resultado" + sisar + "\n");

                }

            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String texto = "soy victor la vida es triste, me quiero morir";
        String resultado = "";
        resultado = sarcasm(texto);
        System.out.println("resultado" + resultado);

        SarcasmDetection model = new SarcasmDetection();
        model.setTexto(texto);
        metodo(model);
    }

}
