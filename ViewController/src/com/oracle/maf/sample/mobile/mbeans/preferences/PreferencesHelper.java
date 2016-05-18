package com.oracle.maf.sample.mobile.mbeans.preferences;

import oracle.adf.model.datacontrols.device.DeviceManagerFactory;

import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.framework.FeatureContext;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;


/**
 * Managed bean class that support the task flow decision process of whether to navigate to the demo start view and
 * whether or not features show as enabled or not on the feature detail views.
 * <p>
 * Sample supports iOS and Android.
 *
 * @author Frank Nimphius
 * @copyright Copyright (c) 2015 Oracle. All rights reserved.
 */
public class PreferencesHelper {
    
    public PreferencesHelper() {
        super();
    }
    
    /**
     * For this dample to work, application preferences need to be configured to access Oracle MCS. If no preferences
     * are are configured, navigation will be to a data input view, to the demo start otherwise
     * @return true/false
     */
    public boolean isHasPreferences(){
        boolean hasPreference = false;
        
        //get mandatory preferences        
        String mobileBackendId  = (String) AdfmfJavaUtilities.getELValue("#{preferenceScope.application.mcs.mobileBackendId}");
        String mobileBackendUrl = (String) AdfmfJavaUtilities.getELValue("#{preferenceScope.application.mcs.mobileBackendURL}");
        String mbeAnonymousKey = (String) AdfmfJavaUtilities.getELValue("#{preferenceScope.application.mcs.mbeAnonymousKey}");
        String appKeyAndroid = (String) AdfmfJavaUtilities.getELValue("#{preferenceScope.application.mcs.mobileBackendApplicationKeyAndroid}");
        String appKeyiOS = (String) AdfmfJavaUtilities.getELValue("#{preferenceScope.application.mcs.mobileBackendApplicationKeyiOS}");
        
        //Rule = backendId && backendUrl && ((appKeyiOS && OS == IOS) || appKeyAndroid OS == Android)) exist ==> true. False otherwise        
        hasPreference = (mobileBackendId != null && !mobileBackendId.isEmpty()) && 
                        (mobileBackendUrl!= null && !mobileBackendUrl.isEmpty())&& 
                        (mbeAnonymousKey!= null && !mbeAnonymousKey.isEmpty());
        
        if(hasPreference == true){
            if(isIOS()){
                hasPreference = hasPreference && (appKeyiOS!=null && !appKeyiOS.isEmpty())? true : false;
            }
            else{
                hasPreference = hasPreference && (appKeyAndroid!=null && !appKeyAndroid.isEmpty())? true : false;
            }
        }        
        return hasPreference;
    }
    
    
    /**
     * <b> Push PRE-REQUISITES </b>
     * <p>
     * The GCM Sender ID is required for applications to receive push notifications from Apple. The registration is upon
     * application start. Without this information no messages can be received by this sample. You get the sender Id and 
     * a project key when registering the application on the Google DCM site.
     * <p>
     * For receiving push notification from Apple you need to 
     *  - register the application with Apple
     *  - change the application bundle Id in the deployment profile to match the ID used when registering the application 
     *  - Upon deployment, sign the application with the p12 certificate (certificate should not require a password) obtained 
     *  - when registering the application. 
     *  <p>
     *  <b> Enable push notifications in MCS</b>
     *  For MCS to send push messages,  ensure a client application is created for Android and iOS. The client application for
     *  Android requires the application package name and application name as the ID, along with the project key and the sender
     *  Id you obtained from Google.
     *  <p>
     *  For iOS, define the ID as the application bundled Id and upload the p12 certificate  
     *  <p>
     *  <p>
     *  For more information see the MAF and MCS product documentation
     * @return
     */
    public boolean isPushEnabled(){
        boolean pushEnabled = false;
        //get mandatory preferences
        String gcmSenderId = (String) AdfmfJavaUtilities.getELValue("#{preferenceScope.application.push.gcmSenderId}");
        String iOsApplicationBundleId = (String) AdfmfJavaUtilities.getELValue("#{preferenceScope.application.push.iosApplicationBundleId}");
        //Rule 1= if OS == Android && gcmSenderId == null or empty ==> false. True otherwise. 
        //Rule 2= if OS == iOS && applicationBundleId == null or empty ==> false. True otherwise.
        
        if(!isIOS()){
            pushEnabled = (gcmSenderId!=null && !gcmSenderId.isEmpty())? true : false;
        }
        else if(isIOS()){
             pushEnabled = (iOsApplicationBundleId!=null && !iOsApplicationBundleId.isEmpty())? true : false;
        }
        return pushEnabled;
    }
        
    /**
     * Check if this application runs on iOS and if, return true. If not return false
     * @return
     */
    public boolean isIOS(){
        return DeviceManagerFactory.getDeviceManager().getOs().toUpperCase() == "IOS";
    }
        
    //satisfy the Java properties API
    public void setHasPreferences(boolean b){};
    public void setPushEnabled(boolean b){};
    public void setIOS(boolean b){}

    /**
     * When the boolean swicth "Enable Receiving Push Notifications" in the preference screen is changed 
     * to true, show an alert to inform user that notifications require a restart of the application.
     * @param valueChangeEvent
     */
    public void onEnablePushBooleanSwitch(ValueChangeEvent valueChangeEvent) {
        
        //alert user to restart applicatio for push to work
        if(((Boolean)valueChangeEvent.getNewValue()).booleanValue() == true){
           Object errorMsg = AdfmfContainerUtilities.invokeContainerJavaScriptFunction(FeatureContext.getCurrentFeatureId(),"popupUtilsShowPopup", new Object[] {"_popShowId" });
        }
    }
}
