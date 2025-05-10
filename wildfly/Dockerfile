FROM docker.io/jboss/wildfly:25.0.0.Final

USER root

# Instalar dependências úteis para troubleshooting
RUN yum install -y procps-ng vim && \
    yum clean all && \
    rm -rf /var/cache/yum

# Configurar diretórios e permissões
RUN mkdir -p /opt/jboss/wildfly/standalone/deployments && \
    chown -R jboss:jboss /opt/jboss/wildfly && \
    chmod -R 755 /opt/jboss/wildfly/standalone/configuration && \
    chmod -R 775 /opt/jboss/wildfly/standalone/deployments

# Adicionar usuário admin
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

# Copiar módulos do driver JDBC
COPY --chown=jboss:jboss modules/ /opt/jboss/wildfly/modules/

# Configurar variáveis de ambiente para depuração
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,address=*:8787,server=y,suspend=n -Xms256m -Xmx1024m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.net.preferIPv4Stack=true -Djboss.management.native.management-interface.skip-persistence=true"

# Configurar tempo de espera para bancos de dados
ENV CONNECTION_CHECK_TIMEOUT=60

# Expor portas necessárias
EXPOSE 8080 9990 8787

# Health check para verificar se o WildFly está pronto
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/ || exit 1

# Configuração padrão com tratamento de sinais melhorado
USER jboss
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-Djboss.modules.system.pkgs=org.jboss.byteman", "-Djava.awt.headless=true"]