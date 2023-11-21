public class Equipo {
    private int constructorid;
    private String constructorref;
    private String name;
    private String nationality;
    private String url;

    public Equipo() {
    }

    public Equipo(int constructorid, String constructorref, String name, String nationality, String url) {
        this.constructorid = constructorid;
        this.constructorref = constructorref;
        this.name = name;
        this.nationality = nationality;
        this.url = url;
    }

    public Equipo(String constructorref, String name, String nationality, String url) {
        this.constructorref = constructorref;
        this.name = name;
        this.nationality = nationality;
        this.url = url;
    }

    public int getConstructorid() {
        return constructorid;
    }

    public void setConstructorid(int constructorid) {
        this.constructorid = constructorid;
    }

    public String getConstructorref() {
        return constructorref;
    }

    public void setConstructorref(String constructorref) {
        this.constructorref = constructorref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "constructorid=" + constructorid +
                ", constructorref='" + constructorref + '\'' +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
