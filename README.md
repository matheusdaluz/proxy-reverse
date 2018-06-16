<a class="badge-align" href="https://www.codacy.com/app/matheus.costa100/proxy-reverse?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cavalerosi/proxy-reverse&amp;utm_campaign=Badge_Grade"><img src="https://api.codacy.com/project/badge/Grade/954a6c23e66f484680d9641ecc395a95"/></a>

# proxy-reverse

proxy HTTPS reverso simples (multi certificados) que aceita requests de clientes que suportem esta extensão (apresentando o certificado correto, dependendo do Host Name) e repasse para um dado webserver de backend.

![alt text](https://www.incapsula.com/cdn-guide/wp-content/uploads/sites/7/2018/04/reverse-proxy-02-1.jpg)

# Como usar

1 - Importar o projeto no eclipse ou spring tool suite.

2 - Dar um update maven no projeto para instalar as biblietas do pom.xml

3 : Rodando o projeto.
	
	- Para rodar no spring tool suite: run as -> spring boot app
	
	- Para rodar no eclipse: spring-boot:run
	
4 - A porta usada é a 8483	

5 - Exemplo de requisicão(deverá ter paramentro ?path= ): 

	o certificado da url do parametro será validaddo e se for o certificado permitido vai ser mandado para o backend server,
	se o certificado não for válido ou sem parametro vai aparecer uma mensagem de erro.
	
	https://localhost:8483/?path=https://www.globo.com -> vai para uma aplicacao em node (https://talentosglobo.herokuapp.com/)
	
	https://localhost:8483/?path=https://talentos.globo.com - vai para uma aplicacao em python (https://globo-com.herokuapp.com/)

6 - O KeyStore se encontra dentro do projeto:
		
	Nome do alias: talentos.globo.com
	Data de criação: 15 de jun de 2018
	Tipo de entrada: PrivateKeyEntry
	Comprimento da cadeia de certificados: 1
	Certificado[1]:
	Proprietário: CN=talentosglobo.herokuapp.com, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown
	Emissor: CN=talentosglobo.herokuapp.com, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown
	Número de série: 1a6826b4
	Válido de: Fri Jun 15 15:04:41 BRT 2018 até: Mon Jun 10 15:04:41 BRT 2019
	Fingerprints do certificado:
		 SHA1: B7:F3:82:7F:8D:EA:C4:45:29:E8:D8:8E:26:31:0A:D6:FB:51:40:59
		 SHA256: 46:DB:EB:B6:D8:66:D1:C5:59:60:E3:46:36:13:1C:68:A0:BC:BD:36:5F:21:F2:EB:0C:B4:B1:4E:BA:EF:36:76
	Nome do algoritmo de assinatura: SHA256withRSA
	Algoritmo de Chave Pública do Assunto: Chave RSA de 2048 bits
	Versão: 3

	Extensões: 
	
	#1: ObjectId: 2.5.29.14 Criticality=false
	SubjectKeyIdentifier [
	KeyIdentifier [
	0000: 83 05 91 B4 57 AB 84 3E   98 78 DC 69 73 F6 C1 9B  ....W..>.x.is...
	0010: 44 26 FF 5C                                        D&.\
	]
	]

	Nome do alias: www.globo.com
	Data de criação: 15 de jun de 2018
	Tipo de entrada: PrivateKeyEntry
	Comprimento da cadeia de certificados: 1
	Certificado[1]:
	Proprietário: CN=globo-com.herokuapp.com, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown
	Emissor: CN=globo-com.herokuapp.com, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown
	Número de série: 65fa0190
	Válido de: Fri Jun 15 15:06:19 BRT 2018 até: Mon Jun 10 15:06:19 BRT 2019
	Fingerprints do certificado:
		 SHA1: 30:BC:E0:A6:51:44:6C:E6:55:FB:D3:32:20:C6:93:9D:C2:B3:39:1A
		 SHA256: A7:36:F5:BC:0A:64:E4:FA:A2:A3:30:43:2E:29:DE:DE:98:50:6B:20:87:6B:95:FF:25:30:63:3B:F9:AE:2B:8E
	Nome do algoritmo de assinatura: SHA256withRSA
	Algoritmo de Chave Pública do Assunto: Chave RSA de 2048 bits
	Versão: 3
	
	Extensões: 
	
	#1: ObjectId: 2.5.29.14 Criticality=false
	SubjectKeyIdentifier [
	KeyIdentifier [
	0000: 02 FE 45 7B 80 7B CB E5   A8 D2 26 00 9E B6 D6 0F  ..E.......&.....
	0010: A1 98 11 53                                        ...S
	]
	]
	