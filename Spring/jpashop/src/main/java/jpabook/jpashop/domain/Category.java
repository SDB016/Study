package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "Category_id")
    private Long id;

    private String name;

    @ManyToMany //실무에서는 중간에 추가해야 할 정보가 생길 수 있으므로 다대다 관계는 사용하지 않는다.
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"), //category_item 테이블 안에 있는 category_id 칼럼
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    /////////////////  self 양방향 연관관계   /////////////////
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
