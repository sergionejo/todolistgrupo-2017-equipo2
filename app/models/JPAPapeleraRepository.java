package models;

import javax.inject.Inject;
import play.db.jpa.JPAApi;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;

public class JPAPapeleraRepository implements PapeleraRepository {
   JPAApi jpaApi;

   @Inject
   public JPAPapeleraRepository(JPAApi api) {
      this.jpaApi = api;
   }

   public Papelera add(Papelera papelera) {
       return jpaApi.withTransaction(entityManager -> {
          entityManager.persist(papelera);
          entityManager.flush();
          entityManager.refresh(papelera);
          return papelera;
       });
    }

    public Papelera findById(Long idPapelera) {
       return jpaApi.withTransaction(entityManager -> {
          return entityManager.find(Papelera.class, idPapelera);
       });
    }

    public List<Papelera> findAllTareas() {
      return jpaApi.withTransaction(entityManager -> {
         TypedQuery<Papelera> query = entityManager.createQuery(
                   "select p.tareas from Papelera p ", Papelera.class);
         try {
             List<Tarea> listTareas = query.getResultList();
             return listTareas;
         } catch (NoResultException ex) {
             return null;
         }
      });
    }
}