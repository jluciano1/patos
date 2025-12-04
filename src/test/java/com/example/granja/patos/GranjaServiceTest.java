package com.example.granja.patos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.granja.patos.constants.ConstantesGranja;
import com.example.granja.patos.dto.ClienteRequestDTO;
import com.example.granja.patos.dto.ClienteResponseDTO;
import com.example.granja.patos.dto.PatoRequestDTO;
import com.example.granja.patos.dto.PatoResponseDTO;
import com.example.granja.patos.dto.PatoVendaDetalheDTOResponse;
import com.example.granja.patos.dto.VendaRequestDTO;
import com.example.granja.patos.dto.VendaResponseDTO;
import com.example.granja.patos.dto.VendedorRequestDTO;
import com.example.granja.patos.dto.VendedorResponseDTO;
import com.example.granja.patos.dto.VendedorVendaDTOResponse;
import com.example.granja.patos.entity.Cliente;
import com.example.granja.patos.entity.Pato;
import com.example.granja.patos.entity.Venda;
import com.example.granja.patos.entity.Vendedor;
import com.example.granja.patos.exceptions.GranjaHttpException;
import com.example.granja.patos.repository.ClienteRepository;
import com.example.granja.patos.repository.PatoRepository;
import com.example.granja.patos.repository.VendaRepository;
import com.example.granja.patos.repository.VendedorRepository;
import com.example.granja.patos.service.GranjaService;

@SpringBootTest
@ActiveProfiles("test")
class GranjaServiceTest {

    @Autowired
    private GranjaService service;
    @Autowired
    private PatoRepository patoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private VendedorRepository vendedorRepository;
    @Autowired
    private VendaRepository vendaRepository;

