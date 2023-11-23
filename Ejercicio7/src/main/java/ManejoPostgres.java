import java.io.IOException;
import java.sql.*;

public class ManejoPostgres {

    public void insercionPiloto(Piloto p, Connection conexion) throws SQLException {

        String insertarPiloto = "INSERT INTO drivers (code, forename, surname, dob, nationality, constructorid, url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)" ;

       PreparedStatement sentencia = conexion.prepareStatement(insertarPiloto, PreparedStatement.RETURN_GENERATED_KEYS);
            // Añadir el parámetro PreparedStatement.RETURN_GENERATED_KEYS nos permite recuperar las claves generadas

            sentencia.setString(1, p.getCode());
            sentencia.setString(2, p.getForename());
            sentencia.setString(3, p.getSurname());
            sentencia.setDate(4, p.getDob());
            sentencia.setString(5, p.getNationality());
            sentencia.setInt(6, p.getConstructorid());
            sentencia.setString(7, p.getUrl());

            sentencia.executeUpdate();

            ResultSet rs = sentencia.getGeneratedKeys();
            rs.next();
            int driverid = rs.getInt(1);
            System.out.println("El driver id nuevo es "+driverid);
    }

    public int insercionEquipo(Equipo e, Connection conexion) throws SQLException {
        String insertarEquipo = "INSERT INTO constructors (constructorref, name, nationality, url) " +
                "VALUES (?, ?, ?, ?)"+
                " ON CONFLICT (constructorid) DO NOTHING RETURNING constructorid;";
        int constructorid;

        PreparedStatement sentencia = conexion.prepareStatement(insertarEquipo, PreparedStatement.RETURN_GENERATED_KEYS);

            sentencia.setString(1, e.getConstructorref());
            sentencia.setString(2, e.getName());
            sentencia.setString(3, e.getNationality());
            sentencia.setString(4, e.getUrl());

            sentencia.executeUpdate();
            ResultSet rs = sentencia.getGeneratedKeys();
            rs.next();
            constructorid = rs.getInt(1);
            System.out.println("El id del equipo nuevo es "+ constructorid);

            return constructorid;
    }

    public void mostrarPilotos (Connection conexion) {
        String consulta = "SELECT * FROM drivers";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta);) {
            ResultSet resultados = sentencia.executeQuery();

            while (resultados.next()) {
                int id = resultados.getInt("driverid");
                String code = resultados.getString("code");
                String forename = resultados.getString("forename");
                String surname = resultados.getString("surname");
                String dob = resultados.getString("dob");
                String nationality = resultados.getString("nationality");
                String url = resultados.getString("url");

                System.out.println("Driver id: " + id + " -Cod: " + code + " -Nombre: " + forename + " -Apellido: " + surname + " -DOB: "+ dob+ " -Nacionalidad: " + nationality + " -URL: "+ url);
            }

        } catch (SQLException ex1) {
            System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());
        }
    }
    public void mostrarEquipos (Connection conexion) {
        String consulta = "SELECT * FROM constructors";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta);) {
            ResultSet resultados = sentencia.executeQuery();

            while (resultados.next()) {
                int id = resultados.getInt("constructorid");
                String ref = resultados.getString("constructorref");
                String name = resultados.getString("name");
                String nationality = resultados.getString("nationality");
                String url = resultados.getString("url");

                System.out.println("Equipo id: " + id + " -Ref: " + ref + " -Nombre: " + name + " -Nacionalidad: " + nationality + " -URL: "+ url);
            }

        } catch (SQLException ex1) {
            System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());
        }
    }

    public void resultadoCarreraPiloto (String code, Connection conexion) {//

        try (CallableStatement resCarreraPiloto = conexion.prepareCall("{call get_results_by_driver(?) }");) {

            resCarreraPiloto.setString(1, code);

            ResultSet resultados = resCarreraPiloto.executeQuery();

            while (resultados.next()) {
                System.out.format(resultados.getInt("round") +
                        resultados.getString("circuit") +
                        resultados.getInt("result") +
                        resultados.getInt("points") +
                        resultados.getDate("date"));

            }

        } catch (SQLException ex1) {
            System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());

        }
    }
    public void clasificacionMundial (Connection conexion) {

        try (CallableStatement clasMundial = conexion.prepareCall("{call get_drivers_standings() }");) {

            ResultSet resultados = clasMundial.executeQuery();

            while (resultados.next()) {
                String driverName = resultados.getString("driver");
                long totalPoints = resultados.getLong("points");
                System.out.printf("Driver Name: %s, Total Points: %d%n", driverName, totalPoints);
            }

        } catch (SQLException ex1) {
            System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());

        }
    }
}