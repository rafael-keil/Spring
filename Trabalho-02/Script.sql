--------- Carteira de motorista ------------

CREATE TABLE mla_cnh (
	id int NOT NULL,
	categoria int NOT NULL,
	data_vencimento date NOT NULL,
	numero varchar(255) NOT NULL,
	CONSTRAINT mla_cnh_pkey PRIMARY KEY (id)
);

--------- Pessoa ------------

CREATE TABLE mla_pessoa (
	id int NOT NULL,
	cpf varchar(11) NOT NULL,
	data_nascimento date NOT NULL,
	email varchar(255) NOT NULL,
	nome varchar(255) NOT NULL,
	saldo float NOT NULL,
	CONSTRAINT mla_pessoa_pkey PRIMARY KEY (id)
);


--------- Motorista ------------

CREATE TABLE mla_motorista (
	is_busy bool NOT NULL,
	id int NOT NULL,
	carteira_habilitacao_id int NOT NULL,
	CONSTRAINT mla_motorista_pkey PRIMARY KEY (id),
	CONSTRAINT fk_cnh_id FOREIGN KEY (carteira_habilitacao_id) REFERENCES mla_cnh(id),
	CONSTRAINT fk_pessoa_id FOREIGN KEY (id) REFERENCES mla_pessoa(id)
);

--------- Notas ------------

CREATE TABLE mla_notas (
	pessoa_id int NOT NULL,
	notas int NOT NULL,
	CONSTRAINT fk_pessoa_id FOREIGN KEY (pessoa_id) REFERENCES mla_pessoa(id)
);

--------- Passageiro ------------

CREATE TABLE mla_passageiro (
	id int NOT NULL,
	CONSTRAINT mla_passageiro_pkey PRIMARY KEY (id),
	CONSTRAINT fk_pessoa_id FOREIGN KEY (id) REFERENCES mla_pessoa(id)
);

--------- Veiculo ------------

CREATE TABLE mla_veiculo (
	id int NOT NULL,
	ano int NOT NULL,
	categoria_cnh int NOT NULL,
	cor int NOT NULL,
	lugares int,
	marca int NOT NULL,
	modelo varchar(255) NOT NULL,
	placa varchar(255) NOT NULL,
	foto varchar(255),
	motorista_id int NOT NULL,
	CONSTRAINT mla_veiculo_pkey PRIMARY KEY (id),
	CONSTRAINT fk_motorista_id FOREIGN KEY (motorista_id) REFERENCES mla_motorista(id)
);

--------- Corrida ------------

CREATE TABLE mla_corrida (
	id uuid NOT NULL,
	fim_x float NOT NULL,
	fim_y float NOT NULL,
	inicio_x float NOT NULL,
	inicio_y float NOT NULL,
	estado_corrida int NOT NULL,
	hora_final timestamp,
	hora_inicio timestamp NOT NULL,
	motorista_avaliado bool NOT NULL,
	passageiro_avaliado bool NOT NULL,
	tempo_estimado int NOT NULL,
	passageiro_id int NOT NULL,
	veiculo_selecionado_id int NOT NULL,
	CONSTRAINT mla_corrida_pkey PRIMARY KEY (id),
	CONSTRAINT fk_pessoa_id FOREIGN KEY (passageiro_id) REFERENCES mla_passageiro(id),
	CONSTRAINT fk_passageiro_id FOREIGN KEY (veiculo_selecionado_id) REFERENCES mla_veiculo(id)
);
