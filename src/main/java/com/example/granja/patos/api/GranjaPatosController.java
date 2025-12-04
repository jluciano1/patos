package com.example.granja.patos.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
import com.example.granja.patos.exceptions.GranjaHttpException;
import com.example.granja.patos.service.GranjaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@SuppressWarnings({"unchecked", "rawtypes"})
@RestController
@Validated
@RequestMapping("/granja")
public class GranjaPatosController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GranjaPatosController.class);
    
    private final GranjaService service;
	
    public GranjaPatosController(GranjaService service) {
        this.service = service;
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleMissingServletRequestParameterException(HttpMessageNotReadableException ex) {
		Map<String, String> errors = new HashMap<>();
		String errorMessage = ConstantesGranja.VALIDAR_PARAMETROS_REQUISICAO;
		errors.put("mensagem", errorMessage);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
		Map<String, String> errors = new HashMap<>();
		String parameterName = ex.getParameterName();
		String errorMessage = "O parâmetro '" + parameterName + "' é obrigatório e não foi fornecido.";
		errors.put("mensagem", errorMessage);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {

        Map<String, String> errors = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            sb.append(violation.getPropertyPath())
              .append(": ")
              .append(violation.getMessage())
              .append(". ");
        }

        errors.put("mensagem", sb.toString().trim());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

	@Operation(summary = "Cadastro de patos com detalhes como nome e mãe.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pato cadastrado",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = PatoResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = ConstantesGranja.ENVIO_INCORRETO,
					content = @Content),
			@ApiResponse(responseCode = "404", description = ConstantesGranja.DADOS_NAO_ENCONTRADOS,
					content = @Content) ,
			@ApiResponse(responseCode = "500", description = ConstantesGranja.ERRO_INTERNO,
					content = @Content) })
	@PostMapping("/cadastrarPato")
	public ResponseEntity<?> cadastrarPato(@RequestBody @Valid PatoRequestDTO pato){
		try {
			PatoResponseDTO response = service.cadastrarPato(pato);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(GranjaHttpException e) {
			LOGGER.error(e.getMessage());
			Map<String, String> errors = new HashMap<>();
			String errorMessage =e.getLocalizedMessage();
			errors.put("mensagem", errorMessage);

			return new ResponseEntity<>(errors, e.getStatusCode());
		}
		catch (Exception e){
			LOGGER.error(e.getMessage());
			Map<String, String> errors = new HashMap<>();
			String errorMessage = e.getLocalizedMessage();
			errors.put("mensagem", errorMessage);
			return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Cadastro de clientes da granja.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente cadastrado",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = PatoResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = ConstantesGranja.ENVIO_INCORRETO,
					content = @Content),
			@ApiResponse(responseCode = "404", description = ConstantesGranja.DADOS_NAO_ENCONTRADOS,
					content = @Content) ,
			@ApiResponse(responseCode = "500", description = ConstantesGranja.ERRO_INTERNO,
					content = @Content) })
	@PostMapping("/cadastrarCliente")
	public ResponseEntity<?> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO cliente){
		try {
			ClienteResponseDTO response = service.cadastrarCliente(cliente);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(GranjaHttpException e) {
			LOGGER.error(e.getMessage());
			Map<String, String> errors = new HashMap<>();
			String errorMessage =e.getLocalizedMessage();
			errors.put("mensagem", errorMessage);

			return new ResponseEntity<>(errors, e.getStatusCode());
		}
		catch (Exception e){
			LOGGER.error(e.getMessage());
			Map<String, String> errors = new HashMap<>();
			String errorMessage = e.getLocalizedMessage();
			errors.put("mensagem", errorMessage);
			return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Cadastro de vendedores da granja.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Vendedor cadastrado",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = PatoResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = ConstantesGranja.ENVIO_INCORRETO,
					content = @Content),
			@ApiResponse(responseCode = "404", description = ConstantesGranja.DADOS_NAO_ENCONTRADOS,
					content = @Content) ,
			@ApiResponse(responseCode = "500", description = ConstantesGranja.ERRO_INTERNO,
					content = @Content) })
	@PostMapping("/cadastrarVendedor")
	public ResponseEntity<?> cadastrarVendedor(@RequestBody @Valid VendedorRequestDTO vendedor){
		try {
			VendedorResponseDTO response = service.cadastrarVendedor(vendedor);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(GranjaHttpException e) {
			LOGGER.error(e.getMessage());
			Map<String, String> errors = new HashMap<>();
			String errorMessage =e.getLocalizedMessage();
			errors.put("mensagem", errorMessage);

			return new ResponseEntity<>(errors, e.getStatusCode());
		}
		catch (Exception e){
			LOGGER.error(e.getMessage());
			Map<String, String> errors = new HashMap<>();
			String errorMessage = e.getLocalizedMessage();
			errors.put("mensagem", errorMessage);
			return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Registrar vendas da granja.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Venda registrada",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = PatoResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = ConstantesGranja.ENVIO_INCORRETO,
					content = @Content),
			@ApiResponse(responseCode = "404", description = ConstantesGranja.DADOS_NAO_ENCONTRADOS,
					content = @Content) ,
			@ApiResponse(responseCode = "500", description = ConstantesGranja.ERRO_INTERNO,
					content = @Content) })
	@PostMapping("/registrarVenda")
	public ResponseEntity<?> registrarVenda(@RequestBody @Valid VendaRequestDTO venda){
		try {
			VendaResponseDTO response = service.cadastrarVenda(venda);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(GranjaHttpException e) {
			LOGGER.error(e.getMessage());
			Map<String, String> errors = new HashMap<>();
			String errorMessage =e.getLocalizedMessage();
			errors.put("mensagem", errorMessage);

			return new ResponseEntity<>(errors, e.getStatusCode());
		}
		catch (Exception e){
			LOGGER.error(e.getMessage());
			Map<String, String> errors = new HashMap<>();
			String errorMessage = e.getLocalizedMessage();
			errors.put("mensagem", errorMessage);
			return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listarPatos")
    public ResponseEntity<List<PatoVendaDetalheDTOResponse>> listarPatos() {
		try {
			List<PatoVendaDetalheDTOResponse> patos = service.listPatosVendidos();
	        return ResponseEntity.ok(patos);
		}catch(GranjaHttpException e) {
			LOGGER.error(e.getMessage());
			Map<String, String> errors = new HashMap<>();
			String errorMessage =e.getLocalizedMessage();
			errors.put("mensagem", errorMessage);
			return new ResponseEntity(errors, e.getStatusCode());
		}
		catch (Exception e){
			LOGGER.error(e.getMessage());
			Map<String, String> errors = new HashMap<>();
			String errorMessage = e.getLocalizedMessage();
			errors.put("mensagem", errorMessage);
			return new ResponseEntity(errors, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@GetMapping("/ranking")
	public ResponseEntity<List<VendedorVendaDTOResponse>> ranking(
			@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Date inicio,
	        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Date fim,
	        @RequestParam String statusPato,
	        @RequestParam(defaultValue = "quantidade") String ordenarPor,
	        @RequestParam Integer ranking
	) {
	    return ResponseEntity.ok(service.listVendedoresRanking(inicio, fim, statusPato, ranking, ordenarPor));
	}
	
	@GetMapping("/relatorio")
	public ResponseEntity<byte[]> gerarRelatorio(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") String inicio,
	        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") String fim) throws IOException {

	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    XSSFWorkbook workbook;
			try {
				workbook = service.gerarRelatorioVendaExcel(inicio, fim);
				workbook.write(out);
			    workbook.close();
	
			    byte[] bytes = out.toByteArray();
	
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			    headers.setContentDisposition(
			            ContentDisposition.attachment().filename("relatorio.xlsx").build()
			    );
	
			    return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
			}catch(GranjaHttpException e) {
				LOGGER.error(e.getMessage());
				Map<String, String> errors = new HashMap<>();
				String errorMessage =e.getLocalizedMessage();
				errors.put("mensagem", errorMessage);
	
				return new ResponseEntity(errors, e.getStatusCode());
			}
			catch (Exception e){
				LOGGER.error(e.getMessage());
				Map<String, String> errors = new HashMap<>();
				String errorMessage = e.getLocalizedMessage();
				errors.put("mensagem", errorMessage);
				return new ResponseEntity(errors, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	    
}/*
	@GetMapping("/relatorio")
	public ResponseEntity<byte[]> gerarRelatorioVenda(
	        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") String inicio,
	        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") String fim) throws Exception {

	    byte[] excel = service.(inicio, fim);

	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_venda.xlsx")
	            .contentType(MediaType.parseMediaType(
	                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
	            .body(excel);
	}*/
//}