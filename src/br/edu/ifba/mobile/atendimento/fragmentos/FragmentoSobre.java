package br.edu.ifba.mobile.atendimento.fragmentos;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.edu.ifba.mobile.atendimento.MainActivity;
import br.edu.ifba.mobile.atendimento.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentoSobre extends Fragment {

	private static final int SEC_NUMBER = 4;

	private static final String ARG_SECTION_NUMBER = "section_number";
	private static FragmentoSobre fragmento;

	public static FragmentoSobre getInstancia() {
		if (fragmento == null) {
			fragmento = new FragmentoSobre();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, SEC_NUMBER);
			fragmento.setArguments(args);
		}

		return fragmento;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sobre, container,
				false);

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}
}
