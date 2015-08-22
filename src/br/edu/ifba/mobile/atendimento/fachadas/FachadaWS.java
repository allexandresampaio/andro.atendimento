package br.edu.ifba.mobile.atendimento.fachadas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import br.edu.ifba.mobile.atendimento.bean.Atendimento;

import com.google.gson.Gson;

//@SuppressWarnings("deprecation")
public class FachadaWS {

	private static final String 
	ENDERECO_SERVICO = 
	"http://10.0.3.2:8080/wservice.atendimento/v1";

	// instancia singleton desta fachada
	private static FachadaWS instancia = null;

	// recupera instancia
	public static FachadaWS getInstancia() {
		if (instancia == null) {
			instancia = new FachadaWS();
		}

		return instancia;
	}

	// esconde construtor void: ninguem poderah instanciar esta classe
	// diretamente
	private FachadaWS() {
	}

	public List<String> getTiposDeDuvida() throws Exception {
		// cria lista vazia de tipos de duvida
		List<String> listaDuvidas = new ArrayList<String>();
		// navega no servico web
		HttpClient cliente = new DefaultHttpClient();

		Log.i("FachadaWebService::getTiposDeDuvida", ENDERECO_SERVICO + "/ws/tiposDuvida");

		HttpGet get = new HttpGet(ENDERECO_SERVICO + "/ws/tiposDuvida");
		// obtem resposta do servico web
		HttpResponse resposta = cliente.execute(get);
		// realiza a leitura do json
		BufferedReader br = new BufferedReader(new InputStreamReader(resposta
				.getEntity().getContent()));
		String json = br.readLine();
		if (json != null) {
			// converter de json para lista de string
			Gson conversor = new Gson();
			String duvidas[] = conversor.fromJson(json,
					(new String[] {}).getClass());
			for (String s : duvidas) {
				listaDuvidas.add(s);
			}
		}
		// retorna a lista de tipos de duvida
		return listaDuvidas;
	}

	public Atendimento solicitarAtendimento(String tipo, String cpf)
			throws Exception {
		HttpClient cliente = new DefaultHttpClient();

		if (tipo.equals("Calçados")){
			tipo = "1";
		} else if (tipo.equals("Vestuário Feminino")){
			tipo = "2";
		} else if (tipo.equals("Vestuário Masculino")){
			tipo = "3";
		}
		
		Log.i("FachadaWebService::solicitarAtendimento", ENDERECO_SERVICO + "/ws/solicitacao/"
				+ tipo + "/" + cpf);

		HttpPost post = new HttpPost(ENDERECO_SERVICO + "/ws/solicitacao/" + tipo
				+ "/" + cpf);
		// obtem resposta do servico web
		HttpResponse resposta = cliente.execute(post);
		// realiza a leitura do retorno
		BufferedReader br = new BufferedReader(new InputStreamReader(resposta
				.getEntity().getContent()));
		String resultado = br.readLine();
		
		Gson gson = new Gson();
        Atendimento atd = gson.fromJson(resultado, Atendimento.class);
		
		return atd;
	}

	public String cancelarAtendimento(String tipo, String id)
			throws Exception {
		HttpClient cliente = new DefaultHttpClient();
		
		if (tipo.equals("Calçados")){
			tipo = "1";
		} else if (tipo.equals("Vestuário Feminino")){
			tipo = "2";
		} else if (tipo.equals("Vestuário Masculino")){
			tipo = "3";
		}

		Log.i("FachadaWebService::cancelarAtendimento", ENDERECO_SERVICO
				+ "/ws/cancelamento/" + tipo + "/" + id);

		HttpDelete delete = new HttpDelete(ENDERECO_SERVICO + "/ws/cancelamento/"
				+ tipo + "/" + id);
		// obtem resposta do servico web
		HttpResponse resposta = cliente.execute(delete);
		// realiza a leitura do retorno
		BufferedReader br = new BufferedReader(new InputStreamReader(resposta
				.getEntity().getContent()));
		String resultado = br.readLine();

		return resultado;
	}
}
