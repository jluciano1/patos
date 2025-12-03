package com.example.granja.patos.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.granja.patos.constants.ConstantesGranja;
import com.example.granja.patos.dto.ClienteRequestDTO;
import com.example.granja.patos.dto.ClienteResponseDTO;
import com.example.granja.patos.dto.PatoRequestDTO;
import com.example.granja.patos.dto.PatoResponseDTO;
import com.example.granja.patos.dto.PatoVendaDetalheDTO;
import com.example.granja.patos.dto.PatoVendaDetalheDTOResponse;
import com.example.granja.patos.dto.VendaRequestDTO;
import com.example.granja.patos.dto.VendaResponseDTO;
import com.example.granja.patos.dto.VendedorRequestDTO;
import com.example.granja.patos.dto.VendedorResponseDTO;
import com.example.granja.patos.dto.VendedorVendaDTO;
import com.example.granja.patos.dto.VendedorVendaDTOResponse;
import com.example.granja.patos.entity.Cliente;
import com.example.granja.patos.entity.Pato;
import com.example.granja.patos.entity.Venda;
import com.example.granja.patos.entity.Vendedor;
import com.example.granja.patos.enums.StatusPato;
import com.example.granja.patos.exceptions.GranjaHttpException;
import com.example.granja.patos.repository.ClienteRepository;
import com.example.granja.patos.repository.PatoRepository;
import com.example.granja.patos.repository.VendaRepository;
import com.example.granja.patos.repository.VendedorRepository;
import com.google.common.base.Strings;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import jakarta.validation.Valid;

@Service
public class GranjaService implements GranjaServiceGeneric {

    private final PatoRepository patoRepository;
    private final ClienteRepository clienteRepository;
    private final VendaRepository vendaRepository;
    private final VendedorRepository vendedorRepository;

    private CellStyle estiloBranco;
    private CellStyle estiloCinza;
    private CellStyle estiloHeader;
    private CellStyle estiloTitulo;

