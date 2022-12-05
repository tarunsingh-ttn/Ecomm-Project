package com.TTN.Ecommerce.Entities;


import com.TTN.Ecommerce.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE product SET is_deleted = true WHERE prod_id=?")
@Where(clause = "is_deleted=false")
public class Product extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_gen")
    @SequenceGenerator(name = "prod_gen", sequenceName = "prod_seq", initialValue = 1, allocationSize=1)
    private long prodId;
    private String name;
    private String description;
    private boolean isCancellable;
    private boolean isReturnable;
    private String brand;
    private boolean isActive;
    private boolean isDeleted;
    @JoinColumn(name = "sellerId")
    @ManyToOne
    @JsonIgnore
    private Seller seller;
    @JoinColumn(name="catId")
    @OneToOne
    private Category category;
    @Override
    public String toString() {
        return "Product{" +
                "prodId=" + prodId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", IS_CANCELLABLE=" + isCancellable +
                ", IS_RETURNABLE=" + isReturnable +
                ", brand='" + brand + '\'' +
                ", IS_ACTIVE=" + isActive +
                ", IS_DELETED=" + isDeleted +
                ", seller=" + seller +
                ", category=" + category +
                '}';
    }
}
