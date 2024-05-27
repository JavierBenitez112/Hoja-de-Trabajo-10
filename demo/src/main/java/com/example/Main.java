package demo.src.main.java.com.example;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import demo.src.Grafo;

import java.util.HashSet; 
import java.util.ArrayList; 


public class Main {
    public static void main(String[] args) {
        Grafo grafo = leerGrafoDesdeArchivo("C:\\Users\\javib\\OneDrive\\Escritorio\\Progra\\AlgoritmoFloyd\\demo\\src\\guategrafo.txt");

        if (grafo == null) {
            System.out.println("Error al leer el archivo.");
            return;
        }

        grafo.floydWarshall();

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Ruta más corta entre dos ciudades.");
            System.out.println("2. Ciudad que queda en el centro del grafo.");
            System.out.println("3. Modificar grafo.");
            System.out.println("4. Finalizar.");
            opcion = scanner.nextInt();
            scanner.nextLine();  

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese ciudad origen: ");
                    String origen = scanner.nextLine();
                    System.out.print("Ingrese ciudad destino: ");
                    String destino = scanner.nextLine();
                    int distancia = grafo.getDistancia(origen, destino);
                    if (distancia == Integer.MAX_VALUE) {
                        System.out.println("No hay ruta disponible entre las ciudades.");
                    } else {
                        List<String> ruta = grafo.getRutaMasCorta(origen, destino);
                        System.out.println("La distancia más corta es: " + distancia + " KM");
                        System.out.println("Ruta: " + String.join(" -> ", ruta));
                    }
                    break;
                case 2:
                    String centro = grafo.getCentroGrafo();
                    System.out.println("La ciudad que queda en el centro del grafo es: " + centro);
                    break;
                case 3:
                    System.out.println("Modificar grafo:");
                    System.out.println("a) Interrupción de tráfico.");
                    System.out.println("b) Establecer conexión.");
                    char subopcion = scanner.nextLine().charAt(0);
                    if (subopcion == 'a') {
                        System.out.print("Ingrese ciudad origen: ");
                        origen = scanner.nextLine();
                        System.out.print("Ingrese ciudad destino: ");
                        destino = scanner.nextLine();
                        grafo.interrupcionTrafico(origen, destino);
                    } else if (subopcion == 'b') {
                        System.out.print("Ingrese ciudad origen: ");
                        origen = scanner.nextLine();
                        System.out.print("Ingrese ciudad destino: ");
                        destino = scanner.nextLine();
                        System.out.print("Ingrese distancia en KM: ");
                        int nuevaDistancia = scanner.nextInt();
                        scanner.nextLine();  
                        grafo.agregarArco(origen, destino, nuevaDistancia);
                    }
                    grafo.floydWarshall();
                    break;
                case 4:
                    System.out.println("Finalizando programa.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
    }

    private static Grafo leerGrafoDesdeArchivo(String archivo) {
        Grafo grafo = null;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            Set<String> ciudades = new HashSet<>();
            List<String[]> datos = new ArrayList<>();

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(" ");
                if (partes.length != 3) {
                    continue;
                }
                String ciudad1 = partes[0];
                String ciudad2 = partes[1];
                int distancia = Integer.parseInt(partes[2]);
                ciudades.add(ciudad1);
                ciudades.add(ciudad2);
                datos.add(new String[]{ciudad1, ciudad2, partes[2]});
            }

            grafo = new Grafo(ciudades.size());
            for (String ciudad : ciudades) {
                grafo.agregarCiudad(ciudad);
            }
            for (String[] dato : datos) {
                grafo.agregarArco(dato[0], dato[1], Integer.parseInt(dato[2]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return grafo;
    }
}
