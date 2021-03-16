package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.professor;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Professor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

public class ProfessorRepositoryQueryImpl implements ProfessorRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Professor> filter(Pageable pageable, String nome, String email) {
        String sql = "SELECT p FROM Professor p " +
                "JOIN p.usuario u " +
                "WHERE p.idProfessor != NULL ";
        sql = filterSql(sql, nome, email);
        Query query = entityManager.createQuery(sql, Professor.class);
        adicionarPaginacao(query, pageable);
        addParamFilter(query, nome, email);
        return query.getResultList();
    }

    @Override
    public Integer filterCount(String nome, String email) {
        String sql = "SELECT COUNT(p.*) FROM professor p " +
                "JOIN usuario u ON p.id_usuario = u.id_usuario " +
                "WHERE true ";

        sql = filterSql(sql, nome, email);
        Query query = entityManager.createNativeQuery(sql);
        addParamFilter(query, nome, email);
        return ((BigInteger) query.getSingleResult()).intValue();
    }

    @Override
    public Boolean hasTurma(Long idProfessor) {
        String sql = "SELECT COUNT(t) FROM Turma t " +
                "JOIN t.professor p " +
                "WHERE p.idProfessor = :idProfessor ";

        return entityManager.createQuery(sql, Long.class)
                .setParameter("idProfessor", idProfessor)
                .setMaxResults(1)
                .getSingleResult() > 0;
    }

    private String filterSql(String sql, String nome, String email) {
        if (!StringUtils.isEmpty(nome)) {
            sql += "AND LOWER(u.nome) LIKE LOWER(:nome) ";
        }
        if (!StringUtils.isEmpty(email)) {
            sql += "AND u.email = :email ";
        }
        return sql;
    }

    private void addParamFilter(Query query, String nome, String email) {
        if(!StringUtils.isEmpty(nome)) {
            query.setParameter("nome", "%" + nome + "%");
        }
        if(!StringUtils.isEmpty(email)) {
            query.setParameter("email", email);
        }
    }

    private void adicionarPaginacao(Query query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int registrosPorPagina = pageable.getPageSize();
        int primeiroRegistro = paginaAtual * registrosPorPagina;

        query.setFirstResult(primeiroRegistro);
        query.setMaxResults(registrosPorPagina);
    }
}
