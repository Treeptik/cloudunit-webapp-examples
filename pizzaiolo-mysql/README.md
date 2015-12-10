#Pizzashop project

## Use case
This WebApp based on spring 3 and hibernate frameworks can be used to test simple app with tomcat and postgresql or mysql databases.
It tests the link between database and servers, system and java properties reading into the container.

## Configuration

There are two ways to configure environment variables :

1.   add them into **servlet-context.xml** with **<context:property-placeholder/>** intruction and use **${MAVARIABLE}** syntax
2.   use the syntax :  **#{systemProperties.MAVARIABLE}** , which is used by default by spring.


## Database initialization :

1. Create the database (or add a database mysql or postgres module on cloudunit). The database name is the application name.
2. The postgres configuration is set by default. If you want to use mysql datasource, Uncomment the following instruction : **jdbc:initialize-database** and the datasource bean for mysql in **servlet-context.xml**. Then, in the same file, the hibernate dialect value for mysql. And comment the same code bloc for postgres.
3. Run **mvn clean package** to generate the web archive
4. Deploy the war.

## Install and use cloudunit-maven-plugin

1. Clone and install cloudunit-maven-plugin into your local m2 repository :

```bash
git clone git@github.com:Treeptik/cloudunit-maven-plugin.git
cd cloudunit-maven-plugin
mvn clean install
```

2. Change your credentials (or/and the configuration) for cloudunit-maven plugin into your pizzashot project POM

2. Package and deploy automatically through the plugin :

```bash
cd pizzaiolo
mvn clean package cloudunit:deploy -e
```



