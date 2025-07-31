package org.example;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    // Clase Producto
    static class Producto {
        String nombre;
        double precio;
        String imagen;

        public Producto(String nombre, double precio, String imagen) {
            this.nombre = nombre;
            this.precio = precio;
            this.imagen = imagen;
        }

        @Override
        public String toString() {
            return nombre + " - $" + precio;
        }
    }

    // Clase Carrito
    static class Carrito {
        ArrayList<Producto> productos = new ArrayList<>();

        public void agregar(Producto p) {
            productos.add(p);
        }

        public ArrayList<Producto> getProductos() {
            return productos;
        }

        public double total() {
            double total = 0;
            for (Producto p : productos) {
                total += p.precio;
            }
            return total;
        }
    }

    // Clase GestorProductos
    static class GestorProductos {
        public static ArrayList<Producto> cargar() {
            ArrayList<Producto> lista = new ArrayList<>();
            lista.add(new Producto("Concha", 10.0, "concha.jpg"));
            lista.add(new Producto("Cuerno", 12.5, "cuerno.jpg"));
            lista.add(new Producto("Pan de muerto", 15.0, "muerto.jpg"));
            return lista;
        }
    }

    // Clase Orden
    static class Orden {
        public static void registrar(String cliente, ArrayList<Producto> productos, String metodo) {
            try {
                FileWriter fw = new FileWriter("ordenes.txt", true);
                fw.write("Cliente: " + cliente + "\n");
                for (Producto p : productos) {
                    fw.write("- " + p.nombre + " - $" + p.precio + "\n");
                }
                fw.write("Método de pago: " + metodo + "\n");
                double total = 0;
                for (Producto p : productos) {
                    total += p.precio;
                }
                fw.write("Total: $" + total + "\n---\n");
                fw.close();
            } catch (IOException e) {
                System.out.println("Error al guardar la orden.");
            }
        }
    }

    // Método principal con menú
    public static void main(String[] args) {

        ArrayList<Producto> productos = GestorProductos.cargar();
        Carrito carrito = new Carrito();
        Scanner sc = new Scanner(System.in);

        int seguir = 1;

        System.out.println("Bienvenido a HornelyGo Self-Checkout");

        while (seguir == 1) {
            System.out.println("");
            System.out.println("Menú Principal");
            System.out.println("--------------------------------------------");
            System.out.println("(1) Ver catálogo de productos");
            System.out.println("(2) Agregar producto al carrito");
            System.out.println("(3) Ver carrito y total");
            System.out.println("(4) Confirmar orden");
            System.out.println("(5) Salir");
            System.out.println("--------------------------------------------");
            System.out.print("¿Qué desea hacer? ");
            int opcion = sc.nextInt();

            if (opcion == 5) {
                System.out.println("\n¡Gracias por usar HornelyGo! :D");
                seguir = 0;

            } else if (opcion == 1) {
                System.out.println("\nCatálogo:");
                for (int i = 0; i < productos.size(); i++) {
                    System.out.println((i + 1) + ". " + productos.get(i));
                }

            } else if (opcion == 2) {
                System.out.println("\nSeleccione el número del producto:");
                for (int i = 0; i < productos.size(); i++) {
                    System.out.println((i + 1) + ". " + productos.get(i));
                }
                int prod = sc.nextInt();
                if (prod >= 1 && prod <= productos.size()) {
                    carrito.agregar(productos.get(prod - 1));
                    System.out.println("Producto agregado.");
                } else {
                    System.out.println("Opción inválida.");
                }

            } else if (opcion == 3) {
                System.out.println("\nCarrito:");
                for (Producto p : carrito.getProductos()) {
                    System.out.println("- " + p.nombre + " - $" + p.precio);
                }
                System.out.println("Total: $" + carrito.total());

            } else if (opcion == 4) {
                sc.nextLine(); // limpiar buffer
                System.out.print("Nombre del cliente: ");
                String cliente = sc.nextLine();

                System.out.print("Método de pago (Efectivo/Tarjeta): ");
                String metodo = sc.nextLine();

                Orden.registrar(cliente, carrito.getProductos(), metodo);
                System.out.println("Orden confirmada y guardada.");
                carrito = new Carrito(); // limpiar carrito

            } else {
                System.out.println("Opción inválida.");
            }
        }
    }
}
