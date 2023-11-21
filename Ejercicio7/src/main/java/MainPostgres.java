import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainPostgres {
    public static void main(String[] args) {
        String urlConexion = "jdbc:postgresql://ad-ej7.co6njyzhm85i.us-east-1.rds.amazonaws.com:5432/f12006";
        String usuario = "postgres";
        String password = "Hola1234";

        try (Connection conexion = DriverManager.getConnection(urlConexion, usuario, password)) {
            try { // Por defecto, el gestor de base de datos ejecuta una operación de confirmación después de la ejecución de cada sentencia de SQL.
                // Para desactivar la confirmación automática e iniciar así una transacción, invocamos el método Connection.setAutoCommit(false)
                conexion.setAutoCommit(false);






            } catch (SQLException ex1) {
                System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());
                try {
                    // Deshacemos las operaciones realizadas en la base de datos
                    conexion.rollback();
                    System.err.println("ROLLBACK ejecutado");
                } catch (SQLException ex2) {
                    System.err.println("Error haciendo ROLLBACK");
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }
}
