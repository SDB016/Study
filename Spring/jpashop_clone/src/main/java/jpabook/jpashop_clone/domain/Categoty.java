package jpabook.jpashop_clone.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Categoty {

    @Id @GeneratedValue
    @Column(name="categoty_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "categoty")
    private List<ItemCategory> itemCategoryList = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Categoty parent;

    @OneToMany(mappedBy = "parent")
    private List<Categoty> child = new ArrayList<>();
}
