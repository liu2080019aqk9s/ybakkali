package info.h417.Model.Database;

import java.sql.*;

public class Database {

    private static Database instance = null;
    private String url;


    public static Database getInstance() {
        return getInstance(DatabaseInfo.PATH_DB);
    }

    public static Database getInstance(String pathSqldbtest) {
        if(instance == null){
            instance = new Database(pathSqldbtest);
        }
        return instance;
    }

    private Database(String pathSqldb) {
        this.url = "jdbc:sqlite:" + pathSqldb;
        try {
            createNewDatabase();
            createTable();
        } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void createTable() throws SQLException {

    }

    private void createNewDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(url);
        if (conn != null) {
            conn.getMetaData();
            conn.close();
        }
    }

    /**
     * Retourne la connection dans la base de données
     *
     * @return la connection dans la base de données
     * @throws SQLException Exception liée à une requete SQL
     */
    public Connection connect() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(url);
        return conn;
    }

    /**
     * Cette méthode exécute des requête SQL
     *
     * @param sql  Requete Sql
     * @param data un ensemble de données
     * @throws SQLException Erreur SQL si la requete est incorrecte
     */
    public void executeSql(QueryInterface sql, Object... data) throws SQLException {
        Connection conn = this.connect();
        PreparedStatement pstmt = getPreparedStatement(sql, conn, data);
        pstmt.executeUpdate();
        conn.close();
    }

    /**
     * Recoit une requete à utiliser
     *
     * @param sql  Interface des requetes
     * @param conn Connexion utilisateur
     * @param data données
     * @return Requete SQL préparée
     * @throws SQLException Erreur de requete sql
     */
    public PreparedStatement getPreparedStatement(QueryInterface sql, Connection conn, Object[] data) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql.getQuery());
        setParams(pstmt, data);
        return pstmt;
    }

    /**
     * Cette méthode permet d'executer une requête sql ayant un retour
     *
     * @param sql  Requete Sql
     * @param conn Connection de la requête
     * @param data un ensemble de données
     * @return ResultSet ensemble de résultats
     * @throws SQLException Exception liée à une requete SQL
     */
    public ResultSet executeSelectQuery(QueryInterface sql, Connection conn, Object... data) throws SQLException {
        PreparedStatement pstmt = getPreparedStatement(sql, conn, data);
        return pstmt.executeQuery();
    }

    /**
     * Place les paramètres
     *
     * @param pstmt requete préparée
     * @param data  données
     * @throws SQLException exception liée à une requete SQL
     */
    private void setParams(PreparedStatement pstmt, Object[] data) throws SQLException {
        for (int i = 0; i < data.length; ++i) {
            pstmt.setObject(i + 1, data[i]);
        }
    }


}