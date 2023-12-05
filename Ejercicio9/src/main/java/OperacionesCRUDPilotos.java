import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class OperacionesCRUDPilotos {

    public void CrearPiloto(Piloto p, MongoCollection<Piloto> collection){ //que reciba un objeto Piloto y lo añada a la base de datos.

        collection.insertOne(p);
        }

    public Piloto LeerPiloto(int driverid, MongoCollection<Piloto> collection) {
        Piloto d = new Piloto();

        d = collection.find(eq("driverid", driverid)).first();
        if (d == null)
            System.out.println("No se ha encontrado ningún piloto con el id " + driverid);

    return d;
    }

    public List<Piloto> LeerPilotos(MongoCollection<Piloto> collection) {//  listado completo de objetos Piloto.

        List<Piloto> listaPilotos = new ArrayList<Piloto>();
        Piloto p = new Piloto();
        MongoCursor<Piloto> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                listaPilotos.add(cursor.next());
            }
        } finally {
            cursor.close();
        }

        System.out.println("LISTA PILOTOS");
        listaPilotos.forEach(System.out::println);

        return listaPilotos;
    }


    public void ActualizarPiloto(Piloto p, MongoCollection<Piloto> collection) { // actualice los datos del registro coincidente en la base de datos con el mismo driverid.

        Piloto pilotoExistente = collection.find(eq("driverid", p.getDriverid())).first();
        if (pilotoExistente != null) {

            pilotoExistente.setCode(p.getCode());
            pilotoExistente.setForename(p.getForename());
            pilotoExistente.setSurname(p.getSurname());
            pilotoExistente.setDob(p.getDob());
            pilotoExistente.setNationality(p.getNationality());
            pilotoExistente.setUrl(p.getUrl());

            // Ahora, realiza la actualización en la base de datos
            collection.replaceOne(eq("driverid", p.getDriverid()), pilotoExistente);

            System.out.println("Piloto actualizado:");
            System.out.println("Driverid : " + pilotoExistente.getDriverid() +
                    " Code : " + pilotoExistente.getCode() +
                    " Nombre : " + pilotoExistente.getForename() +
                    " Apellidos : " + pilotoExistente.getSurname() +
                    " Fecha de nacimiento : " + pilotoExistente.getDob() +
                    " Nacionalidad : " + pilotoExistente.getNationality() +
                    " URL : " + pilotoExistente.getUrl());
        } else {
            System.out.println("No se encontró ningún piloto con el driverid proporcionado.");
        }
    }


    public void BorrarPiloto(Piloto p, MongoCollection<Piloto> collection) {
        collection.deleteOne(eq("driverid", p.getDriverid()));
        System.out.println("Piloto borrado con éxito.");


    }
}
