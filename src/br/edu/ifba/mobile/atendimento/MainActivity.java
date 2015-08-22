package br.edu.ifba.mobile.atendimento;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import br.edu.ifba.mobile.atendimento.fachadas.FachadaBD;
import br.edu.ifba.mobile.atendimento.fragmentos.FragmentoAtendimentos;
import br.edu.ifba.mobile.atendimento.fragmentos.FragmentoConfiguracao;
import br.edu.ifba.mobile.atendimento.fragmentos.FragmentoSobre;
import br.edu.ifba.mobile.atendimento.fragmentos.FragmentoTiposDuvida;
import br.edu.ifba.mobile.atendimento.tasks.GravadorCPF;
import br.edu.ifba.mobile.atendimento.tasks.RecuperadorCPF;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	private static String CPF = "";

	private NavigationDrawerFragment navegador;

	private CharSequence mTitle;

	public static void setCPF(String cpf) {
		CPF = cpf;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FachadaBD.getInstancia(this);

		navegador = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		navegador.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		Fragment fragmento = null;
		FragmentManager fragmentManager = getSupportFragmentManager();

		if (position == 0) {
			fragmento = FragmentoTiposDuvida.getInstancia();
		} else if (position == 1) {
			fragmento = FragmentoAtendimentos.getInstancia();
		} else if (position == 2) {
			fragmento = FragmentoConfiguracao.getInstancia();
		} else if (position == 3) {
			fragmento = FragmentoSobre.getInstancia();
		}

		fragmentManager.beginTransaction().replace(R.id.container, fragmento)
				.commit();

	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!navegador.isDrawerOpen()) {
			// getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.solicitarAtendimento) {
			Toast.makeText(this, "Solicitando atendimento...", 
					Toast.LENGTH_SHORT).show();
			RecuperadorCPF executor = new RecuperadorCPF();
			executor.execute();
			if (ValidaCPF.isCPF(CPF)){
				FragmentoTiposDuvida.getInstancia().realizarSolicitacao(CPF);
			}
			else {
				Toast.makeText(this, "CPF inválido!",
						Toast.LENGTH_SHORT).show();
			}
			return true;
		} 
		
		else if (item.getItemId() == R.id.cancelarSolicitacao) {
			Toast.makeText(this, "Cancelando atendimento...",
					Toast.LENGTH_SHORT).show();

			FragmentoAtendimentos.getInstancia().cancelarAtendimento();
		} 
		
		else if (item.getItemId() == R.id.gravar) {
			Toast.makeText(this, "Gravando CPF...",
					Toast.LENGTH_SHORT).show();
			try {
				GravadorCPF executor = new GravadorCPF();
				executor.execute();
				//MainActivity.setCPF((String)FragmentoConfiguracao.getInstancia().getCpf());
				//Toast.makeText(this, "CPF salvo!",
				//		Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(this, "Erro ao gravar!",
						Toast.LENGTH_SHORT).show();
			}
		}

		return super.onOptionsItemSelected(item);
	}
}
