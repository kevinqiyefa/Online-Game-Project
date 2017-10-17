package net.request;

// Java Imports
import java.io.DataInputStream;
import java.io.IOException;

// Other Imports
import db.ShopDAO;
import net.response.ResponseSpeciesList;

public class RequestSpeciesList extends GameRequest {

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    }

    @Override
    public void process() throws Exception {
        ResponseSpeciesList response = new ResponseSpeciesList();
        response.setSpeciesList(ShopDAO.getItems("level:0,99"));
        client.add(response);
    }
}
