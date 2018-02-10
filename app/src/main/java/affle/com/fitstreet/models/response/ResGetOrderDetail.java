package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 21/9/16.
 */
public class ResGetOrderDetail extends ResBase {
    @SerializedName("orderDetails")
    @Expose
    private List<OrderDetail> orderDetails = null;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("createdTime")
    @Expose
    private String createdTime;
    @SerializedName("deliveredTime")
    @Expose
    private String deliveredTime;
    @SerializedName("paymentBy")
    @Expose
    private String paymentBy;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("userAddressData")
    @Expose
    private UserAddressData userAddressData;

    /**
     * @return The orderDetails
     */
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    /**
     * @param orderDetails The orderDetails
     */
    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    /**
     * @return The orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus The orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return The createdTime
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime The createdTime
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return The deliveredTime
     */
    public String getDeliveredTime() {
        return deliveredTime;
    }

    /**
     * @param deliveredTime The deliveredTime
     */
    public void setDeliveredTime(String deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    /**
     * @return The paymentBy
     */
    public String getPaymentBy() {
        return paymentBy;
    }

    /**
     * @param paymentBy The paymentBy
     */
    public void setPaymentBy(String paymentBy) {
        this.paymentBy = paymentBy;
    }

    /**
     * @return The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return The userAddressData
     */
    public UserAddressData getUserAddressData() {
        return userAddressData;
    }

    /**
     * @param userAddressData The userAddressData
     */
    public void setUserAddressData(UserAddressData userAddressData) {
        this.userAddressData = userAddressData;
    }

    public class OrderDetail {

        @SerializedName("size")
        @Expose
        private String size;
        @SerializedName("createdTime")
        @Expose
        private String createdTime;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("productID")
        @Expose
        private String productID;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("discount")
        @Expose
        private String discount;

        /**
         * @return The size
         */
        public String getSize() {
            return size;
        }

        /**
         * @param size The size
         */
        public void setSize(String size) {
            this.size = size;
        }

        /**
         * @return The createdTime
         */
        public String getCreatedTime() {
            return createdTime;
        }

        /**
         * @param createdTime The createdTime
         */
        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        /**
         * @return The status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * @return The quantity
         */
        public String getQuantity() {
            return quantity;
        }

        /**
         * @param quantity The quantity
         */
        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        /**
         * @return The productID
         */
        public String getProductID() {
            return productID;
        }

        /**
         * @param productID The productID
         */
        public void setProductID(String productID) {
            this.productID = productID;
        }

        /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return The color
         */
        public String getColor() {
            return color;
        }

        /**
         * @param color The color
         */
        public void setColor(String color) {
            this.color = color;
        }

        /**
         * @return The image
         */
        public String getImage() {
            return image;
        }

        /**
         * @param image The image
         */
        public void setImage(String image) {
            this.image = image;
        }

        /**
         * @return The price
         */
        public String getPrice() {
            return price;
        }

        /**
         * @param price The price
         */
        public void setPrice(String price) {
            this.price = price;
        }

        /**
         * @return The discount
         */
        public String getDiscount() {
            return discount;
        }

        /**
         * @param discount The discount
         */
        public void setDiscount(String discount) {
            this.discount = discount;
        }
    }

    public class UserAddressData {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("landmark")
        @Expose
        private String landmark;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("pincode")
        @Expose
        private String pincode;

        /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return The phone
         */
        public String getPhone() {
            return phone;
        }

        /**
         * @param phone The phone
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         * @return The landmark
         */
        public String getLandmark() {
            return landmark;
        }

        /**
         * @param landmark The landmark
         */
        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        /**
         * @return The city
         */
        public String getCity() {
            return city;
        }

        /**
         * @param city The city
         */
        public void setCity(String city) {
            this.city = city;
        }

        /**
         * @return The state
         */
        public String getState() {
            return state;
        }

        /**
         * @param state The state
         */
        public void setState(String state) {
            this.state = state;
        }

        /**
         * @return The address
         */
        public String getAddress() {
            return address;
        }

        /**
         * @param address The address
         */
        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * @return The pincode
         */
        public String getPincode() {
            return pincode;
        }

        /**
         * @param pincode The pincode
         */
        public void setPincode(String pincode) {
            this.pincode = pincode;
        }
    }
}