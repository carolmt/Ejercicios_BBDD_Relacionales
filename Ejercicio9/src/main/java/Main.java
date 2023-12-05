import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Main {
    public static void main(String[] args) {
        // Desactivamos los logs de MongoDB
        Logger logger = LoggerFactory.getLogger("org.mongodb.driver");
        // String uri = "mongodb://usuario:password@host:puerto";
        String uri = "mongodb://adminCarol:hola1234@ec2-23-20-94-120.compute-1.amazonaws.com:27017";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            System.out.println("Conexión con MongoClient y CodecRegistry para el trabajo con POJOs");

            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            MongoDatabase database = mongoClient.getDatabase("f1-2006").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Piloto> collection = database.getCollection("drivers", Piloto.class);

            System.out.println("La colección drivers tiene " + collection.countDocuments() + " documentos");

            OperacionesCRUDPilotos op = new OperacionesCRUDPilotos();
            List<Piloto> listaPilotos;
            /*Piloto sainz = new Piloto();
                sainz.setCode("SAI");
                sainz.setForename("Carlos");
                sainz.setSurname("Sainz");
                sainz.setNationality("Spanish");

            op.CrearPiloto(sainz, collection);
                System.out.println("Piloto creado");
                sainz.toString();*/

            Piloto lecturaPiloto = new Piloto();
            lecturaPiloto = op.LeerPiloto(2, collection);
            lecturaPiloto.toString();

            Piloto pilotoNuevo = new Piloto();
                pilotoNuevo.setUrl("vhddskls.com");
                op.ActualizarPiloto(pilotoNuevo, collection);

            Piloto pilotoBorrado = new Piloto();
            pilotoBorrado.setDriverid(30);
            op.BorrarPiloto(pilotoBorrado, collection);

            listaPilotos = op.LeerPilotos(collection);


        }

    }
}
