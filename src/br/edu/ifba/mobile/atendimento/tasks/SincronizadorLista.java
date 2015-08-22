package br.edu.ifba.mobile.atendimento.tasks;

import java.util.List;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import br.edu.ifba.mobile.atendimento.fachadas.FachadaWS;
import br.edu.ifba.mobile.atendimento.fragmentos.FragmentoTiposDuvida;

public class SincronizadorLista extends AsyncTask<Void, Void, String>{

	private List<String> tiposDuvida;
	private FragmentoTiposDuvida listaDuvidas;
	
	public SincronizadorLista(FragmentoTiposDuvida listaDuvidas) {
		this.listaDuvidas = listaDuvidas;
	}
	
	@Override
	protected String doInBackground(Void... parametros) {
		String resultado = "";
		try {
			tiposDuvida = FachadaWS.getInstancia().getTiposDeDuvida();
			resultado = "Lista de atendimento sincronizada!";
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("SincronizadorLista::doInBackground",
					"Erro ao sincronizar lista!");

			resultado = "Erro ao sincronizar lista!";
		}

		return resultado;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onPostExecute(String resultado) {
		listaDuvidas.atualizarListaTiposDeDuvida(tiposDuvida);
		
		Toast.makeText(listaDuvidas.getActivity(), resultado, Toast.LENGTH_LONG).show();

	}

}