    @Test
    @DisplayName("Cadastro de patos sem informar campo Nome")
    void cadastrarPatoErroNomeMaiorPermitidoTest() {
    	PatoRequestDTO dto = new PatoRequestDTO();
		dto.setNome("Cadastro de patos sem informar campo NomeCadastro de patos sem informar campo NomeCadastro de patos sem informar campo Nome");
		try {
			service.cadastrarPato(dto);
			fail("sem excecao");
		}
		catch (GranjaHttpException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.NOME_MAIOR_PERMITIDO);
		}
    }
    
    @Test
    @DisplayName("Cadastro de patos sem informar campo Nome")
    void cadastrarPatoErroNomeNaoInformadoTest() {
    	PatoRequestDTO dto = new PatoRequestDTO();
		dto.setMaeId(1l);
		try {
			service.cadastrarPato(dto);	
			fail("sem excecao");
		}
		catch (GranjaHttpException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.INFORMAR_NOME);
		}
    }
    
    @Test
    @DisplayName("Cadastro de patos apresentando erro ao informar idMae não existente")
    void cadastrarPatoErroMaeNaoEncontradoTest() {
    	PatoRequestDTO dto = new PatoRequestDTO();
		dto.setNome("Filho 1");
		dto.setMaeId(1l);
		try {
			service.cadastrarPato(dto);
			fail("sem excecao");
		}
		catch (GranjaHttpException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo("Valor inválido para o campo 'maeId', Pato com id 1 não encontrado.");
		}
    }
    
    @Test
    @DisplayName("Cadastro de patos com sucesso")
    void cadastrarPatoTest() {
    	cadastrarPatos();
    	List<Pato> lista = patoRepository.findAll();
        assertThat(lista.size()).isEqualTo(9);
    }
    
    @Test
    @DisplayName("Cadastro de clientes com erro ao não informar Nome")
    void cadastrarClienteErroSemNomeTest() {
    	ClienteRequestDTO dto = new ClienteRequestDTO();
		try {
			service.cadastrarCliente(dto);
			fail("sem excecao");
		}
		catch (GranjaHttpException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.INFORMAR_NOME);
		}
    }
    
    @Test
    @DisplayName("Cadastro de clientes com erro ao não informar Desconto")
    void cadastrarClienteErroSemDescontoTest() {
    	ClienteRequestDTO dto = new ClienteRequestDTO();
    	dto.setNome("teste");
		try {
			service.cadastrarCliente(dto);
			fail("sem excecao");
		}
		catch (GranjaHttpException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.INFORMAR_DESCONTO);
		}
    }
    
    @Test
    @DisplayName("Cadastro de clientes com erro ao não informar Desconto")
    void cadastrarClienteErroNomeMaiorPermitidoTest() {
    	ClienteRequestDTO dto = new ClienteRequestDTO();
    	dto.setNome("testeCadastro de clientes com erro ao não informar DescontotesteCadastro de clientes com erro ao não informar Desconto");
		try {
			service.cadastrarCliente(dto);
			fail("sem excecao");
		}
		catch (GranjaHttpException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.NOME_MAIOR_PERMITIDO);
		}
    }
    
    @Test
    @DisplayName("Cadastro de clientes com sucesso")
    void cadastrarClienteTest() {
		cadastrarClientes();
    	List<Cliente> lista = clienteRepository.findAll();
        assertThat(lista.size()).isEqualTo(2);
    }
	
    @Test
    @DisplayName("Cadastro de vendedores com sucesso")
    void cadastrarVendedorTest() {
    	cadastrarVendedores();
    	List<Vendedor> lista = vendedorRepository.findAll();
        assertThat(lista.size()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Cadastro de vendedores com erro por não informar Nome")
    void cadastrarVendedorErroSemNomeTest() {
    	VendedorRequestDTO dto = new VendedorRequestDTO();
		try {
			service.cadastrarVendedor(dto);	
			fail("sem excecao");
		}
		catch (GranjaHttpException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.INFORMAR_NOME);
		}
    }
    
    @Test
    @DisplayName("Cadastro de vendedores com erro Nome maior que o tamanho permitido")
    void cadastrarVendedorErroNomeMaiorPermitidoTest() {
    	VendedorRequestDTO dto = new VendedorRequestDTO();
    	dto.setNome("Cadastro de vendedores com erro por não informar NomeCadastro de vendedores com erro por não informar Nome");
		try {
			service.cadastrarVendedor(dto);	
			fail("sem excecao");
		}
		catch (GranjaHttpException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.NOME_MAIOR_PERMITIDO);
		}
    }
    
    @Test
    @DisplayName("Cadastro de vendedores com erro por não informar Cpf")
    void cadastrarVendedorErroCpfNaoInformadoTest() {
    	VendedorRequestDTO dto = new VendedorRequestDTO();
    	dto.setNome("Teste");
		try {
			service.cadastrarVendedor(dto);	
			fail("sem excecao");
		}
		catch (GranjaHttpException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.INFORMAR_CPF);
		}
    }
    
    @Test
    @DisplayName("Cadastro de vendedores com erro por não informar Matricula")
    void cadastrarVendedorErroSemMatriculaTest() {
    	VendedorRequestDTO dto = new VendedorRequestDTO();
    	dto.setNome("Teste");
    	dto.setCpf(1l);
		try {
			service.cadastrarVendedor(dto);	
			fail("sem excecao");
		}
		catch (GranjaHttpException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.INFORMAR_MATRICULA);
		}
    }
    
    @Test
    @DisplayName("Cadastro de vendedores com erro por informar Cpf já cadastrado")
    void cadastrarVendedorErroCpfJaCadastradoTest() {
    	cadastrarVendedores();
    	VendedorRequestDTO dto = new VendedorRequestDTO();
    	dto.setNome("Teste");
    	dto.setCpf(86880472001l);
		try {
			service.cadastrarVendedor(dto);	
			fail("sem excecao");
		}
		catch (GranjaHttpException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.CPF_JA_CADASTRADO);
		}
    }
    
    @Test
    @DisplayName("Cadastro de vendedores com erro por não informar Cpf")
    void cadastrarVendedorErroCpfInvalidoTest() {
    	VendedorRequestDTO dto = new VendedorRequestDTO();
    	dto.setNome("Teste");
    	dto.setCpf(1l);
    	dto.setMatricula("a1");
		try {
			service.cadastrarVendedor(dto);	
			fail("sem excecao");
		}
		catch (GranjaHttpException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.CPF_INVALIDO);
		}
    }
    
    @Test
    @DisplayName("Registro de vendas com sucesso")
    void cadastrarVendaTest() {
    	testeCadastroVenda();
        List<Venda> lista = vendaRepository.findAll();
        assertThat(lista.size()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Registro de vendas com erro por pato não estar mais disponível")
    void cadastrarVendaErroPatoIndisponivelTest() {
    	Long[] idsPatos = cadastrarPatos();
		Long[] idsCliente = cadastrarClientes();
		Long[] idsVendedor = cadastrarVendedores();
    	VendaRequestDTO dto = new VendaRequestDTO();
		dto.setIdCliente(idsCliente[0]);
		dto.setIdPato(idsPatos[1]);
		dto.setIdVendedor(idsVendedor[1]);
    	VendaResponseDTO r = service.cadastrarVenda(dto);
    	List<Venda> lista = vendaRepository.findAll();
        assertThat(lista.size()).isEqualTo(1);
        assertThat(r.valor()).isEqualTo(BigDecimal.valueOf(70).setScale(2));
        try {
        	r = service.cadastrarVenda(dto);
        	fail("sem excecao");
        }
        catch (GranjaHttpException e) {
        	assertThat(e.getLocalizedMessage()).isEqualTo("Pato com id 11 não está mais disponível.");
		}
    }
    
    @Test
    @DisplayName("Registro de vendas com erro por não informar cliente")
    void cadastrarVendaErroSemClienteTest() {
    	VendaRequestDTO dto = new VendaRequestDTO();
		try {
    		service.cadastrarVenda(dto);
    		fail("sem excecao");
        }
        catch (GranjaHttpException e) {
        	assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.INFORMAR_ID_CLIENTE);
		}
    }
    
    @Test
    @DisplayName("Registro de vendas com erro por não informar pato")
    void cadastrarVendaErroSemPatoTest() {
    	VendaRequestDTO dto = new VendaRequestDTO();
    	dto.setIdCliente(1l);
		try {
    		service.cadastrarVenda(dto);
    		fail("sem excecao");
        }
        catch (GranjaHttpException e) {
        	assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.INFORMAR_ID_PATO);
		}
    }
    
    @Test
    @DisplayName("Registro de vendas com erro por não informar vendedor")
    void cadastrarVendaErroSemVendedorTest() {
    	VendaRequestDTO dto = new VendaRequestDTO();
    	dto.setIdCliente(1l);
    	dto.setIdPato(1l);
		try {
    		service.cadastrarVenda(dto);
    		fail("sem excecao");
        }
        catch (GranjaHttpException e) {
        	assertThat(e.getLocalizedMessage()).isEqualTo(ConstantesGranja.INFORMAR_ID_VENDEDOR);
		}
    }
    
    @Test
    @DisplayName("Registro de vendas com erro por informar cliente inválido")
    void cadastrarVendaErroClienteInvalidoTest() {
    	VendaRequestDTO dto = new VendaRequestDTO();
    	dto.setIdCliente(1l);
    	dto.setIdPato(1l);
    	dto.setIdVendedor(1l);
		try {
    		service.cadastrarVenda(dto);
    		fail("sem excecao");
        }
        catch (GranjaHttpException e) {
        	assertThat(e.getLocalizedMessage()).isEqualTo("Valor inválido para o campo 'idCliente', Cliente com id 1 não encontrado.");
		}
    }
    
    @Test
    @DisplayName("Registro de vendas com erro por informar pato inválido")
    void cadastrarVendaErroPatoInvalidoTest() {
    	VendaRequestDTO dto = new VendaRequestDTO();
    	Long[] idsCliente = cadastrarClientes();
    	Long[] idsVendedores = cadastrarVendedores();
    	dto.setIdCliente(idsCliente[0]);
    	dto.setIdPato(1l);
    	dto.setIdVendedor(idsVendedores[0]);
		try {
    		service.cadastrarVenda(dto);
    		fail("sem excecao");
        }
        catch (GranjaHttpException e) {
        	assertThat(e.getLocalizedMessage()).isEqualTo("Valor inválido para o campo 'idPato', Pato com id 1 não encontrado.");
		}
    }
    
    @Test
    @DisplayName("Registro de vendas com erro por informar vendedor inválido")
    void cadastrarVendaErroVendedorInvalidoTest() {
    	VendaRequestDTO dto = new VendaRequestDTO();
    	Long[] idsCliente = cadastrarClientes();
    	dto.setIdCliente(idsCliente[0]);
    	dto.setIdPato(1l);
    	dto.setIdVendedor(1l);
		try {
    		service.cadastrarVenda(dto);
    		fail("sem excecao");
        }
        catch (GranjaHttpException e) {
        	assertThat(e.getLocalizedMessage()).isEqualTo("Valor inválido para o campo 'idVendedor', Vendedor com id 1 não encontrado.");
		}
    }
    
    @Test
    @DisplayName("Listagem de vendas")
    void listPatosVendidosTest() {
    	testeCadastroVenda();
        List<PatoVendaDetalheDTOResponse> resposta = service.listPatosVendidos();
        assertThat(resposta.size()).isEqualTo(3);
    }
    
    private void testeCadastroVenda() {
    	Long[] idsPatos = cadastrarPatos();
		Long[] idsCliente = cadastrarClientes();
		Long[] idsVendedor = cadastrarVendedores();
    	VendaRequestDTO dto = new VendaRequestDTO();
		dto.setIdCliente(idsCliente[0]);
		dto.setIdPato(idsPatos[1]);
		dto.setIdVendedor(idsVendedor[1]);
    	VendaResponseDTO r = service.cadastrarVenda(dto);
    	List<Venda> lista = vendaRepository.findAll();
        assertThat(lista.size()).isEqualTo(1);
        assertThat(r.valor()).isEqualTo(BigDecimal.valueOf(70).setScale(2));
        Optional<Pato> p = patoRepository.findById(r.idPato());
        assertThat(p.get().getStatus()).isEqualTo("V");
        
        dto = new VendaRequestDTO();
		dto.setIdCliente(idsCliente[0]);
		dto.setIdPato(idsPatos[0]);
		dto.setIdVendedor(idsVendedor[0]);
		r = service.cadastrarVenda(dto);
        assertThat(r.valor()).isEqualTo(BigDecimal.valueOf(25).setScale(2));
        p = patoRepository.findById(r.idPato());
        assertThat(p.get().getStatus()).isEqualTo("V");
        
        dto = new VendaRequestDTO();
		dto.setIdCliente(idsCliente[1]);
		dto.setIdPato(idsPatos[7]);
		dto.setIdVendedor(idsVendedor[0]);
		r = service.cadastrarVenda(dto);
        assertThat(r.valor()).isEqualTo(BigDecimal.valueOf(40).setScale(2));
        p = patoRepository.findById(r.idPato());
        assertThat(p.get().getStatus()).isEqualTo("V");
        
        lista = vendaRepository.findAll();
        assertThat(lista.size()).isEqualTo(3);
	}

	@Test
    @DisplayName("Ranking de vendedores")
    void listVendedoresRankingTest() {
		testeCadastroVenda();
		List<Vendedor> vendedores = vendedorRepository.findAll();
		LocalDateTime doisDiasAtras = LocalDateTime.now().minusDays(2);
    	Date date = Date.from(doisDiasAtras.atZone(ZoneId.systemDefault()).toInstant());
		List<VendedorVendaDTOResponse> lista = service.listVendedoresRanking(date, new Date(), "V", 3, "quantidade");
		assertThat(lista.size()).isEqualTo(2);
		assertThat(lista.get(0).idVendedor()).isEqualTo(vendedores.get(0).getId());
		assertThat(lista.get(0).nomeVendedor()).isEqualTo("Vendedor 1");
		assertThat(lista.get(0).valorTotal()).isEqualTo(BigDecimal.valueOf(65).setScale(2));
		assertThat(lista.get(0).quantidadeVenda()).isEqualTo(Integer.valueOf(2));
		assertThat(lista.get(0).ranking()).isEqualTo(Integer.valueOf(1));
		
		assertThat(lista.get(1).idVendedor()).isEqualTo(vendedores.get(1).getId());
		assertThat(lista.get(1).nomeVendedor()).isEqualTo("Vendedor 2");
		assertThat(lista.get(1).valorTotal()).isEqualTo(BigDecimal.valueOf(70).setScale(2));
		assertThat(lista.get(1).quantidadeVenda()).isEqualTo(Integer.valueOf(1));
		assertThat(lista.get(1).ranking()).isEqualTo(Integer.valueOf(2));
		
		lista = service.listVendedoresRanking(date, new Date(), "V", 3, "valor");
		assertThat(lista.size()).isEqualTo(2);
		assertThat(lista.get(0).idVendedor()).isEqualTo(vendedores.get(1).getId());
		assertThat(lista.get(0).nomeVendedor()).isEqualTo("Vendedor 2");
		assertThat(lista.get(0).valorTotal()).isEqualTo(BigDecimal.valueOf(70).setScale(2));
		assertThat(lista.get(0).quantidadeVenda()).isEqualTo(Integer.valueOf(1));
		assertThat(lista.get(0).ranking()).isEqualTo(Integer.valueOf(1));
		
		assertThat(lista.get(1).idVendedor()).isEqualTo(vendedores.get(0).getId());
		assertThat(lista.get(1).nomeVendedor()).isEqualTo("Vendedor 1");
		assertThat(lista.get(1).valorTotal()).isEqualTo(BigDecimal.valueOf(65).setScale(2));
		assertThat(lista.get(1).quantidadeVenda()).isEqualTo(Integer.valueOf(2));
		assertThat(lista.get(1).ranking()).isEqualTo(Integer.valueOf(2));
    }
    
    @Test
    @DisplayName("Planilha")
    void gerarRelatorioVendaExcel() {
    	testeCadastroVenda();
    	try {
			XSSFWorkbook excel = service.gerarRelatorioVendaExcel("10/10/2020", "12/10/2020");
			assertThat(excel).isNotNull();
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
    }
    
    private Long[] cadastrarPatos() {
		PatoRequestDTO dto = new PatoRequestDTO();
		dto.setNome("Mãe");
		PatoResponseDTO r = service.cadastrarPato(dto);
		dto = new PatoRequestDTO();
		dto.setNome("Filho 1");
		dto.setMaeId(r.id());
		PatoResponseDTO r1 = service.cadastrarPato(dto);
		
		dto = new PatoRequestDTO();
		dto.setNome("Filho 2");
		dto.setMaeId(r.id());
		PatoResponseDTO r2 = service.cadastrarPato(dto);
		
		dto = new PatoRequestDTO();
		dto.setNome("Mãe 2");
		PatoResponseDTO r3 = service.cadastrarPato(dto);
		dto = new PatoRequestDTO();
		dto.setNome("Filho 21");
		dto.setMaeId(r3.id());
		PatoResponseDTO r4 = service.cadastrarPato(dto);
		
		dto = new PatoRequestDTO();
		dto.setNome("Filho 22");
		dto.setMaeId(r3.id());
		PatoResponseDTO r5 = service.cadastrarPato(dto);
		
		dto = new PatoRequestDTO();
		dto.setNome("Filho 23");
		dto.setMaeId(r3.id());
		PatoResponseDTO r6 = service.cadastrarPato(dto);
		
		dto = new PatoRequestDTO();
		dto.setNome("Mãe 3");
		dto.setMaeId(r.id());
		PatoResponseDTO r7 = service.cadastrarPato(dto);
		
		dto = new PatoRequestDTO();
		dto.setNome("Filho 2");
		dto.setMaeId(r7.id());
		PatoResponseDTO r8 = service.cadastrarPato(dto);
		
		return new Long[]{r.id(), r1.id(), r2.id(), r3.id(), r4.id(), r5.id(), r6.id(), r7.id(), r8.id()};
	}
    
    private Long[] cadastrarClientes() {
		ClienteRequestDTO dto = new ClienteRequestDTO();
		dto.setNome("Cliente 1");
		dto.setDesconto(false);
		ClienteResponseDTO r1 = service.cadastrarCliente(dto);
		dto = new ClienteRequestDTO();
		dto.setNome("Cliente 2");
		dto.setDesconto(true);
		ClienteResponseDTO r2 = service.cadastrarCliente(dto);
		return new Long[]{r1.id(), r2.id()};
	}
    
    private Long[] cadastrarVendedores() {
		VendedorRequestDTO dto = new VendedorRequestDTO();
		dto.setNome("Vendedor 1");
		dto.setMatricula("a1");
		dto.setCpf(Long.valueOf(86880472001l));
		VendedorResponseDTO r1 = service.cadastrarVendedor(dto);
		dto = new VendedorRequestDTO();
		dto.setNome("Vendedor 2");
		dto.setMatricula("a1");
		dto.setCpf(Long.valueOf(19512344050l));
		VendedorResponseDTO r2 = service.cadastrarVendedor(dto);
		return new Long[]{r1.id(), r2.id()};
	}
    
    @BeforeEach
    void beforeEach() {
        limparDados();
    }

    @AfterEach
    void afterEach() {
        limparDados();
    }

    private void limparDados() {
        vendaRepository.deleteAll();
        patoRepository.deleteAll();
        clienteRepository.deleteAll();
        vendedorRepository.deleteAll();
    }
}