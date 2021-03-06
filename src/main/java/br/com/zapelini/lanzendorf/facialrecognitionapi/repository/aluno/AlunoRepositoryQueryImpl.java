package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.aluno;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

public class AlunoRepositoryQueryImpl implements AlunoRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Aluno> filter(Pageable pageable, String nome, String email, String matricula) {
        String sql = "SELECT a FROM Aluno a " +
                "JOIN a.usuario u " +
                "WHERE a.idAluno != NULL ";
        sql = filterSql(sql, nome, email, matricula);
        Query query = entityManager.createQuery(sql, Aluno.class);
        adicionarPaginacao(query, pageable);
        addParamFilter(query, nome, email, matricula);
        return query.getResultList();
    }


    @Override
    public Integer filterCount(String nome, String email, String matricula) {
        String sql = "SELECT COUNT(a.*) FROM aluno a " +
                "JOIN usuario u ON a.id_usuario = u.id_usuario " +
                "WHERE true ";

        sql = filterSql(sql, nome, email, matricula);
        Query query = entityManager.createNativeQuery(sql);
        addParamFilter(query, nome, email, matricula);
        return ((BigInteger) query.getSingleResult()).intValue();
    }

    @Override
    public Boolean hasTurma(Long idAluno) {
        String sql = "SELECT COUNT(t) FROM Turma t " +
                "JOIN t.alunos a " +
                "WHERE a.idAluno = :idAluno ";

        return entityManager.createQuery(sql, Long.class)
                .setParameter("idAluno", idAluno)
                .setMaxResults(1)
                .getSingleResult() > 0;
    }

    @Override
    public Long countAlunosSemFotos() {
        String sql = "SELECT COUNT(a) FROM Aluno a " +
                "LEFT JOIN a.fotos f " +
                "WHERE f.idFoto IS NULL ";

        return entityManager.createQuery(sql, Long.class)
                .setMaxResults(1)
                .getSingleResult();
    }


    private String filterSql(String sql, String nome, String email, String matricula) {
        if (!StringUtils.isEmpty(nome)) {
            sql += "AND LOWER(u.nome) LIKE LOWER(:nome) ";
        }
        if (!StringUtils.isEmpty(email)) {
            sql += "AND u.email = :email ";
        }
        if (!StringUtils.isEmpty(matricula)) {
            sql += "AND a.matricula = :matricula ";
        }

        return sql;
    }

    private void addParamFilter(Query query, String nome, String email, String matricula) {
        if(!StringUtils.isEmpty(nome)) {
            query.setParameter("nome", "%" + nome + "%");
        }
        if(!StringUtils.isEmpty(email)) {
            query.setParameter("email", email);
        }
        if(!StringUtils.isEmpty(matricula)) {
            query.setParameter("matricula", matricula);
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
