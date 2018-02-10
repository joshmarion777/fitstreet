package affle.com.fitstreet.customviews;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.models.response.ResMyOrders;

/**
 * Created by akash on 19/9/16.
 */
    public class SerializableArrayList implements Serializable {

        private List<ResMyOrders.Product> ordersArrayList;

        public SerializableArrayList(List<ResMyOrders.Product> data) {
            this.ordersArrayList = data;
        }

        public List<ResMyOrders.Product> getOrdersArrayList() {
            return this.ordersArrayList;
        }

    }

