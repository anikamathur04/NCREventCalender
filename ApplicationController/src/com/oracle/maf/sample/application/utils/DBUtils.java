package com.oracle.maf.sample.application.utils;


import com.oracle.maf.sample.application.DBConnectionFactory;

import com.oracle.maf.sample.application.pojo.SummaryObject;

import java.util.logging.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import oracle.adfmf.util.Utility;
import oracle.adfmf.util.logging.Trace;


public class DBUtils
{
  public DBUtils()
  {
    super();
  }

  public static Connection getConnection()
  {
    Connection connection = null;
    try
    {
      connection = DBConnectionFactory.getConnection();

      // To improve performance, the statements are executed
      // one at a time in the context of a single transaction
      connection.setAutoCommit(false);

      return connection;
    }
    catch (Exception e)
    {
      Trace.log(Utility.FrameworkLogger, Level.SEVERE, DBUtils.class,
                "getConnection", e);

      e.printStackTrace(System.out);
      throw new RuntimeException(e.getMessage(), e);

    }
  }

  public static void closeConnection(Connection connection)
  {
    //        try
    //        {
    //            if (connection != null)
    //            {
    //                connection.close();
    //                connection = null;
    //            }
    //        }
    //        catch (SQLException e)
    //        {
    //            // do nothing
    //        }
  }

