package br.edu.ifba.mobile.atendimento.fragmentos;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.edu.ifba.mobile.atendimento.MainActivity;
import br.edu.ifba.mobile.atendimento.R;
import br.edu.ifba.mobile.atendimento.bean.Atendimento;
import br.edu.ifba.mobile.atendimento.fachadas.FachadaBD;
import br.edu.ifba.mobile.atendimento.tasks.ExecutorCancelamento;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentoAtendimentos extends Fragment {

	private static final int SEC_NUMBER = 2;

	private static final String ARG_SECTION_NUMBER = "section_number";
	private static FragmentoAtendimentos fragmento;

	public static FragmentoAtendimentos getInstancia() {
		if (fragmento == null) {
			fragmento = new FragmentoAtendimentos();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, SEC_NUMBER);
			fragmento.setArguments(args);

		}

		return fragmento;
	}

	ListView listaDeAtendimentos = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_atendimentos, container,
				false);

		listaDeAtendimentos = (ListView) rootView.findViewById(R.id.listaAtendimentos);
		listaDeAtendimentos.setClickable(true);
		listaDeAtendimentos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		fragmento.atualizarSolicitacoes();

		setHasOptionsMenu(true);
		
		return rootView;
	}
	
	@Override
	 public void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater inflater) {
		inflater.inflate(R.menu.pedidos, menu);
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}

	public void atualizarSolicitacoes() {
		atualizarListaPedidos(FachadaBD.getInstancia().getAtendimentos());
	}

	public void atualizarListaPedidos(List<Atendimento> solicitacoes) {
		if (solicitacoes != null) {
			ArrayAdapter<Atendimento> adaptador = new ArrayAdapter<Atendimento>(
					this.getActivity(),
					android.R.layout.simple_list_item_single_choice, solicitacoes);
			listaDeAtendimentos.setAdapter(adaptador);
		}
	}

	public Atendimento getDuvida() {
		Atendimento solicitacao = null;

		int pos = listaDeAtendimentos.getCheckedItemPosition();

		if (pos != ListView.INVALID_POSITION) {
			solicitacao = (Atendimento) listaDeAtendimentos.getItemAtPosition(pos);
		}
		return solicitacao;
	}

	public void cancelarAtendimento() {
		int pos = listaDeAtendimentos.getCheckedItemPosition();
		Atendimento solicitacao = (Atendimento) listaDeAtendimentos.getItemAtPosition(pos);
		ExecutorCancelamento executor = new ExecutorCancelamento(solicitacao.getIdAtendimento()+"", this);
		executor.execute();
	}

}
