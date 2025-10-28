package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    
    public static Estado caverna11 = new Estado("[1,1]", 
            null,  
            new ArrayList<>(Arrays.asList("[1,2]", "[2,1]")));
    
    public static Estado caverna12 = new Estado("[1,2]", 
            new ArrayList<>(Arrays.asList("[1,1]")),  
            new ArrayList<>(Arrays.asList("[1,3]", "[2,2]")));
    
    public static Estado caverna13 = new Estado("[1,3]", 
            new ArrayList<>(Arrays.asList("[1,2]")),  
            new ArrayList<>(Arrays.asList("[1,4]", "[2,3]")));
    
    public static Estado caverna14 = new Estado("[1,4]", 
            new ArrayList<>(Arrays.asList("[1,3]")),  
            new ArrayList<>(Arrays.asList("[2,4]")));
    
    public static Estado caverna21 = new Estado("[2,1]", 
            new ArrayList<>(Arrays.asList("[1,1]")),  
            new ArrayList<>(Arrays.asList("[2,2]", "[3,1]")));
    
    public static Estado caverna22 = new Estado("[2,2]", 
            new ArrayList<>(Arrays.asList("[1,2]", "[2,1]")),  
            new ArrayList<>(Arrays.asList("[2,3]", "[3,2]")));
    
    public static Estado caverna23 = new Estado("[2,3]", 
            new ArrayList<>(Arrays.asList("[1,3]", "[2,2]")),  
            new ArrayList<>(Arrays.asList("[2,4]", "[3,3]")));
    
    public static Estado caverna24 = new Estado("[2,4]", 
            new ArrayList<>(Arrays.asList("[1,4]", "[2,3]")),  
            new ArrayList<>(Arrays.asList("[3,4]")));
    
    public static Estado caverna31 = new Estado("[3,1]", 
            new ArrayList<>(Arrays.asList("[2,1]")),  
            new ArrayList<>(Arrays.asList("[3,2]", "[4,1]")));
    
    public static Estado caverna32 = new Estado("[3,2]", 
            new ArrayList<>(Arrays.asList("[2,2]", "[3,1]")),  
            new ArrayList<>(Arrays.asList("[3,3]", "[4,2]")));
    
    public static Estado caverna33 = new Estado("[3,3]", 
            new ArrayList<>(Arrays.asList("[2,3]", "[3,2]")),  
            new ArrayList<>(Arrays.asList("[3,4]", "[4,3]")));
    
    public static Estado caverna34 = new Estado("[3,4]", 
            new ArrayList<>(Arrays.asList("[2,4]", "[3,3]")),  
            new ArrayList<>(Arrays.asList("[4,4]")));
    
    public static Estado caverna41 = new Estado("[4,1]", 
            new ArrayList<>(Arrays.asList("[3,1]")),  
            new ArrayList<>(Arrays.asList("[4,2]")));
    
    public static Estado caverna42 = new Estado("[4,2]", 
            new ArrayList<>(Arrays.asList("[3,2]", "[4,1]")),  
            new ArrayList<>(Arrays.asList("[4,3]")));
    
    public static Estado caverna43 = new Estado("[4,3]", 
            new ArrayList<>(Arrays.asList("[3,3]", "[4,2]")),  
            new ArrayList<>(Arrays.asList("[4,4]")));
    
    public static Estado caverna44 = new Estado("[4,4]", 
            new ArrayList<>(Arrays.asList("[3,4]", "[4,3]")),  
            null);

    // Lista de todos los estados para búsqueda
    public static List<Estado> todasCavernas = Arrays.asList(
        caverna11, caverna12, caverna13, caverna14,
        caverna21, caverna22, caverna23, caverna24,
        caverna31, caverna32, caverna33, caverna34,
        caverna41, caverna42, caverna43, caverna44
    );

    // Información adicional sobre las cavernas (peligros y oro)
    public static void inicializarPeligros() {
        System.out.println("=== CONFIGURACIÓN DEL MUNDO DE WUMPUS (SOLUCIONABLE) ===");
        System.out.println("Wumpus está en: [1,3]");
        System.out.println("Fosos en: [3,1], [3,3] y [4,4]");
        System.out.println("Oro en: [4,4]");
        System.out.println("El cazador comienza en: [1,1]");
        System.out.println("========================================================\n");
        System.out.println();
    }

    public static void main(String[] args) {
        inicializarPeligros();
        
        Estado estadoInicial = caverna11;  // El cazador comienza en [1,1]
        Estado estadoObjetivo = caverna23; // El oro está en [2,3]
        
        System.out.println("=== BÚSQUEDA EN ANCHURA (BFS) - Camino más corto ===");
        BreadthFirstSearch(estadoInicial, estadoObjetivo);
        
        System.out.println("\n=== BÚSQUEDA EN PROFUNDIDAD (DFS) ===");
        DepthFirstSearch(estadoInicial, estadoObjetivo);
    }
    
    // Método para buscar un estado por coordenadas
    public static Estado buscarEstadoPorCoordenadas(String coordenadas) {
        for (Estado estado : todasCavernas) {
            if (estado.getNombre().equals(coordenadas)) {
                return estado;
            }
        }
        return null;
    }
    
    // BFS implementado
    public static void BreadthFirstSearch(Estado EstadoInicial, Estado EstadoFinal) {
        ArrayList<Estado> ListaAbierta = new ArrayList<>();
        ListaAbierta.add(EstadoInicial);
        
        ArrayList<Estado> ListaCerrada = new ArrayList<>();
        
        java.util.Map<Estado, Estado> padres = new java.util.HashMap<>();
        
        boolean solucionEncontrada = false;
        
        System.out.println("Buscando camino seguro al oro...");
        
        while (!ListaAbierta.isEmpty()) {
            Estado nodoActual = ListaAbierta.remove(0);
            ListaCerrada.add(nodoActual);
            
            System.out.println("Explorando: " + nodoActual.getNombre() + obtenerSensaciones(nodoActual));
            
            if (nodoActual.getNombre().equals(EstadoFinal.getNombre())) {
                System.out.println("\n¡ORO ENCONTRADO!");
                System.out.println("Camino seguro encontrado:");
                reconstruirCamino(padres, nodoActual, EstadoInicial);
                solucionEncontrada = true;
                break;
            }
            
            if (nodoActual.getHijos() != null) {
                for (String nombreHijo : nodoActual.getHijos()) {
                    Estado hijo = buscarEstadoPorCoordenadas(nombreHijo);
                    
                    if (hijo != null && !ListaCerrada.contains(hijo) && !ListaAbierta.contains(hijo)) {
                        // Verificar si la caverna es segura (no tiene Wumpus ni foso)
                        if (esCavernaSegura(hijo)) {
                            ListaAbierta.add(hijo);
                            padres.put(hijo, nodoActual);
                            System.out.println("  -> Moviendo a: " + hijo.getNombre() + obtenerSensaciones(hijo));
                        } else {
                            System.out.println("  !! Peligro en: " + hijo.getNombre() + " - Evitando");
                        }
                    }
                }
            }
        }
        
        if (!solucionEncontrada) {
            System.out.println("\nNo se pudo encontrar un camino seguro al oro");
            System.out.println("Estados explorados: " + ListaCerrada.size());
        }
    }
    
    // DFS implementado
    public static void DepthFirstSearch(Estado EstadoInicial, Estado EstadoFinal) {
        ArrayList<Estado> ListaAbierta = new ArrayList<>();
        ListaAbierta.add(EstadoInicial);
        
        ArrayList<Estado> ListaCerrada = new ArrayList<>();
        
        java.util.Map<Estado, Estado> padres = new java.util.HashMap<>();
        
        boolean solucionEncontrada = false;
        
        System.out.println("Buscando camino al oro (DFS)...");
        
        while (!ListaAbierta.isEmpty()) {
            Estado nodoActual = ListaAbierta.remove(ListaAbierta.size() - 1);
            ListaCerrada.add(nodoActual);
            
            System.out.println("Explorando: " + nodoActual.getNombre() + obtenerSensaciones(nodoActual));
            
            if (nodoActual.getNombre().equals(EstadoFinal.getNombre())) {
                System.out.println("\n¡ORO ENCONTRADO!");
                System.out.println("Camino encontrado:");
                reconstruirCamino(padres, nodoActual, EstadoInicial);
                solucionEncontrada = true;
                break;
            }
            
            if (nodoActual.getHijos() != null) {
                for (int i = nodoActual.getHijos().size() - 1; i >= 0; i--) {
                    String nombreHijo = nodoActual.getHijos().get(i);
                    Estado hijo = buscarEstadoPorCoordenadas(nombreHijo);
                    
                    if (hijo != null && !ListaCerrada.contains(hijo) && !ListaAbierta.contains(hijo)) {
                        if (esCavernaSegura(hijo)) {
                            ListaAbierta.add(hijo);
                            padres.put(hijo, nodoActual);
                            System.out.println("  -> Moviendo a: " + hijo.getNombre() + obtenerSensaciones(hijo));
                        } else {
                            System.out.println("  !! Peligro en: " + hijo.getNombre() + " - Evitando");
                        }
                    }
                }
            }
        }
        
        if (!solucionEncontrada) {
            System.out.println("\nNo se pudo encontrar un camino seguro al oro");
            System.out.println("Estados explorados: " + ListaCerrada.size());
        }
    }
    
    // Método para determinar si una caverna es segura
    public static boolean esCavernaSegura(Estado caverna) {
        String coordenadas = caverna.getNombre();
        
        // NUEVA CONFIGURACIÓN: Wumpus en [1,3], Fosos en [3,1] y [4,3]
        List<String> cavernasPeligrosas = Arrays.asList("[1,3]", "[3,1]", "[4,4]", "[3,3]");
        
        return !cavernasPeligrosas.contains(coordenadas);
    }
    
    // Método para obtener las sensaciones en una caverna
    public static String obtenerSensaciones(Estado caverna) {
        String coordenadas = caverna.getNombre();
        StringBuilder sensaciones = new StringBuilder();
        
        // Verificar cavernas adyacentes para detectar sensaciones
        List<String> adyacentes = caverna.getHijos();
        if (adyacentes != null) {
            for (String adyacente : adyacentes) {
                // Hedor cerca del Wumpus en [1,3]
                if (adyacente.equals("[1,3]")) {
                    sensaciones.append(" HEDOR");
                }
                // Brisa cerca de los fosos en [3,1] y [4,3]
                if (adyacente.equals("[3,1]") || adyacente.equals("[4,3]")|| adyacente.equals("[3,3]")) {
                    sensaciones.append(" BRISA");
                }
            }
        }
        
        // Verificar si esta caverna tiene el oro
        if (coordenadas.equals("[4,4]")) {
            sensaciones.append(" BRILLO");
        }
        
        return sensaciones.toString();
    }
    
    // Método para reconstruir el camino
    public static void reconstruirCamino(java.util.Map<Estado, Estado> padres, Estado estadoFinal, Estado estadoInicial) {
        List<String> camino = new ArrayList<>();
        Estado actual = estadoFinal;
        
        while (actual != null) {
            camino.add(0, actual.getNombre());
            actual = padres.get(actual);
            
            if (actual != null && actual.getNombre().equals(estadoInicial.getNombre())) {
                camino.add(0, actual.getNombre());
                break;
            }
        }
        
        // Mostrar el camino
        System.out.print("Camino: ");
        for (int i = 0; i < camino.size(); i++) {
            System.out.print(camino.get(i));
            if (i < camino.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}