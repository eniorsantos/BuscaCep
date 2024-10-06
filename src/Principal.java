import br.ers.conexao.ConexaoHttp;
import br.ers.modelo.EndCep;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args)  {

        Scanner sc = new Scanner(System.in);
        List<EndCep> enderecos = new ArrayList<>();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).setPrettyPrinting().create();

        try {

            boolean controle = true;

            while (controle) {

                System.out.print("Digite o Cep pesquisar ou 'Sair' \n");
                String pesquisa = sc.nextLine();

                if (pesquisa.equalsIgnoreCase("sair")) {
                    if (!enderecos.isEmpty()) {
                        System.out.println("Deseja salva a busca em arquivo 'S'im ou 'N'ão?");
                        String opcao = sc.nextLine().toLowerCase();
                        switch (opcao) {
                            case "s":
                                FileWriter escrita = new FileWriter("Enderecos.json");
                                escrita.write(gson.toJson(enderecos));
                                escrita.close();
                                break;
                            case "n":
                                break;
                        }
                    }
                    controle = false;
                    break;
                }

                String url = "https://viacep.com.br/ws/" + pesquisa.replace(" ", "").replace(".", "").replace("-", "") + "/json";
                ConexaoHttp ch = new ConexaoHttp();

                String json = ch.conexaoHttp(url);

                EndCep endCep = gson.fromJson(json, EndCep.class);

                enderecos.add(endCep);
                enderecos.forEach(System.out::println);

            }
        } catch (IllegalArgumentException e) {
            System.out.println("Algum erro de argumento na busca, verifique o CEP digitado.");
        }catch (Exception e){
            System.out.println(e);
        }finally {
            {
                System.out.println("Programa finalizado!");
            }
        }

    }
}