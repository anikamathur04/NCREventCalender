package com.oracle.maf.sample.application;

import java.io.File;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class DBConnectionFactory
{
  public DBConnectionFactory()
  {
    super();
  }

  protected static Connection conn = null;

  public static Connection getConnection()
    throws Exception
  {
    String dbPassword = "ebspassword";
    if (conn == null)
    {
      try
      {
        // create a database connection
        String Dir =
          AdfmfJavaUtilities.getDirectoryPathRoot(AdfmfJavaUtilities.ApplicationDirectory);
        String connStr = "jdbc:sqlite:" + Dir + "/ebs.db";

        File dbFile = new File(Dir, "ebs.db");
        if (dbFile.exists())
        {
          System.out.println("##### DBConnectionFactory:getConnection - database already created ####");
          conn =
            new SQLite.JDBCDataSource(connStr).getConnection(null,
                                                             dbPassword);
          //                    conn = new SQLite.JDBCDataSource(connStr).getConnection();
        }
        else
        {
          System.out.println("##### DBConnectionFactory:getConnection - database does not exist, creating it ####");

          conn = new SQLite.JDBCDataSource(connStr).getConnection();

          // encrypt the database
          AdfmfJavaUtilities.encryptDatabase(conn, dbPassword);
        }

      }
      catch (SQLException e)
      {
        // if the error message is "out of memory",
        // it probably means no database file is found
        System.err.println(e.getMessage());
      }
    }
    return conn;
  }

}
