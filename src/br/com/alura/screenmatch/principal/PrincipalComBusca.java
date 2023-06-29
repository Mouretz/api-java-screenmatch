package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoExeption;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {

        //Lista para cada tipo que converter
        List<Titulo> titulos = new ArrayList<>();
        //Biblioteca google de obejtos para json ou vice  e versa
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting() //para formatar o Json com quebras de linhas
                .create();

        //Fazendo leitura de Filme ou Serie
        Scanner leitura = new Scanner(System.in);
        String busca = "";
        while (!busca.equalsIgnoreCase("sair")) {

            System.out.println("Digite um filme para busca: ");
            busca = leitura.nextLine();
            if(busca.equalsIgnoreCase("sair")) {
                break;
            }
            //voce deve acessar com sua chave que a API Omdb oferece
            String endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=ba50298d";
            System.out.println(endereco);

            //Codigo Supervisionado
            try {
                //Instancia de um client para pedir na segunda instancia uma requisicao pelo endereco no servidor
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();

                //Resposta da requisicao em Json
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String json = response.body();
                System.out.println(json);

                //Aplicacao converter de JSON para um objeto Java)
                TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                System.out.println(meuTituloOmdb);
                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                System.out.println("Titulo já convertido " + meuTitulo);
                //Adicionando titulo na nossa colecao de Lista
                titulos.add(meuTitulo);

            } catch (NumberFormatException e) {
                System.out.println("Aconteceu um erro: ");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Algum erro de argumento na busca, verifique o endereço");
            } catch (ErroDeConversaoDeAnoExeption e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(titulos);
        //Escrevendo lista no formato json
        FileWriter escrita = new FileWriter("filmes.json");
        //(Argument.) Transformar esse titulo que recebe o toString,  em String
        escrita.write(gson.toJson(titulos));
        escrita.close();
        System.out.println("O programa finalizou corretamente!");
    }
}

