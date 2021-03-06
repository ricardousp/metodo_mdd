<?php

include_once "Bruno.class.php";
include_once "Daniel.class.php";

class BrunoDB
{
	private $dbconn;
	private $host = "localhost";
	private $port = "5432";
	private $dbname = "proj3";
	private $user = "postgres";
	private $password = "teste";

	public function __construct()
	{
		$this->dbconn = pg_connect("host= " . $this->host . " port=" . $this->port . " dbname=" . $this->dbname . " user=" . $this->user . " password=" . $this->password);
	}

	public function add($Bruno)
	{
		$query = pg_query("INSERT INTO bruno(
				cpf,
				endereco,
				nascimento,
				mensalidade,
				created_at,
				update_at
			) VALUES(
				'" . $Bruno->getCpf() . "',
				'" . $Bruno->getEndereco() . "',
				'" . $Bruno->getNascimento() . "',
				'" . $Bruno->getMensalidade() . "',
				now(),
				now()
			) RETURNING id;
		");
		
		$result = (pg_affected_rows($query) > 0);
		$id = pg_fetch_assoc($query);
		$id = $id['id'];
		
		foreach ($Bruno->getOrientador() as $rowOrientador) {
			$query = pg_query("INSERT INTO bruno_daniel(
					bruno_id,
					daniel_id,
					created_at,
					update_at
				) VALUES(
					" . $id . ",
					" . $rowOrientador->getId() . ",
					now(),
					now()
				)
			");
			
			$result = $result && (pg_affected_rows($query) > 0);
		}

		return $result;
	}

	public function update($Bruno)
	{
		$query = pg_query("UPDATE bruno SET
				cpf = '" . $Bruno->getCpf() . "',
				endereco = '" . $Bruno->getEndereco() . "',
				nascimento = '" . $Bruno->getNascimento() . "',
				mensalidade = '" . $Bruno->getMensalidade() . "',
				update_at = now()
			WHERE id = " . $Bruno->getId()
		);

		$result = (pg_affected_rows($query) > 0);
		
		$query = pg_query("DELETE FROM bruno_daniel
			WHERE bruno_id = " . $Bruno->getId()
		);
		
		$result = $result && (pg_affected_rows($query) > 0);

		foreach ($Bruno->getOrientador() as $rowOrientador) {
			$query = pg_query("INSERT INTO bruno_daniel(
					bruno_id,
					daniel_id,
					created_at,
					update_at
				) VALUES(
					" . $Bruno->getId() . ",
					" . $rowOrientador->getId() . ",
					now(),
					now()
				)
			");
			
			$result = $result && (pg_affected_rows($query) > 0);
		}

		return $result;
	}

	public function destroy($Bruno)
	{
		$result = true;
		$query = pg_query("DELETE FROM bruno_daniel
			WHERE bruno_id = " . $Bruno->getId()
		);
		
		$result = $result && (pg_affected_rows($query) > 0);

		$query = pg_query("DELETE FROM bruno WHERE id = " . $Bruno->getId());

		$result = $result && (pg_affected_rows($query) > 0);
		
		return $result;
	}

