package entity;

import javax.persistence.*;

@Entity
public class Bank {


    @Id
    @GeneratedValue
    private int id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Customer customer;

    private String bankName;

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Bank() {
    }

    public Bank(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String name) {
        this.bankName = name;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Integer{" +
                "id=" + id +
                ", name='" + bankName + '\'' +
                '}';
    }
}


