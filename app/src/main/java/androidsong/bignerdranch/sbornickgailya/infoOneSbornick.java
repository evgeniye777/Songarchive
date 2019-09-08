package androidsong.bignerdranch.sbornickgailya;

import java.util.UUID;

public class infoOneSbornick {
    UUID mid;
    String Name;

    public void infoOneSbornick() {mid = UUID.randomUUID();}
    public void setId(UUID id) {mid = id;}
    public UUID getId() {return mid;}
    public void setName(String name) {Name = name;}
    public String getName() {return Name;}
}
