import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Date;

public class Piloto {
    ObjectId id;

    private int driverid;
    private String code;
    private String forename;
    private String surname;
    private Date dob;
    private String nationality;
    Constructor constructor;
    private String url;

    public Piloto() {
    }
    //cuando creemos un piloto hay que poner driverid nulo en el main

    public Piloto(ObjectId id, int driverid, String code, String forename, String surname, Date dob, String nationality, Constructor constructor, String url) {
        this.id = id;
        this.driverid = driverid;
        this.code = code;
        this.forename = forename;
        this.surname = surname;
        this.dob = dob;
        this.nationality = nationality;
        this.constructor = constructor;
        this.url = url;
    }

    public Piloto(ObjectId id, String code, String forename, String surname, Date dob, String nationality, Constructor constructor, String url) {
        this.id = id;
        this.code = code;
        this.forename = forename;
        this.surname = surname;
        this.dob = dob;
        this.nationality = nationality;
        this.constructor = constructor;
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDriverid() {
        return driverid;
    }

    public void setDriverid(int driverid) {
        this.driverid = driverid;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public java.sql.Date getDob() {
        return (java.sql.Date) dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Constructor getEscuderia() {
        return constructor;
    }

    public void setEscuderia(Constructor constructor) {
        this.constructor = constructor;
    }

    public void setConstructors(Constructor constructor) {
        this.constructor = constructor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String devolverStringDob (LocalDate dob) {
        String fecha = dob.toString();
        return fecha;
    }


    @Override
    public String toString() {
        return "Piloto{" +
                "driverid=" + driverid +
                ", code='" + code + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", nationality='" + nationality + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
