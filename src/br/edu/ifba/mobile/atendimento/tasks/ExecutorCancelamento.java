package br.edu.ifba.mobile.atendimento.tasks;

import android.os.AsyncTask;
import android.widget.Toast;
import br.edu.ifba.mobile.atendimento.bean.Atendimento;
import br.edu.ifba.mobile.atendimento.fachadas.FachadaBD;
import br.edu.ifba.mobile.atendimento.fachadas.FachadaWS;
import br.edu.ifba.mobile.atendimento.fragmentos.FragmentoAtendimentos;

public class ExecutorCancelamento extends AsyncTask<Void, Void, String> {

	private String id;
	private FragmentoAtendimentos atendimentos;

	public ExecutorCancelamento(String id, FragmentoAtendimentos atendimentos) {
		this.id = id;
		this.atendimentos = atendimentos;
	}

	@Override
	protected String doInBackground(Void... parametros) {
		boolean cancelamentoOk = true;

		Atendimento atendimento = atendimentos.getDuvida();
		if (atendimento == null) {
			return "Você deve escolher uma solicitação de atendimento!";
		}
		
		try {
			FachadaWS.getInstancia().cancelarAtendimento(atendimento.getTipoAtendimento(), id);
			FachadaBD.getInstancia().apagar(id);
		} catch (Exception e) {
			e.printStackTrace();
			cancelamentoOk = false;
		}

		if (cancelamentoOk) {
			return "Solicitação de atendimento cancelada com sucesso!";
		} else {
			return "Erro de realização do cancelamento!";
		}
	}

	@Override
	public void onPostExecute(String resultado) {
		atendimentos.atualizarSolicitacoes();

		Toast.makeText(atendimentos.getActivity(), resultado, Toast.LENGTH_LONG)
				.show();
	}

}
