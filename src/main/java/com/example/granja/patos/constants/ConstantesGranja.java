package com.example.granja.patos.constants;

public interface ConstantesGranja {

	String ID_CLIENTE_INVALIDO = "Valor inválido para o campo 'idCliente', Cliente com id %s não encontrado.";
	String ID_VENDEDOR_INVALIDO = "Valor inválido para o campo 'idVendedor', Vendedor com id %s não encontrado.";
	String ID_PATO_INVALIDO = "Valor inválido para o campo 'idPato', Pato com id %s não encontrado.";
	String PATO_NAO_DISPONIVEL = "Pato com id %s não está mais disponível.";
	String INFORMAR_ID_PATO = "Favor informar campo 'idPato'.";
	String INFORMAR_ID_CLIENTE = "Favor informar campo 'idCliente'.";
	String INFORMAR_ID_VENDEDOR = "Favor informar campo 'idVendedor'.";
	String INFORMAR_MATRICULA = "Favor informar campo 'matricula'.";
	String CPF_INVALIDO = "CPF informado não é válido.";
	String INFORMAR_CPF = "Favor informar campo 'cpf' com o valor um cpf válido, informando somente números, sem zeros a esquerda.";
	String CPF_JA_CADASTRADO = "Campo 'cpf' informado cadastrado no sistema anteriormente.";
	String INFORMAR_DESCONTO = "Favor informar campo 'desconto' com o valor true ou false.";
	String NOME_MAIOR_PERMITIDO = "Campo 'nome' com valor maior do que permitido, informe um valor com tamanho máximo de 100 caracteres.";
	String ID_MAE_INVALIDO = "Valor inválido para o campo 'maeId', Pato com id %s não encontrado.";
	String INFORMAR_NOME = "Favor informar campo 'nome'";
	String INFORMAR_STATUS = "Favor informar campo 'status' com o valor de uma das opções: D (Disponível) ou V (Vendido).";
	String VALIDAR_PARAMETROS_REQUISICAO = "Favor validar parametros da requisição";
	String VALOR_INVALIDO = "Valor inválido para o campo 'valor', informe o valor em formato monetário brasileiro (valor em R$) e valor máximo de 99999999.99.";
	String ENVIO_INCORRETO ="Envio incorreto";
	String DADOS_NAO_ENCONTRADOS ="Dados não encontrados";
	String ERRO_INTERNO=  "Erro interno";

}
