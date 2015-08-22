package br.edu.ifba.mobile.atendimento.fragmentos;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import br.edu.ifba.mobile.atendimento.MainActivity;
import br.edu.ifba.mobile.atendimento.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentoConfiguracao extends Fragment {

	private static final int SEC_NUMBER = 3;

	private static final String ARG_SECTION_NUMBER = "section_number";
	private static FragmentoConfiguracao fragmento;

	public static FragmentoConfiguracao getInstancia() {
		if (fragmento == null) {
			fragmento = new FragmentoConfiguracao();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, SEC_NUMBER);
			fragmento.setArguments(args);
		}

		return fragmento;
	}
	
	View rootView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_configuracoes, container,
				false);

		setHasOptionsMenu(true);

		return rootView;
	}
	
	public String getCpf(){
		EditText texto = (EditText)rootView.findViewById(R.id.campoCpf);
		String cpf2 = texto.getText().toString();
		return cpf2;
	}

	@Override
	 public void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater inflater) {
		inflater.inflate(R.menu.gravar, menu);
	};
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}
}
