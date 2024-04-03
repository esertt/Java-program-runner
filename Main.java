import java.net.http.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

class Main{
    public static void main(String[] args) throws InterruptedException, URISyntaxException, IOException {
        String ApiKey = "apikey";
        String ApiHost = "apihost";
        String ContentType = "application/json";

        Scanner in = new Scanner(System.in);
        System.out.println("Enter your code:");
        String code = in.nextLine();
        in.close();
        HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://online-code-compiler.p.rapidapi.com/v1/"))
        .header("content-type", ContentType)
        .header("X-RapidAPI-Key", ApiKey)
        .header("X-RapidAPI-Host", ApiHost)
        .method("POST", HttpRequest.BodyPublishers.ofString("{\r"+
        "\"language\": \"python3\",\r"+
        "\"version\": \"latest\",\r"+
        "\"code\": " + "\"" + code+ "\",\r"+
        "\"input\": null\r}"))
        .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(extractOutput(response));
    }
    
    public static String extractOutput(HttpResponse<String> res){
        String output = "";
        int indexStart = res.body().indexOf("\"output\"");
        int indexEnd = res.body().indexOf(",\"language\"");
        output = res.body().substring(indexStart + 10, indexEnd - 1);

        return output;
    }
}