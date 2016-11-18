
/**
 * Dao.Java is used as the in between the java application and the postgress database.
 * Each of these methods is performing an operation on the database such as adding an object to it or deleting one.
 */

package nz.ac.op.soften2016.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import nz.ac.op.soften2016.bean.Topping;

import java.io.Serializable;
import java.util.List;

/**
 * Basic DAO operations dependent with Hibernate's specific classes
 * @see SessionFactory
 */
public class Dao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return sessionFactory; }
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    public void delete(Object object) {
        if (object != null)
            getCurrentSession().delete(object);
    }

    public Object get(Class clazz, Serializable id) {
        return getCurrentSession().get(clazz, id);
    }

    public Object save(Object object) {
        return getCurrentSession().save(object);
    }

    public Object update(Object object) {
        getCurrentSession().update(object);
        return object;
    }

    public Object saveOrUpdate(Object object) {
        getCurrentSession().saveOrUpdate(object);
        return object;
    }

    public void flush() {
        getCurrentSession().flush();
    }

    public List list(Class clazz) {
        Criteria criteria = getCurrentSession().createCriteria(clazz);
        return criteria.list();
    }

    public List search(final Class clazz, final Criterion search) {
        Criteria criteria = getCurrentSession().createCriteria(clazz).add(search);
        return criteria.list();
    }
    
  //================================================================================================//
  //Matt 26/10/16
    
    public Object findOneSelection(final Class clazz, String idNumber)
    {
    	Object myObject = null;
    	Criterion crit;
    	try
    	{
    		crit =  Restrictions.eq("id", Long.parseLong(idNumber));
    	}
    	catch(NumberFormatException e)
    	{
    		return myObject;
    	}
		List myList = search(clazz, crit);
    	if(myList.size() == 1)
    	{
    		return myList.get(0);
    	}
    	else
    	{
    		return myObject;
    	}
    }
  //=========================================================================================//
}