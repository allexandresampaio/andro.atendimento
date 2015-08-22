package br.edu.ifba.mobile.atendimento.tasks;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;
import br.edu.ifba.mobile.atendimento.MainActivity;
import br.edu.ifba.mobile.atendimento.fachadas.FachadaBD;
import br.edu.ifba.mobile.atendimento.fragmentos.FragmentoConfiguracao;

public class RecuperadorCPF extends AsyncTask<Void, Void, String>{

	private FragmentoConfiguracao fragmentoConf;
	private String cpf;
		
	@Override
	protected String doInBackground(Void... parametros) {
		try {
			cpf = FachadaBD.getInstancia().getCPF();
			MainActivity.setCPF(cpf);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(fragmentoConf.getActivity(), 
					"Erro ao buscar CPF.", Toast.LENGTH_LONG).show();
		}
		
		return "";
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onPostExecute(String resultado) {
		
	}

}
