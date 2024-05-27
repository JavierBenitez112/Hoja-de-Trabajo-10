package demo.src;

import java.util.*;

public class Grafo {
    private Map<String, Integer> ciudades;
    private int[][] distancias;
    private String[][] next;
    private final int INF = Integer.MAX_VALUE;

    public Grafo(int n) {
        ciudades = new HashMap<>();
        distancias = new int[n][n];
        next = new String[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(distancias[i], INF);
            distancias[i][i] = 0;
        }
    }

    public void agregarCiudad(String ciudad) {
        if (!ciudades.containsKey(ciudad)) {
            int index = ciudades.size();
            ciudades.put(ciudad, index);
        }
    }

    public void agregarArco(String origen, String destino, int distancia) {
        int i = ciudades.get(origen);
        int j = ciudades.get(destino);
        distancias[i][j] = distancia;
        next[i][j] = destino;
    }

    public void floydWarshall() {
        int n = ciudades.size();
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distancias[i][k] != INF && distancias[k][j] != INF &&
                        distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    public List<String> getRutaMasCorta(String origen, String destino) {
        List<String> ruta = new LinkedList<>();
        if (!ciudades.containsKey(origen) || !ciudades.containsKey(destino)) {
            return ruta;
        }
        int i = ciudades.get(origen);
        int j = ciudades.get(destino);
        if (distancias[i][j] == INF) {
            return ruta;
        }
        for (String at = origen; at != null; at = next[ciudades.get(at)][j]) {
            ruta.add(at);
            if (at.equals(destino)) break;
        }
        return ruta;
    }

    public int getDistancia(String origen, String destino) {
        if (!ciudades.containsKey(origen) || !ciudades.containsKey(destino)) {
            return INF;
        }
        return distancias[ciudades.get(origen)][ciudades.get(destino)];
    }

    public String getCentroGrafo() {
        int n = ciudades.size();
        int[] excentricidad = new int[n];
        Arrays.fill(excentricidad, -1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (distancias[i][j] < INF) {
                    excentricidad[i] = Math.max(excentricidad[i], distancias[i][j]);
                }
            }
        }
        int minExcentricidad = INF;
        int centro = -1;
        for (int i = 0; i < n; i++) {
            if (excentricidad[i] < minExcentricidad) {
                minExcentricidad = excentricidad[i];
                centro = i;
            }
        }
        for (Map.Entry<String, Integer> entry : ciudades.entrySet()) {
            if (entry.getValue() == centro) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void mostrarMatriz() {
        for (int i = 0; i < distancias.length; i++) {
            for (int j = 0; j < distancias[i].length; j++) {
                if (distancias[i][j] == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(distancias[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public void interrupcionTrafico(String origen, String destino) {
        if (!ciudades.containsKey(origen) || !ciudades.containsKey(destino)) {
            return;
        }
        int i = ciudades.get(origen);
        int j = ciudades.get(destino);
        distancias[i][j] = INF;
        next[i][j] = null;
    }
}
