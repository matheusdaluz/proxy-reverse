<a class="badge-align" href="https://www.codacy.com/app/matheus.costa100/proxy-reverse?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cavalerosi/proxy-reverse&amp;utm_campaign=Badge_Grade"><img src="https://api.codacy.com/project/badge/Grade/954a6c23e66f484680d9641ecc395a95"/></a>

# proxy-reverse

proxy HTTPS reverso simples (multi certificados) que aceita requests de clientes que suportem esta extensão (apresentando o certificado correto, dependendo do Host Name) e repasse para um dado webserver de backend.

![alt text](https://www.incapsula.com/cdn-guide/wp-content/uploads/sites/7/2018/04/reverse-proxy-02-1.jpg)

# Como usar

1 - importar o projeto no eclipse ou spring tool suite.

2 - dar um update maven no projeto para instalar as biblietas do pom.xml

3 :
	
	- Para rodar no spring tool suite: run as -> spring boot app
	
	- Para rodar no eclipse: spring-boot:run
	
4 - a porta usada é a 8483	

5 - exemplo de requisicão(deverá ter paramentro ?path= ): 

	o certificado da url do parametro será validaddo e se for o certificado permitido vai ser mandado para o backend server,
	se o certificado não for válido ou sem parametro vai aparecer uma mensagem de erro.
	
	https://localhost:8483/?path=https://www.globo.com -> vai para uma aplicacao em node (https://talentosglobo.herokuapp.com/)
	
	https://localhost:8483/?path=https://talentos.globo.com - vai para uma aplicacao em python (https://globo-com.herokuapp.com/)
