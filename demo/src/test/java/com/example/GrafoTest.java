package demo.src.test.java.com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;


import demo.src.Grafo;


public class GrafoTest {
    @Test
    public void testAgregarCiudad() {
        Grafo grafo = new Grafo(3);
        grafo.agregarCiudad("Mixco");
        grafo.agregarCiudad("Antigua");
        assertNotNull(grafo);
    }

    @Test
    public void testAgregarArco() {
        Grafo grafo = new Grafo(3);
        grafo.agregarCiudad("Mixco");
        grafo.agregarCiudad("Antigua");
        grafo.agregarArco("Mixco", "Antigua", 30);
        assertEquals(30, grafo.getDistancia("Mixco", "Antigua"));
    }

    @Test
    public void testRutaMasCorta() {
        Grafo grafo = new Grafo(3);
        grafo.agregarCiudad("Mixco");
        grafo.agregarCiudad("Antigua");
        grafo.agregarArco("Mixco", "Antigua", 30);
        grafo.floydWarshall();
        assertEquals(30, grafo.getDistancia("Mixco", "Antigua"));
        assertEquals(Arrays.asList("Mixco", "Antigua"), grafo.getRutaMasCorta("Mixco", "Antigua"));
    }

    @Test
    public void testCentroGrafo() {
        Grafo grafo = new Grafo(3);
        grafo.agregarCiudad("Mixco");
        grafo.agregarCiudad("Antigua");
        grafo.agregarCiudad("Escuintla");
        grafo.agregarArco("Mixco", "Antigua", 30);
        grafo.agregarArco("Antigua", "Escuintla", 25);
        grafo.floydWarshall();
        assertEquals("Escuintla", grafo.getCentroGrafo());
    }
}
