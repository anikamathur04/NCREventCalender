package com.oracle.maf.sample.application;

import com.oracle.maf.sample.application.push.PushConstants;
import com.oracle.maf.sample.application.push.PushEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.sql.Connection;
import java.sql.Statement;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import oracle.adfmf.application.LifeCycleListener;
import oracle.adfmf.application.PushNotificationConfig;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.event.EventSource;
import oracle.adfmf.framework.event.EventSourceFactory;
import oracle.adfmf.util.Utility;
import oracle.adfmf.util.logging.Trace;

/**
 * The application life cycle listener provides the basic structure for developers needing
 * to include their own functionality during the different stages of the application life
 * cycle.  Please note that there is <b>no user</b> associated with any of the following
 * methods.
 *
 * Common examples of functionality that might be added:
 *
 * start:
 *   1. determine if the application has updates
 *   2. determine if there already exists a local application database image
 *   3. setup any one time state for the application
 *
 * activate:
 *   1. read any application cache stores and re-populate state (if needed)
 *   2. establish/re-establish any database connections and cursors
 *   3. process any pending web-service requests
 *   4. obtain any required resources
 *
 * deactivate:
 *   1. write any restorable state to an application cache store
 *   2. close any database cursors and connections
 *   3. defer any pending web-service requests
 *   4. release held resources
 *
 * stop:
 *   1. logoff any remote services
 *
 * NOTE:
 * 1. In order for the system to recognize an application life cycle listener
 *    it must be registered in the maf-application.xml file.
 * 2. Application assemblers must implement this interface if they would like to
 *    receive notification of application start, hibernation, and application resume.
 *    If a secure web service is needed, you will need to do this from your 'default'
 *    feature where you will have an associated user.
 *
 * @see oracle.adfmf.application.LifeCycleListener
 *
 * @author Frank Nimphius
 * @copyright Copyright (c) 2015 Oracle. All rights reserved.
 */
