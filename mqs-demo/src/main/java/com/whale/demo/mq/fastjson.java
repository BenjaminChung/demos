package com.whale.demo.mq;


import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2015/7/30.
 */
public class fastjson {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("cetus");
        person.setAge(26);
        Address address = new Address();
        address.setStreet("nanhai da dao");
        person.setAddress(address);
        System.out.println(JSON.toJSONString(person));
        String jsonStr = "{\"address\":{\"street\":\"nanhai da dao\"},\"age\":26,\"name\":\"cetus\"}";
        Person person1 = JSON.parseObject(jsonStr,Person.class);
        System.out.println(person1.getName());
    }

}

class Person{
    private String name;
    private Integer age;
    Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

class Address{
    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
