/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cats.gatos_app.Controller;

import com.cats.gatos_app.Model.Gatos;
import com.cats.gatos_app.Model.GatosFav;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Camilo
 */
public class GatosService {

    public static void verGatos() throws IOException {

        System.out.println("se ejecuta verGatos()");
        //1. traer los datos de la API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();
        Response response = client.newCall(request).execute();
        String elJson = response.body().string();
        //Cortar los corchetes
        elJson = elJson.substring(1, elJson.length());
        elJson = elJson.substring(0, elJson.length() - 1);
        //Convertir a objeto tipo gato
        Gson gson = new Gson();
        Gatos gatos = gson.fromJson(elJson, Gatos.class);
        System.out.println(gatos);
        //Redimencionar en caso de necesitar
        Image image = null;
        try {
            URL url = new URL(gatos.getUrl());

            image = ImageIO.read(url);
            ImageIcon fondoGato = new ImageIcon(image);

            if (fondoGato.getIconWidth() > 800) {
                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
            }
            String menu = "Opciones: \n "
                    + "1. ver otra imagen\n "
                    + "2. Marcar favorito\n "
                    + "3. Volver \n ";
            String[] botones = {"Ver otra imagen", "favorito", "volver"};
            String id_gato = gatos.getId();
            String opcion = (String) JOptionPane.showInputDialog(null, menu, id_gato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);

            int seleccion = -1;
            //Validar seleccion usuario
            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    seleccion = i;
                }
            }
            switch (seleccion) {
                case 0:
                    verGatos();
                    System.out.println("Se ven mas gatos");
                    break;
                case 1:
                    favoritoGato(gatos);
                    System.out.println("Se marca el gato como favorito");
                    break;
                default:
                    throw new AssertionError();
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void favoritoGato(Gatos gato) {

        try {

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n\t\"image_id\": \"" + gato.getId() + "\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.getApikey())
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println("Se guarda el gato como favorito");
        } catch (IOException e) {
            System.out.println("no se pudo guardar como favorito");
            System.out.println(e);
        }

    }

    public static void verFavoritos(String apikey) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .get()
                .addHeader("x-api-key", apikey)
                .build();
        Response response = client.newCall(request).execute();

        //guardamos el string con la respuesta
        String elJson = response.body().string();

        //Objeto Gson
        Gson gson = new Gson();
        GatosFav[] gatosArray = gson.fromJson(elJson, GatosFav[].class);

        if (gatosArray.length > 0) {
            int min = 1;
            int max = gatosArray.length;
            int aleatorio = (int) (Math.random() * ((max - min) + 1)) + min;
            int indice = aleatorio - 1;

            GatosFav gatofav = gatosArray[indice];

            //Redimencionar en caso de necesitar
            System.out.println("Se traen todos los gatos favoritos");
            System.out.println("Cantidad: " + gatosArray.length);

            Image image = null;
            try {
                URL url = new URL(gatofav.image.getUrl());

                image = ImageIO.read(url);
                ImageIcon fondoGato = new ImageIcon(image);

                if (fondoGato.getIconWidth() > 800) {
                    Image fondo = fondoGato.getImage();
                    Image modificada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                    fondoGato = new ImageIcon(modificada);
                }
                String menu = "Opciones: \n "
                        + "1. ver otra imagen\n "
                        + "2. Eliminar favorito\n "
                        + "3. Volver \n ";
                String[] botones = {"Ver otra imagen", "Eliminar Favorito", "volver"};
                String id_gato = gatofav.getId();
                String opcion = (String) JOptionPane.showInputDialog(null, menu, id_gato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);

                int seleccion = -1;
                //Validar seleccion usuario
                for (int i = 0; i < botones.length; i++) {
                    if (opcion.equals(botones[i])) {
                        seleccion = i;

                    }
                }
                switch (seleccion) {
                    case 0:
                        verFavoritos(apikey);
                        System.out.println("se ven otros favoritos");
                        break;
                    case 1:
                        borrarFavorito(gatofav);
                        break;
                    default:
                        throw new AssertionError();
                }

            } catch (IOException e) {
                System.out.println(e);
            }

        }

    }

    public static void borrarFavorito(GatosFav gatofav) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/" + gatofav.getId())
                    .delete(null)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gatofav.getApikey())
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println("Se elimina gato de favorito");
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("No se pudo eliminar el gato de favorito");
        }
    }

}
