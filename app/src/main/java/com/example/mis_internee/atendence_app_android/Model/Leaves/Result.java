
package com.example.mis_internee.atendence_app_android.Model.Leaves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable {

    @SerializedName("TRAN_ID")
    @Expose
    private String tRANID;
    @SerializedName("TRAN_DATE")
    @Expose
    private String tRANDATE;
    @SerializedName("EMP_ID")
    @Expose
    private String eMPID;
    @SerializedName("EMP_NAME")
    @Expose
    private String eMPNAME;
    @SerializedName("SUBMISSION_DATE")
    @Expose
    private Object sUBMISSIONDATE;
    @SerializedName("EMP_CODE")
    @Expose
    private String eMPCODE;
    @SerializedName("DESIGNATION")
    @Expose
    private String dESIGNATION;
    @SerializedName("UNIT_CODE")
    @Expose
    private String uNITCODE;
    @SerializedName("UNIT_NAME")
    @Expose
    private Object uNITNAME;
    @SerializedName("NOTIFICATION_DATE")
    @Expose
    private String nOTIFICATIONDATE;
    @SerializedName("NOTIFICATION_TRAN_ID")
    @Expose
    private Object nOTIFICATIONTRANID;
    @SerializedName("NOTIFCAITON_TRAN_TYPE")
    @Expose
    private String nOTIFCAITONTRANTYPE;
    @SerializedName("HOD_ID")
    @Expose
    private String hODID;
    @SerializedName("MANAGER_ID")
    @Expose
    private String mANAGERID;
    @SerializedName("NATURE_OF_LEAVE")
    @Expose
    private String nATUREOFLEAVE;
    @SerializedName("HR_REMARKS")
    @Expose
    private Object hRREMARKS;
    @SerializedName("ATTND_FLAG")
    @Expose
    private Object aTTNDFLAG;
    @SerializedName("STATUS")
    @Expose
    private String sTATUS;
    @SerializedName("HOD_FLAG")
    @Expose
    private String hODFLAG;
    @SerializedName("HOD_REMARKS")
    @Expose
    private Object hODREMARKS;
    @SerializedName("HR_FLAG")
    @Expose
    private Object hRFLAG;
    @SerializedName("REMARKS")
    @Expose
    private String rEMARKS;
    @SerializedName("CREATED_BY")
    @Expose
    private String cREATEDBY;
    @SerializedName("CREATED_AT")
    @Expose
    private String cREATEDAT;
    @SerializedName("UPDATED_BY")
    @Expose
    private String uPDATEDBY;
    @SerializedName("UPDATED_AT")
    @Expose
    private Object uPDATEDAT;
    @SerializedName("LAST_DAY")
    @Expose
    private Object lASTDAY;
    @SerializedName("TYPE_ID")
    @Expose
    private Object tYPEID;
    @SerializedName("LAST_STATUS")
    @Expose
    private Object lASTSTATUS;
    @SerializedName("REGION_ID")
    @Expose
    private Object rEGIONID;
    @SerializedName("LEAVE_APP_STATUS")
    @Expose
    private Object lEAVEAPPSTATUS;
    @SerializedName("LEAVE_DESC")
    @Expose
    private String lEAVEDESC;
    @SerializedName("LEAVE_ID")
    @Expose
    private String lEAVEID;
    @SerializedName("CONSUMED")
    @Expose
    private String cONSUMED;
    @SerializedName("DATE_FROM")
    @Expose
    private String dATEFROM;
    @SerializedName("DATE_TO")
    @Expose
    private String dATETO;
    @SerializedName("TIME_OUT")
    @Expose
    private String tIMEOUT;
    @SerializedName("TIME_IN")
    @Expose
    private String tIMEIN;
    @SerializedName("INTERFACE")
    @Expose
    private String iNTERFACE;
    @SerializedName("DEP_NAME")
    @Expose
    private Object dEPNAME;
    @SerializedName("DEP_ID")
    @Expose
    private String dEPID;
    @SerializedName("IS_HOD")
    @Expose
    private String iSHOD;
    @SerializedName("USER_IMG")
    @Expose
    private Object uSERIMG;

    public String getTRANID() {
        return tRANID;
    }

    public void setTRANID(String tRANID) {
        this.tRANID = tRANID;
    }

    public String getTRANDATE() {
        return tRANDATE;
    }

    public void setTRANDATE(String tRANDATE) {
        this.tRANDATE = tRANDATE;
    }

    public String getEMPID() {
        return eMPID;
    }

    public void setEMPID(String eMPID) {
        this.eMPID = eMPID;
    }

    public String getEMPNAME() {
        return eMPNAME;
    }

    public void setEMPNAME(String eMPNAME) {
        this.eMPNAME = eMPNAME;
    }

    public Object getSUBMISSIONDATE() {
        return sUBMISSIONDATE;
    }

    public void setSUBMISSIONDATE(Object sUBMISSIONDATE) {
        this.sUBMISSIONDATE = sUBMISSIONDATE;
    }

    public String getEMPCODE() {
        return eMPCODE;
    }

    public void setEMPCODE(String eMPCODE) {
        this.eMPCODE = eMPCODE;
    }

    public String getDESIGNATION() {
        return dESIGNATION;
    }

    public void setDESIGNATION(String dESIGNATION) {
        this.dESIGNATION = dESIGNATION;
    }

    public String getUNITCODE() {
        return uNITCODE;
    }

    public void setUNITCODE(String uNITCODE) {
        this.uNITCODE = uNITCODE;
    }

    public Object getUNITNAME() {
        return uNITNAME;
    }

    public void setUNITNAME(Object uNITNAME) {
        this.uNITNAME = uNITNAME;
    }

    public String getNOTIFICATIONDATE() {
        return nOTIFICATIONDATE;
    }

    public void setNOTIFICATIONDATE(String nOTIFICATIONDATE) {
        this.nOTIFICATIONDATE = nOTIFICATIONDATE;
    }

    public Object getNOTIFICATIONTRANID() {
        return nOTIFICATIONTRANID;
    }

    public void setNOTIFICATIONTRANID(Object nOTIFICATIONTRANID) {
        this.nOTIFICATIONTRANID = nOTIFICATIONTRANID;
    }

    public String getNOTIFCAITONTRANTYPE() {
        return nOTIFCAITONTRANTYPE;
    }

    public void setNOTIFCAITONTRANTYPE(String nOTIFCAITONTRANTYPE) {
        this.nOTIFCAITONTRANTYPE = nOTIFCAITONTRANTYPE;
    }

    public String getHODID() {
        return hODID;
    }

    public void setHODID(String hODID) {
        this.hODID = hODID;
    }

    public String getMANAGERID() {
        return mANAGERID;
    }

    public void setMANAGERID(String mANAGERID) {
        this.mANAGERID = mANAGERID;
    }

    public String getNATUREOFLEAVE() {
        return nATUREOFLEAVE;
    }

    public void setNATUREOFLEAVE(String nATUREOFLEAVE) {
        this.nATUREOFLEAVE = nATUREOFLEAVE;
    }

    public Object getHRREMARKS() {
        return hRREMARKS;
    }

    public void setHRREMARKS(Object hRREMARKS) {
        this.hRREMARKS = hRREMARKS;
    }

    public Object getATTNDFLAG() {
        return aTTNDFLAG;
    }

    public void setATTNDFLAG(Object aTTNDFLAG) {
        this.aTTNDFLAG = aTTNDFLAG;
    }

    public String getSTATUS() {
        return sTATUS;
    }

    public void setSTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }

    public String getHODFLAG() {
        return hODFLAG;
    }

    public void setHODFLAG(String hODFLAG) {
        this.hODFLAG = hODFLAG;
    }

    public Object getHODREMARKS() {
        return hODREMARKS;
    }

    public void setHODREMARKS(Object hODREMARKS) {
        this.hODREMARKS = hODREMARKS;
    }

    public Object getHRFLAG() {
        return hRFLAG;
    }

    public void setHRFLAG(Object hRFLAG) {
        this.hRFLAG = hRFLAG;
    }

    public String getREMARKS() {
        return rEMARKS;
    }

    public void setREMARKS(String rEMARKS) {
        this.rEMARKS = rEMARKS;
    }

    public String getCREATEDBY() {
        return cREATEDBY;
    }

    public void setCREATEDBY(String cREATEDBY) {
        this.cREATEDBY = cREATEDBY;
    }

    public String getCREATEDAT() {
        return cREATEDAT;
    }

    public void setCREATEDAT(String cREATEDAT) {
        this.cREATEDAT = cREATEDAT;
    }

    public String getUPDATEDBY() {
        return uPDATEDBY;
    }

    public void setUPDATEDBY(String uPDATEDBY) {
        this.uPDATEDBY = uPDATEDBY;
    }

    public Object getUPDATEDAT() {
        return uPDATEDAT;
    }

    public void setUPDATEDAT(Object uPDATEDAT) {
        this.uPDATEDAT = uPDATEDAT;
    }

    public Object getLASTDAY() {
        return lASTDAY;
    }

    public void setLASTDAY(Object lASTDAY) {
        this.lASTDAY = lASTDAY;
    }

    public Object getTYPEID() {
        return tYPEID;
    }

    public void setTYPEID(Object tYPEID) {
        this.tYPEID = tYPEID;
    }

    public Object getLASTSTATUS() {
        return lASTSTATUS;
    }

    public void setLASTSTATUS(Object lASTSTATUS) {
        this.lASTSTATUS = lASTSTATUS;
    }

    public Object getREGIONID() {
        return rEGIONID;
    }

    public void setREGIONID(Object rEGIONID) {
        this.rEGIONID = rEGIONID;
    }

    public Object getLEAVEAPPSTATUS() {
        return lEAVEAPPSTATUS;
    }

    public void setLEAVEAPPSTATUS(Object lEAVEAPPSTATUS) {
        this.lEAVEAPPSTATUS = lEAVEAPPSTATUS;
    }

    public String getLEAVEDESC() {
        return lEAVEDESC;
    }

    public void setLEAVEDESC(String lEAVEDESC) {
        this.lEAVEDESC = lEAVEDESC;
    }

    public String getLEAVEID() {
        return lEAVEID;
    }

    public void setLEAVEID(String lEAVEID) {
        this.lEAVEID = lEAVEID;
    }

    public String getCONSUMED() {
        return cONSUMED;
    }

    public void setCONSUMED(String cONSUMED) {
        this.cONSUMED = cONSUMED;
    }

    public String getDATEFROM() {
        return dATEFROM;
    }

    public void setDATEFROM(String dATEFROM) {
        this.dATEFROM = dATEFROM;
    }

    public String getDATETO() {
        return dATETO;
    }

    public void setDATETO(String dATETO) {
        this.dATETO = dATETO;
    }

    public String getTIMEOUT() {
        return tIMEOUT;
    }

    public void setTIMEOUT(String tIMEOUT) {
        this.tIMEOUT = tIMEOUT;
    }

    public String getTIMEIN() {
        return tIMEIN;
    }

    public void setTIMEIN(String tIMEIN) {
        this.tIMEIN = tIMEIN;
    }

    public String getINTERFACE() {
        return iNTERFACE;
    }

    public void setINTERFACE(String iNTERFACE) {
        this.iNTERFACE = iNTERFACE;
    }

    public Object getDEPNAME() {
        return dEPNAME;
    }

    public void setDEPNAME(Object dEPNAME) {
        this.dEPNAME = dEPNAME;
    }

    public String getDEPID() {
        return dEPID;
    }

    public void setDEPID(String dEPID) {
        this.dEPID = dEPID;
    }

    public String getISHOD() {
        return iSHOD;
    }

    public void setISHOD(String iSHOD) {
        this.iSHOD = iSHOD;
    }

    public Object getUSERIMG() {
        return uSERIMG;
    }

    public void setUSERIMG(Object uSERIMG) {
        this.uSERIMG = uSERIMG;
    }

}
