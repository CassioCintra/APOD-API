/**
 * NasaAPOD - Integração com a API da NASA para obter a Imagem ou Vídeo do Dia.
 * Esta classe solicita a API Key do usuário, realiza uma chamada à API da NASA
 * e exibe informações sobre a Imagem do Dia.
 * Utiliza a biblioteca JSON para analisar a resposta JSON da API.
 *
 * @author Cássio Cintra
 */
package API_Integration;

import Exceptions.JSONNotFound;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Scanner;

public class NasaAPOD {
    public static void main(String[] args) {
        System.out.println("Insira sua API Key:");

        // Utilizando try-with-resources para garantir que o Scanner seja fechado corretamente
        try (Scanner scanner = new Scanner(System.in)) {
            String apiKey = scanner.nextLine();

            // Constroi a URL da solicitação na API
            String apiUrl = "https://api.nasa.gov/planetary/apod";
            String currentDate = LocalDate.now().toString();
            String url = apiUrl + "?api_key=" + apiKey + "&date=" + currentDate;

            // Cria um client HTTP e uma requisição
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

            // Envia uma requisição e recebe uma resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Analisa a resposta JSON
            JSONObject jsonResponse = new JSONObject(response.body());

            // Exibe as Informações
            System.out.println("Título: " + jsonResponse.getString("title"));
            System.out.println("Data: " + jsonResponse.getString("date"));
            System.out.println("Explicação: " + jsonResponse.getString("explanation"));
            System.out.println("Tipo: " + jsonResponse.getString("media_type"));
            System.out.println("URL da imagem: " + jsonResponse.getString("url"));
        } catch (JSONException e) {
            // Captura a exceção de JSON
            throw new JSONNotFound("Hmm... Não foi possível conectar ao sistema.");
        } catch (IOException | InterruptedException e) {
            // Captura exceções de Entrada/Saída ou Interrupção e relança como RuntimeException
            throw new RuntimeException(e);
        }
    }
}