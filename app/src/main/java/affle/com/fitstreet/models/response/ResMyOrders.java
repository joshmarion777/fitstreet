package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 15/9/16.
 */
public class ResMyOrders extends  ResBase implements Serializable{
    @SerializedName("orderData")
    @Expose
    private List<OrderDatum> orderData = new ArrayList<OrderDatum>();
    /**
     *
     * @return
     * The orderData
     */
    public List<OrderDatum> getOrderData() {
        return orderData;
    }

    /**
     *
     * @param orderData
     * The orderData
     */
    public void setOrderData(List<OrderDatum> orderData) {
        this.orderData = orderData;
    }

public class OrderDatum {

    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("createdTime")
    @Expose
    private String createdTime;
    @SerializedName("products")
    @Expose
    private List<Product> products = new ArrayList<Product>();
    @SerializedName("cancelledOn")
    @Expose
    private String cancelledOn;
    @SerializedName("deliveredTime")
    @Expose
    private String deliveredTime;

    /**
     *
     * @return
     * The orderID
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     *
     * @param orderID
     * The orderID
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /**
     *
     * @return
     * The orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     *
     * @param orderStatus
     * The orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    /**
     *
     * @return
     * The cancelledOn
     */
    public String getCancelledOn() {
        return cancelledOn;
    }

    /**
     *
     * @param cancelledOn
     * The cancelledOn
     */
    public void setCancelledOn(String cancelledOn) {
        this.cancelledOn = cancelledOn;
    }
    /**
     *
     * @return
     * The createdTime
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     *
     * @param createdTime
     * The createdTime
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     *
     * @return
     * The products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     *
     * @param products
     * The products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(String deliveredTime) {
        this.deliveredTime = deliveredTime;
    }
}
public class Product implements Serializable {

    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("createdTime")
    @Expose
    private String createdTime;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("ordProMapID")
    @Expose
    private String ordProMapID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("image")
    @Expose
    private String image;

    /**
     *
     * @return
     * The ordProMapID
     */
    public String getOrdProMapID() {
        return ordProMapID;
    }

    /**
     *
     * @param ordProMapID
     * The ordProMapID
     */
    public void setOrdProMapID(String ordProMapID) {
        this.ordProMapID = ordProMapID;
    }


    /**
     *
     * @return
     * The size
     */
    public String getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The createdTime
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     *
     * @param createdTime
     * The createdTime
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     *
     * @return
     * The productID
     */
    public String getProductID() {
        return productID;
    }

    /**
     *
     * @param productID
     * The productID
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }
    /**
     *
     * @return
     * The statusORDER_INACTIVE
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The color
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color
     * The color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
}