public class LifeCycleListenerImpl
  implements LifeCycleListener, PushNotificationConfig
{
  public LifeCycleListenerImpl()
  {
  }


  public void start()
  {
    /* *** REGISTER FOR PUSH NOTIFICATION *** */

    //There is a setting in the preferences that needs to be switched to true, indicating that all prerequisites
    //for registering the app to Apple and Google push providers are provided: Google Sender Id, application name
    //and package, and the Apple bundle Id
    boolean pushEnabledForApplication =
      (Boolean) AdfmfJavaUtilities.getELValue(PushConstants.PUSH_ENABLED);

    //ensure push is enabled only if it is enabled for this application
    if (pushEnabledForApplication == true)
    {
      Utility.ApplicationLogger.logp(Level.FINE,
                                     this.getClass().getSimpleName(),
                                     "LifeCycleListener::start()",
                                     "Push is enabled for application");
      EventSource evtSource =
        EventSourceFactory.getEventSource(EventSourceFactory.NATIVE_PUSH_NOTIFICATION_REMOTE_EVENT_SOURCE_NAME);
      evtSource.addListener(new PushEventListener());
    }
    // Add code here...
    try
    {
      System.out.println("hello world");
      initializeDatabaseFromScript();
    }
    catch (Exception e)
    {
      e.printStackTrace(System.out);
      Trace.log(Utility.FrameworkLogger, Level.SEVERE,
                LifeCycleListenerImpl.class, "start", e);
    }

  }


  /**
   * Both Android and iOS require the device to contact the push provider (GCM or APNs) to receive a special string
   * that uniquely identifies a specific app on a specific device. In GCM this is called the device ID, while APNs
   * calls it a device token. The application need to send this identifier to Oracle MCS (registration) to be able
   * to receive notifications (see the FiFTechnicianDC for the registration code)
   */
  public long getNotificationStyle()
  {

    // Allow for alerts and badging and sounds
    return PushNotificationConfig.NOTIFICATION_STYLE_ALERT |
           PushNotificationConfig.NOTIFICATION_STYLE_BADGE |
           PushNotificationConfig.NOTIFICATION_STYLE_SOUND;
  }


  /**
   * For Google this returns the sender ID that is configured in the application preferences.
   */
  public String getSourceAuthorizationId()
  {
    // Return the GCM sender id. This information is available in the preferences. The gcmSenderId is an
    //application level preferences configured in the maf-application.xml configuration. A string value
    //is needed even if the deployment is to iOS only. In teh latter case, use a random value as shown
    //below

    String senderId =
      (String) AdfmfJavaUtilities.getELValue("#{preferenceScope.application.push.gcmSenderId}");
    if (senderId == null)
    {
      senderId = "no-sender-id-found-in-configuration";
    }
    return senderId;
  }


  public void stop()
  {
  }

  /**
   * COPY the documents to upload to the MCS Storage API into the application download folder. The code  below is copied
   * from the MAF DeviceDemo shipped within the publisSamples.zip file.
   *
   * Note: Lifecycle operations are blocking calls, which means that the application holds-on until all logic in the lifecycle
   * is executed. This can add negative to the application startup time. This sample needs to copy files from the classpath
   * to a directory that the application can access the files easily from. The file copy operation is performed in a separate
   * thread to avoid blocking application start.
   *
   */
  public void activate()
  {

    //FILE copy is an asynchronous task in this sample to not block application startup
    Runnable fileCopyJob = new Runnable()
    {
      public void run()
      {
        String files[] =
        {
          "doc.docx", "mp4.mp4", "pdf.pdf", "ppt.pptx", "xls.xlsx",
          "png.png"
        };
        for (int x = 0; x < files.length; x++)
        {

          String targetFileNameAndPath =
            AdfmfJavaUtilities.getDirectoryPathRoot(AdfmfJavaUtilities.DownloadDirectory) +
            "/" + files[x];
          File targetFile = new File(targetFileNameAndPath);
          // If the file already exists, just continue to the next one
          if (targetFile != null && targetFile.exists())
          {
            continue;
          }

          ClassLoader cl = Thread.currentThread().getContextClassLoader();
          InputStream sourceSream =
            cl.getResourceAsStream(".adf/META-INF/" + files[x]);

          try
          {
            Files.copy(sourceSream, targetFile.toPath(),
                       StandardCopyOption.REPLACE_EXISTING);
          }
          catch (IOException e)
          {
            Utility.ApplicationLogger.logp(Level.FINE,
                                           this.getClass().getSimpleName(),
                                           "LifeCycleListener::activate()",
                                           "Could not copy file " +
                                           files[x]);
            Utility.ApplicationLogger.logp(Level.FINE,
                                           this.getClass().getSimpleName(),
                                           "LifeCycleListener::activate()",
                                           "Error is: " + e.getMessage());
          }
        }
      }
    };


    //Execute runnable
    ExecutorService executor = Executors.newFixedThreadPool(2);
    executor.execute(fileCopyJob);
    executor.shutdown();


  }

  public void deactivate()
  {
  }

  private static void initializeDatabaseFromScript()
    throws Exception
  {
    InputStream scriptStream = null;
    Connection conn = null;
    String dbPassword = "ebspassword";
    try
    {
      // ApplicationDirectory returns the private read-write sandbox area
      // of the mobile device's file system that this application can access.
      // This is where the database is created
      String docRoot =
        AdfmfJavaUtilities.getDirectoryPathRoot(AdfmfJavaUtilities.ApplicationDirectory);
      String dbName = docRoot + "/ebs.db";

      // Verify whether or not the database exists.
      // If it does, then it has already been initialized
      // and no furher actions are required
      File dbFile = new File(dbName);
      if (dbFile.exists())
      {
        System.out.println("##### database already created ####");
        return;
      }

      System.out.println("######## initializing database #####");
      Trace.log(Utility.FrameworkLogger, Level.INFO,
                LifeCycleListenerImpl.class, "initializeDatabaseFromScript",
                "initializing database");

      // If the database does not exist, a new database is automatically
      // created when the SQLite JDBC connection is created
      conn = DBConnectionFactory.getConnection();

      // encrypt the database
      //           AdfmfJavaUtilities.encryptDatabase(conn, dbPassword);

      // To improve performance, the statements are executed
      // one at a time in the context of a single transaction
      conn.setAutoCommit(false);

      // Since the SQL script has been packaged as a resource within
      // the application, the getResourceAsStream method is used
      scriptStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/initialize.sql");
      BufferedReader scriptReader =
        new BufferedReader(new InputStreamReader(scriptStream));
      String nextLine;
      StringBuffer nextStatement = new StringBuffer();

      // The while loop iterates over all the lines in the SQL script,
      // assembling them into valid SQL statements and executing them as
      // a terminating semicolon is encountered
      Statement stmt = conn.createStatement();
      while ((nextLine = scriptReader.readLine()) != null)
      {
        // Skipping blank lines, comments, and COMMIT statements
        if (nextLine.startsWith("REM") || nextLine.startsWith("COMMIT") ||
            nextLine.length() < 1)
          continue;
        nextStatement.append(nextLine);
        if (nextLine.endsWith(";"))
        {
          stmt.execute(nextStatement.toString());
          nextStatement = new StringBuffer();
        }
      }
      conn.commit();
      Trace.log(Utility.FrameworkLogger, Level.INFO,
                LifeCycleListenerImpl.class, "initializeDatabaseFromScript",
                "database initialized successfully");

    }
    catch (Exception e)
    {
      Trace.log(Utility.FrameworkLogger, Level.SEVERE,
                LifeCycleListenerImpl.class, "initializeDatabaseFromScript",
                e);

      e.printStackTrace(System.out);
      throw new RuntimeException(e.getMessage(), e);
    }
    //       finally {
    //          if (conn != null)
    //             conn.close();
    //       }
  }
}
