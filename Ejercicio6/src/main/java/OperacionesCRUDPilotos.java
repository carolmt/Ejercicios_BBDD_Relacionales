import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OperacionesCRUDPilotos {
    public OperacionesCRUDPilotos() {
    }

    public void CrearPiloto(Piloto p, Connection conexion) throws SQLException {
        // Creamos ahora una sentencia de modificación, en este caso un INSERT
        String insercionSQL = "INSERT INTO drivers (code, forename, surname, dob, nationality, url) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement insercion = conexion.prepareStatement(insercionSQL);
        insercion.setString(1, p.getCode());
        insercion.setString(2, p.getForename());
        insercion.setString(3, p.getSurname());
        insercion.setString(4, p.devolverStringDob(p.getDob()));
        insercion.setString(5, p.getNationality());
        insercion.setString(6, p.getUrl());

        //Ejecutamos la sentencia DML y recogemos el número de filas afectadas, si quisiéramos utilizarlo a posteriori
        int filasAfectadas = insercion.executeUpdate();
        System.out.println("Filas afectadas: " + filasAfectadas);
        System.out.println("Piloto creado con éxito.");
        insercion.close();
    }
    public Piloto LeerPiloto(int driverid, Connection conexion) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Preparamos una sentencia que lanzaremos a través del PreparedStatement
            String consultaSQL = "SELECT driverid, code, forename, surname, dob, nationality, url " +
                                    "FROM drivers " +
                                    "WHERE driverid = ? ";

        PreparedStatement consulta = conexion.prepareStatement(consultaSQL);
        consulta.setInt(1, driverid); //aqui le digo que el primer ? es el driverid.

        ResultSet resultados = consulta.executeQuery(); //la consulta SELECT se ejecuta pasándola por el método executeQuery. Si la consulta devuelve datos,
                                                        // estos estarán accesibles a través de un "conjunto de resultados" (ResultSet)
        int id = resultados.getInt("driverid");
        String code = resultados.getString("code");//podemos obtener el valor de cada uno de ellos con rs.getString(x) o rs.getInt(x),
        String forename = resultados.getString("forename");  // donde x puede ser la posición de la columna (empezando con 1) o el nombre del campo (indiferente de mayúsculas o minúsculas
        String surname = resultados.getString("surname");
        String dob = resultados.getString("dob");
        String nationality = resultados.getString("nationality");
        String url = resultados.getString("url");

        LocalDate dob_nuevo = LocalDate.parse(dob, formatter);
        Piloto p = new Piloto(code, forename, surname, dob_nuevo, nationality, url);

        consulta.close();
        return p;
    }
    public List<Piloto> LeerPilotos(Connection conexion) throws SQLException {//LeerPilotos(), que devuelva un listado completo de objetos Piloto
        List<Piloto> listaPilotos = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String consultaSQL = "SELECT code, forename, surname, dob, nationality, url " +
                "FROM drivers ";

        PreparedStatement consulta = conexion.prepareStatement(consultaSQL);
        ResultSet resultados = consulta.executeQuery();
        while (resultados.next()) {
            String code = resultados.getString("code");
            String forename = resultados.getString("forename");
            String surname = resultados.getString("surname");
            String dob = resultados.getString("dob");
            String nationality = resultados.getString("nationality");
            String url = resultados.getString("url");
            LocalDate dob_nuevo = LocalDate.parse(dob, formatter);
            Piloto pilot = new Piloto(code, forename, surname, dob_nuevo, nationality, url);
            listaPilotos.add(pilot);
            System.out.println(pilot.getForename()+" añadido a la lista de pilotos.");
        }
        consulta.close();
        return listaPilotos;
    }
    public void ActualizarPiloto(Piloto p, Connection conexion) throws SQLException {//ActualizarPiloto(), recibe objeto Piloto y actualice los datos del registro coincidente con driverid.

        String insercionSQL = "REPLACE INTO drivers (driverid, code, forename, surname, dob, nationality, url) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement insercion = conexion.prepareStatement(insercionSQL);

        insercion.setInt(1, p.getDriverid());
        insercion.setString(2, p.getCode());
        insercion.setString(3, p.getForename());
        insercion.setString(4, p.getSurname());
        insercion.setString(5, p.getDobString());
        insercion.setString(6, p.getNationality());
        insercion.setString(7, p.getUrl());

        //Ejecutamos la sentencia DML y recogemos el número de filas afectadas, si quisiéramos utilizarlo a posteriori
        int filasAfectadas = insercion.executeUpdate();

        System.out.println("Filas afectadas: " + filasAfectadas);
        insercion.close();
    }

    public void BorrarPiloto(Piloto p, Connection conexion) throws SQLException {
        String borradoSQL = "DELETE FROM drivers WHERE driverid = ?";
        PreparedStatement borrado = conexion.prepareStatement(borradoSQL);
        borrado.setInt(1, p.getDriverid());

        int filasAfectadas = borrado.executeUpdate();

        System.out.println("Filas afectadas: " + filasAfectadas);

        System.out.println("--------------------------------------------------------------------");
        System.out.println("Piloto " + p.getForename() + " borrado de la base de datos.");

        borrado.close();
    }
    public void MostrarClasificacionPiloto(Connection conexion) throws SQLException {//muestre la clasificación final del mundial ordenada por puntos de los pilotos.

        String consultaSQL = "SELECT d.forename, d.surname, SUM(r.points) AS puntos_totales "+
                             "FROM drivers as d "+
                             "JOIN results as r ON d.driverid = r.driverid "+
                "GROUP BY d.driverid, d.forename, d.surname "+
                             "ORDER BY puntos_totales DESC";

        PreparedStatement consulta = conexion.prepareStatement(consultaSQL);
        ResultSet resultados = consulta.executeQuery();

        System.out.println("Clasificación de Pilotos:");
        System.out.println("------------------------------");

        while (resultados.next()) {
            System.out.println("Nombre: " + resultados.getString("forename")+
            " Apellido: "+resultados.getString("surname")+
            " Puntos: " + resultados.getInt("puntos_totales"));
        }

        consulta.close();
    }
    public void MostrarClasificacionConstructores(Connection conexion) throws SQLException {

        String consultaSQL = "SELECT c.name AS constructor_name, SUM(r.points) AS puntos_totales\n" +
                             "FROM constructors c\n" +
                            "JOIN drivers d ON c.constructorid = d.constructorid\n" +
                            "JOIN results r ON d.driverid = r.driverid\n" +
                            "GROUP BY c.constructorid, constructor_name\n" +
                            "ORDER BY puntos_totales DESC";

        PreparedStatement consulta = conexion.prepareStatement(consultaSQL);
        ResultSet resultados = consulta.executeQuery();

        System.out.println("Clasificación de Equipos:");
        System.out.println("------------------------------");

        while (resultados.next()) {
            System.out.println("Nombre equipo: " + resultados.getString("constructor_name")+
                    " Puntos equipo: " + resultados.getInt("puntos_totales"));
        }

        consulta.close();
    }

}
