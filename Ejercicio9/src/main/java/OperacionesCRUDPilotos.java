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

        p = collection.find(eq("driverid", p.getDriverid())).first();
        if (p != null) {
            collection.replaceOne(eq("driverid", p.getDriverid()), p);
            System.out.println("Piloto atualizado:");
            System.out.println("Driverid : "+ p.getDriverid()+
                                "Code : " + p.getCode()+
                                "Nombre : " + p.getForename() +
                                "Apellidos : "+ p.getSurname() +
                                "Fecha de nacimiento : " + p.getDob() +
                                "Nacionalidad : " + p.getNationality() +
                                "URL : " + p.getUrl());
        }
    }

    public void BorrarPiloto(Piloto p, MongoCollection<Piloto> collection) {
        collection.deleteOne(eq("driverid", p.getDriverid()));
        System.out.println("Piloto borrado con éxito.");


    }
}
