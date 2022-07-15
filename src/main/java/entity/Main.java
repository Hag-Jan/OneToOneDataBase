package entity;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    static boolean loop = true;
    static Scanner sc = new Scanner(System.in);
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

    public static void main(String[] args) {

        while (loop) {
            menu();

        }

    }

    public static void menu() {
        System.out.println("\n=================================");
        System.out.println("              Menu                 ");
        System.out.println("===================================");
        System.out.println("1. Add Bank");
        System.out.println("2. Add Customer");
        System.out.println("3. Add New Customer to New Bank");
        System.out.println("4. Add Add New Customer To Existing Bank");
        System.out.println("5. Add Add Existing Customer To Existing Bank");
        System.out.println("6. Delete Customer");
        System.out.println("7. Delete Bank");
        System.out.println("8. Find Customer by Id");
        System.out.println("9. Find Bank by Id");
        System.out.println("10. Show All Bank Clients");
        System.out.println("11. Show All Bank");
        System.out.println("0. Exit");
        System.out.print("\n Make a Choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {

            case 1:
                addBank();
                break;
            case 2:
                addCustomer();
                break;
            case 3:
                addNewCusomerToNewBank();
                break;
            case 4:
                addNewCustomerToExistingBank();
                break;
            case 5:
                addExistingCustomerToExistingBank();
                break;
            case 6:
                deleteCustomer();
                break;
            case 7:
                deleteBank();
                break;
            case 8:
                findCustomerbyId();
                break;
            case 9:
                findBankbyId();
                break;
            case 10:
                showAllBankClients();
                break;
            case 11:
                showAllBank();
            case 0:
                loop = false;
                System.out.println("Program Terminates");
                break;
            default:
                System.out.println("Error");
        }
    }


    public static void addBank() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Name: ");
        String name = sc.nextLine();

        Bank bank = new Bank(name);

        em.getTransaction().begin();
        em.persist(bank);

        em.getTransaction().commit();

        em.close();

    }


    public static void addCustomer() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Phone number: ");
        String pn = sc.nextLine();

        Customer c = new Customer(name, pn);

        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();

        em.close();

    }

    public static void addNewCusomerToNewBank() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Name: ");
        String n = sc.nextLine();

        System.out.print("Phone number: ");
        String pn = sc.nextLine();

        Customer c = new Customer(n, pn);

        em.getTransaction().begin();
        em.persist(c);

        em.getTransaction().commit();


        System.out.print("Bank name: ");
        String bn = sc.nextLine();

        Bank bank = new Bank(bn);

        em.getTransaction().begin();
        em.persist(bank);

        em.getTransaction().commit();
        em.close();


    }

    private static void addNewCustomerToExistingBank() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Customer Name: ");
        String customerid = sc.nextLine();

        System.out.print("Bank Id: ");
        int bankId = sc.nextInt();
        sc.nextLine();

        Customer customer = em.find(Customer.class, customerid);

        Bank bank = em.find(Bank.class, bankId);

        em.getTransaction().begin();

        em.merge(customer);
        em.merge(bank);

        bank.setCustomer(customer);
        em.getTransaction().commit();

        em.close();
    }


    private static void addExistingCustomerToExistingBank() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Kund Id: ");
        int kundId = sc.nextInt();

        System.out.print("Bank id: ");
        int bankId = sc.nextInt();


        Bank bank = em.find(Bank.class, bankId);
        Customer customer = em.find(Customer.class, kundId);

        System.out.println(bank);
        System.out.println(customer);

        em.getTransaction().begin();

        em.merge(customer);
        em.merge(bank);

        bank.setCustomer(customer);

        em.getTransaction().commit();
        em.close();
    }


    private static void deleteCustomer() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Id: ");
        int id = sc.nextInt();
        sc.nextLine();

        em.getTransaction().begin();

        em.createQuery("SELECT b FROM Bank b WHERE b.id=:id", Bank.class)
                .setParameter("id", id)
                .getResultStream()
                .forEach(c -> c.setId(null));

        em.remove(em.find(Customer.class, id));
        em.getTransaction().commit();

        em.close();

    }


    private static void deleteBank() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Id: ");
        int id = sc.nextInt();
        sc.nextLine();

        Bank b = em.find(Bank.class, id);

        em.getTransaction().begin();
        em.remove(b);

        em.getTransaction().commit();
        em.close();

    }


    private static void findCustomerbyId() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Id: ");
        int id = sc.nextInt();
        sc.nextLine();

        Query q = em.createQuery("SELECT c FROM Customer c WHERE c.id=:id");
        q.setParameter("id", id);

        q.getResultList().forEach(System.out::println);

        em.close();


    }

    public static void findBankbyId() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Id: ");
        int id = sc.nextInt();
        sc.nextLine();

        Query q = em.createQuery("SELECT b FROM Bank b WHERE b.id=:id");
        q.setParameter("id", id);

        q.getResultList().forEach(System.out::println);

    }

    private static void showAllBankClients() {

        EntityManager em = emf.createEntityManager();

        TypedQuery<Customer> q = em.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> customer = q.getResultList();

        customer.forEach(c -> System.out.println(c));

        em.close();
    }

    private static void showAllBank() {

        EntityManager em = emf.createEntityManager();

        TypedQuery<Bank> q = em.createQuery("SELECT b FROM Bank b", Bank.class);
        List<Bank> bank = q.getResultList();

        bank.forEach(b -> System.out.println(b));

        em.close();

    }
}
