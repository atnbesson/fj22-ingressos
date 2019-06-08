package br.com.caelum.ingresso.dao;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.caelum.ingresso.model.Compra;

@Repository
public class CompraDao {

	private EntityManager manager;
	
	public void save(Compra compra) {
		manager.persist(compra);
	}
}
