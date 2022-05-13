/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.cats.gatos_app;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Camilo
 */
public class Gatos_app {

    public static void main(String[] args) throws IOException {
        System.out.println("Aplicacion iniciada");

        String[] botones = {"1. ver gatos", "2. Salir"};
        int opcion_menu = -1;

        do {
            //Menu principal
            String opcion = (String) JOptionPane.showInputDialog(null, "Gatitos Java", "Menu principal", JOptionPane.INFORMATION_MESSAGE, null, botones, botones[0]);

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
                default:
                    break;
            }
        } while (opcion_menu != 1);

    }
}
