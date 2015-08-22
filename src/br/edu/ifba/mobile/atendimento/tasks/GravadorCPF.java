package br.edu.ifba.mobile.atendimento.tasks;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;
import br.edu.ifba.mobile.atendimento.fachadas.FachadaBD;
import br.edu.ifba.mobile.atendimento.fragmentos.FragmentoConfiguracao;

public class GravadorCPF extends AsyncTask<Void, Void, String>{

	private String cpf;
	
	@Override
	protected String doInBackground(Void... parametros) {
		String resultado = "CPF salvo!";
		try {
			cpf = FragmentoConfiguracao.getInstancia().getCpf();
		} catch (Exception e) {
			e.printStackTrace();
			resultado = "Erro ao buscar CPF na aplicação!";
		}
		
		try {
			FachadaBD.getInstancia().setCPF(cpf);
		} catch (Exception e) {
			e.printStackTrace();
			resultado = "Erro ao gravar CPF no banco!";
		}
		return resultado;
	}
	
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onPostExecute(String resultado) {
		Toast.makeText(FragmentoConfiguracao.getInstancia().getActivity(), "CPF salvo!",
				Toast.LENGTH_SHORT).show();
	}

}
