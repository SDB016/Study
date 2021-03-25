package jpabook.jpashop_clone.domain;

import jpabook.jpashop_clone.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "item_category")
@Getter @Setter
public class ItemCategory {

    @Id @GeneratedValue
    @Column(name="item_category_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Categoty categoty;
}
