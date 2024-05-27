import networkx as nx

def leer_grafo(archivo):
    G = nx.DiGraph()
    with open(archivo, 'r') as f:
        for linea in f:
            ciudad1, ciudad2, km = linea.strip().split()
            G.add_edge(ciudad1, ciudad2, weight=int(km))
    return G

def floyd_warshall(G):
    return nx.floyd_warshall_predecessor_and_distance(G)

def centro_grafo(G, distancias):
    excentricidades = nx.eccentricity(G, sp=distancias)
    return min(excentricidades, key=excentricidades.get)

def main():
    G = leer_grafo('C:/Users/javib/OneDrive/Documentos/GitHub/Hoja-de-Trabajo-10/guategrafo.txt')
    preds, dists = floyd_warshall(G)

    while True:
        print("Seleccione una opción:")
        print("1. Ruta más corta entre dos ciudades.")
        print("2. Ciudad que queda en el centro del grafo.")
        print("3. Modificar grafo.")
        print("4. Finalizar.")
        opcion = int(input())

        if opcion == 1:
            origen = input("Ingrese ciudad origen: ")
            destino = input("Ingrese ciudad destino: ")
            try:
                distancia = dists[origen][destino]
                ruta = []
                while destino:
                    ruta.append(destino)
                    destino = preds[origen].get(destino, None)
                ruta.reverse()
                print(f"La distancia más corta es: {distancia} KM")
                print("Ruta: " + " -> ".join(ruta))
            except KeyError:
                print("No hay ruta disponible entre las ciudades.")
        elif opcion == 2:
            print("La ciudad que queda en el centro del grafo es: " + centro_grafo(G, dists))
        elif opcion == 3:
            subopcion = input("Modificar grafo:\na) Interrupción de tráfico.\nb) Establecer conexión.\n")[0]
            origen = input("Ingrese ciudad origen: ")
            destino = input("Ingrese ciudad destino: ")
            if subopcion == 'a':
                G.remove_edge(origen, destino)
            elif subopcion == 'b':
                km = int(input("Ingrese distancia en KM: "))
                G.add_edge(origen, destino, weight=km)
            preds, dists = floyd_warshall(G)
        elif opcion == 4:
            print("Finalizando programa.")
            break
        else:
            print("Opción no válida.")

if __name__ == "__main__":
    main()
