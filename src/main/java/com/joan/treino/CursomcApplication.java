package com.joan.treino;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.joan.treino.domain.Categoria;
import com.joan.treino.domain.Cidade;
import com.joan.treino.domain.Cliente;
import com.joan.treino.domain.Endereco;
import com.joan.treino.domain.Estado;
import com.joan.treino.domain.Pagamento;
import com.joan.treino.domain.PagamentoComBoleto;
import com.joan.treino.domain.PagamentoComCartao;
import com.joan.treino.domain.Pedido;
import com.joan.treino.domain.Produto;
import com.joan.treino.domain.enums.EstadoPagamento;
import com.joan.treino.domain.enums.TipoCliente;
import com.joan.treino.repositories.CategoriaRepository;
import com.joan.treino.repositories.CidadeRepository;
import com.joan.treino.repositories.ClienteRepository;
import com.joan.treino.repositories.EnderecoRepository;
import com.joan.treino.repositories.EstadoRepository;
import com.joan.treino.repositories.PagamentoRepository;
import com.joan.treino.repositories.PedidoRepository;
import com.joan.treino.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentocoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null,"computador",2000.00);
		Produto p2 = new Produto(null,"impressora",800.00);
		Produto p3 = new Produto(null,"mouse",80.00);
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Cliente cli1 = new Cliente(null, "Maria", "mariasilva@gmail.com", "213131231", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("32244867","988119032"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "apartamento 303", "Jardim", "36046010", cli1, c1);
		Endereco e2 = new Endereco(null, "Av Matos", "106", "sala 200", "Centro", "36046015", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"),cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentocoRepository.saveAll(Arrays.asList(pagto1,pagto2));
	}
	
	
	
}
