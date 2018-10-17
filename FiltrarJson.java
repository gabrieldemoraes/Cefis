package Exercicio.Exercicio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FiltrarJson {
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static String readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream entrada = new URL(url).openStream();
		try {
			BufferedReader leitura = new BufferedReader(new InputStreamReader(entrada, Charset.forName("UTF-8")));
			String textoJson = readAll(leitura);
			return textoJson;
		} 
		finally {
			entrada.close();
		}
	}
	
	public static void buscarCursos() throws JSONException, IOException {
		String stringJson = readJsonFromUrl("https://cefis.com.br/api/v1/event");
		  
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		
		JsonObject objectParsed = parser.parse(stringJson).getAsJsonObject();
		JsonArray jsonArrayCursos = objectParsed.getAsJsonArray("data");
		List<Curso> cursos = new ArrayList<Curso>();
		
		for (int i=0; i < jsonArrayCursos.size(); i++){
			JsonObject jsonObjCurso = jsonArrayCursos.get(i).getAsJsonObject();
			cursos.add(gson.fromJson(jsonObjCurso.toString(), Curso.class)); 
		}
		char a = '"';
		for (int i = 0; i < cursos.size() ; i++) {
			System.out.println("{" + a + "Curso" + a + ": {" + a + "Titulo" + a + ": " + a + cursos.get(i).getTitle() + a + "," + a + "Banner" + a + ": "+ a + cursos.get(i).getBanner() + a + "," + a + "Id" + a + ": " + a +cursos.get(i).getId() + a + "}}");
		}
	}
	
	public static void buscarCursoEspecifico() throws JSONException, IOException {
		String codigoCurso = "1089";
		
		String stringJson = readJsonFromUrl("https://cefis.com.br/api/v1/event/" + codigoCurso +"?include=classes");

		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		
		JsonObject objectParsed = parser.parse(stringJson).getAsJsonObject();
		JsonObject jsonArrayCursos = objectParsed.getAsJsonObject("data");
		
		Curso cursoEspec = gson.fromJson(jsonArrayCursos.toString(), Curso.class); 
		
		char a = '"';
		
		System.out.println("{" + a + "Curso" + a + ": {" + a + "Titulo" + a + ": " + a + cursoEspec.getTitle() + a + "," + a + "Banner" + a + ": "+ a + cursoEspec.getBanner() + a + "," + a + "Titulo das aulas" + a + ": " + a + cursoEspec.getSubtitle() + a + "}}");
	
	}

	public static void main(String[] args) throws JSONException, IOException{
		buscarCursoEspecifico();
		//buscarCursos();
		
	}
}