    public GranjaService(
            PatoRepository patoRepository,
            VendaRepository vendaRepository,
            VendedorRepository vendedorRepository,
            ClienteRepository clienteRepository) {

        this.patoRepository = patoRepository;
        this.vendaRepository = vendaRepository;
        this.vendedorRepository = vendedorRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public PatoResponseDTO cadastrarPato(@Valid PatoRequestDTO pato) throws GranjaHttpException {
        validarCadastroPato(pato);

        Pato p = new Pato();
        p.setNome(pato.getNome());
        p.setStatus(StatusPato.DISPONIVEL.getCodigo());

        p.setMae(findAndValidatePatoById(pato.getMaeId(), false));

        Pato saved = patoRepository.save(p);
        return PatoResponseDTO.from(saved);
    }

    private Pato findAndValidatePatoById(Long id, Boolean patoMae) {
        if (Objects.nonNull(id)) {
            Optional<Pato> mae = patoRepository.findById(id);
            if (mae.isPresent()) {
                return mae.get();
            } else {
                throw new GranjaHttpException(
                        HttpStatus.BAD_REQUEST,
                        String.format(
                                patoMae ? ConstantesGranja.ID_MAE_INVALIDO : ConstantesGranja.ID_PATO_INVALIDO,
                                id.toString()
                        )
                );
            }
        }
        return null;
    }

    private void validarCadastroPato(@Valid PatoRequestDTO pato) throws GranjaHttpException {
        if (Objects.nonNull(pato) && Objects.isNull(pato.getNome())) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.INFORMAR_NOME);
        }
        if (Objects.nonNull(pato) && pato.getNome().length() > 100) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.NOME_MAIOR_PERMITIDO);
        }
    }

    public ClienteResponseDTO cadastrarCliente(@Valid ClienteRequestDTO cliente) {
        validarCadastroCliente(cliente);

        Cliente c = new Cliente();
        c.setNome(cliente.getNome());
        c.setDesconto(cliente.getDesconto());

        Cliente saved = clienteRepository.save(c);
        return ClienteResponseDTO.from(saved);
    }

    private void validarCadastroCliente(@Valid ClienteRequestDTO cliente) {
        if (Objects.isNull(cliente.getNome())) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.INFORMAR_NOME);
        }
        if (cliente.getNome().length() > 100) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.NOME_MAIOR_PERMITIDO);
        }
        if (Objects.isNull(cliente.getDesconto())) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.INFORMAR_DESCONTO);
        }
    }

    public VendedorResponseDTO cadastrarVendedor(@Valid VendedorRequestDTO vendedor) {
        validarCadastroVendedor(vendedor);

        Vendedor v = new Vendedor();
        v.setNome(vendedor.getNome());
        v.setCpf(vendedor.getCpf());
        v.setMatricula(vendedor.getMatricula());

        Vendedor saved = vendedorRepository.save(v);
        return VendedorResponseDTO.from(saved);
    }

    private void validarCadastroVendedor(@Valid VendedorRequestDTO vendedor) {

        if (Objects.isNull(vendedor.getNome())) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.INFORMAR_NOME);
        }
        if (vendedor.getNome().length() > 100) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.NOME_MAIOR_PERMITIDO);
        }
        if (Objects.isNull(vendedor.getCpf())) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.INFORMAR_CPF);
        }
        if (Objects.isNull(vendedor.getMatricula())) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.INFORMAR_MATRICULA);
        }
        if (!isCpfValido(Strings.padStart(vendedor.getCpf().toString(), 11, '0'))) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.CPF_INVALIDO);
        }
    }

    private static Boolean isCpfValido(String cpf) {
        CPFValidator validator = new CPFValidator();
        try {
            validator.assertValid(cpf);
            return true;
        } catch (InvalidStateException e) {
            return false;
        }
    }

    public VendaResponseDTO cadastrarVenda(@Valid VendaRequestDTO venda) {
        validarCadastroVenda(venda);

        Cliente cliente = validateClienteById(venda.getIdCliente());
        validateVendedorById(venda.getIdVendedor());

        Pato pato = findAndValidatePatoById(venda.getIdPato(), false);

        if (!pato.getStatus().equals(StatusPato.DISPONIVEL.getCodigo())) {
            throw new GranjaHttpException(
                    HttpStatus.BAD_REQUEST,
                    String.format(ConstantesGranja.PATO_NAO_DISPONIVEL, pato.getId())
            );
        }

        Venda v = new Venda();
        v.setDataHora(new Date());
        v.setIdCliente(venda.getIdCliente());
        v.setIdPato(venda.getIdPato());
        v.setIdVendedor(venda.getIdVendedor());
        
        v.setValor(setValorVenda(venda));

        if (cliente.getDesconto()) {
            v.setValor(
                    v.getValor().multiply(BigDecimal.valueOf(0.8)).setScale(2, RoundingMode.FLOOR)
            );
        }

        Venda saved = vendaRepository.save(v);

        pato.setStatus(StatusPato.VENDIDO.getCodigo());
        patoRepository.save(pato);

        return VendaResponseDTO.from(saved);
    }

    private BigDecimal setValorVenda(@Valid VendaRequestDTO venda) {
		Integer quantidadeFilhos = patoRepository.countByMaeId(venda.getIdPato());
		if (Objects.nonNull(quantidadeFilhos) && quantidadeFilhos.compareTo(Integer.valueOf(1)) == 0)
		{
			return BigDecimal.valueOf(50);
		}
		else if (Objects.nonNull(quantidadeFilhos) 
				&& (quantidadeFilhos.compareTo(Integer.valueOf(2)) == 0 || quantidadeFilhos.compareTo(Integer.valueOf(2)) > 0))
		{
			return BigDecimal.valueOf(25);
		}
		else
		{
			return BigDecimal.valueOf(70);
		}
	}

	private void validarCadastroVenda(@Valid VendaRequestDTO venda) {
        if (Objects.isNull(venda.getIdCliente())) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.INFORMAR_ID_CLIENTE);
        }
        if (Objects.isNull(venda.getIdPato())) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.INFORMAR_ID_PATO);
        }
        if (Objects.isNull(venda.getIdVendedor())) {
            throw new GranjaHttpException(HttpStatus.BAD_REQUEST, ConstantesGranja.INFORMAR_ID_VENDEDOR);
        }
    }

    private Cliente validateClienteById(Long id) {
        Optional<Cliente> c = clienteRepository.findById(id);
        return c.orElseThrow(() ->
                new GranjaHttpException(
                        HttpStatus.BAD_REQUEST,
                        String.format(ConstantesGranja.ID_CLIENTE_INVALIDO, id)
                )
        );
    }

    private void validateVendedorById(Long id) {
        Optional<Vendedor> v = vendedorRepository.findById(id);
        if (!v.isPresent()) {
            throw new GranjaHttpException(
                    HttpStatus.BAD_REQUEST,
                    String.format(ConstantesGranja.ID_VENDEDOR_INVALIDO, id)
            );
        }
    }

    public List<PatoVendaDetalheDTOResponse> listPatosVendidos() {
        List<PatoVendaDetalheDTO> patos = patoRepository.findByStatus("V");

        return patos.stream()
                .map(PatoVendaDetalheDTOResponse::from)
                .toList();
    }

    public List<VendedorVendaDTOResponse> listVendedoresRanking(
            Date inicio,
            Date fim,
            String status,
            Integer ranking,
            String ordenarPor) {

        List<VendedorVendaDTO> vendas =
                vendedorRepository.findByStatusAndPeriodo(inicio, fim, status);

        ordenarPor = "quantidade".equals(ordenarPor) ? "Q" : "V";

        Comparator<VendedorVendaDTO> comparator = "Q".equalsIgnoreCase(ordenarPor)
                ? Comparator.comparing(VendedorVendaDTO::getQuantidadeVenda).reversed()
                : Comparator.comparing(VendedorVendaDTO::getValorTotal).reversed();

        vendas.forEach(dto -> {
            if (dto.getValorTotal() != null) {
                dto.setValorTotal(dto.getValorTotal().setScale(2, RoundingMode.FLOOR));
            }
        });

        vendas.sort(comparator);

        if (ranking != null && ranking > 0) {
            vendas = vendas.stream().limit(ranking).toList();
        }

        int pos = 1;
        for (VendedorVendaDTO dto : vendas) {
            dto.setRanking(pos++);
        }

        return vendas.stream()
                .map(VendedorVendaDTOResponse::from)
                .toList();
    }

    public XSSFWorkbook gerarRelatorioVendaExcel(String dataInicial, String dataFinal) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();
        prepararEstilos(wb);

        XSSFSheet sheet = wb.createSheet("Relatório");

        int rowIndex = 0;

        // Título
        Row titulo = sheet.createRow(rowIndex++);
        Cell cell = titulo.createCell(3);
        cell.setCellValue("RELATÓRIO DE VENDA");
        aplicarEstiloTitulo(wb, cell);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 7));

        // Datas
        Row datas = sheet.createRow(rowIndex++);
        datas.createCell(3).setCellValue("Gerado em:");
        datas.createCell(4).setCellValue(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        datas.createCell(5).setCellValue("Data inicial / Data final");
        datas.createCell(6).setCellValue(dataInicial + " - " + dataFinal);

        // Cabeçalho da tabela
        Row header = sheet.createRow(rowIndex++);
        String[] colunas = {
                "Nome", "Status", "Cliente", "Tipo do Cliente",
                "Valor", "Data/hora", "Vendedor"
        };

        CellStyle headerStyle = estiloCabecalho(wb);

        for (int i = 0; i < colunas.length; i++) {
            Cell c = header.createCell(i + 3);
            c.setCellValue(colunas[i]);
            c.setCellStyle(headerStyle);
        }

        // Dados
        List<Pato> patosMae = patoRepository.findByMaeIsNull();

        for (Pato mae : patosMae) {
            rowIndex = montarLinhaPato(sheet, wb, rowIndex, mae, 0);
        }

        for (int i = 0; i < 15; i++) {
            sheet.autoSizeColumn(i);
        }

        return wb;
    }

    private int montarLinhaPato(
            XSSFSheet sheet,
            XSSFWorkbook wb,
            int rowIndex,
            Pato pato,
            int nivel) {
        boolean linhaCinza = (rowIndex % 2 == 0);

        Row row = sheet.createRow(rowIndex++);

        for (int i = 3; i < 10; i++) {
            Cell c = row.createCell(i);
            c.setCellStyle(linhaCinza ? estiloCinza : estiloBranco);
        }

        int colBase = 3 + nivel;

        // Nome
        row.getCell(colBase).setCellValue(pato.getNome());

        // Status
        row.getCell(4).setCellValue(pato.getStatus() == null ? "-" : pato.getStatus());

        // Venda
        Optional<Venda> vendaOpt = vendaRepository.findByIdPato(pato.getId());

        if (vendaOpt.isPresent()) {

            Venda venda = vendaOpt.get();

            Cliente cli = venda.getIdCliente() == null
                    ? null
                    : clienteRepository.findById(venda.getIdCliente()).orElse(null);

            Vendedor vendedor = venda.getIdVendedor() == null
                    ? null
                    : vendedorRepository.findById(venda.getIdVendedor()).orElse(null);

            row.getCell(5).setCellValue(cli == null ? "-" : cli.getNome());
            row.getCell(6).setCellValue(cli == null ? "-" :
                    (cli.getDesconto() ? "Com Desconto" : "Sem Desconto"));

            row.getCell(7).setCellValue(
                    venda.getValor() == null
                            ? "-"
                            : "R$ " + venda.getValor().setScale(2, RoundingMode.HALF_UP)
            );

            row.getCell(8).setCellValue(
                    venda.getDataHora() == null
                            ? "-"
                            : new SimpleDateFormat("dd/MM/yyyy HH:mm").format(venda.getDataHora())
            );

            row.getCell(9).setCellValue(vendedor == null ? "-" : vendedor.getNome());

        } else {
            row.getCell(5).setCellValue("-");
            row.getCell(6).setCellValue("-");
            row.getCell(7).setCellValue("-");
            row.getCell(8).setCellValue("-");
            row.getCell(9).setCellValue("-");
        }

        // Recursivo (filhos)
        List<Pato> filhos = patoRepository.findByMaeId(pato.getId());

        for (Pato filho : filhos) {
            rowIndex = montarLinhaPato(sheet, wb, rowIndex, filho, nivel + 1);
        }

        return rowIndex;
    }

    private void aplicarEstiloTitulo(XSSFWorkbook wb, Cell cell) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();

        font.setBold(true);
        font.setFontHeightInPoints((short) 16);

        style.setFont(font);
        cell.setCellStyle(style);
    }

    private CellStyle estiloCabecalho(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();

        font.setBold(true);
        style.setFont(font);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return style;
    }

    private void prepararEstilos(XSSFWorkbook wb) {
        // Fonte normal
        XSSFFont fonteNormal = wb.createFont();
        fonteNormal.setFontHeightInPoints((short) 11);
        fonteNormal.setFontName("Calibri");

        // Fonte header
        XSSFFont fonteHeader = wb.createFont();
        fonteHeader.setBold(true);
        fonteHeader.setFontHeightInPoints((short) 11);
        fonteHeader.setColor(IndexedColors.WHITE.getIndex());
        fonteHeader.setFontName("Calibri");

        // Fonte título
        XSSFFont fonteTitulo = wb.createFont();
        fonteTitulo.setBold(true);
        fonteTitulo.setFontHeightInPoints((short) 18);
        fonteTitulo.setColor(IndexedColors.WHITE.getIndex());
        fonteTitulo.setFontName("Calibri");

        // Branco
        estiloBranco = wb.createCellStyle();
        estiloBranco.setFont(fonteNormal);
        aplicarBordas(estiloBranco);

        // Cinza
        estiloCinza = wb.createCellStyle();
        estiloCinza.setFont(fonteNormal);
        XSSFColor corCinza = new XSSFColor(new java.awt.Color(242, 242, 242), null);
        ((XSSFCellStyle) estiloCinza).setFillForegroundColor(corCinza);
        estiloCinza.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        aplicarBordas(estiloCinza);

        // Header azul
        estiloHeader = wb.createCellStyle();
        estiloHeader.setFont(fonteHeader);
        estiloHeader.setAlignment(HorizontalAlignment.CENTER);
        estiloHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFColor corAzul = new XSSFColor(new java.awt.Color(0, 82, 155), null);
        ((XSSFCellStyle) estiloHeader).setFillForegroundColor(corAzul);
        estiloHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        aplicarBordas(estiloHeader);

        // Título azul
        estiloTitulo = wb.createCellStyle();
        estiloTitulo.setFont(fonteTitulo);
        ((XSSFCellStyle) estiloTitulo).setFillForegroundColor(corAzul);
        estiloTitulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloTitulo.setAlignment(HorizontalAlignment.LEFT);
    }

    private void aplicarBordas(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }
}