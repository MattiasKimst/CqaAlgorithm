import DatabaseDomain.Curriculum;

public class Fact {

    int ID;
    String firstname;
    String lastname;
    Curriculum curriculum;

    public Fact(int ID, String firstname, String lastname, Curriculum curriculum) {
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.curriculum = curriculum;
    }

    @Override
    public String toString() {
        return "Fact{" +
                "ID=" + ID +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", curriculum=" + curriculum +
                '}';
    }
}
