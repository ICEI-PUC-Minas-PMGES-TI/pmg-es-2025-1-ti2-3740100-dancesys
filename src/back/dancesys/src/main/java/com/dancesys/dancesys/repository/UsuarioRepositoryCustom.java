//package com.dancesys.dancesys.repository;
//
//import com.dancesys.dancesys.dto.UsuarioFilterDto;
//import com.dancesys.dancesys.entity.Usuario;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.TypedQuery;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Repository
//public class UsuarioRepositoryCustom {
//    private EntityManager em;
//    public UsuarioRepositoryCustom(EntityManager em) {
//        this.em = em;
//    }
//
//    public List<Usuario> buscasrUsuario(UsuarioFilterDto filtro){
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
//        Root<Usuario> root = query.from(Usuario.class);
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        if(filtro.getId() != null && !filtro.getId().isEmpty()){
//            predicates.add(root.get("id").in(filtro.getId()));
//        }
//
//        if(filtro.getEnumTipo() != null && !filtro.getEnumTipo().isEmpty()){
//            predicates.add(root.get("enumTipo").in(filtro.getEnumTipo()));
//        }
//
//        if(filtro.getStatus() != null){
//            predicates.add(root.get("status").in(filtro.getStatus()));
//        }
//
//        if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
//            List<Predicate> nomePredicates = new ArrayList<>();
//            for(String nome : filtro.getNome()){
//                if (nome.contains("%")) {
//                    nomePredicates.add(cb.like(root.get("nome"), nome));
//                } else {
//                    nomePredicates.add(cb.equal(root.get("nome"), nome));
//                }
//            }
//            predicates.add(cb.or(nomePredicates.toArray(new Predicate[0])));
//        }
//
//        if(filtro.getCpf() != null && !filtro.getCpf().isEmpty()){
//            List<Predicate> cpfPredicates = new ArrayList<>();
//            for(String cpf : filtro.getCpf()){
//                if (cpf.contains("%")) {
//                    cpfPredicates.add(cb.like(root.get("cpf"), cpf));
//                } else {
//                    cpfPredicates.add(cb.equal(root.get("cpf"), cpf));
//                }
//            }
//            predicates.add(cb.or(cpfPredicates.toArray(new Predicate[0])));
//        }
//
//        if (filtro.getDataInicio() != null && filtro.getDataFim() != null) {
//            predicates.add(cb.between(root.get("criadoEm"), filtro.getDataInicio(), filtro.getDataFim()));
//        }
//
//        query.where(cb.and(predicates.toArray(new Predicate[0])));
//
//        TypedQuery<Usuario> typedQuery = em.createQuery(query);
//        return typedQuery.getResultList();
//    }
//}
