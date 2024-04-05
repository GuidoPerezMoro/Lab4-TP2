package utn.lab4.tp2;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

@SpringBootApplication
public class Tp2Application {

	public static void main(String[] args) {
		//SpringApplication.run(Tp2Application.class, args);
		System.out.println("\n--------------------GESTOR DE BASE DE DATOS JAVA CON SPRING--------------------\n");

		//Conexión a la base de datos
		String urlBBDD = "jdbc:mysql://localhost:3306/lab4_tp2";
		String username = "root";
		String password = "123456789";

		try {

			//Conexión a la BBDD
			Connection connectionBBDD = DriverManager.getConnection(urlBBDD,username,password);

			/* TEST
			PreparedStatement statementPrueba = connectionBBDD.prepareStatement("SELECT * FROM Pais WHERE codigoPais = 1");
			ResultSet resultSetPrueba = statementPrueba.executeQuery();
			System.out.println(resultSetPrueba.toString());
			*/


			//Recorrido de los distintos países en la URL
			for(int codigo = 1 ; codigo <=300; codigo++) {

				//URL: https://restcountries.com/v2/callingcode/{code}
				URL url = new URL("https://restcountries.com/v2/callingcode/" + codigo);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();

				//Verificamos que el país exista en la URL
				if (connection.getResponseCode() == 200) {
					//Recopilamos la información de la url en formato String
					StringBuilder jsonBruto = new StringBuilder();
					Scanner scanner = new Scanner(url.openStream());
					while (scanner.hasNext()) {
						jsonBruto.append(scanner.nextLine());
					}
					scanner.close();
					//System.out.println(jsonBruto.toString());

					//Convertimos la información a tipo JSON
					JSONArray jsonArray = new JSONArray(jsonBruto.toString());
					JSONObject jsonObject = jsonArray.getJSONObject(0);

					//System.out.println(jsonObject.toString());
					//System.out.println(jsonObject.getString("capital"));

					String nombrePais = jsonObject.getString("name");
					String capitalPais;
					if (jsonObject.has("capital"))
						capitalPais = jsonObject.getString("capital");
					else
						capitalPais = "";
					String region = jsonObject.getString("region");
					int poblacion = jsonObject.getInt("population");
					double latitud = jsonObject.getJSONArray("latlng").getDouble(0);
					double longitud = jsonObject.getJSONArray("latlng").getDouble(1);

					//Actualización de la BBDD
					PreparedStatement statement = connectionBBDD.prepareStatement("SELECT * FROM Pais WHERE codigoPais = ?");
					statement.setInt(1, codigo);
					ResultSet resultSet = statement.executeQuery();
					if (resultSet.next()) {
						// Si el país existe, actualizamos sus datos
						PreparedStatement updateStatement = connectionBBDD.prepareStatement("UPDATE Pais SET nombrePais = ?, capitalPais = ?, region = ?, poblacion = ?, latitud = ?, longitud = ? WHERE codigoPais = ?");
						updateStatement.setString(1, nombrePais);
						updateStatement.setString(2, capitalPais);
						updateStatement.setString(3, region);
						updateStatement.setInt(4, poblacion);
						updateStatement.setDouble(5, latitud);
						updateStatement.setDouble(6, longitud);
						updateStatement.setInt(7, codigo);
						updateStatement.executeUpdate();
					} else {
						// Si el país no existe, insertamos los nuevos datos
						PreparedStatement insertStatement = connectionBBDD.prepareStatement("INSERT INTO Pais (codigoPais, nombrePais, capitalPais, region, poblacion, latitud, longitud) VALUES (?, ?, ?, ?, ?, ?, ?)");
						insertStatement.setInt(1, codigo);
						insertStatement.setString(2, nombrePais);
						insertStatement.setString(3, capitalPais);
						insertStatement.setString(4, region);
						insertStatement.setInt(5, poblacion);
						insertStatement.setDouble(6, latitud);
						insertStatement.setDouble(7, longitud);
						insertStatement.executeUpdate();
						//System.out.println("Listo");
					}

				}
				//Si no se encontró el país, continúa con el siguiente
			}

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}