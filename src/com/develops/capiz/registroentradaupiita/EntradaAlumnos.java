package com.develops.capiz.registroentradaupiita;

import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EntradaAlumnos extends SQLiteOpenHelper {

	public EntradaAlumnos(Context context) {
		super(context, "EntradaAlumnos", null, 1);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	@Override
	public void onCreate(SQLiteDatabase dataBase) {
		// TODO Apéndice de método generado automáticamente
		dataBase.execSQL("create table Alumno(Boleta TEXT PRIMARY KEY,Nombre TEXT, Apaterno TEXT, Amaterno TEXT, Carrera TEXT, Correo TEXT, Escuela TEXT);");
		dataBase.execSQL("create table RegistroEntrada(Boleta TEXT PRIMARY KEY, Fecha TEXT, Entrada NUMERIC, Salida NUMERIC, Asunto TEXT, Observaciones TEXT,FOREIGN KEY(Boleta) REFERENCES Alumno(Boleta));");
		dataBase.execSQL("create table Admin(Boleta TEXT PRIMARY KEY, Nombre TEXT,Password TEXT,FOREIGN KEY(Boleta) REFERENCES Alumno(Boleta));");
		ContentValues values = new ContentValues();
		values.put("Boleta", "2011640347");
		values.put("Nombre", "Juan");
		values.put("Apaterno", "Capiz");
		values.put("Amaterno", "Castro");
		values.put("Carrera", "Telemática");
		values.put("Correo", "chesco134@gmail.com");
		values.put("Escuela", "UPIITA");
		dataBase.insert("Alumno", "---", values);
		ContentValues values2 = new ContentValues();
		values2.put("Boleta", "2011640347");
		values2.put("Nombre", "Admin");
		values2.put("Password", "123");
		dataBase.insert("Admin", "---", values2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Apéndice de método generado automáticamente

	}

	public boolean insertaRegistroAlumno(String Boleta, String[] datos) {
		if (datos.length != 6)
			return false;
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Boleta", Boleta);
		values.put("Nombre", datos[0]);
		values.put("Apaterno", datos[1]);
		values.put("Amaterno", datos[2]);
		values.put("Carrera", datos[3]);
		values.put("Correo", datos[4]);
		values.put("Escuela", datos[5]);
		long id = db.insert("Alumno", "---", values);
		db.close();
		if (id == -1)
			return false;
		else
			return true;
	}

	public String insertaRegistroAdmin(String Boleta, String[] datos) {
		String result = null;
		if (datos.length != 2)
			return result;
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Boleta", Boleta);
		values.put("Nombre", datos[0]);
		values.put("Password", datos[1]);
		long id = db.insert("Admin", "---", values);
		if (id == -1)
			result = "Nope";
		else
			result = "OK";
		db.close();
		return result;
	}

	public LinkedList<String> consultaMacho(String[] columns, String selection,
			String[] selVals) {
		LinkedList<String> results = new LinkedList<String>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query("Admin", columns, selection, selVals, null,
				null, null, null);
		while (cursor.moveToNext()) {
			String aux = new String();
			for (int i = 0; i < cursor.getColumnCount(); i++) {
				aux = aux.concat(cursor.getString(i) + " \\_");
			}
			results.add(aux);
		}
		db.close();
		return results;
	}

	public LinkedList<String> consultaBoleta(String Boleta) {
		LinkedList<String> results = new LinkedList<String>();
		SQLiteDatabase db = getReadableDatabase();
		String[] aux = { Boleta.toString() };
		Cursor cursor = db.query("RegistroEntrada", null, "Boleta", aux, null,
				null, null, null);
		if (cursor.getCount() > 1) {
			results.add("La boleta estuvo dos veces");
		}
		while (cursor.moveToNext()) {
			try {
				results.add(cursor.getInt(0) + "\n" + cursor.getString(1)
						+ "\n" + cursor.getString(2) + "\n"
						+ cursor.getString(3) + "\n" + cursor.getString(4)
						+ "\n" + cursor.getString(5) + "\n"
						+ cursor.getString(6));
			} catch (Exception e) {
				results.add("ERROR\n" + e.toString());
			}
		}
		db.close();
		return results;
	}

	public boolean registrarEncargado(String boleta, String nombre,
			String aPaterno, String aMaterno, String carrera, String correo,
			String telefono, String horario, String tareas, String ubicacion,
			String contrasena) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues registroA = new ContentValues();
		registroA.put("boleta", boleta);
		registroA.put("nombre", nombre);
		registroA.put("aPaterno", aPaterno);
		registroA.put("aMaterno", aMaterno);
		registroA.put("carrera", carrera);
		registroA.put("correo", correo);
		db.insert("alumno", null, registroA);

		ContentValues registroB = new ContentValues();
		registroB.put("boleta", boleta);
		registroB.put("telefono", telefono);
		registroB.put("horario", horario);
		registroB.put("ubicacion", ubicacion);
		registroB.put("tareas", tareas);
		registroB.put("contrasena", contrasena);
		registroB.put("perfil", "0");
		long value = db.insert("brigadista", null, registroB);
		db.close();
		if (value == -1)
			return false;
		else
			return true;
	}

	public boolean registrarVisitante(String matricula, String horaE,
			String horaS, String fecha, String objetos, String asunto,
			String perfil) {
		SQLiteDatabase db = getWritableDatabase();
		long value;
		if (perfil.equalsIgnoreCase("")) {
			ContentValues registroA = new ContentValues();
			registroA.put("boleta", matricula);
			registroA.put("fecha", fecha);
			registroA.put("horaEntrada", horaE);
			registroA.put("horaSalida", horaS);
			registroA.put("objetos", objetos);
			registroA.put("asunto", asunto);
			value = db.insert("registroalumno", null, registroA);
		} else {
			ContentValues registroB = new ContentValues();
			registroB.put("numeroEmpleado", matricula);
			registroB.put("fecha", fecha);
			registroB.put("horaEntrada", horaE);
			registroB.put("horaSalida", horaS);
			registroB.put("objetos", objetos);
			registroB.put("asunto", asunto);
			registroB.put("tipo", perfil);
			value = db.insert("registrootros", null, registroB);
		}
		db.close();
		if (value == -1)
			return false;
		else
			return true;
	}

	public boolean registrarMinuta(String fecha, String horaI, String horaF,
			String lugar, String asunto) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues registroA = new ContentValues();
		registroA.put("fecha", fecha);
		registroA.put("horaInicio", horaI);
		registroA.put("horaFinal", horaF);
		registroA.put("lugar", lugar);
		registroA.put("asunto", asunto);
		long value = db.insert("minuta", null, registroA);		
		db.close();
		if(value == -1)
			return false;
		else
			return true;
	}

	public LinkedList<String> consultaEncargados() {
		LinkedList<String> results = new LinkedList<String>();
		SQLiteDatabase db = getWritableDatabase();
		Cursor filaA = db.rawQuery("select boleta,telefono from brigadista ",
				null);
		if (filaA != null) {
			while (filaA.moveToNext()) {
				String aux = new String();
				for (int i = 0; i < filaA.getColumnCount(); i++) {
					aux = aux.concat(filaA.getString(i) + " \\_");
				}
				results.add(aux);
			}
		}
		db.close();
		if (filaA == null)
			return null;
		else
			return results;
	}

	public LinkedList<String> consultaVisitantes(String fecha) {
		LinkedList<String> results = new LinkedList<String>();
		SQLiteDatabase db = getWritableDatabase();
		Cursor filaA = db
				.rawQuery(
						"select boleta,horaEntrada,horaSalida,objetos,asunto from registroalumno where fecha="
								+ fecha, null);
		Cursor filaB = db
				.rawQuery(
						"select numeroEmpleado,horaEntrada,horaSalida,objetos,asunto,tipo from registrootros where fecha="
								+ fecha, null);
		if (filaA != null) {
			while (filaA.moveToNext()) {
				String aux = new String();
				for (int i = 0; i < filaA.getColumnCount(); i++) {
					aux = aux.concat(filaA.getString(i) + " \\_");
				}
				results.add(aux);
			}
		}
		if (filaB != null) {
			while (filaB.moveToNext()) {
				String aux = new String();
				for (int i = 0; i < filaB.getColumnCount(); i++) {
					aux = aux.concat(filaB.getString(i) + " \\_");
				}
				results.add(aux);
			}
		}
		db.close();
		if (filaA == null && filaB == null)
			return null;
		else
			return results;
	}

	public LinkedList<String> consultaHistorial(String matricula) {
		LinkedList<String> results = new LinkedList<String>();
		SQLiteDatabase db = getWritableDatabase();
		Cursor filaA = db
				.rawQuery(
						"select boleta,fecha,horaEntrada,horaSalida,objetos,asunto from registroalumno where boleta="
								+ matricula, null);
		Cursor filaB = db
				.rawQuery(
						"select numeroEmpleado,fecha,horaEntrada,horaSalida,objetos,asunto,tipo from registrootros where numeroEmpleado="
								+ matricula, null);
		if (filaA != null) {
			while (filaA.moveToNext()) {
				String aux = new String();
				for (int i = 0; i < filaA.getColumnCount(); i++) {
					aux = aux.concat(filaA.getString(i) + " \\_");
				}
				results.add(aux);
			}
		}
		if (filaB != null) {
			while (filaB.moveToNext()) {
				String aux = new String();
				for (int i = 0; i < filaB.getColumnCount(); i++) {
					aux = aux.concat(filaB.getString(i) + " \\_");
				}
				results.add(aux);
			}
		}

		db.close();
		if (filaA == null && filaB == null)
			return null;
		else
			return results;
	}

	public LinkedList<String> consultaMinutas(String fecha) {
		LinkedList<String> results = new LinkedList<String>();
		SQLiteDatabase db = getWritableDatabase();
		Cursor filaA = db.rawQuery(
				"select horaInicio,horaFinal,lugar,asunto from minuta where fecha="
						+ fecha, null);
		if (filaA != null) {
			while (filaA.moveToNext()) {
				String aux = new String();
				for (int i = 0; i < filaA.getColumnCount(); i++) {
					aux = aux.concat(filaA.getString(i) + " \\_");
				}
				results.add(aux);
			}
		}
		db.close();
		if (filaA == null)
			return null;
		else
			return results;
	}

	public boolean modificarEncargado(String boleta, String nombre,
			String aPaterno, String aMaterno, String carrera, String correo,
			String telefono, String horario, String tareas, String ubicacion,
			String contrasena) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues registroA = new ContentValues();
		registroA.put("boleta", boleta);
		registroA.put("nombre", nombre);
		registroA.put("aPaterno", aPaterno);
		registroA.put("aMaterno", aMaterno);
		registroA.put("carrera", carrera);
		registroA.put("correo", correo);

		ContentValues registroB = new ContentValues();
		registroB.put("boleta", boleta);
		registroB.put("telefono", telefono);
		registroB.put("horario", horario);
		registroB.put("ubicacion", ubicacion);
		registroB.put("tareas", tareas);
		registroB.put("contrasena", contrasena);
		registroB.put("perfil", "0");
		int cant = db.update("brigadista", registroB, "boleta=" + boleta, null);
		db.close();
		if (cant == -1) {
			db.update("alumno", registroA, "boleta=" + boleta, null);
			return false;
		} else
			return true;// Toast.makeText(this,"No existe un estudiante con esa boleta",Toast.LENGTH_SHORT).show();
	}

	public boolean eliminarEncargados(String boleta) {
		SQLiteDatabase db = getWritableDatabase();
		String[] cosa = { boleta };
		int cant = db.delete("brigadista", "boleta=?", cosa);
		db.close();
		if (cant == -1)
			return false;// Toast.makeText(this,"Se borro el estudiante",Toast.LENGTH_SHORT).show();
		else
			return true;// Toast.makeText(this,"No existe un estudiante con esa boleta",Toast.LENGTH_SHORT).show();
	}
}
