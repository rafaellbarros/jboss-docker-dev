package com.github.rafaellbarros.resource.api;

public final class ApiConstants {
    // Códigos de status
    public static final String OK_CODE = "200";
    public static final String CREATED_CODE = "201";
    public static final String NO_CONTENT_CODE = "204";
    public static final String BAD_REQUEST_CODE = "400";
    public static final String UNAUTHORIZED_CODE = "401";
    public static final String FORBIDDEN_CODE = "403";
    public static final String NOT_FOUND_CODE = "404";
    public static final String INTERNAL_SERVER_ERROR_CODE = "500";

    // Descrições padrão
    public static final String OK_DESCRIPTION = "Operação realizada com sucesso";
    public static final String CREATED_DESCRIPTION = "Recurso criado com sucesso";
    public static final String NO_CONTENT_DESCRIPTION = "Recurso removido com sucesso";
    public static final String BAD_REQUEST_DESCRIPTION = "Requisição inválida - verifique os parâmetros fornecidos";
    public static final String UNAUTHORIZED_DESCRIPTION = "Autenticação necessária para acessar este recurso";
    public static final String FORBIDDEN_DESCRIPTION = "Acesso não permitido para este usuário";
    public static final String NOT_FOUND_DESCRIPTION = "Recurso não encontrado";
    public static final String INTERNAL_SERVER_ERROR_DESCRIPTION = "Erro interno no servidor";

    // Descrições específicas
    public static final String LIST_SUCCESS = "Lista de recursos retornada com sucesso";
    public static final String SEARCH_SUCCESS = "Pesquisa de recursos realizada com sucesso";
    public static final String RESOURCE_FOUND = "Recurso encontrado e retornado";
    public static final String RESOURCE_UPDATED = "Recurso atualizado com sucesso";
    public static final String RESOURCE_DELETED = "Recurso removido com sucesso";
    public static final String VALIDATION_ERROR = "Erro de validação nos dados fornecidos";

    private ApiConstants() {
        throw new UnsupportedOperationException("Esta é uma classe de constantes e não pode ser instanciada");
    }
}