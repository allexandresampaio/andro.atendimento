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
import br.edu.ifba.mobile.atendimento.tasks.ExecutorPedido;
import br.edu.ifba.mobile.atendimento.tasks.SincronizadorLista;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentoTiposDuvida extends Fragment {

	private static final int SEC_NUMBER = 1;

	private static final String ARG_SECTION_NUMBER = "section_number";
	private static FragmentoTiposDuvida fragmento;

	public static FragmentoTiposDuvida getInstancia() {
		if (fragmento == null) {
			fragmento = new FragmentoTiposDuvida();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, SEC_NUMBER);
			fragmento.setArguments(args);
		}

		return fragmento;
	}

	ListView listaDeDuvidas = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tiposduvida, container,
				false);

		listaDeDuvidas = (ListView) rootView.findViewById(
				R.id.configuracoes);
		listaDeDuvidas.setClickable(true);
		listaDeDuvidas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		fragmento.atualizarLista();

		setHasOptionsMenu(true);
		
		return rootView;
	}

	@Override
	 public void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
	};
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}

	public void atualizarLista() {
		SincronizadorLista sincronizador = new SincronizadorLista(this);
		sincronizador.execute();
	}

	public void atualizarListaTiposDeDuvida(List<String> tiposDeDuvida) {
		if ((tiposDeDuvida != null) && (!tiposDeDuvida.isEmpty())) {
			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
					this.getActivity(),
					android.R.layout.simple_list_item_single_choice,
					tiposDeDuvida);

			listaDeDuvidas.setAdapter(adaptador);

		}
	}

	public String getAtendimento() {
		String solicitacao = null;

		int pos = listaDeDuvidas.getCheckedItemPosition();

		if (pos != ListView.INVALID_POSITION) {
			solicitacao = (String) listaDeDuvidas.getItemAtPosition(pos);

		}

		return solicitacao;
	}
	
	public void realizarSolicitacao(String cpf) {
		ExecutorPedido executor = new ExecutorPedido(cpf, this);
		executor.execute();
	}

}
