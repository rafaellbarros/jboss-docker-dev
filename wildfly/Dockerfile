FROM docker.io/jboss/wildfly:25.0.0.Final

# Adicionar usuário admin
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

# Copiar módulos do driver JDBC
COPY modules/ /opt/jboss/wildfly/modules/

# Configurar variáveis de ambiente para depuração
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,address=*:8787,server=y,suspend=n -Xms256m -Xmx1024m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.net.preferIPv4Stack=true"

# Expor a porta de depuração
EXPOSE 8787

# Configuração padrão (substitui o CMD original para incluir as JAVA_OPTS)
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]