package sample;

//TODO: Nethanel, implement this please
public class ViewVacation {

    Controller control;
    String VacID;

    public void setControl(Controller control)
    {
        this.control = control;
    }

    public void setVacID(String vacID) {
        VacID = vacID;
        System.out.println(VacID);
    }
}
