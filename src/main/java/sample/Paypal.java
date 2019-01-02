package sample;

import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;
import sample.ModelLogic.AccessLayer;

import java.util.ArrayList;

public class Paypal {
    private Controller ac;

    public Paypal(Controller al) {
        this.ac = al;
    }

    public boolean exsecuteTrans(String seller, String buyer, int vacId, double vacPrice) {
        ArrayList<Pair> ans= new ArrayList<>();
        ans.add(new Pair<>(Fields.userName+"" , buyer));
        double newBalanceBuyer= Double.parseDouble(ac.ReadEntries(ans,Tables.PayPal).get(0).get(Fields.balance+"")) - vacPrice;
        RESULT res=ac.UpdateEntries(Tables.PayPal, Fields.balance,newBalanceBuyer+"",ans);

        ans= new ArrayList<>();
        ans.add(new Pair<>(Fields.userName+"",seller));
        double newBalanceSeller= Double.parseDouble(ac.ReadEntries(ans,Tables.PayPal).get(0).get(Fields.balance+"")) + vacPrice;
        RESULT res1=ac.UpdateEntries(Tables.PayPal, Fields.balance,newBalanceSeller+"",ans);

        return res != RESULT.Fail && res1 != RESULT.Fail;
    }
}
