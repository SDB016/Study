package jpabook.jpashop_clone.domain.item;

import jpabook.jpashop_clone.domain.ItemCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<ItemCategory> itemCategoryList = new ArrayList<>();

    //==연관관계 메서드==//
    public void addItemCategory(ItemCategory itemCategory) {
        this.getItemCategoryList().add(itemCategory);
        itemCategory.setItem(this);
    }

}
