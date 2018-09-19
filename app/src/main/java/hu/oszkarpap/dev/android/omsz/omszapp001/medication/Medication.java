package hu.oszkarpap.dev.android.omsz.omszapp001.medication;

public class Medication {
    private String MedName;
    private String MedAgent;
    private String MedPack;
    private String MedIndication;
    private String MedContra;
    private String MedAdultDose;
    private String MedChildDose;

    public String getMedName() {
        return MedName;
    }

    public String getMedAgent() {
        return MedAgent;
    }

    public String getMedPack() {
        return MedPack;
    }

    public String getMedIndication() {
        return MedIndication;
    }

    public String getMedContra() {
        return MedContra;
    }

    public String getMedAdultDose() {
        return MedAdultDose;
    }

    public String getMedChildDose() {
        return MedChildDose;
    }

    public Medication(String medName, String medAgent, String medPack, String medIndication, String medContra, String medAdultDose, String medChildDose) {
        MedName = medName;
        MedAgent = medAgent;
        MedPack = medPack;
        MedIndication = medIndication;
        MedContra = medContra;
        MedAdultDose = medAdultDose;
        MedChildDose = medChildDose;


    }
}