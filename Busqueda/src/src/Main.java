package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    
    // Declaración de estados corregida
    public static Estado Ny = new Estado("New York", 
            null,  
            new ArrayList<>(Arrays.asList("Toronto", "Denver", "Chicago")));
    public static Estado Tor = new Estado("Toronto", 
            new ArrayList<>(Arrays.asList("New York")),  
            new ArrayList<>(Arrays.asList("Calgary", "Los Angeles")));
    public static Estado Calg = new Estado("Calgary", 
            new ArrayList<>(Arrays.asList("Toronto")),  
            null);
    public static Estado Den = new Estado("Denver", 
            new ArrayList<>(Arrays.asList("New York")),  
            new ArrayList<>(Arrays.asList("Chicago", "Los Angeles")));
    public static Estado LA = new Estado("Los Angeles", 
            new ArrayList<>(Arrays.asList("Toronto", "Denver", "Houston")),  
            null);
    public static Estado Chi = new Estado("Chicago", 
            new ArrayList<>(Arrays.asList("New York", "Denver")),  
            new ArrayList<>(Arrays.asList("Houston")));
    public static Estado Hou = new Estado("Houston", 
            new ArrayList<>(Arrays.asList("Chicago")),  
            new ArrayList<>(Arrays.asList("Los Angeles", "Urbana")));
    public static Estado Urb = new Estado("Urbana", 
            new ArrayList<>(Arrays.asList("Houston")),  
            null);

    // Lista de todos los estados para búsqueda
    public static List<Estado> todosEstados = Arrays.asList(Ny, Tor, Calg, Den, LA, Chi, Hou, Urb);

    public static void main(String[] args) {
        Estado EstadoInicial = Ny;
        Estado EstadoFinal = Urb;
        
        System.out.println("=== BÚSQUEDA EN PROFUNDIDAD (DFS) ===");
        DepthFirstSearch(EstadoInicial, EstadoFinal);
        
        System.out.println("\n=== BÚSQUEDA EN ANCHURA (BFS) ===");
        BreadthFirstSearch(EstadoInicial, EstadoFinal);
    }
    
    // Método para buscar un estado por nombre
    public static Estado buscarEstadoPorNombre(String nombre) {
        for (Estado estado : todosEstados) {
            if (estado.getNombre().equals(nombre)) {
                return estado;
            }
        }
        return null;
    }
    
    public static void DepthFirstSearch(Estado EstadoInicial, Estado EstadoFinal) {
        // 1- Crear lista abierta con estado inicial
        ArrayList<Estado> ListaAbierta = new ArrayList<>();
        ListaAbierta.add(EstadoInicial);
        
        // 2- Crear lista cerrada vacía
        ArrayList<Estado> ListaCerrada = new ArrayList<>();
        
        // Para guardar la relación padre-hijo (para reconstruir el camino)
        java.util.Map<Estado, Estado> padres = new java.util.HashMap<>();
        
        boolean solucionEncontrada = false;
        
        // 3- Mientras la lista abierta no esté vacía
        while (!ListaAbierta.isEmpty()) {
            // 3.1.1- Establecer el nodo actual (último elemento para DFS - LIFO)
            Estado nodoActual = ListaAbierta.remove(ListaAbierta.size() - 1);
            
            // 3.1.2- Extraer nodo actual e incorporarlo a lista cerrada
            ListaCerrada.add(nodoActual);
            
            System.out.println("Procesando: " + nodoActual.getNombre());
            
            // 3.1.3- Comparar el nodo actual con el estado final
            if (nodoActual.getNombre().equals(EstadoFinal.getNombre())) {
                // 3.1.3.2- Solución encontrada
                System.out.println("\n¡SOLUCIÓN ENCONTRADA!");
                System.out.println("Camino encontrado:");
                reconstruirCamino(padres, nodoActual, EstadoInicial);
                solucionEncontrada = true;
                break;
            }
            
            // 3.1.3.1- No es el estado final
            // 3.1.3.1.1- Investigar descendientes inmediatos
            if (nodoActual.getHijos() != null) {
                // Recorrer hijos en orden inverso para procesar en el orden correcto (DFS)
                for (int i = nodoActual.getHijos().size() - 1; i >= 0; i--) {
                    String nombreHijo = nodoActual.getHijos().get(i);
                    Estado hijo = buscarEstadoPorNombre(nombreHijo);
                    
                    if (hijo != null && !ListaCerrada.contains(hijo) && !ListaAbierta.contains(hijo)) {
                        // 3.1.3.1.2- Incorporar descendientes a lista abierta
                        ListaAbierta.add(hijo);
                        
                        // 3.1.3.1.3- Establecer apuntadores al nodo padre
                        padres.put(hijo, nodoActual);
                        
                        System.out.println("  -> Agregando hijo: " + hijo.getNombre());
                    }
                }
            } else {
                System.out.println("  -> No tiene hijos");
            }
        }
        
        // 3.2- Si la lista abierta está vacía y no encontramos solución
        if (!solucionEncontrada) {
            System.out.println("\nNO SE ENCONTRÓ SOLUCIÓN");
        }
    }
    
    public static void BreadthFirstSearch(Estado EstadoInicial, Estado EstadoFinal) {
        // 1- Crear lista abierta con estado inicial
        ArrayList<Estado> ListaAbierta = new ArrayList<>();
        ListaAbierta.add(EstadoInicial);
        
        // 2- Crear lista cerrada vacía
        ArrayList<Estado> ListaCerrada = new ArrayList<>();
        
        // Para guardar la relación padre-hijo (para reconstruir el camino)
        java.util.Map<Estado, Estado> padres = new java.util.HashMap<>();
        
        boolean solucionEncontrada = false;
        
        System.out.println("Iniciando BFS desde: " + EstadoInicial.getNombre() + " hasta: " + EstadoFinal.getNombre());
        
        // 3- Mientras la lista abierta no esté vacía
        while (!ListaAbierta.isEmpty()) {
            // 3.1.1- Establecer el nodo actual (PRIMER elemento para BFS - FIFO)
            Estado nodoActual = ListaAbierta.remove(0);
            
            // 3.1.2- Extraer nodo actual e incorporarlo a lista cerrada
            ListaCerrada.add(nodoActual);
            
            System.out.println("Procesando: " + nodoActual.getNombre());
            
            // 3.1.3- Comparar el nodo actual con el estado final
            if (nodoActual.getNombre().equals(EstadoFinal.getNombre())) {
                // 3.1.3.2- Solución encontrada
                System.out.println("\n¡SOLUCIÓN ENCONTRADA!");
                System.out.println("Camino encontrado:");
                reconstruirCamino(padres, nodoActual, EstadoInicial);
                solucionEncontrada = true;
                break;
            }
            
            // 3.1.3.1- No es el estado final
            // 3.1.3.1.1- Investigar descendientes inmediatos
            if (nodoActual.getHijos() != null) {
                // Recorrer hijos en orden normal (no inverso como en DFS)
                for (String nombreHijo : nodoActual.getHijos()) {
                    Estado hijo = buscarEstadoPorNombre(nombreHijo);
                    
                    if (hijo != null && !ListaCerrada.contains(hijo) && !ListaAbierta.contains(hijo)) {
                        // 3.1.3.1.2- Incorporar descendientes a lista abierta
                        ListaAbierta.add(hijo);
                        
                        // 3.1.3.1.3- Establecer apuntadores al nodo padre
                        padres.put(hijo, nodoActual);
                        
                        System.out.println("  -> Agregando hijo: " + hijo.getNombre());
                    }
                }
            } else {
                System.out.println("  -> No tiene hijos");
            }
            
            // Mostrar estado actual de las listas (opcional, para debugging)
            System.out.println("    Lista Abierta: " + obtenerNombresEstados(ListaAbierta));
            System.out.println("    Lista Cerrada: " + obtenerNombresEstados(ListaCerrada));
        }
        
        // 3.2- Si la lista abierta está vacía y no encontramos solución
        if (!solucionEncontrada) {
            System.out.println("\nNO SE ENCONTRÓ SOLUCIÓN");
        }
    }
    
    // Método para reconstruir el camino desde el estado final al inicial
    public static void reconstruirCamino(java.util.Map<Estado, Estado> padres, Estado estadoFinal, Estado estadoInicial) {
        List<String> camino = new ArrayList<>();
        Estado actual = estadoFinal;
        
        while (actual != null) {
            camino.add(0, actual.getNombre()); // Agregar al inicio
            actual = padres.get(actual);
            
            // Si llegamos al estado inicial, terminar
            if (actual != null && actual.getNombre().equals(estadoInicial.getNombre())) {
                camino.add(0, actual.getNombre());
                break;
            }
        }
        
        // Mostrar el camino
        for (int i = 0; i < camino.size(); i++) {
            System.out.print(camino.get(i));
            if (i < camino.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
    
    // Método auxiliar para obtener nombres de estados de una lista
    public static List<String> obtenerNombresEstados(List<Estado> estados) {
        List<String> nombres = new ArrayList<>();
        for (Estado estado : estados) {
            nombres.add(estado.getNombre());
        }
        return nombres;
    }
}