  public static SummaryObject[] getAllWfSummaryInfo()
  {
    Connection connection = null;
    Statement statement = null;
    String sql =
      "SELECT NOTIFICATION_ID,CONTEXT,FROM_USER ,TO_USER,SUBJECT,LANGUAGE,BEGIN_DATE,DUE_DATE ,STATUS,PRIORITY,PRIORITY_F,RECIPIENT_ROLE,END_DATE ,TYPE,MORE_INFO_ROLE ,FROM_ROLE ,MESSAGE_TYPE,MESSAGE_NAME,MAIL_STATUS ,ORIGINAL_RECIPIENT,SUBTYPE FROM WF_SUMMARY;";
    SummaryObject wfSummary;
    List allInformation = new ArrayList();
    SummaryObject[] informationArray;

    try
    {
      connection = DBUtils.getConnection();
      statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);

      int size = resultSet.getFetchSize();
      while (resultSet.next())
      {
        wfSummary = new SummaryObject();

        wfSummary.setNotificationId(resultSet.getString("NOTIFICATION_Id"));
        wfSummary.setContext(resultSet.getString("CONTEXT"));
        wfSummary.setFromUser(resultSet.getString("FROM_USER "));
        wfSummary.setToUser(resultSet.getString("TO_USER"));
        wfSummary.setSubject(resultSet.getString("SUBJECT"));
        wfSummary.setLanguage(resultSet.getString("LANGUAGE"));
        wfSummary.setBeginDate(resultSet.getString("BEGIN_DATE"));
        wfSummary.setDueDate(resultSet.getString("DUE_DATE "));
        wfSummary.setStatus(resultSet.getString("STATUS"));
        wfSummary.setPriority(resultSet.getString("PRIORITY"));
        wfSummary.setPriorityF(resultSet.getString("PRIORITY_F"));
        wfSummary.setRecipientRole(resultSet.getString("RECIPIENT_ROLE"));
        wfSummary.setEndDate(resultSet.getString("END_DATE "));
        wfSummary.setType(resultSet.getString("TYPE"));
        wfSummary.setMoreInfoRole(resultSet.getString("MORE_INFO_ROLE "));
        wfSummary.setFromRole(resultSet.getString("FROM_ROLE "));
        wfSummary.setMessageType(resultSet.getString("MESSAGE_TYPE"));
        wfSummary.setMessageName(resultSet.getString("MESSAGE_NAME"));
        wfSummary.setMailStatus(resultSet.getString("MAIL_STATUS "));
        wfSummary.setOriginalRecipient(resultSet.getString("ORIGINAL_RECIPIENT"));
        wfSummary.setSubType(resultSet.getString("SUBTYPE"));

        allInformation.add(wfSummary);
      }
      informationArray = new SummaryObject[allInformation.size()];
      informationArray =
        (SummaryObject[]) allInformation.toArray(informationArray);

      return informationArray;
    }
    catch (Exception e)
    {
      Trace.log(Utility.FrameworkLogger, Level.SEVERE, DBUtils.class,
                "getInformation", e);

      e.printStackTrace(System.out);
      throw new RuntimeException(e.getMessage(), e);

    }
    finally
    {
      closeConnection(connection);
    }
  }

  public static boolean deleteWfSummary()
  {
    Connection connection = null;
    PreparedStatement statement = null;
    String sql = "DELETE FROM WF_SUMMARY;";

    try
    {
      connection = DBUtils.getConnection();
      statement = connection.prepareStatement(sql);
      boolean result = statement.execute();
      connection.commit();
      return result;
    }
    catch (Exception e)
    {
      Trace.log(Utility.FrameworkLogger, Level.SEVERE, DBUtils.class,
                "deleteWfSummary", e);

      e.printStackTrace(System.out);
      throw new RuntimeException(e.getMessage(), e);

    }
    finally
    {
      closeConnection(connection);
    }
  }

  public static void insertWfSummary(SummaryObject summaryObject)
  {
    Connection connection = null;
    PreparedStatement statement = null;
    String sql = "INSERT INTO WF_SUMMARY (NOTIFICATION_ID, CONTEXT, FROM_USER , TO_USER, SUBJECT, LANGUAGE, BEGIN_DATE, DUE_DATE , STATUS, PRIORITY, PRIORITY_F, RECIPIENT_ROLE, END_DATE , TYPE, MORE_INFO_ROLE , FROM_ROLE , MESSAGE_TYPE, MESSAGE_NAME, MAIL_STATUS , ORIGINAL_RECIPIENT, SUBTYPE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    try
    {
      int indx = 1;
      connection = DBUtils.getConnection();
      statement = connection.prepareStatement(sql);
      statement.setString(indx++, cleanString(summaryObject.getNotificationId()));
      statement.setString(indx++, cleanString(summaryObject.getContext()));
      statement.setString(indx++, cleanString(summaryObject.getFromUser()));
      statement.setString(indx++, cleanString(summaryObject.getToUser()));
      statement.setString(indx++, cleanString(summaryObject.getSubject()));
      statement.setString(indx++, cleanString(summaryObject.getLanguage()));
      statement.setString(indx++, cleanString(summaryObject.getBeginDate()));
      statement.setString(indx++, cleanString(summaryObject.getDueDate() ));
      statement.setString(indx++, cleanString(summaryObject.getStatus()));
      statement.setString(indx++, cleanString(summaryObject.getPriority()));
      statement.setString(indx++, cleanString(summaryObject.getPriorityF()));
      statement.setString(indx++, cleanString(summaryObject.getRecipientRole()));
      statement.setString(indx++, cleanString(summaryObject.getEndDate()));
      statement.setString(indx++, cleanString(summaryObject.getType()));
      statement.setString(indx++, cleanString(summaryObject.getMoreInfoRole()));
      statement.setString(indx++, cleanString(summaryObject.getFromRole()));
      statement.setString(indx++, cleanString(summaryObject.getMessageType()));
      statement.setString(indx++, cleanString(summaryObject.getMessageName()));
      statement.setString(indx++, cleanString(summaryObject.getMailStatus()));
      statement.setString(indx++, cleanString(summaryObject.getOriginalRecipient()));
      statement.setString(indx++, cleanString(summaryObject.getSubType()));
      boolean result = statement.execute();
      connection.commit();
    }
    catch (Exception e)
    {
      Trace.log(Utility.FrameworkLogger, Level.SEVERE, DBUtils.class,
                "insertWfSummary", e);

      e.printStackTrace(System.out);
      throw new RuntimeException(e.getMessage(), e);
    }
    finally
    {
      closeConnection(connection);
    }
  }
  
  private static String cleanString(String s)
  {
    String newString = s;
    if (s.equalsIgnoreCase("{\"@nil\":\"true\"}"))
    {
      newString = "";
    }
    return newString;
  }
  public static SummaryObject getWfSummaryInfo(String notificationId)
  {
    Connection connection = null;
    PreparedStatement statement = null;
    String sql =
      "SELECT NOTIFICATION_ID,CONTEXT,FROM_USER ,TO_USER,SUBJECT,LANGUAGE,BEGIN_DATE,DUE_DATE ,STATUS,PRIORITY,PRIORITY_F,RECIPIENT_ROLE,END_DATE ,TYPE,MORE_INFO_ROLE ,FROM_ROLE ,MESSAGE_TYPE,MESSAGE_NAME,MAIL_STATUS ,ORIGINAL_RECIPIENT,SUBTYPE FROM WF_SUMMARY WHERE NOTIFICATION_ID=?;";
    SummaryObject wfSummary = null;

    try
    {
      connection = DBUtils.getConnection();
      statement = connection.prepareStatement(sql);
      statement.setString(1, notificationId);
      ResultSet resultSet = statement.executeQuery();

      int size = resultSet.getFetchSize();
      while (resultSet.next())
      {
        wfSummary = new SummaryObject();

        wfSummary.setNotificationId(resultSet.getString("NOTIFICATION_ID"));
        wfSummary.setContext(resultSet.getString("CONTEXT"));
        wfSummary.setFromUser(resultSet.getString("FROM_USER"));
        wfSummary.setToUser(resultSet.getString("TO_USER"));
        wfSummary.setSubject(resultSet.getString("SUBJECT"));
        wfSummary.setLanguage(resultSet.getString("LANGUAGE"));
        wfSummary.setBeginDate(resultSet.getString("BEGIN_DATE"));
        wfSummary.setDueDate(resultSet.getString("DUE_DATE"));
        wfSummary.setStatus(resultSet.getString("STATUS"));
        wfSummary.setPriority(resultSet.getString("PRIORITY"));
        wfSummary.setPriorityF(resultSet.getString("PRIORITY_F"));
        wfSummary.setRecipientRole(resultSet.getString("RECIPIENT_ROLE"));
        wfSummary.setEndDate(resultSet.getString("END_DATE"));
        wfSummary.setType(resultSet.getString("TYPE"));
        wfSummary.setMoreInfoRole(resultSet.getString("MORE_INFO_ROLE"));
        wfSummary.setFromRole(resultSet.getString("FROM_ROLE"));
        wfSummary.setMessageType(resultSet.getString("MESSAGE_TYPE"));
        wfSummary.setMessageName(resultSet.getString("MESSAGE_NAME"));
        wfSummary.setMailStatus(resultSet.getString("MAIL_STATUS"));
        wfSummary.setOriginalRecipient(resultSet.getString("ORIGINAL_RECIPIENT"));
        wfSummary.setSubType(resultSet.getString("SUBTYPE"));
      }

      return wfSummary;
    }
    catch (Exception e)
    {
      Trace.log(Utility.FrameworkLogger, Level.SEVERE, DBUtils.class,
                "getWfSummaryInfo", e);

      e.printStackTrace(System.out);
      throw new RuntimeException(e.getMessage(), e);

    }
    finally
    {
      closeConnection(connection);
    }
  }


}
