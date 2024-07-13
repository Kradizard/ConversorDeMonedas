import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class ConversorApi {

    private static final URI URL = URI.create("https://v6.exchangerate-api.com/v6/2fa5078a861577b35b58838c/latest/");

    public static void main(String[] args) {
        int opcion = 0;
        Scanner teclado = new Scanner(System.in);

        System.out.println("*********************************************");
        System.out.println("Bienvenido/a, Conversor de monedas: \n");
        String menu = """
                1.- Dolar =>> Peso Argentino
                2.- Peso Argentino =>> Dolar
                3.- Dolar =>> Real Brasileño
                4.- Real Brasileño =>> Dolar
                5.- Dolar =>> Peso Chileno
                6.- Peso Chileno =>> Dolar
                7.- Salir
                Elija una opcion valida:
                *********************************************""";

        while (opcion!=7){
            System.out.println(menu);
            opcion = teclado.nextInt();
            if (opcion == 7) {
                System.out.println("**********  Saliendo  **********");
                break;
            }
            System.out.println("Indique el monto que desea convertir: ");
            double monto = teclado.nextDouble();

            try {

                switch (opcion){
                    case 1:
                        convertir("USD", "ARS", monto);
                        break;
                    case 2:
                        convertir("ARS", "USD", monto);
                        break;
                    case 3:
                        convertir("USD", "BRL", monto);
                        break;
                    case 4:
                        convertir("BRL", "USD", monto);
                        break;
                    case 5:
                        convertir("USD", "CLP", monto);
                        break;
                    case 6:
                        convertir("CLP", "USD", monto);
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        }
    }

    public static void convertir(String moneda, String a, double monto) throws IOException, InterruptedException {
        String url = URL + moneda;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        TasaDeCambio tasaDeCambio = new TasaDeCambio();
        double tasa = tasaDeCambio.obtenerTasa(response.body(), a);
        double resultado = monto * tasa;
        System.out.println("El monto " + monto +" "+ moneda +" equivale a :  " + resultado +" "+ a +"\n");

    }






}
