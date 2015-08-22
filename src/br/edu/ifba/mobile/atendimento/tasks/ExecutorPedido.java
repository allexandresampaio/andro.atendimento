package br.edu.ifba.mobile.atendimento.tasks;

import android.os.AsyncTask;
import android.widget.Toast;
import br.edu.ifba.mobile.atendimento.bean.Atendimento;
import br.edu.ifba.mobile.atendimento.fachadas.FachadaBD;
import br.edu.ifba.mobile.atendimento.fachadas.FachadaWS;
import br.edu.ifba.mobile.atendimento.fragmentos.FragmentoTiposDuvida;

public class ExecutorPedido extends AsyncTask<Void, Void, String> {

	private String cpf;
	private FragmentoTiposDuvida tiposDuvida;
	private String nomeAtendente;

	public ExecutorPedido(String cpf, FragmentoTiposDuvida tiposDuvida) {
		this.cpf = cpf;
		this.tiposDuvida = tiposDuvida;
	}

	@Override
	protected String doInBackground(Void... parametros) {
		boolean pedidoOk = true;

		String duvida = tiposDuvida.getAtendimento();

		if ((duvida == null) || (duvida == "")) {
			return "Voc� deve escolher pelo meno um tipo de d�vida!";
		}
		try {
			Atendimento atd = FachadaWS.getInstancia().solicitarAtendimento(
					duvida, cpf);
			nomeAtendente = atd.getNomeAtendente();

			FachadaBD.getInstancia().inserir(atd);
		} catch (Exception e) {
			e.printStackTrace();
			pedidoOk = false;
			// break;
		}

		if (pedidoOk) {
			return "Solicita��o de atendimento feita! " + nomeAtendente
					+ " ir� te atender.";
		} else {
			return "Erro de realiza��o da solicita��o de atendimento!";
		}
	}

	@Override
	public void onPostExecute(String resultado) {
		Toast.makeText(tiposDuvida.getActivity(), resultado, Toast.LENGTH_LONG)
				.show();
	}

}
