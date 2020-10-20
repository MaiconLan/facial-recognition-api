
CREATE TABLE public.foto(
	id_foto serial NOT NULL,
	id_aluno serial NOT NULL,
	nome character varying,
	extensao character varying,
	CONSTRAINT id_foto_pk PRIMARY KEY (id_foto)
);


ALTER TABLE public.foto ADD CONSTRAINT aluno_fk FOREIGN KEY (id_aluno)
REFERENCES public.aluno (id_aluno) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;