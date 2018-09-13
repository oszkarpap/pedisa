package hu.oszkarpap.dev.android.omsz.omszapp001.medication;

public class Medication {
    private String MedName;
    private String MedEmail;
    private String MedPhone;

    public Medication(String medName, String medEmail, String medPhone) {
        MedName = medName;
        MedEmail = medEmail;
        MedPhone = medPhone;
    }

    public String getMedName() {
        return MedName;
    }

    public void setMedName(String medName) {
        MedName = medName;
    }

    public String getMedEmail() {
        return MedEmail;
    }

    public void setMedEmail(String medEmail) {
        MedEmail = medEmail;
    }

    public String getMedPhone() {
        return MedPhone;
    }

    public void setMedPhone(String medPhone) {
        MedPhone = medPhone;
    }
}