	public function getAll()
	{
		$result = array();

		$query = pg_query("SELECT
				bruno.id AS bruno_id,
				bruno.cpf AS bruno_cpf,bruno.endereco AS bruno_endereco,bruno.nascimento AS bruno_nascimento,bruno.mensalidade AS bruno_mensalidade,
				created_at,
				update_at
			FROM bruno
			
			ORDER BY bruno.id
		");

		if ($query) {
			while ($row = pg_fetch_assoc($query)) {
				$Bruno = new Bruno();

				$Bruno->setId($row["bruno_id"]);
			
				$Bruno->setCpf($row["bruno_cpf"]);
			
				$Bruno->setEndereco($row["bruno_endereco"]);
			
				$Bruno->setNascimento($row["bruno_nascimento"]);
			
				$Bruno->setMensalidade($row["bruno_mensalidade"]);
			
				$query_orientador = pg_query("SELECT
						daniel.id AS daniel_id, daniel.cpf AS daniel_cpf,daniel.endereco AS daniel_endereco,
						bruno_daniel.created_at,
						bruno_daniel.update_at
					FROM bruno_daniel
					INNER JOIN daniel ON daniel.id = bruno_daniel.daniel_id 
					WHERE bruno_daniel.bruno_id = " . $Bruno->getId()
				);
				
				if ($query_orientador) {
					while ($row_orientador = pg_fetch_assoc($query_orientador)) {
						$Daniel = new Daniel();
						$Daniel->setId($row_orientador["daniel_id"]);
						$Daniel->setCpf($row_orientador["daniel_cpf"]);
						$Daniel->setEndereco($row_orientador["daniel_endereco"]);
						
						$Bruno->addOrientador($Daniel);
					}
				}
			
				$result[] = $Bruno;
			}
		}

		return $result;
	}

	public function get($Bruno)
	{
		$result = array();

		$where = "";

	
		if (strlen($Bruno->getCpf()) > 0) {
			if (strlen($where) > 0) {
				$where .= " AND ";
			} else {
				$where = "WHERE ";
			}

			$where .= "bruno.cpf = '" . $Bruno->getCpf() . "'";
			
		}
	
		if (strlen($Bruno->getEndereco()) > 0) {
			if (strlen($where) > 0) {
				$where .= " AND ";
			} else {
				$where = "WHERE ";
			}

			$where .= "bruno.endereco = '" . $Bruno->getEndereco() . "'";
			
		}
	
		if (strlen($Bruno->getNascimento()) > 0) {
			if (strlen($where) > 0) {
				$where .= " AND ";
			} else {
				$where = "WHERE ";
			}

			$where .= "bruno.nascimento = '" . $Bruno->getNascimento() . "'";
			
		}
	
		if (strlen($Bruno->getMensalidade()) > 0) {
			if (strlen($where) > 0) {
				$where .= " AND ";
			} else {
				$where = "WHERE ";
			}

			$where .= "bruno.mensalidade = '" . $Bruno->getMensalidade() . "'";
			
		}
	
		if (
			(is_array($Bruno->getOrientador()) && count($Bruno->getOrientador()) > 0)
			|| (is_object($Bruno->getOrientador()) && $Bruno->getOrientador()->getId() > 0)
		) {
			if (strlen($where) > 0) {
				$where .= " AND ";
			} else {
				$where = "WHERE ";
			}

			if (is_array($Bruno->getOrientador())) {
					$first = true;
					$where .= "(";
					foreach ($Bruno->getOrientador() as $orientador) {
						if ( ! $first) {
							$where .= " OR ";
						}

						$where .= "(
							SELECT count(*)
							FROM bruno_daniel
							WHERE
								bruno_daniel.bruno_id = bruno.id
								AND bruno_daniel.daniel_id = " . $orientador->getId() . "
						) > 0";
						
						$first = false;
					}
					$where .= ")";
				} else {
					$where .= "bruno.orientador = " . $Bruno->getOrientador()->getId();
				}
			
		}
	
		$query = pg_query("SELECT
				bruno.id AS bruno_id,
				bruno.cpf AS bruno_cpf,bruno.endereco AS bruno_endereco,bruno.nascimento AS bruno_nascimento,bruno.mensalidade AS bruno_mensalidade,
				created_at,
				update_at
			FROM bruno
			
		" . $where . "ORDER BY bruno.id");

		if ($query) {
			while ($row = pg_fetch_assoc($query)) {
				$Bruno = new Bruno();

				$Bruno->setId($row["bruno_id"]);
			
				$Bruno->setCpf($row["bruno_cpf"]);
			
				$Bruno->setEndereco($row["bruno_endereco"]);
			
				$Bruno->setNascimento($row["bruno_nascimento"]);
			
				$Bruno->setMensalidade($row["bruno_mensalidade"]);
			
				$query_orientador = pg_query("SELECT
						daniel.id AS daniel_id, daniel.cpf AS daniel_cpf,daniel.endereco AS daniel_endereco,
						bruno_daniel.created_at,
						bruno_daniel.update_at
					FROM bruno_daniel
					INNER JOIN daniel ON daniel.id = bruno_daniel.daniel_id 
					WHERE bruno_daniel.bruno_id = " . $Bruno->getId()
				);
				
				if ($query_orientador) {
					while ($row_orientador = pg_fetch_assoc($query_orientador)) {
						$Daniel = new Daniel();
						$Daniel->setId($row_orientador["daniel_id"]);
						$Daniel->setCpf($row_orientador["daniel_cpf"]);
						$Daniel->setEndereco($row_orientador["daniel_endereco"]);
						
						$Bruno->addOrientador($Daniel);
					}
				}
			
				$result[] = $Bruno;
			}
		}

		return $result;
	}
}

?>