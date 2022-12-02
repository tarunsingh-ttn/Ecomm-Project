package com.TTN.Ecommerce.Entities;


import com.TTN.Ecommerce.utils.Auditable;
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
    private boolean IS_CANCELLABLE;
    private boolean IS_RETURNABLE;
    private String brand;
    private boolean IS_ACTIVE;
    private boolean IS_DELETED;

    @JoinColumn(name = "sellerId")
    @ManyToOne
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
                ", IS_CANCELLABLE=" + IS_CANCELLABLE +
                ", IS_RETURNABLE=" + IS_RETURNABLE +
                ", brand='" + brand + '\'' +
                ", IS_ACTIVE=" + IS_ACTIVE +
                ", IS_DELETED=" + IS_DELETED +
                ", seller=" + seller +
                ", category=" + category +
                '}';
    }
}
