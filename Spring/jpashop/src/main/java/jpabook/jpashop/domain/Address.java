package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

//값 타입은 변경 불가능하게 설계해야 한다. @Setter 대신 생성자 초기화
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address(){
    } //JPA 스펙상 엔티티나 임베디드 타입은 기본생성자를 설정해야 한다.

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
