package bookjpa.shopjpa.domain.item;

import bookjpa.shopjpa.domain.BaseEntity;
import bookjpa.shopjpa.domain.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  //한 테이블에 모두 넣기
//@Inheritance(strategy = InheritanceType.JOINED) // 테이블 상속 구조
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) //각 테이블마다 name, price 등 중복해서 가짐
@DiscriminatorColumn
public abstract class Item extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockquantity() {
        return stockQuantity;
    }

    public void setStockquantity(int stockquantity) {
        this.stockQuantity = stockquantity;
    }
}
