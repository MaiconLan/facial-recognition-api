package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.turma;


import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Professor;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Tipo;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Turma;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

public class TurmaRepositoryQueryImpl implements TurmaRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Turma> filter(Pageable pageable, Professor professor, String materia, String periodo, Tipo tipo, Boolean finalizada) {
        String sql = "SELECT t FROM Turma t " +
                "WHERE t.idTurma != NULL ";
        sql = filterSql(sql, professor, materia, periodo, tipo, finalizada);
        Query query = entityManager.createQuery(sql, Turma.class);
        adicionarPaginacao(query, pageable);
        addParamFilter(query, professor, materia, periodo, tipo, finalizada);
        return query.getResultList();
    }

    @Override
    public Long filterCount(String materia, Professor professor, String periodo, Tipo tipo, Boolean finalizada) {
        String sql = "SELECT COUNT(t) FROM Turma t " +
                "WHERE t.idTurma != NULL ";

        sql = filterSql(sql, professor, materia, periodo, tipo, finalizada);
        Query query = entityManager.createQuery(sql);
        addParamFilter(query, professor, materia, periodo, tipo, finalizada);
        return (Long) query.getSingleResult();
    }

    private String filterSql(String sql, Professor professor, String materia, String periodo, Tipo tipo, Boolean finalizada) {
        if (!StringUtils.isEmpty(materia)) {
            sql += "AND LOWER(t.materia) LIKE LOWER(:materia) ";
        }
        if (!StringUtils.isEmpty(periodo)) {
            sql += "AND t.periodo = :periodo ";
        }
        if (tipo != null) {
            sql += "AND t.tipo = :tipo ";
        }
        if(professor != null) {
            sql += "AND t.professor = :professor ";
        }
        if (finalizada != null) {
            sql += "AND t.finalizada = :finalizada ";
        }
        return sql;
    }

    private void addParamFilter(Query query, Professor professor, String materia, String periodo, Tipo tipo, Boolean finalizada) {
        if (!StringUtils.isEmpty(materia)) {
            query.setParameter("materia", "%" + materia + "%");
        }
        if (!StringUtils.isEmpty(periodo)) {
            query.setParameter("periodo", periodo);
        }
        if (tipo != null) {
            query.setParameter("tipo", tipo);
        }
        if (professor != null) {
            query.setParameter("professor", professor);
        }
        if (finalizada != null) {
            query.setParameter("finalizada", finalizada);
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
