package models;

import javax.inject.Inject;
import play.db.jpa.JPAApi;

import java.util.List;

import javax.persistence.EntityManager;

public class JPALabelRepository implements LabelRepository {
   JPAApi jpaApi;

   @Inject
   public JPALabelRepository(JPAApi api) {
      this.jpaApi = api;
   }

   public Label add(Label label) {
      return jpaApi.withTransaction(entityManager -> {
         entityManager.persist(label);
         entityManager.flush();
         entityManager.refresh(label);
         return label;
      });
   }

   public Label update(Label label) {
      return jpaApi.withTransaction(entityManager -> {
         Label labelBD = entityManager.find(Label.class, label.getId());
         labelBD.setTitulo(label.getTitulo());
         labelBD.setDescripcion(label.getDescripcion());
         labelBD.setColor(label.getColor());
         return labelBD;
      });
   }

   public void delete(Long idLabel) {
      jpaApi.withTransaction(() -> {
         EntityManager entityManager = jpaApi.em();
         Label labelBD = entityManager.getReference(Label.class, idLabel);
         entityManager.remove(labelBD);
      });
   }

   public Label findById(Long idLabel) {
      return jpaApi.withTransaction(entityManager -> {
         return entityManager.find(Label.class, idLabel);
      });
   }
}
