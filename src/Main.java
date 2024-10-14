import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.InputStream;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

    public static double obtenerTasaCambio(String monedaBase, String monedaObjetivo) {
        String apiKey = "261c35ad8a7f9dafe3286653"; // Coloca tu API Key aquí
        String url_str = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + monedaBase;

        try {
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();

            JsonObject conversionRates = jsonobj.getAsJsonObject("conversion_rates");
            return conversionRates.get(monedaObjetivo).getAsDouble();

        } catch (Exception e) {
            System.out.println("Error al obtener la tasa de cambio: " + e.getMessage());
            return -1;
        }
    }

    // Método para mostrar el menú
    public static void mostrarMenu() {
        System.out.println("Seleccione una opción de conversión:");
        System.out.println("1. Dólar a Sol");
        System.out.println("2. Sol a Dólar");
        System.out.println("3. Dólar a Peso Argentino");
        System.out.println("4. Peso Argentino a Dólar");
        System.out.println("5. Dólar a Euro");
        System.out.println("6. Euro a Dólar");
        System.out.println("7. Salir");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        double monto, resultado;
        String monedaBase = "", monedaObjetivo = "";

        do {
            mostrarMenu();
            opcion = scanner.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                System.out.print("Ingrese el monto a convertir: ");
                monto = scanner.nextDouble();

                switch (opcion) {
                    case 1:
                        monedaBase = "USD";
                        monedaObjetivo = "PEN";
                        break;
                    case 2:
                        monedaBase = "PEN";
                        monedaObjetivo = "USD";
                        break;
                    case 3:
                        monedaBase = "USD";
                        monedaObjetivo = "ARS";
                        break;
                    case 4:
                        monedaBase = "ARS";
                        monedaObjetivo = "USD";
                        break;
                    case 5:
                        monedaBase = "USD";
                        monedaObjetivo = "EUR";
                        break;
                    case 6:
                        monedaBase = "EUR";
                        monedaObjetivo = "USD";
                        break;
                }

                double tasaCambio = obtenerTasaCambio(monedaBase, monedaObjetivo);
                if (tasaCambio != -1) {
                    resultado = monto * tasaCambio;
                    System.out.println("El valor ingresado es: " + monto + " " + monedaBase);
                    System.out.println("El valor final es: " + resultado + " " + monedaObjetivo);
                } else {
                    System.out.println("No se pudo obtener la tasa de cambio.");
                }

            } else if (opcion == 7) {
                System.out.println("Saliendo...");
            } else {
                System.out.println("Opción inválida. Inténtelo de nuevo.");
            }
        } while (opcion != 7);

        scanner.close();
    }
}
