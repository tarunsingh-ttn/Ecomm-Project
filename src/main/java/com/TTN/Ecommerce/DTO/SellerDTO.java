package com.TTN.Ecommerce.DTO;

import lombok.Data;

@Data
public class SellerDTO extends CustomerDTO{

    private long seller_id;
    private String gst;
    private String companyContact;
    private String companyName;
}
