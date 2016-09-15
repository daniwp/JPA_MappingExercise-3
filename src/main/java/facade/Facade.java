package facade;

import entity.ClassicOrder;
import entity.Customer;
import entity.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class Facade {

    EntityManagerFactory emf;

    public Facade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Employee createEmployee(Employee e) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(e);
            em.flush();
            return e;
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }

    public Customer updateCustomer(Customer cust) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(cust);
            em.flush();
            return cust;
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }

    public int getEmployeeCount() {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT COUNT(e) FROM Employee e");
            return query.getFirstResult();
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }

    public List<Customer> getCustomersInCity(String city) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            return (List<Customer>) em.createQuery("SELECT c FROM Customer c WHERE c.city LIKE :city").setParameter("city", city).getResultList();
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }

    /*
    public List<Employee> getEmployeeMaxCustomers() {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT e FROM Employee e WHERE ");
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    } 
     */
    public List<ClassicOrder> getOrdersOnHold() {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            return (List<ClassicOrder>) em.createQuery("SELECT c FROM ClassicOrder c WHERE c.status LIKE 'On Hold'").getResultList();
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }

    public List<ClassicOrder> getOrdersOnHold(int customerNumber) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            return (List<ClassicOrder>) em.createQuery("SELECT c FROM ClassicOrder c WHERE c.status LIKE 'On Hold' AND c.customer.customerNumber LIKE :customerNumber").setParameter("customerNumber", customerNumber).getResultList();
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }
}
