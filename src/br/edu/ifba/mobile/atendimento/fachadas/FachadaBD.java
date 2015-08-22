package br.edu.ifba.mobile.atendimento.fachadas;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.mobile.atendimento.bean.Atendimento;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class FachadaBD extends SQLiteOpenHelper {

	private static FachadaBD instancia = null;

	public static FachadaBD getInstancia(Context contexto) {
		if (instancia == null) {
			instancia = new FachadaBD(contexto);
		}
		return instancia;
	}
	
	public static FachadaBD getInstancia() {
		return instancia;
	}

	private static final String NOME_BANCO = "atendimento";
	private static final int VERSAO = 1;

	private FachadaBD(Context contexto) {
		super(contexto, NOME_BANCO, null, VERSAO);
	}

	private static final String CMD_CRIACAO_TABELA_SOLICITACOES = "CREATE TABLE SOLICITACOES ("
			+ "ID INTEGER PRIMARY KEY, "
			+ "ATENDIMENTO TEXT, "
			+ "ATENDENTE TEXT, "
			+ "CPF TEXT, "
			+ "HORARIO TEXT)";
	
	private static final String CMD_CRIACAO_TABELA_CPF = "CREATE TABLE TABELACPF ("
			+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "CPF TEXT)";
	
	private static final String CMD_SELECIONAR_TODOS_ATENDIMENTOS = "SELECT ID, ATENDIMENTO, ATENDENTE,"
			+ "CPF, HORARIO FROM SOLICITACOES";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CMD_CRIACAO_TABELA_SOLICITACOES);
		db.execSQL(CMD_CRIACAO_TABELA_CPF);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// READ/SELECT
	public List<Atendimento> getAtendimentos() {
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(CMD_SELECIONAR_TODOS_ATENDIMENTOS, null);
		if (c != null) {
			boolean temProximo = c.moveToFirst();
			while (temProximo) {
				Atendimento atendimento = new Atendimento(
						c.getString(c.getColumnIndex("CPF")),
						c.getString(c.getColumnIndex("ATENDIMENTO")),
						c.getString(c.getColumnIndex("ATENDENTE")),
						c.getInt(c.getColumnIndex("ID")),
						c.getString(c.getColumnIndex("HORARIO")));
				
				atendimentos.add(atendimento);
				temProximo = c.moveToNext();
			}
		}

		return atendimentos;
	}

	// CREATE/INSERT
	public long inserir(Atendimento atendimento) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues valores = new ContentValues();
		valores.put("ID", atendimento.getIdAtendimento());
		valores.put("ATENDIMENTO", atendimento.getTipoAtendimento());
		valores.put("ATENDENTE", atendimento.getNomeAtendente());
		valores.put("CPF", atendimento.getCpf());
		valores.put("HORARIO", atendimento.getHoraAtendimento());
		
		return db.insert("SOLICITACOES", null, valores);
	}

	// UPDATE
	public long atualizar(Atendimento atendimento) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues valores = new ContentValues();
		valores.put("ID", atendimento.getIdAtendimento());
		valores.put("ATENDIMENTO", atendimento.getTipoAtendimento());
		valores.put("ATENDENTE", atendimento.getNomeAtendente());
		valores.put("CPF", atendimento.getCpf());
		valores.put("HORARIO", atendimento.getHoraAtendimento());
		
		return db.update("SOLICITACOES", valores, "ID = ?",
				new String[] { atendimento.getIdAtendimento() + "" });
	}

	// DELETE
	public void apagar(String id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete("SOLICITACOES", "ID = ?",
				new String[] { id });
	}
	
	public void setCPF(String cpf){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS TABELACPF");
		db.execSQL(CMD_CRIACAO_TABELA_CPF);
		ContentValues valores = new ContentValues();
		valores.put("CPF", cpf);
		db.insert("TABELACPF", null, valores);
	}
	
	public String getCPF(){
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery("SELECT ID, CPF FROM TABELACPF ORDER BY ID", null);
			c.moveToLast();
			try {
				String retorno = c.getString(c.getColumnIndex("CPF"));
				return retorno;
			}
			catch (Exception e){
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(null, 
					"Erro ao buscar CPF.", Toast.LENGTH_LONG).show();
			return "";
		}
	}
}
