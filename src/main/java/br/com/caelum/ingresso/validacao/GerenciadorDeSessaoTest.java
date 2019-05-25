package br.com.caelum.ingresso.validacao;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTest {

	private Filme rogueOne;
	private Sala sala3D;
	private Sessao SessaoDasDez;
	private Sessao SessaoDasTreze;
	private Sessao SessaoDasDezoito;

	@Before
	public void preparaSessaoes() {
		this.rogueOne = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI");
		this.sala3D = new Sala("Sala 3d");
		this.SessaoDasDez = new Sessao(LocalTime.parse("10:00:00"), sala3D, rogueOne);
		this.SessaoDasTreze = new Sessao(LocalTime.parse("13:00:00"), sala3D, rogueOne);
		this.SessaoDasDezoito = new Sessao(LocalTime.parse("18:00:00"), sala3D, rogueOne);
	}

	@Test
	public void garanteQueNãoDevePermitirSessaoNoMesmoHorario() {
		List<Sessao> sessoes = Arrays.asList(SessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(SessaoDasDez));
	}

	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoJaExistente() {
		List<Sessao> sessoes = Arrays.asList(SessaoDasDez);
		Sessao sessao = new Sessao(SessaoDasDez.getHorario().minusHours(1), sala3D, rogueOne);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessao));
	}

	@Test
	public void garanteQueNaoDevePermitirSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExistente() {
		List<Sessao> sessoesDaSala = Arrays.asList(SessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesDaSala);
		Sessao sessao = new Sessao(SessaoDasDez.getHorario().plusHours(1), sala3D, rogueOne);
		Assert.assertFalse(gerenciador.cabe(sessao));
	}

	@Test
	public void garantequeDevePermitirUmaInsercaoEntreDoisFilmes() {
		List<Sessao> sessoes = Arrays.asList(SessaoDasDez, SessaoDasDezoito);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertTrue(gerenciador.cabe(SessaoDasTreze));
	}

	@Test
	public void GaranteQueDeveNaoPermitirUmaSessaoQueTerminaNoProximoDia() {
		List<Sessao> sessoes = Collections.emptyList();
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Sessao sessaoQueTerminaAmanha = new Sessao(LocalTime.parse("23:00:00"), sala3D, rogueOne);
		Assert.assertFalse(gerenciador.cabe(sessaoQueTerminaAmanha));

	}

}
