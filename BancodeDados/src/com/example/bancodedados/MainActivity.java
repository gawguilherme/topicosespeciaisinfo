package com.example.bancodedados;

import android.support.v7.app.ActionBarActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	private EditText edtFabricante;
	private EditText edtModelo;
	private EditText edtAno;
	private EditText edtValor;
	private Button btInsere;
	private Button btConsulta;
	private Button btDeleta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edtFabricante = (EditText) findViewById(R.id.edtFabricante);
		edtModelo = (EditText) findViewById(R.id.edtModelo);
		edtAno = (EditText) findViewById(R.id.edtAno);
		edtValor = (EditText) findViewById(R.id.edtValor);
		btInsere = (Button) findViewById(R.id.btInsere);
		btConsulta = (Button) findViewById(R.id.btConsulta);
		btDeleta = (Button) findViewById(R.id.btDeleta);

		btInsere.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				btInsereOnClick();

			}
		});

		btConsulta.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				btConsultaOnClick();

			}
		});

		btDeleta.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				btDeletaOnClick();

			}
		});

		// Acesso ao banco
		SQLiteDatabase db = openOrCreateDatabase("carros_db.db", MODE_PRIVATE, null);

		// Monta a string SQL
		StringBuilder SQL = new StringBuilder();
		SQL.append("CREATE TABLE IF NOT EXISTS [Veiculos] ( ");
		SQL.append("[Fabricante] VARCHAR(50) NOT NULL, ");
		SQL.append("[Modelo] VARCHAR (30) NOT NULL,");
		SQL.append("[Ano] SMALLINT NOT NULL, ");
		SQL.append("[Valor] DECIMAL (15, 2) NOT NUL DEFAULT 0 ) ");

	}

	private void btInsereOnClick() {

		SQLiteDatabase db = openOrCreateDatabase("carros_db.db", MODE_PRIVATE, null);

		StringBuilder SQL = new StringBuilder();
		SQL.append("INSERT INTO VEICULOS ");
		SQL.append("(FABRICANTE, MODELO, ANO, VALOR) ");
		SQL.append("values ('" + edtFabricante.getText().toString() + "',");
		SQL.append("'" + edtModelo.getText().toString() + "',");
		SQL.append("" + edtAno.getText().toString() + ",");
		SQL.append("" + edtValor.getText().toString() + ")");

		db.execSQL(SQL.toString());
		Toast.makeText(this, "Gravado", 2000).show();
		
		
	}

	private void btConsultaOnClick() {

		SQLiteDatabase db = openOrCreateDatabase("carros_db.db", MODE_PRIVATE, null);

		String modelo[] = new String[] { edtModelo.getText().toString() };

		Cursor c = db.query("Veiculos", new String[] { "Fabricante", "Modelo", "Ano", "Valor" }, "Modelo=?", modelo,
				null, null, null);

		if (c.getCount() > 0) {
			c.moveToFirst();
			edtFabricante.setText(c.getString(0));
			edtModelo.setText(c.getString(1));
			edtAno.setText(c.getString(2));
			edtValor.setText(c.getString(3));
			Toast.makeText(this, "Para o modelo apresentado há " + String.valueOf(c.getCount()) + " registros.", 2000)
					.show();
		} else {
			edtFabricante.setText("");
			edtModelo.setText("");
			edtAno.setText("");
			edtValor.setText("");

			Toast.makeText(this, "Não há registros para o critério", 2000).show();

		}

	}

	private void btDeletaOnClick() {

		SQLiteDatabase db = openOrCreateDatabase("carros_db.db", MODE_PRIVATE, null);

		StringBuilder SQL = new StringBuilder();
		SQL.append("DELETE FROM VEICULOS ");
		SQL.append("WHERE FABRICANTE = '" + edtFabricante.getText().toString() + "' AND ");
		SQL.append("MODELO = '" + edtModelo.getText().toString() + "' AND ");
		SQL.append("ANO = " + edtAno.getText().toString() + " AND ");
		SQL.append("VALOR = " + edtValor.getText().toString());

		db.execSQL(SQL.toString());

		Toast.makeText(this, "Registro Deletado !", 2000).show();

		edtFabricante.setText("");
		edtModelo.setText("");
		edtAno.setText("");
		edtValor.setText("");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
