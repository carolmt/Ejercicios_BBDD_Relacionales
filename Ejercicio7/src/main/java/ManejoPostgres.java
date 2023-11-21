import java.sql.*;

public class ManejoPostgres {

    public void insercionPiloto(Piloto p, Connection conexion) {

        String insertarPiloto = "INSERT INTO drivers (code, forename, surname, dob, nationality, constructorid, url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(insertarPiloto, PreparedStatement.RETURN_GENERATED_KEYS);) {
            // Añadir el parámetro PreparedStatement.RETURN_GENERATED_KEYS nos permite recuperar las claves generadas

            sentencia.setString(1, p.getCode());
            sentencia.setString(2, p.getForename());
            sentencia.setString(3, p.getSurname());
            sentencia.setString(4, p.getDobString());
            sentencia.setString(5, p.getNationality());
            sentencia.setInt(6, p.getConstructorid());
            sentencia.setString(7, p.getUrl());

            sentencia.executeUpdate();
            sentencia.close();

            ResultSet rs = sentencia.getGeneratedKeys();
            rs.next();
            int driverid = rs.getInt(1);
            System.out.println("El driver id nuevo es "+driverid);

        } catch (SQLException ex1) {
            System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());
            try {
                conexion.rollback();
                System.err.println("ROLLBACK ejecutado");
            } catch (SQLException ex2) {
                System.err.println("Error haciendo ROLLBACK");
            }
        }
    }

    public void insercionEquipo(Equipo e, Connection conexion) {
        String insertarPiloto = "INSERT INTO constructors (constructorref, name, nationality, url) " +
                "VALUES (?, ?, ?, ?)";
        /*String insertarPiloto = "INSERT INTO constructors (constructorref, name, nationality, url) " +
                "VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (constructorref) DO NOTHING";*/

        try (PreparedStatement sentencia = conexion.prepareStatement(insertarPiloto, PreparedStatement.RETURN_GENERATED_KEYS);) {

            sentencia.setString(1, e.getConstructorref());
            sentencia.setString(2, e.getName());
            sentencia.setString(3, e.getNationality());
            sentencia.setString(4, e.getUrl());

            sentencia.executeUpdate();
            sentencia.close();

            ResultSet rs = sentencia.getGeneratedKeys();
            rs.next();
            int constructorid = rs.getInt(1);
            System.out.println("El id del equipo nuevo es "+ constructorid);

        } catch (SQLException ex1) {
            System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());
            try {
                conexion.rollback();
                System.err.println("ROLLBACK ejecutado");
            } catch (SQLException ex2) {
                System.err.println("Error haciendo ROLLBACK");
            }
        }
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

    public void resultadoCarreraPiloto (int driverid, Connection conexion) {//

        try (CallableStatement resCarreraPiloto = conexion.prepareCall("{call get_results_by_driver(?)}");) {


            resCarreraPiloto.setInt(1, driverid);
            resCarreraPiloto.execute();
            ResultSet resultados = resCarreraPiloto.executeQuery();

            resultados = resCarreraPiloto.getResultSet();

            while (resultados.next()) {
                System.out.format(resultados.getInt("driverid") +
                        resultados.getString("code") +
                        resultados.getString("forename") +
                        resultados.getDouble("surname") +
                        resultados.getString("dob") +
                        resultados.getString("nationality") +
                        resultados.getInt("constructorid") +
                        resultados.getString("url"));
            }

            conexion.commit();
            // Es buena práctica volver a activar la confirmación automática
            conexion.setAutoCommit(true);
        } catch (SQLException ex1) {
            System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());

        }
    }
    public void clasificacionMundial (Connection conexion) {

        try (CallableStatement clasMundial = conexion.prepareCall("{get_drivers_standings()}");) {

            clasMundial.execute();
            ResultSet resultados = clasMundial.executeQuery();

            resultados = clasMundial.getResultSet();

            while (resultados.next()) {
                int posicion = 1;
                System.out.println(posicion+".");
                System.out.format(resultados.getInt("driverid") +
                        resultados.getString("code") +
                        resultados.getString("forename") +
                        resultados.getDouble("surname") +
                        resultados.getString("dob") +
                        resultados.getString("nationality") +
                        resultados.getInt("constructorid") +
                        resultados.getString("url"));
                posicion ++;
            }

            conexion.commit();
            conexion.setAutoCommit(true);
        } catch (SQLException ex1) {
            System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());

        }
    }
}