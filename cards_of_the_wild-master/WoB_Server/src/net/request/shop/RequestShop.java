package net.request.shop;

// Java Imports
import java.io.DataInputStream;
import java.io.IOException;

// Other Imports

import db.ShopDAO;
import net.request.GameRequest;
import net.response.shop.ResponseShop;

public class RequestShop extends GameRequest {

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    }

    @Override
    public void process() throws Exception {
        ResponseShop response = new ResponseShop();
        response.setShopList(ShopDAO.getItems("level:0,99"));
        client.add(response);
    }
}
