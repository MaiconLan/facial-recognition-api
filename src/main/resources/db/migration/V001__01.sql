-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.1-beta
-- PostgreSQL version: 10.0
-- Project Site: pgmodeler.com.br
-- Model Author: ---


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: new_database | type: DATABASE --
-- -- DROP DATABASE IF EXISTS new_database;
-- CREATE DATABASE new_database
-- ;
-- -- ddl-end --
--

-- object: public.aluno | type: TABLE --
-- DROP TABLE IF EXISTS public.aluno CASCADE;
CREATE TABLE public.aluno(
	id_aluno serial NOT NULL,
	id_usuario integer,
	matricula character varying,
	CONSTRAINT id_aluno_pk PRIMARY KEY (id_aluno)

);
-- ddl-end --
ALTER TABLE public.aluno OWNER TO postgres;
-- ddl-end --

-- object: public.usuario | type: TABLE --
-- DROP TABLE IF EXISTS public.usuario CASCADE;
CREATE TABLE public.usuario(
	id_usuario serial NOT NULL,
	nome character varying NOT NULL,
	email character varying,
	usuario character varying,
	senha character varying,
	CONSTRAINT id_usuario_pk PRIMARY KEY (id_usuario)

);
-- ddl-end --
ALTER TABLE public.usuario OWNER TO postgres;
-- ddl-end --

-- object: public.professor | type: TABLE --
-- DROP TABLE IF EXISTS public.professor CASCADE;
CREATE TABLE public.professor(
	id_usuario integer,
	id_professor serial NOT NULL,
	CONSTRAINT id_professor_pk PRIMARY KEY (id_professor)

);
-- ddl-end --
ALTER TABLE public.professor OWNER TO postgres;
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.professor DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.professor ADD CONSTRAINT usuario_fk FOREIGN KEY (id_usuario)
REFERENCES public.usuario (id_usuario) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: professor_uq | type: CONSTRAINT --
-- ALTER TABLE public.professor DROP CONSTRAINT IF EXISTS professor_uq CASCADE;
ALTER TABLE public.professor ADD CONSTRAINT professor_uq UNIQUE (id_usuario);
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.aluno DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.aluno ADD CONSTRAINT usuario_fk FOREIGN KEY (id_usuario)
REFERENCES public.usuario (id_usuario) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: aluno_uq | type: CONSTRAINT --
-- ALTER TABLE public.aluno DROP CONSTRAINT IF EXISTS aluno_uq CASCADE;
ALTER TABLE public.aluno ADD CONSTRAINT aluno_uq UNIQUE (id_usuario);
-- ddl-end --

-- object: public.aula | type: TABLE --
-- DROP TABLE IF EXISTS public.aula CASCADE;
CREATE TABLE public.aula(
	id_aula serial NOT NULL,
	id_turma integer,
	titulo text,
	inicio timestamp NOT NULL,
	termino timestamp NOT NULL,
	CONSTRAINT id_aula_pk PRIMARY KEY (id_aula)

);
-- ddl-end --
ALTER TABLE public.aula OWNER TO postgres;
-- ddl-end --

-- object: public.turma | type: TABLE --
-- DROP TABLE IF EXISTS public.turma CASCADE;
CREATE TABLE public.turma(
	id_turma serial NOT NULL,
	id_professor integer,
	materia character varying NOT NULL,
	ano integer NOT NULL,
	periodo character varying NOT NULL,
	tipo character varying,
	finalizada boolean,
	CONSTRAINT id_turma_pk PRIMARY KEY (id_turma),
	CONSTRAINT tipo_turma_check CHECK (tipo IN ('Semestral', 'Bimestral', 'Trimestral', 'Anual'))

);
-- ddl-end --
ALTER TABLE public.turma OWNER TO postgres;
-- ddl-end --

-- object: professor_fk | type: CONSTRAINT --
-- ALTER TABLE public.turma DROP CONSTRAINT IF EXISTS professor_fk CASCADE;
ALTER TABLE public.turma ADD CONSTRAINT professor_fk FOREIGN KEY (id_professor)
REFERENCES public.professor (id_professor) MATCH FULL
ON DELETE NO ACTION ON UPDATE CASCADE;
-- ddl-end --

-- object: turma_fk | type: CONSTRAINT --
-- ALTER TABLE public.aula DROP CONSTRAINT IF EXISTS turma_fk CASCADE;
ALTER TABLE public.aula ADD CONSTRAINT turma_fk FOREIGN KEY (id_turma)
REFERENCES public.turma (id_turma) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: public.coordenador | type: TABLE --
-- DROP TABLE IF EXISTS public.coordenador CASCADE;
CREATE TABLE public.coordenador(
	id_coordenador serial NOT NULL,
	id_usuario integer,
	CONSTRAINT id_cordenador_pk PRIMARY KEY (id_coordenador)

);
-- ddl-end --
ALTER TABLE public.coordenador OWNER TO postgres;
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.coordenador DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.coordenador ADD CONSTRAINT usuario_fk FOREIGN KEY (id_usuario)
REFERENCES public.usuario (id_usuario) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: coordenador_uq | type: CONSTRAINT --
-- ALTER TABLE public.coordenador DROP CONSTRAINT IF EXISTS coordenador_uq CASCADE;
ALTER TABLE public.coordenador ADD CONSTRAINT coordenador_uq UNIQUE (id_usuario);
-- ddl-end --

-- object: public.turma_aluno | type: TABLE --
-- DROP TABLE IF EXISTS public.turma_aluno CASCADE;
CREATE TABLE public.turma_aluno(
	id_aluno integer NOT NULL,
	id_turma integer NOT NULL,
	CONSTRAINT turma_aluno_pk PRIMARY KEY (id_aluno,id_turma)

);
-- ddl-end --

-- object: aluno_fk | type: CONSTRAINT --
-- ALTER TABLE public.turma_aluno DROP CONSTRAINT IF EXISTS aluno_fk CASCADE;
ALTER TABLE public.turma_aluno ADD CONSTRAINT aluno_fk FOREIGN KEY (id_aluno)
REFERENCES public.aluno (id_aluno) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: turma_fk | type: CONSTRAINT --
-- ALTER TABLE public.turma_aluno DROP CONSTRAINT IF EXISTS turma_fk CASCADE;
ALTER TABLE public.turma_aluno ADD CONSTRAINT turma_fk FOREIGN KEY (id_turma)
REFERENCES public.turma (id_turma) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: public.presenca | type: TABLE --
-- DROP TABLE IF EXISTS public.presenca CASCADE;
CREATE TABLE public.presenca(
	id_presenca serial NOT NULL,
	id_aluno integer,
	id_aula integer,
	presenca boolean,
	CONSTRAINT id_presenca_pk PRIMARY KEY (id_presenca)

);
-- ddl-end --
ALTER TABLE public.presenca OWNER TO postgres;
-- ddl-end --

-- object: aluno_fk | type: CONSTRAINT --
-- ALTER TABLE public.presenca DROP CONSTRAINT IF EXISTS aluno_fk CASCADE;
ALTER TABLE public.presenca ADD CONSTRAINT aluno_fk FOREIGN KEY (id_aluno)
REFERENCES public.aluno (id_aluno) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: aula_fk | type: CONSTRAINT --
-- ALTER TABLE public.presenca DROP CONSTRAINT IF EXISTS aula_fk CASCADE;
ALTER TABLE public.presenca ADD CONSTRAINT aula_fk FOREIGN KEY (id_aula)
REFERENCES public.aula (id_aula) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

