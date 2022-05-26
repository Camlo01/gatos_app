package com.cats.gatos_app;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
import com.cats.gatos_app.Model.Gatos;
import com.cats.gatos_app.Controller.GatosService;
import com.cats.gatos_app.View.Inicio;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Camilo
 */
public class Gatos_app {

    public static void main(String[] args) throws IOException {
//        Componente inicio

        System.out.println("Aplicacion iniciada");

        String[] botones = {"1. ver gatos", "2. Ver favoritos", "3. Salir"};
        int opcion_menu = -1;

        do {
//            Menu principal
            String opcion = (String) JOptionPane.showInputDialog(null, "Gatitos Java", "Menu principal", JOptionPane.INFORMATION_MESSAGE, null, botones, botones[0]);
//            Inicio inicio = new Inicio();
//            inicio.setVisible(true);

//Validar seleccion usuario
            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    opcion_menu = i;
                }
            }
            switch (opcion_menu) {
                case 0:
                    GatosService.verGatos();
                    break;
                case 1:
                    Gatos gato = new Gatos();
                    GatosService.verFavoritos(gato.getApikey());
                    break;
                default:
                    break;
            }
        } while (opcion_menu != 1);

    }
}
